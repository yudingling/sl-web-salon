package com.sl.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;

import com.sl.web.mapper.SlShopMapper;
import com.sl.web.mapper.SlUserMapper;
import com.sl.web.mapper.SlUserShopMapper;
import com.sl.web.model.SigninResult;
import com.sl.web.model.UserType;
import com.sl.web.model.db.SlUser;
import com.sl.web.model.db.SlUserShop;
import com.sl.web.model.result.ApiPageResult;
import com.sl.web.util.Common;

@Service
public class ShopWorkerService {
	@Autowired
	private SlShopMapper shopMapper;
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
	
	private Long getShopId(SigninResult auth){
		Example example = new Example(SlUserShop.class);
	    example.createCriteria().andEqualTo("uId", auth.getUserId()).andEqualTo("roleId", UserType.SHOP.toString());
	    
	    List<SlUserShop> users = this.userShopMapper.selectByExample(example);
	    if(CollectionUtils.isNotEmpty(users)){
	    	return users.get(0).getShopId();
	    	
	    }else{
	    	return null;
	    }
	}
	
	public ApiPageResult<SlUser> getList(int pageNum, int pageSize, SigninResult auth, String phone){
		int startIndex = this.getStartIndex(pageNum, pageSize);
		int size = this.getSize(pageSize);
		
		if(StringUtils.isEmpty(phone)){
			phone = null;
		}else{
			phone = "%" + phone + "%";
		}
		
		Long shopId = this.getShopId(auth);
		if(shopId != null){
			List<SlUser> workers = this.userMapper.getShopWorkers(startIndex, size, shopId, phone);
			
			return new ApiPageResult<>(this.userMapper.getShopWorkerCount(shopId, phone), workers);
			
		}else{
			return new ApiPageResult<>(0l, new ArrayList<>());
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SigninResult auth, SlUser user){
		Long shopId = this.getShopId(auth);
		if(shopId == null){
			return false;
		}
		
		return this.createShopUser(auth.getBrandId(), shopId, user);
	}
	
	private boolean createShopUser(String bdId, Long shopId, SlUser user){
		Long ts = System.currentTimeMillis();
		
		List<Long> ids = this.commonService.nextId(2);
		
		SlUser worker = new SlUser(
				ids.get(0), 
				bdId, 
				user.getuNm(), 
				user.getuThirdid(), 
				user.getuThirdid(), 
				null, 
				Common.md5(user.getuPwd()), 
				UserType.SHOP_WORKER.toString(), 
				1, 
				0, 
				null, 
				null, 
				ts, 
				ts);
		
		if(this.userMapper.insert(worker) == 1){
			SlUserShop sb = new SlUserShop(
					ids.get(1), 
					shopId, 
					worker.getuId(), 
					worker.getRoleId(), 
					ts, 
					ts);
			
			return this.userShopMapper.insert(sb) == 1;
			
		}else{
			return false;
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(SigninResult auth, Set<Long> uIds){
		Long shopId = this.getShopId(auth);
		if(shopId == null){
			return false;
		}
		
		Example example = new Example(SlUserShop.class);
	    example.createCriteria().andEqualTo("shopId", shopId).andIn("uId", uIds);
	    
	    if(this.userShopMapper.deleteByExample(example) == uIds.size()){
	    	Example example2 = new Example(SlUser.class);
		    example2.createCriteria().andIn("uId", uIds);
		    this.userMapper.deleteByExample(example2);
		    
		    return true;
		    
	    }else{
	    	//fire rollback
	    	throw new RuntimeException("delete user failed");
	    }
	}
}
