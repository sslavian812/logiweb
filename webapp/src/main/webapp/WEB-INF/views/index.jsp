<%--
  Created by IntelliJ IDEA.
  User: viacheslav
  Date: 08.07.2015
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Logiweb Start Page</title>
    <%--<meta http-equiv="refresh" content="0; url=secure/manager.jsp" />--%>
    <%--<link rel="stylesheet" href="/resources/core/css/bootstrap.min.css">--%>
    <%--<link rel="stylesheet" href="resources/core/css/style.css">--%>
    <link rel="stylesheet" href="<c:url value="/resources/core/css/bootstrap.min.css"/>" >
    <link rel="stylesheet" href="<c:url value="/resources/core/css/style.css"/>" >

</head>
<body>
<%@include  file="/WEB-INF/views/navBar.html" %>
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
        <a href="/secure/showAssignments">
            <button type="button" class="btn btn-info btn-block">Assignments</button>
        </a>
    </div>
</section>
</body>
</html>
