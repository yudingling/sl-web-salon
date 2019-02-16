package com.sl.web.model.result;

import java.io.Serializable;

public class ApiArrayResult<T extends Serializable> extends ApiResult {
	private static final long serialVersionUID = -7571350741157548224L;
	
	private Iterable<T> data;

	public Iterable<T> getData() {
		return data;
	}
	public void setData(Iterable<T> data) {
		this.data = data;
	}
	
	public ApiArrayResult(){
		super();
	}

	public ApiArrayResult(Iterable<T> data) {
		super(ApiError.SUCCESS);
		this.data = data;
	}
}
