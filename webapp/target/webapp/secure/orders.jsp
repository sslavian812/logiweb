<%--
  Created by IntelliJ IDEA.
  User: viacheslav
  Date: 09.07.2015
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>
<table class="table table-striped">
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.orderIdentifier}</td>
            <td>${order.status}</td>
        </tr>
    </c:forEach>
</table>

<hr>

<form id="adder" method="post" action="/secure/addDriver">
    <fieldset>
        <legend>Add new order</legend>
        <label>Order identifier</label>
        <input type="text" placeholder="Type something…" name="orderIdentifier">
        <%--<span class="help-block"></span>--%>

        <button type="submit" class="btn">Submit</button>
    </fieldset>
</form>
</body>
</html>
