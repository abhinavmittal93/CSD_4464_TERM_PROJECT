<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add New Client</title>
<style type="text/css">
	.middle {
      display: table;
      margin-right: auto;
      margin-left: auto;
  	}
</style>
</head>
<body align="center">
	<h1>Add New Client</h1>
	<hr>
	<div class="middle">
		<form:form method="post" action="addClient">
			<table>
				<tr>
					<td>
						First Name
					</td>
					<td>
						<input type="text" name="firstName" required />
					</td>
				</tr>
				<tr>
					<td>
						Last Name
					</td>
					<td>
						<input type="text" name="lastName" required />
					</td>
				</tr>
				<tr>
					<td>
						Gender
					</td>
					<td>
						<select name="gender" required>
							<option value="M">Male</option>
							<option value="F">Female</option>
							<option value="O">Other</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						Email
					</td>
					<td>
						<input type="email" name="email" required />
					</td>
				</tr>
				<tr>
					<td>
						Phone
					</td>
					<td>
						<input type="tel" name="phone" required />
					</td>
				</tr>
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
						<input type="submit" value="Add Client" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>