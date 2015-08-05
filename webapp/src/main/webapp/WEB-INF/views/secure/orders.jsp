<%--
  Created by IntelliJ IDEA.
  User: viacheslav
  Date: 09.07.2015
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Orders</title>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css">
</head>
<body>
<script src="/resources/core/js/validation.js"></script>
<%@include  file="/WEB-INF/views/navBar.html" %>

<c:if test="${fn:length(orders) > 0}">

    <table class="table table-striped">
        <tr>
            <td>order identifier</td>
            <td>order status</td>
            <td>edit</td>
            <td>delete if possible</td>
            <td>assign</td>
        </tr>

        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.orderIdentifier}</td>
                <td>${order.status}</td>
                <td>
                    <form method="post"
                          action="">
                        <button type="submit" class="btn btn-warning">edit</button>
                    </form>
                </td>
                <td>
                    <form method="post"
                          action="/secure/orders/delete/${order.orderIdentifier}">
                        <button type="submit" class="btn btn-danger">delete</button>
                    </form>
                </td>
                <td>
                    <form method="post"
                          action="/secure/assignmets/construct/${order.orderIdentifier}">
                        <button type="submit" class="btn btn-success">assign</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

</c:if>

<%--<hr>--%>

<form class="form-control" id="adder" method="post" onsubmit="return validateOrderForm()" action="/secure/orders/add">
    <fieldset>
        <legend>Add new order</legend>
        <label>Order Identifier</label>
        <input type="text" placeholder="Type something…" name="orderIdentifier">
        <span class="help-block">Example: 0xbeadface</span>

        <label>Cargo denomination</label>
        <input type="text" placeholder="Type something…" name="denomination">
        <span class="help-block">Example: bricks</span>

        <label>Cargo weight (kg)</label>
        <input type="text" placeholder="Type something…" name="weight">
        <span class="help-block">Example: 1500</span>

        <button type="submit" class="btn">Submit</button>
    </fieldset>
</form>
</body>
</html>
