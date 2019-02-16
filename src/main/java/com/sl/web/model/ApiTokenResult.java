package com.sl.web.model;

import com.sl.web.model.result.ApiResult;

public class ApiTokenResult extends ApiResult {
	private static final long serialVersionUID = -5985406810675739463L;
	
	private String data;

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
