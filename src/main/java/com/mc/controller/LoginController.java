package com.mc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {

	
	@RequestMapping("login")
	public String login(String username, String pwd,HttpServletResponse response,HttpServletRequest request) {
		
		if("admin".equals(username) && "admin".equals(pwd)) {
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
		}else {
			return "error-login";
		}
		return "check";
	}
}
