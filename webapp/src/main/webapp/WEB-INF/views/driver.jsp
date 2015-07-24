<%--
  Created by IntelliJ IDEA.
  User: viacheslav
  Date: 04.07.2015
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html>
<head>
    <title>Driver Information</title>
    <%--<meta http-equiv="refresh" content="0; url=/login" />--%>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/core/css/style.css">
</head>
<body>
<section class="container">
    <div class="login">
        <p>driver: <c:out value="${assignment.driverPersonalNumber}"/></p>

        <p>truck: <c:out value="${assignment.truckRegistrationNumber}"/></p>

        <p>order: <c:out value="${assignment.orderIdentifier}"/></p>


        <p>involved drivers:</p>
        <table>
            <c:forEach var="codriver" items="${assignment.coDrivers}">
                <tr>
                    <td>${codriver.personalNumber}</td>
                </tr>
            </c:forEach>
        </table>



        <p>cargoes:</p>
        <table>
            <c:forEach var="cargo" items="${assignment.cargos}">
                <tr>
                    <td>${cargo.denomination}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</section>
</body>
</html>
