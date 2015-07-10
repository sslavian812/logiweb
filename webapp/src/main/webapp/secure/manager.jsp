<%--
  Created by IntelliJ IDEA.
  User: viacheslav
  Date: 08.07.2015
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager</title>
    <%--<meta http-equiv="refresh" content="0; url=/login" />--%>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/core/css/style.css">
</head>
<body>
<section class="container">
    <div class="login" >
        <a href="/secure/showDrivers">
            <button type="button" class="btn btn-info btn-block submit">Drivers</button>
        </a>
        <a href="/secure/showOrders">
            <button type="button" class="btn btn-info btn-block">Orders</button>
        </a>
        <a href="/secure/showTrucks">
            <button type="button" class="btn btn-info btn-block">Trucks</button>
        </a>
        <%--<a href="/secure/showAssignments">--%>
        <a href="">
            <button type="button" disabled="disabled" class="btn btn-info btn-block">Assign</button>
        </a>
    </div>
</section>
</body>
</html>
