<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transaction History</title>
<%@include file="Menu.jsp" %>
<style type="text/css">
	.middle {
      display: table;
      margin-right: auto;
      margin-left: auto;
  	}
  	table, th, td {
	  border:1px solid black;
	  border-collapse: collapse;
	}
	table.center {
	  margin-left: auto; 
	  margin-right: auto;
	}
</style>
</head>
<body align="center">
	<div style="text-align: center;">
		<h1>Transaction History</h1>
	</div>
	<div>
		<c:choose>
			<c:when test="${param.status eq 'success'}">
				<span style="color:green;"><c:out value="${param.message}"/></span>
			</c:when>
			<c:otherwise>
				<span style="color:red;"><c:out value="${param.message}"/></span>
			</c:otherwise>
		</c:choose>
		<br>
		<br>
		<table class="center" style="width:100%">
			<tr>
				<th>Transferred From</th>
				<th>Transferred To</th>
				<th>Amount</th>
				<th>Status</th>
				<th>Reason Code</th>
				<th>Date</th>
			</tr>
			<c:forEach items="${transactionsAuditModels}" var="transactionsAuditModel">
			    <tr>
					<td>
						${transactionsAuditModel.transactionAccountModel.clientsModel.firstName} ${transactionsAuditModel.transactionAccountModel.clientsModel.lastName} - ${transactionsAuditModel.transactionAccountModel.accountNo} - ${transactionsAuditModel.transactionAccountModel.bankTypesModel.bankType} 
					</td>
					<td>
						<c:if test="${not empty transactionsAuditModel.transferToAccountModel}">
							${transactionsAuditModel.transferToAccountModel.clientsModel.firstName} ${transactionsAuditModel.transferToAccountModel.clientsModel.lastName} - ${transactionsAuditModel.transferToAccountModel.accountNo} - ${transactionsAuditModel.transferToAccountModel.bankTypesModel.bankType}
						</c:if>
						
						<c:if test="${empty transactionsAuditModel.transferToAccountModel}">
							N/A
						</c:if>
					</td>
					<td>
						$${transactionsAuditModel.transactionAmount}
					</td>
					<td>
						${transactionsAuditModel.status}
					</td>
					<td>
						${transactionsAuditModel.reasonCode}
					</td>
					<td>
						${transactionsAuditModel.transactionDate}
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>