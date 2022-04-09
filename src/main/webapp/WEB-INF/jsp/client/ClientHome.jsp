<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Client Details</title>
<%@include file="Menu.jsp" %>
<style type="text/css">
	.middle {
      display: table;
      margin-right: auto;
      margin-left: auto;
  	}
	
	.rounded {
	  border: 2px solid grey;
	  border-radius: 5px;
	  margin: 20px 0px 20px 0px;
	}
	
	.inner {
		margin: 20px 20px 20px 20px;
	}
</style>
</head>
<body align="center">
	<div style="text-align: center;">
		<h1>Client Details</h1>
		<c:choose>
			<c:when test="${param.status eq 'success'}">
				<span style="color:green;"><c:out value="${param.message}"/></span>
			</c:when>
			<c:otherwise>
				<span style="color:red;"><c:out value="${param.message}"/></span>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="rounded">
		<table class="center" style="width:100%">
			<tr>
				<th>Name</th>
				<td>${clientsModel.firstName} ${clientsModel.lastName}</td>
			</tr>
			<tr>
				<th>Gender</th>
				<td>
					<c:choose>
						<c:when test="${clientsModel.gender eq 'M'}">Male</c:when>
						<c:when test="${clientsModel.gender eq 'F'}">Female</c:when>
						<c:otherwise>Other</c:otherwise>
					</c:choose>
				</td>
				<th>Email</th><td>${clientsModel.email}</td>
			</tr>
			<tr>
				<th>Phone</th><td>${clientsModel.phone}</td>
				<th>Username</th><td>${clientsModel.username}</td>
			</tr>
		</table>
	</div>
	<div class="rounded">
		<h2>Accounts</h2>
		<hr>
		<c:forEach items="${accountsModels}" var="accountsModel">
			 <div class="inner rounded">
			 	<table class="center" style="width:100%">
					<tr>
						<th>Account Type</th>
						<td>${accountsModel.bankTypesModel.bankType}</td>
					</tr>
					<tr>
						<th>Account #</th>
						<td>
							${accountsModel.accountNo}
						</td>
						<th>Balance</th><td>$${accountsModel.balance}</td>
					</tr>
					<tr>
						<th>Created On</th><td>${accountsModel.createdDate}</td>
						<th>Last Updated On</th><td>${accountsModel.updatedDate}</td>
					</tr>
				</table>
			 </div>   
		</c:forEach>
	</div>
</body>
</html>