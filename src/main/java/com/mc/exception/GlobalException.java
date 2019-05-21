package com.mc.exception;

public class GlobalException extends RuntimeException {

    private String msg;

    public GlobalException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
