package com.sl.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.web.model.SigninResult;
import com.sl.web.model.db.SlUser;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiObjectResult;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.TokenService;

@RestController
@RequestMapping("/signin")
public class SigninController {
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping
	public ApiResult get(@RequestParam Boolean keep, @RequestParam String phoneNumber, @RequestParam String password, 
			HttpServletRequest request, HttpServletResponse response){
		Assert.hasText(phoneNumber, "phoneNumber should not be null or empty");
		Assert.hasText(password, "password should not be null or empty");
		
		SlUser user = this.tokenService.getNormalUser(phoneNumber);
		if(user == null){
			return ApiResult.error(ApiError.USER_UNEXISTS);
		}
		
		SigninResult result = this.tokenService.signin(user, phoneNumber, password, keep, request, response);
		
		if(result != null){
			return new ApiObjectResult<>(result);
			
		}else{
			return ApiResult.error(ApiError.USER_PWD_ERROR);
		}
	}
}
