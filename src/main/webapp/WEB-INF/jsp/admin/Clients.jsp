<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Clients</title>
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
		<h1>Clients</h1>
	</div>
	<div>
		<table class="center" style="width:100%">
			<tr>
				<th>Name</th>
				<th>Gender</th>
				<th>Email</th>
				<th>Phone</th>
				<th>Username</th>
			</tr>
			<c:forEach items="${clientsModels}" var="clientsModel">
			    <tr>
					<td>
						<a href="accounts/${clientsModel.clientId}">${clientsModel.firstName} ${clientsModel.lastName}</a>
					</td>
					<td>
						<c:choose>
							<c:when test="${clientsModel.gender eq 'M'}">Male</c:when>
							<c:when test="${clientsModel.gender eq 'F'}">Female</c:when>
							<c:otherwise>Other</c:otherwise>
						</c:choose>
					</td>
					<td>${clientsModel.email}</td>
					<td>${clientsModel.phone}</td>
					<td>${clientsModel.username}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>