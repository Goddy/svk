<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css'/>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SVK Voetbal</title>
</head>
<body>
<div class="wrapper">
   <div class="divmenu">
    <ul id="menu">
    	<li><a href="home.html">Home</a></li>
    	<li><a href="login.html">Login</a></li>
    </ul>    
   </div>
    
    <div class="headerimage">
        <img src="<c:url value='/resources/images/svk.gif'/>" alt="SVK">
    </div>
    
    <div class="login">
    <security:authorize access="isAuthenticated()">
    <div class="loggedin">
        Ingelogd als <security:authentication property="principal.username" />. <a href="<c:url value="j_spring_security_logout" />" > Uitloggen</a>
    </div>
    </security:authorize>
    
    <security:authorize access="isAnonymous()"> 
    <form action="<c:url value='j_spring_security_check' />" method="post" name="login_form">
    <table>
    	<tr>
    		<td>
             	<p>Email:</p> 
            </td>
            <td>
             	<input type="text" name="j_username" value="test@example.com"/>
        	</td>
        </tr>
        <tr>
        	<td>
             	<p>Password:</p> 
            </td>
         	<td>
             	<input type="password" name="j_password" id="j_password" value="p@ssword"/>
            </td>
        </tr>
        <tr>
        	<td colspan="2">
             	<input type="submit" name="submit_login" value="Login" />
            </td>
        </tr>
        <tr>
        	<td colspan="2">
             	<p>Not registered? Register <a href="../login/register.php">here</a>
            </td>
        </tr>
	</table>
	</form>
	</security:authorize>
	</div>
                        <!-- main content -->
	<div class="mainbody">
						<!-- main body -->
						