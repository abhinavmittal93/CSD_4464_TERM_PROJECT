<%@page import="com.termproject.csd4464.utils.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pay Utility Bills</title>
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
		<h1>Pay Utility Bills</h1>
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
		<form:form action="${pageContext.request.contextPath}/client/utilities/bills/pay" method="POST">
			<table class="center" style="width:100%">
				<tr>
					<th>Utility</th>
					<td>
						<select name="utilityBillId" required>
							<c:forEach items="${utilityBillsModels}" var="utilityBillsModel">
								<option value="${utilityBillsModel.utilityBillId}">${utilityBillsModel.utilitiesModel.utilityType} - ${utilityBillsModel.utilitiesModel.utilityName} - $${utilityBillsModel.balance}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>Pay From</th>
					<td>
						<select name="transferFromAccountId" required>
							<c:forEach items="${clientAccounts}" var="clientAccount">
								<option value="${clientAccount.accountId}">${clientAccount.bankTypesModel.bankType} - ${clientAccount.accountNo} - $${clientAccount.balance}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<input type="hidden" name="action" value="<%= Constants.TRANSACTION_ACTION_UTILITY_BILL_PAYMENT %>" />
				<tr>
					<th></th>
					<td><input type="submit" value="Pay" /></td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>