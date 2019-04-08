package com.mc.Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class testFilter1
 */
public class testFilter1 implements Filter {

	FilterConfig config;

	/**
	 * Default constructor.
	 */
	public testFilter1() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		System.out.println("------destroy Filter");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("------start Filter");
		HttpServletRequest resque= (HttpServletRequest) request;
		HttpServletResponse respon = (HttpServletResponse) response;
		HttpSession session = resque.getSession();

		String noLoginPaths = config.getInitParameter("noLoginPaths");
		if (noLoginPaths != null) {
			String strAttr[] = noLoginPaths.split(";");
			for (int i = 0; i < strAttr.length; i++) {
				if (!"".equals(strAttr[i]) || strAttr[i] != null) {
					System.out.println("RequestURL---------"+resque.getRequestURL());
					System.out.println("RequestURI---------"+resque.getRequestURI());
					System.out.println(resque.getRequestURI().split("/").length-1);
					System.out.println("resque.getContextPath()---------"+resque.getContextPath());
					if (resque.getRequestURI().split("/")[resque.getRequestURI().split("/").length-1].equals(strAttr[i]) ) {
						chain.doFilter(request, response);
						return;
					}
				}
			}
		}
		if (session.getAttribute("username") != null) {
			chain.doFilter(request, response);
		} else {
			respon.sendRedirect(resque.getContextPath() + "/login.jsp");
		}

		// 该方法为进入下一个过滤器的doFilter中chain.doFilter方法前的操作，接着执行web.xml中过滤设置的请求。
//		chain.doFilter(request, response);

		// 重定向 resque.getContextPath()得到/zhaohang （/+"项目名")
//		respon.sendRedirect(resque.getContextPath()+"/webTest.jsp");

		// 转发
		// 当出现
		// webTest.jsp被拦截的时候，又重定向到该jsp会又被拦截，等于进入死循环。此时用forward可继续访问该jsp(主要是重定向二次请求的问题)
		//resque.getRequestDispatcher("webTest.jsp").forward(request, response);
		System.out.println("------end Filter");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		config = fConfig;
		System.out.println("-------init Filter");
	}

}
