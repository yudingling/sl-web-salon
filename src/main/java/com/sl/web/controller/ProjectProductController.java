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
import com.sl.web.model.ProjectProductInfo;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.ProjectProductService;

@RestController
@RequestMapping("/authed/brandMgr/project/product")
public class ProjectProductController {
	@Autowired
	private ProjectProductService ppService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ApiResult getList(@RequestParam Long pjId, FilterHttpServletRequest request, HttpServletResponse response){
		
		return this.ppService.getList(pjId, request.getAuth());
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApiResult add(ProjectProductInfo pp, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.ppService.add(request.getAuth(), pp);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApiResult edit(@RequestParam String pjpId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.hasText(pjpId, "ids should not be null or empty");
		
		Set<Long> ids = new HashSet<>();
		String[] uIds = pjpId.split(",");
		for(String tmp : uIds){
			if(tmp.length() > 0){
				ids.add(Long.parseLong(tmp));
			}
		}
		Assert.notEmpty(ids, "ids should not be null or empty");
		
		boolean ok = this.ppService.delete(request.getAuth(), ids);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
}
