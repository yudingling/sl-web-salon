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
import com.sl.web.model.BarberShieldInfo;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.BarberShieldService;

@RestController
@RequestMapping("/authed/shopMgr/barberShield")
public class BarberShieldController {
	@Autowired
	private BarberShieldService shieldService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ApiResult getList(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam(required = false) String uId,
			FilterHttpServletRequest request, HttpServletResponse response){
		
		Long bbUid = StringUtils.isNotEmpty(uId) ? Long.parseLong(uId) : null;
		
		return this.shieldService.getList(pageNum, pageSize, bbUid, request.getAuth());
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApiResult add(BarberShieldInfo shield, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.shieldService.add(request.getAuth(), shield);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApiResult edit(@RequestParam String bbtId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.hasText(bbtId, "ids should not be null or empty");
		
		Set<Long> ids = new HashSet<>();
		String[] uIds = bbtId.split(",");
		for(String tmp : uIds){
			if(tmp.length() > 0){
				ids.add(Long.parseLong(tmp));
			}
		}
		Assert.notEmpty(ids, "ids should not be null or empty");
		
		boolean ok = this.shieldService.delete(request.getAuth(), ids);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
}
