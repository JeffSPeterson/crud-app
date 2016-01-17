

<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
    <link href="<c:url value="/resources/css/myCSS.css" />" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.8.3.min.js" type="text/javascript"></script>

    <link rel="stylesheet" type="text/css" href="<c:url value = "/resources/css/myCSS.css" />" />
    <script src="js/jquery.min.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>

 <script type="text/javascript">
(function($,W,D)
		{
		    var JQUERY4U = {};

		    JQUERY4U.UTIL =
		    {
		        setupFormValidation: function()
		        {
		            //form validation rules
		            $("#register-form").validate({
		                rules: {
		                    username: "required",
		                    emailAddress: {
		                        required: true,
		                        email: true
		                    },
		                    password: {
		                        required: true,
		                        minlength: 5
		                    },
		                    agree: "required"
		                },
		                messages: {
		                    username: "Please enter your firstname",
		                    password: {
		                        required: "Please provide a password",
		                        minlength: "Your password must be at least 5 characters long"
		                    },
		                    emailAddress: "Please enter a valid email address",
		                    agree: "Please accept our policy"
		                },
		                submitHandler: function(form) {
		                    form.submit();
		                }
		            });
		        }
		    }

		    //when the dom has loaded setup form validation rules
		    $(D).ready(function($) {
		        JQUERY4U.UTIL.setupFormValidation();
		    });

		})(jQuery, window, document);</script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Would you please sign in?</title>
    </head>
    <body>
        <h1>Sign in:</h1>
        <c:if test="${fn:length(errors) gt 0}">
            <p>Please correct the following errors in your submission:</p>
            <ul>
                <c:forEach items="${errors}" var="error">
                    <li>${error}</li>
                </c:forEach>
            </ul>
        </c:if>
		<form  id="register-form" action="${pageContext.request.contextPath}/user/signin" method="POST">
    
        <fieldset>

            <div class="fieldgroup">
                <label for="username">First Name</label>
                <input type="text" name="username"/>
            </div>


            <div class="fieldgroup">
                <label for="password">Password</label>
                <input type="password" name="password"/>
            </div>

            <div class="fieldgroup">
                <input type="submit" value="Submit" class="submit"/>
            </div>

        </fieldset>
    

        <div class="fieldgroup">
            <p><a href="${pageContext.request.contextPath}/user/create">Create User</a></p>
        </div>
</form>
    </body>
</html>