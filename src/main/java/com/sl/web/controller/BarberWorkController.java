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
import com.sl.web.model.BarberWorkInfo;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiObjectResult;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.BarberWorkService;

@RestController
@RequestMapping("/authed/shopMgr/barberwork")
public class BarberWorkController {
	@Autowired
	private BarberWorkService workService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ApiResult getList(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam(required = false) Long barberUid, 
			FilterHttpServletRequest request, HttpServletResponse response){
		
		return this.workService.getList(pageNum, pageSize, request.getAuth(), barberUid);
	}
	
	@RequestMapping(value = "/item", method = RequestMethod.GET)
	public ApiResult item(@RequestParam Long bbwId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.notNull(bbwId, "bbwId should not be null or empty");
		
		BarberWorkInfo info = this.workService.get(request.getAuth(), bbwId);
		
		return info != null ? new ApiObjectResult<>(info) : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApiResult add(BarberWorkInfo work, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.workService.add(request.getAuth(), work);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApiResult edit(@RequestParam String bbwId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.hasText(bbwId, "ids should not be null or empty");
		
		Set<Long> ids = new HashSet<>();
		String[] tmpIds = bbwId.split(",");
		for(String tmp : tmpIds){
			if(tmp.length() > 0){
				ids.add(Long.parseLong(tmp));
			}
		}
		Assert.notEmpty(ids, "ids should not be null or empty");
		
		boolean ok = this.workService.delete(request.getAuth(), ids);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
}
