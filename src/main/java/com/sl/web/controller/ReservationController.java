package com.sl.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sl.web.filter.FilterHttpServletRequest;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.ReservationService;

@RestController
@RequestMapping("/authed/shopMgr/reservation")
public class ReservationController {
	@Autowired
	private ReservationService reservationService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ApiResult getList(FilterHttpServletRequest request, HttpServletResponse response){
		return this.reservationService.getReservations(request.getAuth());
	}
}
