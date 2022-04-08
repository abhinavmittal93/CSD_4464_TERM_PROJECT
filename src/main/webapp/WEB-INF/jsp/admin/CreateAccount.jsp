<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add New Account</title>
<style type="text/css">
	.middle {
      display: table;
      margin-right: auto;
      margin-left: auto;
  	}
</style>
</head>
<body align="center">
	<h1>Add New Account</h1>
	<hr>
	<div class="middle">
		<form:form method="post" action="${pageContext.request.contextPath}/admin/accounts/create">
			<table>
				<tr>
					<td>
						Client Name
					</td>
					<td>
						${clientsModel.firstName} ${clientsModel.lastName}
						<input type="hidden" name="clientsModel.clientId" value="${clientsModel.clientId}" />
					</td>
				</tr>
				<tr>
					<td>
						Account Type
					</td>
					<td>
						<select name="bankTypesModel.bankTypeId" required>
							<c:forEach items="${bankTypesModels}" var="bankTypesModel">
								<option value="${bankTypesModel.bankTypeId}">${bankTypesModel.bankType}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						Balance
					</td>
					<td>
						<input type="number" name="balance" value="0" required />
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input type="submit" value="Create Account" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>