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
<form action="write" method="post">
<p>
	<input type="text" name="title" value="${param.title}" placeholder="제목">
	<c:if test="${errors.title}">제목을 입력하세요!</c:if>
</p>
<p>
	<textarea rows="10" cols="30" name = "content" placeholder="내용">${param.content }</textarea>
</p>
<input type="submit" value="등록">
</form>
</body>
</html>    