<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
	<head>
		<style>
		ul {
		  list-style-type: none;
		  margin: 0;
		  padding: 0;
		  overflow: hidden;
		  background-color: #333;
		}
		
		li {
		  float: left;
		  border-right:1px solid #bbb;
		}
		
		li:last-child {
		  border-right: none;
		}
		
		li a {
		  display: block;
		  color: white;
		  text-align: center;
		  padding: 14px 16px;
		  text-decoration: none;
		}
		
		li a:hover:not(.active) {
		  background-color: #111;
		}
		
		.active {
		  background-color: #04AA6D;
		}
		</style>
	</head>
<body>
	<ul>
	  <li><a href="${pageContext.request.contextPath}/client/home">Home</a></li>
	  <li><a href="${pageContext.request.contextPath}/transaction">Make Transactions</a></li>
	  <li><a href="${pageContext.request.contextPath}/transaction/history">Transaction History</a></li>
	  <li style="float:right"><a href="${pageContext.request.contextPath}/client/logout">Logout</a></li>
	</ul>
</body>
</html>


