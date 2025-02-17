<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<c:set var="title" value="Receipts" scope="page"/>
<%@include file="head.jspf" %>
<body>

<c:set var="currentPage" value="receipts" scope="page"/>
<%@include file="header.jspf" %>

<table class="table">
    <thead>
    <tr>
        <th>id</th>
        <th>userId</th>
        <th>managerId</th>
        <th>status</th>
        <th>createDate</th>
        <th>products</th>
        <th>total</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${receipts}" var="receipt">
        <jsp:useBean id="receipt" class="com.denbondd.restaurant.db.entity.Receipt"/>
        <tr>
            <th>${receipt.id}</th>
            <th>${receipt.userId}</th>
            <th>${receipt.managerId}</th>
            <th>
                    ${receipt.status}
                <c:if test="${receipt.status.ordinal() != 4 && (receipt.managerId == 0 || receipt.managerId == user.id)}">
                    <form method="post" action="${pageContext.request.contextPath}/receipts">
                        <input value="${receipt.id}" name="id" style="display: none">
                        <input value="${receipt.status.ordinal() + 2}" name="status-id" style="display: none">
                        <input type="submit" value="Next status">
                    </form>
                </c:if>
            </th>
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

</body>
</html>
