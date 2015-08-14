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
<%@include file="/WEB-INF/views/navBar.html" %>
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

        <form class="form-control" id="adder" method="post" onsubmit="return validateDriverForm()"
              action="/secure/drivers/add">
            <tr>
                <td>
                    <input type="text" placeholder="name" name="firstName">
                    <span class="help-block">Ex.: Ivan</span>
                </td>
                <td>
                    <input type="text" placeholder="surname" name="lastName">
                    <span class="help-block">Ex.: Ivanov</span>
                </td>
                <td>
                    <input type="text" placeholder="personal number" name="personalNumber">
                    <span class="help-block">Ex.: vania55</span>
                </td>
                <td>
                    UNASSIGNED
                </td>
                <td>
                    <button type="submit" class="btn btn-success">add</button>
                </td>
                <td>
                    <button class="btn btn-danger" disabled>delete</button>
                </td>
            </tr>
        </form>

        <c:forEach var="driver" items="${drivers}">
            <tr>
                <td>${driver.firstName}</td>
                <td>${driver.lastName}</td>
                <td>${driver.personalNumber}</td>
                <td>${driver.driverStatus}</td>
                <td>
                    <form method="post"
                          action="/secure/drivers/edit/${driver.personalNumber}">
                        <button type="submit" class="btn btn-warning">edit</button>
                    </form>
                </td>
                <td>
                    <form method="post"
                          action="/secure/drivers/delete/${driver.personalNumber}">
                        <button type="submit" class="btn btn-danger">delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

</c:if>

</body>
</html>
