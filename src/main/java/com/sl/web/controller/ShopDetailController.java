package com.sl.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sl.web.filter.FilterHttpServletRequest;
import com.sl.web.model.ShopInfo;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiObjectResult;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.ShopService;

@RestController
@RequestMapping("/authed/shopMgr/detail")
public class ShopDetailController {
	@Autowired
	private ShopService shopService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResult item(FilterHttpServletRequest request, HttpServletResponse response){
		ShopInfo info = this.shopService.getByWorker(request.getAuth());
		
		return info != null ? new ApiObjectResult<>(info) : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ApiResult edit(ShopInfo shop, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.shopService.updateByWorker(request.getAuth(), shop);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
}
