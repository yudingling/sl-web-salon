package com.sl.web.model.result;

public enum ApiError {
	SUCCESS(0, null),
	INTERNAL_ERROR(500, "internal server error"),
	TOKEN_ERROR(1000, "token is invalid, please re-acquire"),
	ARGUMENT_ERROR(1001, "illegal argument error"),
	
	UNAUTHORIZED(2001, "UNAUTHORIZED"),
	USER_UNEXISTS(2003, "user not exist"),
	USER_PWD_ERROR(2005, "password error"),
	
	ORDER_UNPAIED(3001, "order not paied"),
	RESERVATION_TIME_SHILED(3002, "reservation time shiled"),
	ORDER_CONFIRM_FAILED(3003, "order confirm failed"),
	ORDER_CANCEL_FAILED(3004, "order cancel failed"),
	ORDER_PAID_FAILED(3005, "order pay failed"),
	
	SHOP_OUT_OF_SERVICE(4001, "shop out of service");
	
	
	private int code;
	private String msg;
	
	private ApiError(int code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
