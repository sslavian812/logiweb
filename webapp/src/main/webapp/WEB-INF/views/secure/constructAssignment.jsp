<%--
  Created by IntelliJ IDEA.
  User: viacheslav
  Date: 10.07.2015
  Time: 23:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Assign Page</title>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css">
</head>
<body>
<%@include file="/WEB-INF/views/navBar.html" %>

<form class="form-control" method="post" action="/secure/assignments/create">
    <legend>Make new assignment</legend>
    <div>
        <label>Order</label>
        <input type="text" placeholder="${orderIdentifier}" name="orderIdentifier" value="${orderIdentifier}">
    </div>
    <table class="table table-striped">
        <td>
            Trucks:
            <c:forEach var="truck" items="${trucks}">
                <div class="radio">
                    <label>
                        <input type="radio" name="truck" value="${truck.registrationNumber}">
                            ${truck.registrationNumber} : ${truck.crewSize}
                    </label>
                </div>
            </c:forEach>
        </td>
        <td>
            Drivers:
            <c:forEach var="driver" items="${drivers}">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="driver" value="${driver.personalNumber}">
                            ${driver.personalNumber}
                    </label>
                </div>
            </c:forEach>
        </td>
    </table>

    <div align="right">
        <button type="submit" class="btn btn-success">Assign</button>
    </div>
</form>


</body>
</html>
