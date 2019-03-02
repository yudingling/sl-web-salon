package com.sl.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;

import com.sl.web.mapper.SlShopMapper;
import com.sl.web.mapper.SlUserBarberMapper;
import com.sl.web.mapper.SlUserMapper;
import com.sl.web.mapper.SlUserShopMapper;
import com.sl.web.model.SigninResult;
import com.sl.web.model.UserType;
import com.sl.web.model.db.SlUser;
import com.sl.web.model.db.SlUserBarber;
import com.sl.web.model.db.SlUserShop;
import com.sl.web.model.result.ApiPageResult;
import com.sl.web.util.Common;

@Service
public class BarberService {
	@Autowired
	private SlShopMapper shopMapper;
	@Autowired
	private SlUserShopMapper userShopMapper;
	@Autowired
	private SlUserBarberMapper userBarberMapper;
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
			List<SlUser> workers = this.userMapper.getShopBarbers(startIndex, size, shopId, phone);
			
			return new ApiPageResult<>(this.userMapper.getShopBarberCount(shopId, phone), workers);
			
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
		
		return this.createBarberUser(auth.getBrandId(), shopId, user);
	}
	
	private boolean createBarberUser(String bdId, Long shopId, SlUser user){
		Long ts = System.currentTimeMillis();
		
		List<Long> ids = this.commonService.nextId(2);
		
		SlUser barber = new SlUser(
				ids.get(0), 
				bdId, 
				user.getuNm(), 
				user.getuThirdid(), 
				user.getuThirdid(), 
				null, 
				Common.md5(user.getuPwd()), 
				UserType.BARBER.toString(), 
				1, 
				0, 
				null, 
				null, 
				ts, 
				ts);
		
		try{
			this.userMapper.insert(barber);
			
		}catch(DuplicateKeyException ex){
			barber = this.getUser(bdId, user.getuThirdid());
			
			this.updateUser(barber);
		}
		
		SlUserBarber sb = new SlUserBarber(
				ids.get(1), 
				shopId, 
				barber.getuId(), 
				bdId, 
				ts, 
				ts);
		
		return this.userBarberMapper.insert(sb) == 1;
	}
	
	public SlUser getUser(String bdId, String phone){
		Example example = new Example(SlUser.class);
	    example.createCriteria().andEqualTo("bdId", bdId).andEqualTo("uThirdid", phone);
	    
	    List<SlUser> users = this.userMapper.selectByExample(example);
	    return CollectionUtils.isEmpty(users) ? null : users.get(0);
	}
	
	public void updateUser(SlUser user){
		SlUser upt = new SlUser();
		upt.setuId(user.getuId());
		upt.setuActive(1);
		upt.setuDisabled(0);
		
	    this.userMapper.updateByPrimaryKeySelective(upt);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(SigninResult auth, Set<Long> uIds){
		Long shopId = this.getShopId(auth);
		if(shopId == null){
			return false;
		}
		
		Example example = new Example(SlUserBarber.class);
	    example.createCriteria().andEqualTo("shopId", shopId).andIn("uId", uIds);
	    
	    this.userBarberMapper.deleteByExample(example);
	    return true;
	}
}
