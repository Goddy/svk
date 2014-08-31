<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css'/>" />
<script src="<c:url value='/resources/js/jquery-1.8.2.js'/>" type="text/javascript" ></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SVK Voetbal</title>
</head>
<body>
<div class="wrapper">
   <div class="divmenu">
    <ul id="menu">
    	<li><a href="/home.html">Home</a></li>
        <li><a href="/news/news.html">Nieuws</a></li>
    	<li><a href="/account/edit.html">Profiel</a></li>
    </ul>    
   </div>
    
    <div class="headerimage">
        <img src="<c:url value='/resources/images/svk.gif'/>" alt="SVK">
    </div>
    
    <div class="login">
    <security:authorize access="isAuthenticated()">
    <div class="loggedin">
        <spring:message code="text.loggedin" /> <security:authentication property="principal.firstName" />. <a href="<c:url value="j_spring_security_logout" />" > <spring:message code="text.logout" /> </a>
    </div>
    </security:authorize>
    
    <security:authorize access="isAnonymous()"> 
    <form action="<c:url value='j_spring_security_check' />" method="post" name="login_form">
    <table>
    	<tr><td><p><spring:message code="label.email" /></p></td><td><input type="text" name="j_username" value="test@example.com"/></td></tr>
        <tr><td><p><spring:message code="label.password" /></p></td><td><input type="password" name="j_password" id="j_password" value="P@ssword"/></td>        </tr>
        <tr><td colspan="2"><input type="submit" name="submit_login" value="<spring:message code="button.login" />" /></td></tr>
        <tr><td colspan="2"><p>Not registered? Register <a href="<c:url value='/account/register.html' />">here</a></td></tr>
        </table>
	</form>
	</security:authorize>
	</div>
                        <!-- main content -->
	<div class="mainbody">
						<!-- main body -->
						