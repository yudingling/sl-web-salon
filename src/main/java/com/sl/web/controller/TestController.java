package com.sl.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@RequestMapping(path="/kk/getCookie")
	public Object getxxx(HttpServletRequest request, HttpServletResponse response){
		System.out.println(request.getSession().getAttribute("id"));
		
		Map<String, Double> kk = new HashMap<>();
		kk.put("s1", 12.7);
		return kk;
	}
	
	@RequestMapping(path="/kk/setCookie")
	public void gsxxxx(@RequestParam Boolean keep, HttpServletRequest request, HttpServletResponse response){
		request.getSession().setAttribute("id", System.currentTimeMillis());
		
		request.getSession().setMaxInactiveInterval(86400);
		
		if(keep){
			Cookie cookie = new Cookie("SESSION", request.getSession().getId());
		    cookie.setPath("/");
		    cookie.setHttpOnly(true);
		    cookie.setMaxAge(Integer.MAX_VALUE);
		    response.addCookie(cookie);
		}
	}
	
	@RequestMapping(path="/kk/clearCookie")
	public void getxxxx(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		
		//should clear the cookie if 'keep' was set when sign in
		Cookie cookie = new Cookie("SESSION", session.getId());
	    cookie.setPath("/");
	    cookie.setHttpOnly(true);
	    cookie.setMaxAge(0);
	    response.addCookie(cookie);
		
		session.removeAttribute("id");
		session.invalidate();
	}
}
