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
    <title>Drivers</title>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css">

</head>
<body>
<script src="/resources/core/js/validation.js"></script>
<%@include  file="/WEB-INF/views/navBar.html" %>
<c:if test="${fn:length(drivers) > 0}">

    <table class="table table-striped">
        <tr>
            <td>First name</td>
            <td>Last name</td>
            <td>personal number</td>
            <td>status</td>
            <td>edit</td>
            <td>delete if possible</td>
        </tr>
        <c:forEach var="driver" items="${drivers}">
            <tr>
                <td>${driver.firstName}</td>
                <td>${driver.lastName}</td>
                <td>${driver.personalNumber}</td>
                <td>${driver.driverStatusEntity.status}</td>
                <td>
                    <form method="post"
                          action="/secure/editDriver?personalNumber=${driver.personalNumber}">
                        <button type="submit" class="btn btn-warning">edit</button>
                    </form>
                </td>
                <td>
                    <form method="post"
                          action="/secure/deleteDriver?personalNumber=${driver.personalNumber}">
                        <button type="submit" class="btn btn-danger">delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

</c:if>

<%@include  file="/WEB-INF/views/driverForm.html" %>

</body>
</html>
