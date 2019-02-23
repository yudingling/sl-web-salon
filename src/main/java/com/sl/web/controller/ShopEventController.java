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
import com.sl.web.model.EventInfo;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiObjectResult;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.ShopEventService;

@RestController
@RequestMapping("/authed/shopMgr/event")
public class ShopEventController {
	@Autowired
	private ShopEventService eventService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ApiResult getList(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam(required = false) String name, 
			FilterHttpServletRequest request, HttpServletResponse response){
		
		return this.eventService.getList(pageNum, pageSize, request.getAuth(), name);
	}
	
	@RequestMapping(value = "/item", method = RequestMethod.GET)
	public ApiResult item(@RequestParam Long eventId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.notNull(eventId, "eventId should not be null or empty");
		
		EventInfo info = this.eventService.get(request.getAuth(), eventId);
		
		return info != null ? new ApiObjectResult<>(info) : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApiResult add(EventInfo event, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.eventService.add(request.getAuth(), event);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ApiResult edit(EventInfo event, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.eventService.update(request.getAuth(), event);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApiResult edit(@RequestParam String eventId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.hasText(eventId, "ids should not be null or empty");
		
		Set<Long> ids = new HashSet<>();
		String[] uIds = eventId.split(",");
		for(String tmp : uIds){
			if(tmp.length() > 0){
				ids.add(Long.parseLong(tmp));
			}
		}
		Assert.notEmpty(ids, "ids should not be null or empty");
		
		boolean ok = this.eventService.delete(request.getAuth(), ids);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
}
