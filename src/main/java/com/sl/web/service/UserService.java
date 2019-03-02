package com.sl.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.sl.web.mapper.SlUserMapper;
import com.sl.web.model.SigninResult;
import com.sl.web.model.UserInfo;
import com.sl.web.model.db.SlUser;
import com.sl.web.util.Common;

@Service
public class UserService {
	@Autowired
	private SlUserMapper slUserMapper;
	@Autowired
	private CommonService commonService;
	
	public UserInfo getUserInfo(SigninResult auth){
		UserInfo info = this.slUserMapper.getUserInfo(auth.getUserId());
		
		this.commonService.setUserIconUrl(info);
		
		return info;
	}
	
	public boolean updateUserInfo(SigninResult auth, UserInfo info){
		if(StringUtils.isEmpty(info.getuNm())){
			return false;
		}
		
		SlUser user = new SlUser();
		user.setuId(auth.getUserId());
		user.setuNm(info.getuNm());
		user.setuIcon(info.getuIcon());
		user.setUptTs(System.currentTimeMillis());
		
		return this.slUserMapper.updateByPrimaryKeySelective(user) == 1;
	}
	
	public boolean updatePwd(SigninResult auth, UserInfo info){
		if(StringUtils.isEmpty(info.getNewPwd()) || !info.getNewPwd().equals(info.getNewPwd2())){
			return false;
		}
		
		SlUser old = this.slUserMapper.selectByPrimaryKey(auth.getUserId());
		if(old != null && Common.md5(info.getuPwd()).equals(old.getuPwd())){
			SlUser upt = new SlUser();
			upt.setuId(auth.getUserId());
			upt.setuPwd(Common.md5(info.getNewPwd()));
			upt.setUptTs(System.currentTimeMillis());
			
			return this.slUserMapper.updateByPrimaryKeySelective(upt) == 1;
			
		}else{
			return false;
		}
	}
}
