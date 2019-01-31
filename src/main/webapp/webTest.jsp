<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script type="text/javascript" src="js/websocket.js"></script>
<title>webSocket测试</title>
<body>
<p>用户<input type="text" id="user"></p>
<p>点击链接<input type="button" id="connnect" onclick="startWebSocket()"></p>
<p>按钮<input type="button" id="sendbutton"></p>
<p>信息<input type="text" id="message"></p>
<p>回调<input type="text" id="msg"></p>

</body>
</html>