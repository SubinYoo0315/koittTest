<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="login" method="post">
<c:if test="${errors.idOrPwNotMatch}">아이디와 비밀번호가 맞기 않습니다.</c:if>
<p>
	<input type = "text" name = "loginId" value = "${param.loginId}" placeholder="아이디">
	<c:if test="${erros.loginId}">아이디를 입력하세요</c:if>
</p>
<p>
	<input type = "password" name = "password" value = "${param.password}" placeholder="비밀번호">
	<c:if test="${erros.password}">비밀번호를 입력하세요</c:if>
</p>
<input type = "submit" value = "로그인">
</form>
</body>
</html>    