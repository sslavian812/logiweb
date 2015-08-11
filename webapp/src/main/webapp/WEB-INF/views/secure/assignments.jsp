<%--
  Created by IntelliJ IDEA.
  User: viacheslav
  Date: 11.07.2015
  Time: 0:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Assignments Page</title>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css">
</head>
<body>
<script src="/resources/core/js/validation.js"></script>
<%@include  file="/WEB-INF/views/navBar.html" %>

<c:if test="${fn:length(assignments) > 0}">
    <table class="table table-striped">
        <c:forEach var="assignment" items="${assignments}">
            <tr>
                <td>${assignment.orderIdentifier}</td>
                <td>${assignment.orderStatus}</td>
                <td>${assignment.truckRegistrationNumber}</td>
                <td>
                    <c:forEach items="${assignment.coDrivers}" var="driver" varStatus="status">
                        <c:out value="${driver.personalNumber}"/>
                        <c:if test="${!status.last}">,</c:if>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>


</body>
</html>
