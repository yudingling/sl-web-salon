package com.sl.web.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HtmlController {
	
	@RequestMapping(path="/{htmlName}")
	public String get(@PathVariable String htmlName, HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(htmlName.equals("signin")){
			response.sendRedirect(request.getContextPath() + "/signin.html");
			return null;
			
		}else{
			return htmlName;
		}
	}
}
