<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Utility Bills History</title>
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
		<h1>Utility Bills History</h1>
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
				<th>Utility Type</th>
				<th>Utility</th>
				<th>Amount</th>
				<th>Status</th>
				<th>Paid On</th>
				<th>Status</th>
				<th>Reason Code</th>
			</tr>
			<c:forEach items="${utilitiesAuditModels}" var="utilitiesAuditModel">
			    <tr>
					<td>${utilitiesAuditModel.utilityBillsModel.utilitiesModel.utilityType}</td>
					<td>${utilitiesAuditModel.utilityBillsModel.utilitiesModel.utilityName}</td>
					<td>$${utilitiesAuditModel.amount}</td>
					<td>${utilitiesAuditModel.status}</td>
					<td>${utilitiesAuditModel.paidOn}</td>
					<td>${utilitiesAuditModel.status}</td>
					<td>${utilitiesAuditModel.reasonCode}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>