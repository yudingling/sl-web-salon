package com.sl.web.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sl.web.model.SigninResult;
import com.sl.web.model.UserType;
import com.sl.web.model.result.ApiError;
import com.sl.web.model.result.ApiResult;
import com.sl.web.service.AuthService;
import com.sl.web.service.TokenService;

public class AuthFilter extends BaseFilter {
	private AuthService authService;
	private Map<UserType, Set<String>> roleApis;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        this.authService  = ctx.getBean(AuthService.class);
        
        this.roleApis = this.authService.getAllRoleApi();
	}
	
	/**
	 * wildcard check, '#' for next all， '+' for 1 level.
	 * @return the api string which match the path, if none of them was matched, return null
	 */
	private String wildcardCheck(Set<String> apiList, String path){
		for(String key: apiList){
			if(path.matches("^" + key.replace("#", "\\S*").replace("+/", "(\\S*)/")))
				return key;
		}
		
		return null;
	}
	
	private boolean matchApiPath(Set<String> apis, String path) {
		return apis.contains(path) || this.wildcardCheck(apis, path) != null;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		//httpMethod + ":" + url,  convert to lowerCase for compare
		String path = (req.getMethod() + ":" + req.getServletPath()).toLowerCase() 
				+ (req.getPathInfo() != null ? req.getPathInfo().toLowerCase() : "");
		
		SigninResult result = TokenService.getSession(req);
		if(result != null){
			Set<String> apis = this.roleApis.get(UserType.valueOf(result.getRoleId()));
			
			if(!CollectionUtils.isEmpty(apis) && (this.matchApiPath(apis, path))){
				chain.doFilter(new FilterHttpServletRequest(req, result), response);
				
			}else{
				this.error(ApiResult.error(ApiError.UNAUTHORIZED), req, resp);
			}
			
		}else{
			this.goLogin(req, resp);
		}
	}

	@Override
	public void destroy() {
		//nothing
	}
}
