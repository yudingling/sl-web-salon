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
import com.sl.web.model.db.SlProductType;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiObjectResult;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.ProductTypeService;

@RestController
@RequestMapping("/authed/brandMgr/productType")
public class ProductTypeController {
	@Autowired
	private ProductTypeService ptService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ApiResult getList(@RequestParam int pageNum, @RequestParam int pageSize,
			FilterHttpServletRequest request, HttpServletResponse response){
		
		return this.ptService.getList(pageNum, pageSize, request.getAuth());
	}
	
	@RequestMapping(value = "/item", method = RequestMethod.GET)
	public ApiResult item(@RequestParam Long pdtpId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.notNull(pdtpId, "pdId should not be null or empty");
		
		SlProductType info = this.ptService.get(request.getAuth(), pdtpId);
		
		return info != null ? new ApiObjectResult<>(info) : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApiResult add(SlProductType pt, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.ptService.add(request.getAuth(), pt);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ApiResult edit(SlProductType pt, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.ptService.update(request.getAuth(), pt);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApiResult edit(@RequestParam String pdtpId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.hasText(pdtpId, "ids should not be null or empty");
		
		Set<Long> ids = new HashSet<>();
		String[] uIds = pdtpId.split(",");
		for(String tmp : uIds){
			if(tmp.length() > 0){
				ids.add(Long.parseLong(tmp));
			}
		}
		Assert.notEmpty(ids, "ids should not be null or empty");
		
		boolean ok = this.ptService.delete(request.getAuth(), ids);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
}
