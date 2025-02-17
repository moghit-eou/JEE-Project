<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="title" value="Account" scope="page"/>
<%@include file="head.jspf" %>
<%--"stylesheet" href="${pageContext.request.contextPath}/css/login.css">--%>
<body>
<c:set var="currentPage" value="account" scope="page"/>
<%@include file="header.jspf" %>

<jsp:useBean id="user" scope="session" type="com.denbondd.restaurant.db.entity.User"/>

<p class="a_login">Bienvenue Mr\Mme ${user.login} :</p>
<div class="a_buttons_container">
    <a href="${pageContext.request.contextPath}/account/change-password" class="a_changepass">Change password</a>
    <a href="${pageContext.request.contextPath}/account?signout=" class="a_changepass">Sign out</a>
</div>

<c:if test="${user.roleId != 2}">
    <div class="a_rec_container">
        <p class="a_rec_title">Your receipts:</p>

        <table class="table table-dark">
            <thead class="table-dark">
            <tr>
                <th>#</th>
                <th>Status</th>
                <th>Date of order</th>
                <th>Products</th>
                <th>Total</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="receipt" items="${receipts}">
                <tr>
                    <th>${receipt.id}</th>
                    <th>${receipt.status.value}</th>
                    <th>${receipt.createDate}</th>
                    <th>
                        <c:forEach var="dish" items="${receipt.dishes}">
                            ${dish.name}: ${dish.price} MAD * ${dish.count}<br>
                        </c:forEach>
                    </th>
                    <th>${receipt.total} MAD</th>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
</c:if>

</body>
</html>
