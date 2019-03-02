package com.sl.web.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.web.filter.FilterHttpServletRequest;
import com.sl.web.model.BarberProjectInfo;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiObjectResult;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.BarberProjectService;

@RestController
@RequestMapping("/authed/shopMgr/barberPrj")
public class BarberProjectController {
	@Autowired
	private BarberProjectService bpService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ApiResult getList(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam(required = false) String uId,
			FilterHttpServletRequest request, HttpServletResponse response){
		
		Long bbUid = StringUtils.isNotEmpty(uId) ? Long.parseLong(uId) : null;
		
		return this.bpService.getList(pageNum, pageSize, bbUid, request.getAuth());
	}
	
	@RequestMapping(value = "/allProjects", method = RequestMethod.POST)
	public ApiResult getList(FilterHttpServletRequest request, HttpServletResponse response){
		return this.bpService.getAllProjects(request.getAuth());
	}
	
	@RequestMapping(value = "/item", method = RequestMethod.GET)
	public ApiResult item(@RequestParam Long bbpId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.notNull(bbpId, "bbpId should not be null or empty");
		
		BarberProjectInfo info = this.bpService.get(request.getAuth(), bbpId);
		
		return info != null ? new ApiObjectResult<>(info) : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApiResult add(BarberProjectInfo bp, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.bpService.add(request.getAuth(), bp);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ApiResult edit(BarberProjectInfo bp, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.bpService.edit(request.getAuth(), bp);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApiResult edit(@RequestParam String bbpId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.hasText(bbpId, "ids should not be null or empty");
		
		Set<Long> ids = new HashSet<>();
		String[] uIds = bbpId.split(",");
		for(String tmp : uIds){
			if(tmp.length() > 0){
				ids.add(Long.parseLong(tmp));
			}
		}
		Assert.notEmpty(ids, "ids should not be null or empty");
		
		boolean ok = this.bpService.delete(request.getAuth(), ids);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
}
