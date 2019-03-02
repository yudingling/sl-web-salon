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
import com.sl.web.model.db.SlUser;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.BarberService;

@RestController
@RequestMapping("/authed/shopMgr/barber")
public class BarberController {
	@Autowired
	private BarberService barberService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ApiResult getList(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam(required = false) String name, 
			FilterHttpServletRequest request, HttpServletResponse response){
		
		return this.barberService.getList(pageNum, pageSize, request.getAuth(), name);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApiResult add(SlUser user, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.barberService.add(request.getAuth(), user);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApiResult edit(@RequestParam String uId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.hasText(uId, "ids should not be null or empty");
		
		Set<Long> ids = new HashSet<>();
		String[] uIds = uId.split(",");
		for(String tmp : uIds){
			if(tmp.length() > 0){
				ids.add(Long.parseLong(tmp));
			}
		}
		Assert.notEmpty(ids, "ids should not be null or empty");
		
		boolean ok = this.barberService.delete(request.getAuth(), ids);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
}
