<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
[<c:forEach items="${requestScope.messages}" var="sp">{"contenu" : "${sp.contenu}", "idMembre" : "${sp.idMembre}", "idEvent" : "${sp.idEvent}", "Date" : "${sp.date}"},</c:forEach>]