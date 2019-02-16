package com.sl.web.model.result;

import java.io.Serializable;
import java.util.List;

public class PageResult<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = 1235769928278403846L;
	
	private Long totalSize;
	private List<T> data;
	
	public Long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	public PageResult(){
		super();
	}
	
	public PageResult(Long totalSize, List<T> data) {
		super();
		this.totalSize = totalSize;
		this.data = data;
	}
}
