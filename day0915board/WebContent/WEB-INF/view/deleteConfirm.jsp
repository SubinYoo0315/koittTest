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
<form action="delete" method="post">
	<input type="hidden" name="no" value = "${delReq.articleId}">
	<p>
		게시글 번호 : ${delReq.articleId}를 정말 삭제하시겠습니까?		
	</p>
	<input type="submit" value="글 삭제">
</form>
</body>
</html>    