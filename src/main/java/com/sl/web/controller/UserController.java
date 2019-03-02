package com.sl.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sl.web.filter.FilterHttpServletRequest;
import com.sl.web.model.UserInfo;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiObjectResult;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.UserService;

@RestController
@RequestMapping("/authed/normal/userInfo")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResult get(FilterHttpServletRequest request, HttpServletResponse response){
		UserInfo info = this.userService.getUserInfo(request.getAuth());
		
		return info != null ? new ApiObjectResult<>(info) : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/uptInfo", method = RequestMethod.POST)
	public ApiResult uptInfo(UserInfo user, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.userService.updateUserInfo(request.getAuth(), user);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/uptPwd", method = RequestMethod.POST)
	public ApiResult uptPwd(UserInfo user, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.userService.updatePwd(request.getAuth(), user);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR, "输入有误");
	}
}
