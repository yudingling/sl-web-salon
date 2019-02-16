package com.sl.web.model.result;

import java.io.Serializable;

public class ApiPageResult<T extends Serializable> extends ApiResult {
	private static final long serialVersionUID = 6592853158557000527L;
	
	private Long totalSize;
	private Iterable<T> data;
	
	public Long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}
	public Iterable<T> getData() {
		return data;
	}
	public void setData(Iterable<T> data) {
		this.data = data;
	}
	
	public ApiPageResult(){
		super();
	}
	
	public ApiPageResult(Long totalSize, Iterable<T> data) {
		super(ApiError.SUCCESS);
		this.totalSize = totalSize;
		this.data = data;
	}
}
