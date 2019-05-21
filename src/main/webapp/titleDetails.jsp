<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<title>抬头查询</title>
<style type="text/css">
html, body {
	height: 100%;
}

button {
	width: 100px;
	height: 50px;
}

table {
	/*表格居中*/
	margin: 0 auto;
	/*表格文本居中*/
	text-align: center;
	/*表格文本类型*/
	font-family: 微软雅黑;
	/*合并单元格*/
	border-collapse: collapse;
}
table td{high:10px;}
</style>
</head>
<body>
	<form action="<%=request.getContextPath()%>/wx/getTitleDetails" method="get">
		<button type="submit">点击查询</button>
		查询受理点:<input type="text" name="attach">
		<table border="1px" id="tableId" >
			<thead>
				<tr style="background: #bbfffa;">
					<th>单位名称</th>
					<th>税号</th>
					<th>单位地址</th>
					<th>单位电话</th>
					<th>开户银行</th>
					<th>银行账户</th>
					<th>抬头类型</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${titleList}" var="item">
					<tr>
						<td>${item.title}</td>
						<td>${item.taxNo}</td>
						<td>${item.addr}</td>
						<td>${item.phone}</td>
						<td>${item.bankType}</td>
						<td>${item.bankNo}</td>
						<c:choose>
							<c:when test="${item.titleType=='InvoiceUserTitlePersonType'}">    
								<td>个人</td>
  							</c:when>
							<c:otherwise> 
								<td>企业</td>
   							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>

	<script type="text/javascript">
		$(function() {
			$("#tableId tr:nth-child(even)").css("background-color", "#F2F2F2"); //偶数行样式
		});
	</script>
</body>
</html>