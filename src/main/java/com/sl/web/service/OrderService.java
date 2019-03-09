package com.sl.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.sl.web.mapper.SlOrderEvaluationMapper;
import com.sl.web.mapper.SlOrderMapper;
import com.sl.web.mapper.SlOrderProductMapper;
import com.sl.web.mapper.SlShopMapper;
import com.sl.web.mapper.SlUserMapper;
import com.sl.web.mapper.SlUserShopMapper;
import com.sl.web.model.HistoryOrder;
import com.sl.web.model.OrderInfo;
import com.sl.web.model.OrderProductInfo;
import com.sl.web.model.SigninResult;
import com.sl.web.model.db.SlOrder;
import com.sl.web.model.db.SlOrderEvaluation;
import com.sl.web.model.db.SlUser;
import com.sl.web.model.db.SlUserShop;
import com.sl.web.model.result.ApiArrayResult;
import com.sl.web.model.result.ApiPageResult;

@Service
public class OrderService {
	@Autowired
	private SlShopMapper shopMapper;
	@Autowired
	private SlUserShopMapper userShopMapper;
	@Autowired
	private SlOrderMapper orderMapper;
	@Autowired
	private SlOrderProductMapper orderProductMapper;
	@Autowired
	private SlOrderEvaluationMapper orderEvaluationMapper;
	@Autowired
	private SlUserMapper userMapper;
	@Autowired
	private CommonService commonService;
	
	private int getStartIndex(int pageNum, int pageSize){
		int startIndex = (pageNum - 1) * pageSize;
		if(startIndex < 0){
			startIndex = 0;
		}
		
		return startIndex;
	}
	
	private int getSize(int pageSize){
		return pageSize > 0 ? pageSize : 10;
	}
	
	private Long getShopId(SigninResult auth){
		Example example = new Example(SlUserShop.class);
	    example.createCriteria().andEqualTo("uId", auth.getUserId());
	    
	    List<SlUserShop> users = this.userShopMapper.selectByExample(example);
	    if(CollectionUtils.isNotEmpty(users)){
	    	return users.get(0).getShopId();
	    	
	    }else{
	    	return null;
	    }
	}
	
	public ApiPageResult<HistoryOrder> getList(int pageNum, int pageSize, SigninResult auth, 
			Integer paied, Integer confirmed, Long barberUid, Long projectId, Long stm, Long etm){
		int startIndex = this.getStartIndex(pageNum, pageSize);
		int size = this.getSize(pageSize);
		
		Long shopId = this.getShopId(auth);
		if(shopId != null){
			List<HistoryOrder> orders = this.orderMapper.getHistoryOrders(shopId, paied, confirmed, barberUid, projectId, stm, etm, startIndex, size);
			
			if(CollectionUtils.isNotEmpty(orders)){
				this.setEvaValue(orders);
				this.setOdUserInfo(orders);
			}
			
			return new ApiPageResult<>(this.orderMapper.getHistoryOrderCount(shopId, paied, confirmed, barberUid, projectId, stm, etm), orders);
			
		}else{
			return new ApiPageResult<>(0l, new ArrayList<>());
		}
	}
	
	public ApiArrayResult<OrderInfo> getWaitingConfirmOrders(SigninResult auth){
		Long shopId = this.getShopId(auth);
		if(shopId != null){
			List<OrderInfo> orders = this.orderMapper.getWaitingConfirmOrders(shopId);
			
			if(CollectionUtils.isNotEmpty(orders)){
				this.setOdBarberInfo(orders);
				this.setOdProductInfo(orders);
			}
			
			return new ApiArrayResult<>(orders);
					
		}else{
			return new ApiArrayResult<>(new ArrayList<>());
		}
	}
	
	public ApiArrayResult<OrderInfo> getRunningOrders(SigninResult auth){
		Long shopId = this.getShopId(auth);
		if(shopId != null){
			List<OrderInfo> orders = this.orderMapper.getRunningOrders(shopId);
			
			if(CollectionUtils.isNotEmpty(orders)){
				this.setOdBarberInfo(orders);
				this.setOdProductInfo(orders);
			}
			
			return new ApiArrayResult<>(orders);
					
		}else{
			return new ApiArrayResult<>(new ArrayList<>());
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean confirmOrder(SigninResult auth, Long odId){
		Long shopId = this.getShopId(auth);
		if(shopId != null){
			SlOrder upt = new SlOrder();
			upt.setOdConfirm(1);
			upt.setUptTs(System.currentTimeMillis());
			
			Example example = new Example(SlOrder.class);
		    example.createCriteria().andEqualTo("odId", odId).andEqualTo("odConfirm", 0);
			
			return this.orderMapper.updateByExampleSelective(upt, example) == 1;
					
		}else{
			return false;
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean cancelOrder(SigninResult auth, Long odId){
		Long shopId = this.getShopId(auth);
		if(shopId != null){
			
			SlOrder upt = new SlOrder();
			upt.setOdConfirm(2);
			upt.setUptTs(System.currentTimeMillis());
			
			Example example = new Example(SlOrder.class);
		    example.createCriteria().andEqualTo("odId", odId).andNotEqualTo("odConfirm", 2).andEqualTo("odPaied", 0);
			
			return this.orderMapper.updateByExampleSelective(upt, example) == 1;
					
		}else{
			return false;
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean payedOrderManually(SigninResult auth, Long odId){
		Long shopId = this.getShopId(auth);
		if(shopId != null){
			SlOrder upt = new SlOrder();
			upt.setOdPaied(1);
			upt.setOdPaiedTp("CASH");
			upt.setOdPaiedTs(System.currentTimeMillis());
			upt.setUptTs(System.currentTimeMillis());
			
			Example example = new Example(SlOrder.class);
		    example.createCriteria().andEqualTo("odId", odId).andEqualTo("odConfirm", 1).andEqualTo("odPaied", 0);
			
			return this.orderMapper.updateByExampleSelective(upt, example) == 1;
					
		}else{
			return false;
		}
	}
	
	private void setOdBarberInfo(List<OrderInfo> orders){
		Set<Long> uIds = orders.stream().map(OrderInfo::getOdBarberUid).collect(Collectors.toSet());
		
		Example example = new Example(SlUser.class);
	    example.createCriteria().andIn("uId", uIds);
		
		List<SlUser> barbers = this.userMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(barbers)){
			Map<Long, SlUser> bbMap = new HashMap<>();
			barbers.forEach(bb -> {
				bbMap.put(bb.getuId(), bb);
			});
	    	
	    	for(OrderInfo od : orders){
	    		SlUser tmp = bbMap.get(od.getOdBarberUid());
	    		if(tmp != null){
	    			od.setOdBarberUnm(tmp.getuNm());
	    		}
	    	}
		}
	}
	
	private void setOdProductInfo(List<OrderInfo> orders){
		Set<Long> odIds = orders.stream().map(OrderInfo::getOdId).collect(Collectors.toSet());
		
		List<OrderProductInfo> products = this.orderProductMapper.getOrderProducts(odIds);
		
		if(CollectionUtils.isNotEmpty(products)){
			Map<Long, List<OrderProductInfo>> pdMap = new HashMap<>();
			
			products.forEach(pd -> {
				pdMap.computeIfAbsent(pd.getOdId(), odId -> new ArrayList<>()).add(pd);
			});
			
			for(OrderInfo od : orders){
				od.setProducts(pdMap.get(od.getOdId()));
	    	}
		}
	}
	
	private void setOdUserInfo(List<HistoryOrder> orders){
		Set<Long> uIds = orders.stream().map(HistoryOrder::getOdUid).collect(Collectors.toSet());
		
		Example example = new Example(SlUser.class);
	    example.createCriteria().andIn("uId", uIds);
		
		List<SlUser> barbers = this.userMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(barbers)){
			Map<Long, SlUser> bbMap = new HashMap<>();
			barbers.forEach(bb -> {
				bbMap.put(bb.getuId(), bb);
			});
	    	
	    	for(HistoryOrder od : orders){
	    		SlUser tmp = bbMap.get(od.getOdUid());
	    		if(tmp != null){
	    			od.setOdUnm(tmp.getuNm());
	    			od.setOdUphone(tmp.getuPhone());
	    		}
	    	}
		}
	}
	
	private void setEvaValue(List<HistoryOrder> orders){
		Set<Long> odIds = orders.stream().map(HistoryOrder::getOdId).collect(Collectors.toSet());
		
		Example example = new Example(SlOrderEvaluation.class);
	    example.createCriteria().andIn("odId", odIds);
	    
	    List<SlOrderEvaluation> data = this.orderEvaluationMapper.selectByExample(example);
	    if(CollectionUtils.isNotEmpty(data)){
	    	Map<Long, SlOrderEvaluation> evaMap = new HashMap<>();
	    	
	    	for(SlOrderEvaluation item : data){
	    		SlOrderEvaluation tmp = evaMap.get(item.getOdId());
	    		if(tmp == null || tmp.getCrtTs() < item.getCrtTs()){
	    			evaMap.put(item.getOdId(), item);
	    		}
	    	}
	    	
	    	for(HistoryOrder od : orders){
	    		SlOrderEvaluation tmp = evaMap.get(od.getOdId());
	    		if(tmp != null){
	    			od.setOdEva(tmp.getOdevaVal());
	    			od.setOdEvaDesc(tmp.getOdevaDesc());
	    		}
	    	}
	    }
	}
}
