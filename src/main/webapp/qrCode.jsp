<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<!DOCTYPE html>
<html>
<head>
    <title>生成二维码</title>
</head>
<body>
<h2>生成二维码</h2>
<form action="<%=request.getContextPath()%>/wx/getTitleDetails" method="get">
    <table>
        <tr>
            <td>请输入受理点:</td>
            <td><input type="text" name="attach" id="attach"></td>
        </tr>
        <tr>
            <img id="imgSrc" />
        </tr>
        <tr>
            <td><input type="button" value="生成二维码" onclick="getQrCode()"></td>
             <td><input type="submit" value="查询"></td>
        </tr>
    </table>
</form>
<script type="text/javascript">
	function getQrCode(){
		$.ajax({
			url:'<%=request.getContextPath()%>/wx/getUserTitleUrl',
			type : 'POST',
			data:{
				attach:$("#attach").val()
				},
			success:function(data){
				console.log(data);
				console.log(encodeURIComponent(data));
				
				$("#imgSrc").attr("src","http://qr.liantu.com/api.php?text="+encodeURIComponent(data));
			},
			error:function(data){
				alert("获取二维码链接失败");
			}
		});
	}
</script>
</body>
</html>
