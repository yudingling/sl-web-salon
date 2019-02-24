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
import com.sl.web.model.db.SlProject;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiObjectResult;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.ProjectService;

@RestController
@RequestMapping("/authed/brandMgr/project")
public class ProjectController {
	@Autowired
	private ProjectService pjService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ApiResult getList(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam(required = false) String name,  
			FilterHttpServletRequest request, HttpServletResponse response){
		
		return this.pjService.getList(pageNum, pageSize, request.getAuth(), name);
	}
	
	@RequestMapping(value = "/item", method = RequestMethod.GET)
	public ApiResult item(@RequestParam Long pjId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.notNull(pjId, "pjId should not be null or empty");
		
		SlProject info = this.pjService.get(request.getAuth(), pjId);
		
		return info != null ? new ApiObjectResult<>(info) : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApiResult add(SlProject project, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.pjService.add(request.getAuth(), project);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ApiResult edit(SlProject project, FilterHttpServletRequest request, HttpServletResponse response){
		boolean ok = this.pjService.update(request.getAuth(), project);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApiResult edit(@RequestParam String pjId, FilterHttpServletRequest request, HttpServletResponse response){
		Assert.hasText(pjId, "ids should not be null or empty");
		
		Set<Long> ids = new HashSet<>();
		String[] uIds = pjId.split(",");
		for(String tmp : uIds){
			if(tmp.length() > 0){
				ids.add(Long.parseLong(tmp));
			}
		}
		Assert.notEmpty(ids, "ids should not be null or empty");
		
		boolean ok = this.pjService.delete(request.getAuth(), ids);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR);
	}
}
