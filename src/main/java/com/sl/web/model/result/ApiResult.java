package com.sl.web.model.result;

import java.io.Serializable;

public class ApiResult implements Serializable {
	private static final long serialVersionUID = -1315930430531289873L;
	
	private int errorCode;
	private String errorMsg;
	private long timestamp = System.currentTimeMillis();
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public ApiResult(){
		super();
	}
	
	public ApiResult(ApiError error) {
		super();
		this.errorCode = error.getCode();
		this.errorMsg = error.getMsg();
	}
	
	public ApiResult(int errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public static ApiResult error(int errorCode, String errorMsg){
		return new ApiResult(errorCode, errorMsg);
	}
	
	public static ApiResult error(ApiError error){
		return new ApiResult(error);
	}
	
	public static ApiResult error(ApiError error, String errorMsg){
		return new ApiResult(error.getCode(), errorMsg);
	}
	
	public static ApiResult success(){
		return new ApiResult(ApiError.SUCCESS);
	}
}
