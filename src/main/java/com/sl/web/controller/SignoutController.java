package com.sl.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.TokenService;

@RestController
@RequestMapping("/api/signout")
public class SignoutController {
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping
	public ApiResult get(HttpServletRequest request, HttpServletResponse response){
		this.tokenService.signout(request, response);
		return ApiResult.success();
	}
}
