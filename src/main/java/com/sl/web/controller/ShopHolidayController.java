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
import com.sl.web.model.HolidayInfo;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.ShopHolidayService;

@RestController
@RequestMapping("/authed/shopMgr/holiday")
public class ShopHolidayController {
	@Autowired
	private ShopHolidayService holidayService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ApiResult getList(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam(required = false) String name,
			FilterHttpServletRequest request, HttpServletResponse response){
		
		return this.holidayService.getList(pageNum, pageSize, request.getAuth(), name);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApiResult add(HolidayInfo holiday, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.holidayService.add(request.getAuth(), holiday);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApiResult edit(@RequestParam String sofId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.hasText(sofId, "ids should not be null or empty");
		
		Set<Long> ids = new HashSet<>();
		String[] uIds = sofId.split(",");
		for(String tmp : uIds){
			if(tmp.length() > 0){
				ids.add(Long.parseLong(tmp));
			}
		}
		Assert.notEmpty(ids, "ids should not be null or empty");
		
		boolean ok = this.holidayService.delete(request.getAuth(), ids);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
}
