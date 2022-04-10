<%@page import="com.termproject.csd4464.utils.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Make Transactions</title>
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
		<h1>Deposit | Transfer | Withdraw</h1>
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
		<h2>Self Transfer</h2>
		<hr>
		<form:form action="${pageContext.request.contextPath}/transaction/self" method="POST">
			<table class="center" style="width:100%">
				<tr>
					<th>Transfer From</th>
					<td>
						<select name="transferFromAccountId">
							<c:forEach items="${clientAccounts}" var="clientAccount">
								<option value="${clientAccount.accountId}">${clientAccount.bankTypesModel.bankType} - ${clientAccount.accountNo} - $${clientAccount.balance}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>Transfer To</th>
					<td>
						<select name="transferToAccount">
							<c:forEach items="${clientAccounts}" var="clientAccount">
								<option value="${clientAccount.accountId}">${clientAccount.bankTypesModel.bankType} - ${clientAccount.accountNo} - $${clientAccount.balance}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>Amount</th>
					<td><input name="balance" type="number" step=".01" required /></td>
				</tr>
				<input type="hidden" name="action" value="<%= Constants.TRANSACTION_ACTION_SELF_TRANSFER %>" />
				<tr>
					<th></th>
					<td><input type="submit" value="Transfer" /></td>
				</tr>
			</table>
		</form:form>
	</div>
	
	<div class="rounded">
		<h2>Transfer to other Client</h2>
		<hr>
		<form:form action="${pageContext.request.contextPath}/transaction/other" method="POST">
			<table class="center" style="width:100%">
				<tr>
					<th>Transfer From</th>
					<td>
						<select name="transferFromAccountId">
							<c:forEach items="${clientAccounts}" var="clientAccount">
								<option value="${clientAccount.accountId}">${clientAccount.bankTypesModel.bankType} - ${clientAccount.accountNo} - $${clientAccount.balance}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>Transfer To</th>
					<td>
						<select name="transferToAccount" required>
							<c:forEach items="${accountsModelList}" var="accountsModel">
								<option value="${accountsModel.accountId}">${accountsModel.clientsModel.firstName} ${accountsModel.clientsModel.lastName} - ${accountsModel.bankTypesModel.bankType} - ${accountsModel.accountNo}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>Amount</th>
					<td><input name="balance" type="number" step=".01" required /></td>
				</tr>
				<input type="hidden" name="action" value="<%= Constants.TRANSACTION_ACTION_OTHER_TRANSFER %>" />
				<tr>
					<th></th>
					<td><input type="submit" value="Transfer" /></td>
				</tr>
			</table>
		</form:form>
	</div>
	
	<div class="rounded">
		<h2>Deposit</h2>
		<hr>
		<form:form action="${pageContext.request.contextPath}/transaction/deposit" method="POST">
			<table class="center" style="width:100%">
				<tr>
					<th>Deposit To</th>
					<td>
						<select name="transferFromAccountId">
							<c:forEach items="${clientAccounts}" var="clientAccount">
								<option value="${clientAccount.accountId}">${clientAccount.bankTypesModel.bankType} - ${clientAccount.accountNo} - $${clientAccount.balance}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>Amount</th>
					<td><input name="balance" type="number" step=".01" required /></td>
				</tr>
				<input type="hidden" name="action" value="<%= Constants.TRANSACTION_ACTION_DEPOSIT %>" />
				<tr>
					<th></th>
					<td><input type="submit" value="Deposit" /></td>
				</tr>
			</table>
		</form:form>
	</div>
	
	<div class="rounded">
		<h2>Withdraw</h2>
		<hr>
		<form:form action="${pageContext.request.contextPath}/transaction/withdraw" method="POST">
			<table class="center" style="width:100%">
				<tr>
					<th>Withdraw From</th>
					<td>
						<select name="transferFromAccountId">
							<c:forEach items="${clientAccounts}" var="clientAccount">
								<option value="${clientAccount.accountId}">${clientAccount.bankTypesModel.bankType} - ${clientAccount.accountNo} - $${clientAccount.balance}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>Amount</th>
					<td><input name="balance" type="number" step=".01" required /></td>
				</tr>
				<input type="hidden" name="action" value="<%= Constants.TRANSACTION_ACTION_WITHDRAW %>" />
				<tr>
					<th></th>
					<td><input type="submit" value="Withdraw" /></td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>