package com.mc.util;


public enum ResponseCode {

	SUCCESS(1,"成功"),
	ERROR(-1,"失败");
	
	private final int code;
	private final String desc;
	
	ResponseCode(int code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
}
