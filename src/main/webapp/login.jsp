<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录页面</title>
</head>
<body>
	<form action ="<%=request.getContextPath()%>/login" method="post">
		账号：<input type="text" name="username"/>
		密码：<input type="password" name="pwd"/>
		<input type="submit" value="提交">
	</form>
</body>
</html>