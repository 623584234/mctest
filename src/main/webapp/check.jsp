<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<form action="/zhaohang/getBilling" method="post">
		<button type="button" onclick="start()">执行/重启脚本</button>
		<button type="submit">查询账单</button>
		查询流水号:<input type="text" name="code">
		<table border="1px" id="tableId" >
			<thead>
				<tr style="background: #bbfffa;">
					<th>流水号</th>
					<th>交易日</th>
					<th>交易时间</th>
					<th>起息日</th>
					<th>交易类型</th>
					<th>借方金额</th>
					<th>贷方金额</th>
					<th>余额</th>
					<th>摘要</th>
					<th>流程实例号</th>
					<th>业务名称</th>
					<th>用途</th>
					<th>业务参考号</th>
					<th>业务摘要</th>
					<th>其它摘要</th>
					<th>收/付方分行名</th>
					<th>收/付方名称</th>
					<th>收/付方帐号</th>
					<th>收/付方开户行行号</th>
					<th>收/付方开户行名</th>
					<th>收/付方开户行地址</th>
					<th>母/子公司帐号分行名</th>
					<th>母/子公司帐号</th>
					<th>母/子公司名称</th>
					<th>信息标志</th>
					<th>有否附件信息</th>
					<th>冲帐标志</th>
					<th>扩展摘要</th>
					<th>交易分析码</th>
					<th>票据号</th>
					<th>商务支付订单号</th>
					<th>内部编号</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${billingList}" var="item">
					<tr>
						<td>${item.serialNumber}</td>
						<td>${item.tradingDay}</td>
						<td>${item.transactionTime}</td>
						<td>${item.interestDate}</td>
						<td>${item.transactionType}</td>
						<td>${item.debitAmount}</td>
						<td>${item.creditAmount}</td>
						<td>${item.balance}</td>
						<td>${item.summary}</td>
						<td>${item.instanceNumber}</td>
						<td>${item.businessName}</td>
						<td>${item.businessNumber}</td>
						<td>${item.purpose}</td>
						<td>${item.businessSummary}</td>
						<td>${item.otherAbstracts}</td>
						<td>${item.branchName}</td>
						<td>${item.name}</td>
						<td>${item.accounts}</td>
						<td>${item.bankNumber}</td>
						<td>${item.bankName}</td>
						<td>${item.bankAddress}</td>
						<td>${item.companyBranchName}</td>
						<td>${item.companyAccount}</td>
						<td>${item.companyName}</td>
						<td>${item.informationSigns}</td>
						<td>${item.stampingMark}</td>

						<td>${item.isanyAttachment}</td>
						<td>${item.extendedAbstract}</td>
						<td>${item.analysisCode}</td>
						<td>${item.billNumber}</td>
						<td>${item.orderNumber}</td>
						<td>${item.internalNumber}</td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>

	<script type="text/javascript">
		$(function() {
			$("#tableId tr:nth-child(even)").css("background-color", "#F2F2F2"); //偶数行样式
		});

		function start() {
			var startExe = confirm("是否启动脚本?\r\n(先检测并关闭已开始的脚本)");
			if (startExe == true) {
				$.ajax({
					url : '/zhaohang/start',
					type : 'POST',
					success : function(data) {
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
