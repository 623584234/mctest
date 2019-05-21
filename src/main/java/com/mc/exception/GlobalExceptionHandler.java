package com.mc.exception;

import com.mc.util.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value=Exception.class)
	public ServerResponse<?> exceptionHandler(HttpServletRequest request ,Exception e){

		if(e instanceof GlobalException){
			logger.error(e.getMessage());
			return ServerResponse.responseByError(e.getMessage());
		}else {
            logger.error("系统超时",e);
            return ServerResponse.responseByError("系统超时");
        }

	}

}

