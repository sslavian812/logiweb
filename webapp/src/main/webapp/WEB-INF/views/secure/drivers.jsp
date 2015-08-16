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
                    <input required="true" type="text" placeholder="Ex.: Ivan" name="firstName">
                </td>
                <td>
                    <input required="true" type="text" placeholder="Ex.: Ivanov" name="lastName">
                </td>
                <td>
                    <input required="true" type="text" placeholder="Ex.: vania55" value="${generated}" name="personalNumber">
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

        <c:forEach var="driver" items="${drivers}" varStatus="loop">
            <tr class="${colors.get(loop.index)}">
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

<c:if test="${fn:length(drivers) == 0}">
    no drivers yet
</c:if>

</body>
</html>
