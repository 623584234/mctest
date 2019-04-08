package com.mc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
 * 拦截器（Interceptor）测试类
 */
public class testInterceptor implements HandlerInterceptor {

	/*请求处理前调用.表示请求是否需要执行
	 * 返回值true:请求会继续被执行
	      返回值false:请求被终止.只执行preHandel方法，请求和下面两个方法均被拦截，不执行。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("---------preHandle1");
		return true;
	}
	
	//请求处理后调用
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("---------postHandle");
//		modelAndView.addObject("test", "拦截后修改页面值");
		//转发
//		modelAndView.setViewName("/index.jsp");
	}
	
	//请求结束后调用,不常用。主要用于关闭资源：流之类
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("---------afterCompletion");
	}
	
}
