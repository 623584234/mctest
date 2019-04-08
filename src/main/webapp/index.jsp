<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<title>执行脚本</title>
<style type="text/css">
html, body {
     height: 100%;
}
.btn_left{
    position: fixed;
    left: 45%;
    top: 50%;
    width: 150px;
    height: 70px;
    margin: -100px 0px 0px -150px;
}
.btn_right{
    position: fixed;
    left: 55%;
    top: 50%;
    width: 150px;
    height: 70px;
    margin: -100px 0px 0px -150px;
}

</style>
</head>
<body>
<form action ="<%=request.getContextPath()%>/getBilling" method="post">
	<button type="button" class="btn_left" onclick="start()">执行/重启脚本</button>
	<button type="submit" class="btn_right">查询账单</button>
</form>

<script type="text/javascript">	
	function start(){
		var startExe=confirm("是否启动脚本?\r\n(先检测并关闭已开始的脚本)");
		if(startExe==true)
		{
		  $.ajax({
	            url:'/mctest/start',
	            type:'POST',
	            success:function(data){
	                alert(data.message);
	            },
				error: function(data){
					alert("启动脚本失败");
				}
		  });
		}
	}

</script>
</body>
</html>
