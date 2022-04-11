<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Utility Bills</title>
<%@include file="AdminMenu.jsp" %>
<style type="text/css">
	.middle {
      display: table;
      margin-right: auto;
      margin-left: auto;
  	}
</style>
</head>
<body align="center">
	<h1>Create Utility Bills</h1>
	<c:choose>
			<c:when test="${param.status eq 'success'}">
				<span style="color:green;"><c:out value="${param.message}"/></span>
			</c:when>
			<c:otherwise>
				<span style="color:red;"><c:out value="${param.message}"/></span>
			</c:otherwise>
		</c:choose>
	<hr>
	<div class="middle">
		<form:form method="post" action="${pageContext.request.contextPath}/admin/utilities/bills/create">
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
						Utility
					</td>
					<td>
						<select name="utilitiesModel.utilityId" required>
							<c:forEach items="${utilitiesModels}" var="utilitiesModel">
								<option value="${utilitiesModel.utilityId}">${utilitiesModel.utilityType} - ${utilitiesModel.utilityName}</option>
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
						<input type="submit" value="Create Bill" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>