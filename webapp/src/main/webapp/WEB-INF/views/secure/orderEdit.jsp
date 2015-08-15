<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: viacheslav
  Date: 12.07.2015
  Time: 0:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit order</title>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css">
</head>
<body>
<%@include file="/WEB-INF/views/navBar.html" %>
<script src="/resources/core/js/validation.js"></script>

<legend>Edit order: ${order.orderIdentifier}</legend>

<label>Order identifier:</label>
<input type="text" name="oldOrderIdentifier" value="${order.orderIdentifier}" readonly>
<br>


<table class="table table-striped">
    <tr>
        <td>identifier</td>
        <td>denomination</td>
        <td>weight(kg)</td>
        <td>status</td>
        <td>
            delete
        </td>
    </tr>

    <c:forEach var="cargo" items="${cargoes}" varStatus="loop">
        <tr class="${colors.get(loop.index)}">
            <td>${cargo.cargoIdentifier}</td>
            <td>${cargo.denomination}</td>
            <td>${cargo.weight}</td>
            <td>${cargo.status}</td>
            <td>
                <form method="post"
                      action="/secure/orders/${order.orderIdentifier}/cargo/delete/${cargo.cargoIdentifier}">
                    <button type="submit" class="btn btn-danger">delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>

    <form method="post" id="adder" onsubmit="return validateCargoForm()"
          action="/secure/orders/add/${order.orderIdentifier}/cargo">
        <tr>
            <td>
                <input type="text" value="${generated}" name="cargoIdentifier">
            </td>
            <td>
                <input required="true" type="text" value="" name="denomination">
            </td>
            <td>
                <input required="true" type="text" value="" name="weight">
            </td>
            <td>PREPARED</td>
            <td>
                <button type="submit" class="btn btn-success">add</button>
            </td>
        </tr>
    </form>

    <form method="get" action="/secure/orders/">
        <button type="submit" class="btn btn-info">OK</button>
    </form>

</table>


</body>
</html>
