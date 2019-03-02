package com.sl.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.sl.web.mapper.SlBarberShieldMapper;
import com.sl.web.mapper.SlUserShopMapper;
import com.sl.web.model.BarberShieldInfo;
import com.sl.web.model.SigninResult;
import com.sl.web.model.db.SlBarberShield;
import com.sl.web.model.db.SlUserShop;
import com.sl.web.model.result.ApiPageResult;
import com.sl.web.util.DateUtil;

@Service
public class BarberShieldService {
	@Autowired
	private SlUserShopMapper userShopMapper;
	@Autowired
	private SlBarberShieldMapper shieldMapper;
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
	
	public ApiPageResult<BarberShieldInfo> getList(int pageNum, int pageSize, Long uId, SigninResult auth){
		int startIndex = this.getStartIndex(pageNum, pageSize);
		int size = this.getSize(pageSize);
		
		Long shopId = this.getShopId(auth);
		
		if(shopId != null){			
			List<BarberShieldInfo> shields = this.shieldMapper.getShields(startIndex, size, uId, shopId);
			
			return new ApiPageResult<>(this.shieldMapper.getShieldCount(shopId, uId), shields);
			
		}else{
			return new ApiPageResult<>(0l, new ArrayList<>());
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SigninResult auth, BarberShieldInfo shield){
		Long shopId = this.getShopId(auth);
		
		if(shopId == null){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		SlBarberShield sb = new SlBarberShield(
				this.commonService.nextId(), 
				shield.getuId(), 
				DateUtil.toDate(shield.getBbtStmStr(), "yyyy-MM-dd HH:mm").getTime(), 
				DateUtil.toDate(shield.getBbtEtmStr(), "yyyy-MM-dd HH:mm").getTime(), 
				shield.getBbtDesc(), 
				ts, 
				ts);
		
		return this.shieldMapper.insert(sb) == 1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(SigninResult auth, Set<Long> ids){
		Long shopId = this.getShopId(auth);
		
		if(shopId == null){
			return false;
		}
		
		Example example = new Example(SlBarberShield.class);
	    example.createCriteria().andIn("bbtId", ids);
	    
	    this.shieldMapper.deleteByExample(example);
	    
	    return true;
	}
}
