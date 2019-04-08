package com.mc.util;

import java.io.Serializable;

//当值为空时，不参与序列化
public class ServerResponse<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int code;
	private String message;
	private T data;
	
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public T getData() {
		return data;
	}
	
	private ServerResponse(int code) {
		this.code = code;
	}
	private ServerResponse(int code,String message) {
		this.code = code;
		this.message = message;
	}
	private ServerResponse(int code,String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public boolean isSuccess() {
		return this.code == ResponseCode.SUCCESS.getCode();
	}
	
	public static <T> ServerResponse<T> responseBySuccess(String message){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),message);
	}
	
	public static <T> ServerResponse<T> responseBySuccess(String message,T data){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),message,data);
	}
	
	public static <T> ServerResponse<T> responseBySuccessData(T data){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc(),data);
	}
	
	public static <T> ServerResponse<T> responseByError(String message){
		return new ServerResponse<T>(ResponseCode.ERROR.getCode(),message);
	}
}
