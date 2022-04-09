<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Accounts for Client: ${clientsModel.firstName} ${clientsModel.lastName}</title>
<%@include file="AdminMenu.jsp" %>
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
		<c:choose>
			<c:when test="${param.status eq 'success'}">
			<br>
				<span style="color:green;"><c:out value="${param.message}"/></span>
			</c:when>
			<c:otherwise>
				<br>
				<span style="color:red;"><c:out value="${param.message}"/></span>
			</c:otherwise>
		</c:choose>
		<br>
		<h1>Accounts for Client: ${clientsModel.firstName} ${clientsModel.lastName}</h1>
		<hr>
		<a href="${pageContext.request.contextPath}/admin/accounts/create/${clientsModel.clientId}">Create a new Account</a>
	</div>
	<br>
	<br>
	<div>
		<table class="center" style="width:100%">
			<tr>
				<th>Account #</th>
				<th>Account Type</th>
				<th>Balance</th>
				<th>Created On</th>
				<th>Last Updated On</th>
			</tr>
			<c:forEach items="${accountsModels}" var="accountModel">
			    <tr>
					<td>${accountModel.accountNo}</td>
					<td>${accountModel.bankTypesModel.bankType}</td>
					<td>$${accountModel.balance}</td>
					<td>${accountModel.createdDate}</td>
					<td>${accountModel.updatedDate}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<br>
	<br>
	<h1>Utility Bills for Client: ${clientsModel.firstName} ${clientsModel.lastName}</h1>
	<hr>
	<a href="${pageContext.request.contextPath}/admin/utilities/bills/create/${clientsModel.clientId}">Create a new Bill</a>
	<br><br>
	<div>
		<table class="center" style="width:100%">
			<tr>
				<th>Utility Type</th>
				<th>Utility</th>
				<th>Balance</th>
				<th>Status</th>
				<th>Generated On</th>
				<th>Paid On</th>
			</tr>
			<c:forEach items="${utilityBillsModels}" var="utilityBillsModel">
			    <tr>
					<td>${utilityBillsModel.utilitiesModel.utilityType}</td>
					<td>${utilityBillsModel.utilitiesModel.utilityName}</td>
					<td>$${utilityBillsModel.balance}</td>
					<td>${utilityBillsModel.status}</td>
					<td>${utilityBillsModel.generatedOn}</td>
					<td>${utilityBillsModel.paidOn}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>