package com.sl.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import com.alibaba.fastjson.JSON;
import com.sl.web.model.result.ApiResult;

public abstract class BaseFilter implements Filter {
	protected void error(ApiResult data, HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(data == null){
			this.error(null, response);
			return;
		}
		
		String msg = JSON.toJSONString(data);
		response.setContentType("application/json; charset=UTF-8");
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setStatus(HttpStatus.SC_OK);
		
		try(PrintWriter pw = response.getWriter()){
			pw.write(msg);
			pw.flush();
		}
	}
	
	protected void error(String message, HttpServletResponse response) throws IOException{
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.sendError(HttpStatus.SC_UNAUTHORIZED, message);
	}
	
	protected void goLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.sendRedirect(request.getContextPath() + "/signin.html");
	}
}
