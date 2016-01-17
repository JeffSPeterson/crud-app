<%-- 
    Document   : create
    Created on : Apr 22, 2011, 3:24:13 PM
    Author     : FMilens
--%>

<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create User</title>
    </head>
    <body>
        <h1>Create User</h1>
        <c:if test="${fn:length(errors) gt 0}">
            <p>Please correct the following errors in your submission:</p>
            <ul>
                <c:forEach items="${errors}" var="error">
                    <li>${error}</li>
                </c:forEach>
            </ul>
        </c:if>
        <form action="${pageContext.request.contextPath}/user/create" method="POST">
            <br/>
            <label for="username">Username:</label>
            <input type="text" name="username" value="${user.username}"/>
            <br/>
            <label for="password">Password:</label>
            <input type="text" name="password" value="${user.password}"/>
            <br/>
            <label for="emailAddress">Email Address:</label>
            <input type="text" name="emailAddress" value="${user.emailAddress}"/>
		<form:errors path="user" cssClass="error" />
			<br/>
            <input type="submit" name="Submit" value="Submit"/>
            <button type="button" name="back" onclick="history.back()">Cancel</button>
        </form>
    </body>
</html>
