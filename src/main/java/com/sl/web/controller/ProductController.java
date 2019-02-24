package com.sl.web.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.web.filter.FilterHttpServletRequest;
import com.sl.web.model.ProductInfo;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiObjectResult;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.ProductService;

@RestController
@RequestMapping("/authed/brandMgr/product")
public class ProductController {
	@Autowired
	private ProductService pdService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ApiResult getList(@RequestParam int pageNum, @RequestParam int pageSize,
			@RequestParam(required = false) String name, @RequestParam(required = false) Long pdtpId, @RequestParam(required = false) Integer enabled,
			FilterHttpServletRequest request, HttpServletResponse response){
		
		return this.pdService.getList(pageNum, pageSize, request.getAuth(), name, pdtpId, enabled);
	}
	
	@RequestMapping(value = "/item", method = RequestMethod.GET)
	public ApiResult item(@RequestParam Long pdId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.notNull(pdId, "pdId should not be null or empty");
		
		ProductInfo info = this.pdService.get(request.getAuth(), pdId);
		
		return info != null ? new ApiObjectResult<>(info) : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApiResult add(ProductInfo product, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.pdService.add(request.getAuth(), product);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ApiResult edit(ProductInfo product, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.pdService.update(request.getAuth(), product);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApiResult edit(@RequestParam String pdId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.hasText(pdId, "ids should not be null or empty");
		
		Set<Long> ids = new HashSet<>();
		String[] uIds = pdId.split(",");
		for(String tmp : uIds){
			if(tmp.length() > 0){
				ids.add(Long.parseLong(tmp));
			}
		}
		Assert.notEmpty(ids, "ids should not be null or empty");
		
		boolean ok = this.pdService.delete(request.getAuth(), ids);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
}
