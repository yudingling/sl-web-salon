package com.sl.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.web.filter.FilterHttpServletRequest;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.OrderService;
import com.sl.web.util.DateUtil;

@RestController
@RequestMapping("/authed/shopMgr/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ApiResult getList(@RequestParam int pageNum, @RequestParam int pageSize, 
			@RequestParam(required = false) String paied, @RequestParam(required = false) String confirmed, @RequestParam(required = false) String barberUid, 
			@RequestParam(required = false) String projectId, @RequestParam(required = false) String tmRange,
			FilterHttpServletRequest request, HttpServletResponse response){
		
		Integer paiedVal = StringUtils.isNotEmpty(paied) ? Integer.parseInt(paied) : null;
		Integer confirmedVal = StringUtils.isNotEmpty(confirmed) ? Integer.parseInt(confirmed) : null;
		Long barberUidVal = StringUtils.isNotEmpty(barberUid) ? Long.parseLong(barberUid) : null;
		Long projectIdVal = StringUtils.isNotEmpty(projectId) ? Long.parseLong(projectId) : null;
		
		Long stmVal = null;
		Long etmVal = null;
		if(StringUtils.isNotEmpty(tmRange)){
			String[] tmp = tmRange.split(" - ");
			if(tmp.length == 2){
				stmVal = DateUtil.toDate(tmp[0].trim(), "yyyy-MM-dd").getTime();
				etmVal = DateUtil.toDate(tmp[1].trim(), "yyyy-MM-dd").getTime();
			}
		}
		
		return this.orderService.getList(pageNum, pageSize, request.getAuth(), paiedVal, confirmedVal, barberUidVal, projectIdVal, stmVal, etmVal);
	}
}
