package com.sl.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.web.filter.FilterHttpServletRequest;
import com.sl.web.model.BrandInfo;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiObjectResult;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.BrandService;

@RestController
@RequestMapping("/authed/brand")
public class BrandController {
	@Autowired
	private BrandService brandService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ApiResult getList(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam(required = false) String name,
			FilterHttpServletRequest request, HttpServletResponse response){
		
		return this.brandService.getList(pageNum, pageSize, name);
	}
	
	@RequestMapping(value = "/item", method = RequestMethod.GET)
	public ApiResult item(@RequestParam String bdId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.hasText(bdId, "bdId should not be null or empty");
		
		BrandInfo brandInfo = this.brandService.get(bdId);
		
		return brandInfo != null ? new ApiObjectResult<>(brandInfo) : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApiResult add(BrandInfo brand, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.brandService.add(request.getAuth(), brand);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ApiResult edit(BrandInfo brand, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.brandService.update(request.getAuth(), brand);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
}
