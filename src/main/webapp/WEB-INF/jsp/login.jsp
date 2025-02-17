<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<c:set var="title" value="LogIn" scope="page"/>
<%@include file="head.jspf" %>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/allstyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body class="login-page">

<c:set var="currentPage" value="account" scope="page"/>
<%@include file="header.jspf" %>


<div class="ls_container shadow">
    <p class="ls_title">Log In</p>
    <form class="ls_form" action="${pageContext.request.contextPath}/account/login" method="post">
        <input name="login" class="ls_et form-control" placeholder="Login">
        <input type="password" name="password" class="ls_et form-control" placeholder="Password">
        <input type="submit" class="ls_btn btn btn-primary" value="LogIn">
    </form>
    <c:if test="${param.err ne null}">
        <p class="ls_err">You entered wrong login or password</p>
    </c:if>
    <p class="ls_text">Don't have an account? <a href="${pageContext.request.contextPath}/account/signup">Sign Up</a>
    </p>
</div>

</body>
</html>
