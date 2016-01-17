<%-- 
    Document   : edit
    Created on : Apr 22, 2011, 3:04:46 PM
    Author     : FMilens
--%>

<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${client.companyName}</title>
    </head>
    <body>
        <h1>${client.companyName}</h1>
        <c:if test="${fn:length(errors) gt 0}">
            <p>Please correct the following errors in your submission:</p>
            <ul>
                <c:forEach items="${errors}" var="error">
                    <li>${error}</li>
                </c:forEach>
            </ul>
        </c:if>
        <form action="${pageContext.request.contextPath}/client/view" method="POST">
            <input type="hidden" name="clientId" value="${client.clientId}"/>
			<br /> 
			<label for="phoneNumber">Phone Number:</label>
            <a>${client.phoneNumber}</a>
            <br/>
            <label for="website">Website:</label>
            <a>${client.website}</a>
            <br/>
            <label for="streetAddress">Street Address:</label>
            <a>${client.streetAddress} </a>
            <br/>
            <label for="city">City:</label>
            <a>${client.city}</a>
            <br/>
            <label for="state">State:</label>
            <a>${client.state}</a>
            <br/>
            <label for="zipCode">Zip Code:</label>
            <a>${client.zipCode}</a>
            <br/>
            <label for="contacts">Contacts:</label>
            <br />
			<c:forEach items="${contactList}" var="contact">
    			${contact.value}
    			<br/>
			</c:forEach>
            <button type="button" name="back" onclick="history.back()">Back To People</button>
        </form>
    </body>
</html>
