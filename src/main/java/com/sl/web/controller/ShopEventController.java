package com.sl.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.web.filter.FilterHttpServletRequest;
import com.sl.web.model.ShopInfo;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiObjectResult;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.ShopService;

@RestController
@RequestMapping("/authed/shopMgr/event")
public class ShopEventController {
	@Autowired
	private ShopService shopService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ApiResult getList(@RequestParam int pageNum, @RequestParam int pageSize, 
			@RequestParam(required = false) String name, @RequestParam(required = false) String bdId,
			FilterHttpServletRequest request, HttpServletResponse response){
		
		return this.shopService.getList(pageNum, pageSize, bdId, name);
	}
	
	@RequestMapping(value = "/item", method = RequestMethod.GET)
	public ApiResult item(@RequestParam Long shopId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.notNull(shopId, "shopId should not be null or empty");
		
		ShopInfo info = this.shopService.get(shopId);
		
		return info != null ? new ApiObjectResult<>(info) : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApiResult add(ShopInfo shop, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.shopService.add(request.getAuth(), shop);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ApiResult edit(ShopInfo shop, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.shopService.update(request.getAuth(), shop);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
}
