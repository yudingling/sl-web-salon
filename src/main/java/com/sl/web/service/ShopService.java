package com.sl.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;

import com.sl.web.mapper.SlShopMapper;
import com.sl.web.mapper.SlShopServiceMapper;
import com.sl.web.mapper.SlUserMapper;
import com.sl.web.mapper.SlUserShopMapper;
import com.sl.web.model.ShopInfo;
import com.sl.web.model.SigninResult;
import com.sl.web.model.UserType;
import com.sl.web.model.db.SlShop;
import com.sl.web.model.db.SlShopService;
import com.sl.web.model.db.SlUser;
import com.sl.web.model.db.SlUserShop;
import com.sl.web.model.result.ApiPageResult;
import com.sl.web.util.Common;
import com.sl.web.util.DateUtil;

@Service
public class ShopService {
	@Autowired
	private SlShopMapper shopMapper;
	@Autowired
	private SlShopServiceMapper shopServiceMapper;
	@Autowired
	private SlUserShopMapper userShopMapper;
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
	
	public ApiPageResult<ShopInfo> getList(int pageNum, int pageSize, String bdId, String name){
		int startIndex = this.getStartIndex(pageNum, pageSize);
		int size = this.getSize(pageSize);
		
		if(StringUtils.isEmpty(bdId)){
			bdId = null;
		}
		if(StringUtils.isEmpty(name)){
			name = null;
		}else{
			name = "%" + name + "%";
		}
		
		List<ShopInfo> shops = this.shopMapper.getShops(startIndex, size, bdId, name);
		
		List<ShopInfo> result = CollectionUtils.isNotEmpty(shops) ? this.setShopServices(shops) : new ArrayList<>();
		
		Long totalSize = this.shopMapper.getShopCount(bdId, name);
		
		return new ApiPageResult<>(totalSize, result);
	}
	
	private List<ShopInfo> setShopServices(List<ShopInfo> shops){
		Map<Long, ShopInfo> shopMap = new HashMap<>();
		
		shops.forEach(shop -> {
			shopMap.put(shop.getShopId(), shop);
		});
		
		Example example = new Example(SlShopService.class);
	    example.createCriteria().andIn("shopId", shopMap.keySet()).andEqualTo("spsAvailable", 1);
	    
	    List<SlShopService> sslist = this.shopServiceMapper.selectByExample(example);
	    if(CollectionUtils.isNotEmpty(sslist)){
	    	sslist.forEach(ss -> {
	    		ShopInfo si = shopMap.get(ss.getShopId());
	    		si.setServiceTime(ss.getSpsStm(), ss.getSpsEtm());
	    	});
	    }
	    
	    return shops;
	}
	
	private ShopInfo setShopService(ShopInfo shop){
		Example example = new Example(SlShopService.class);
	    example.createCriteria().andEqualTo("shopId", shop.getShopId()).andEqualTo("spsAvailable", 1);
	    
	    List<SlShopService> sslist = this.shopServiceMapper.selectByExample(example);
	    if(CollectionUtils.isNotEmpty(sslist)){
	    	SlShopService ss = sslist.get(0);
	    	
	    	shop.setServiceTime(ss.getSpsStm(), ss.getSpsEtm());
	    }
	    
	    return shop;
	}
	
	public ShopInfo getByWorker(SigninResult auth){
		ShopInfo shop = null;
		
		Example example = new Example(SlUserShop.class);
	    example.createCriteria().andEqualTo("uId", auth.getUserId());
	    
	    List<SlUserShop> users = this.userShopMapper.selectByExample(example);
	    if(CollectionUtils.isNotEmpty(users)){
	    	shop = this.shopMapper.getShop(users.get(0).getShopId());
	    	
	    	if(shop != null){
	    		this.setShopService(shop);
	    		
	    		//clear privacy information
	    		shop.setShopWechatpayId(null);
	    		shop.setShopUserPwd(null);
	    		shop.setSpsStm(null);
	    	}
	    }
		
	    return shop;
	}
	
	public ShopInfo get(Long shopId){
		ShopInfo shop = this.shopMapper.getShop(shopId);
		
		return shop != null ? this.setShopService(shop) : null;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SigninResult auth, ShopInfo shop){
		Long ts = System.currentTimeMillis();
		
		SlShop sp = new SlShop(
				this.commonService.nextId(), 
				shop.getShopNm(), 
				shop.getBdId(), 
				1, 
				shop.getShopLgtd(), 
				shop.getShopLttd(), 
				shop.getShopLocation(), 
				shop.getShopPhone(), 
				shop.getShopWechatpayId(), 
				shop.getShopStm(), 
				shop.getShopEtm(), 
				ts, 
				ts);
		
		if(this.shopMapper.insert(sp) == 1){
			return this.saveNewShopService(shop, sp.getShopId()) && this.createShopUser(sp, shop.getShopUserPhone(), shop.getShopUserPwd());
			
		}else{
			return false;
		}
	}
	
	private boolean saveNewShopService(ShopInfo shop, Long shopId){
		Long ts = System.currentTimeMillis();
		
		Long stm = DateUtil.toDate(shop.getSpsStm(), "yyyy-MM-dd").getTime();
		Long etm = DateUtil.toDate(shop.getSpsEtm(), "yyyy-MM-dd").getTime();
		etm = DateUtil.addDay(etm, 1);
		
		SlShopService ss = new SlShopService(
				this.commonService.nextId(), 
				shopId, 
				stm, 
				etm, 
				1, 
				ts, 
				ts);
		
		
		return this.shopServiceMapper.insert(ss) == 1;
	}
	
	private boolean createShopUser(SlShop shop, String phone, String pwd){
		Long ts = System.currentTimeMillis();
		
		List<Long> ids = this.commonService.nextId(2);
		
		SlUser user = new SlUser(
				ids.get(0), 
				shop.getBdId(), 
				shop.getShopNm(), 
				phone, 
				phone, 
				null, 
				Common.md5(pwd), 
				UserType.SHOP.toString(), 
				1, 
				0, 
				null, 
				null, 
				ts, 
				ts);
		
		if(this.userMapper.insert(user) == 1){
			SlUserShop sb = new SlUserShop(
					ids.get(1), 
					shop.getShopId(), 
					user.getuId(), 
					user.getRoleId(), 
					ts, 
					ts);
			
			return this.userShopMapper.insert(sb) == 1;
			
		}else{
			return false;
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean update(SigninResult auth, ShopInfo shop){
		Long ts = System.currentTimeMillis();
		
		SlShop upt = new SlShop();
		upt.setShopId(shop.getShopId());
		upt.setShopNm(shop.getShopNm());
		upt.setShopLgtd(shop.getShopLgtd());
		upt.setShopLttd(shop.getShopLttd());
		upt.setShopLocation(shop.getShopLocation());
		upt.setShopPhone(shop.getShopPhone());
		upt.setShopWechatpayId(shop.getShopWechatpayId());
		upt.setShopStm(shop.getShopStm());
		upt.setShopEtm(shop.getShopEtm());
		upt.setUptTs(ts);
		
		if(this.shopMapper.updateByPrimaryKeySelective(upt) == 1){
			Example example = new Example(SlShopService.class);
		    example.createCriteria().andEqualTo("shopId", shop.getShopId());
			
			this.shopServiceMapper.deleteByExample(example);
			
			return this.saveNewShopService(shop, shop.getShopId());
			
		}else{
			return false;
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean updateByWorker(SigninResult auth, ShopInfo shop){
		Long ts = System.currentTimeMillis();
		
		SlShop upt = new SlShop();
		upt.setShopId(shop.getShopId());
		upt.setShopNm(shop.getShopNm());
		upt.setShopLgtd(shop.getShopLgtd());
		upt.setShopLttd(shop.getShopLttd());
		upt.setShopLocation(shop.getShopLocation());
		upt.setShopPhone(shop.getShopPhone());
		upt.setShopStm(shop.getShopStm());
		upt.setShopEtm(shop.getShopEtm());
		upt.setUptTs(ts);
		
		return this.shopMapper.updateByPrimaryKeySelective(upt) == 1;
	}
}
