<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Accounts for Client: ${clientsModel.firstName} ${clientsModel.lastName}</title>
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
		<c:out value="${param.message}"/>
		<br>
		<h1>Accounts for Client: ${clientsModel.firstName} ${clientsModel.lastName}</h1>
		<a href="create/${clientsModel.clientId}">Create a new Account</a>
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
</body>
</html>