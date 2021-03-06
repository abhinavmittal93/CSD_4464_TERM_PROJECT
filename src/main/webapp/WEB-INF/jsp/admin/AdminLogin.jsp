<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Login</title>
<style type="text/css">
	.middle {
      display: table;
      margin-right: auto;
      margin-left: auto;
  	}
</style>
</head>
<body align="center">
	<h1>Admin Login</h1>
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
		<form:form method="post" action="${pageContext.request.contextPath}/admin/login">
			<table>
				<tr>
					<td>
						Username
					</td>
					<td>
						<input type="text" name="username" />
					</td>
				</tr>
				<tr>
					<td>
						Password
					</td>
					<td>
						<input type="password" name="password" />
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input type="submit" value="Login" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>