<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- /WEB-INF/view/joinForm.jsp -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="join" method="post">
<p>
	<input type = "text" name="loginId" value = "${param.loginId }" placeholder="아이디">
	<c:if test="${errors.loginId}">ID를 입력하세요</c:if>
	<c:if test="${errors.duplicateId}">이미 사용중인 아이디입니다.</c:if>
</p>
<p>
	<input type = "text" name="name" value = "${param.name }" placeholder="이름">
	<c:if test="${errors.name}">이름을 입력하세요</c:if>
</p>
<p>
	<input type = "text" name="password" value = "${param.password }" placeholder="비밀번호">
	<c:if test="${errors.password}">비밀번호를 입력하세요</c:if>
</p>
<p>
	<input type = "text" name="confirmPassword" value = "${param.confirmPassword }" placeholder="비밀번호확인">
	<c:if test="${errors.confirmPassword}">비밀번호 확인을 입력하세요</c:if>
	<c:if test="${errors.notMatch}">비밀번호가 일치하지 않습니다</c:if>
</p>
<input type="submit" value="가입">
</form>
</body>
</html>    