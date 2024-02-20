<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css.css" type="text/css">
<title>Messages MongoDB</title>
</head>
<body>

	<h3>Liste des Messages</h3>
	<c:forEach items="${requestScope.messages}" var="sp">
		<h4>${sp.contenu}</h4>
	</c:forEach>

</body>
</html>