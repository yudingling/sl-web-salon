package com.sl.web.model.result;

import java.io.Serializable;

public class ApiObjectResult<T extends Serializable> extends ApiResult {
	private static final long serialVersionUID = -8114319301901581598L;
	
	private T data;

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public ApiObjectResult(){
		super();
	}
	
	public ApiObjectResult(T data) {
		super(ApiError.SUCCESS);
		this.data = data;
	}
}
