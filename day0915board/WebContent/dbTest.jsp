<%@page import="java.sql.SQLException"%>
<%@page import="jdbc.Connection.ConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>dbTest.jsp</title>
</head>
<body>
DB확인!
<%
	try(Connection conn = ConnectionProvider.getConnection()){
		out.println("커넥션 성공!");
	}catch(SQLException e){
		out.println("커넥션 실패!" + e.getMessage());
	}
%>
</body>
</html>    