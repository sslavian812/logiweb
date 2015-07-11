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
            <td>First name last name</td>
            <td>personal number</td>
            <td>
                delete if possible
            </td>
        </tr>
        <c:forEach var="driver" items="${drivers}">
            <tr>
                <td>${driver.firstName} ${driver.lastName}</td>
                <td>${driver.personalNumber}</td>
                <td>
                    <form method="post"
                          action="/secure/deleteDriver?driver=${driver.personalNumber}">
                        <button type="submit" class="btn btn-danger">delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

</c:if>

<%--<hr>--%>

<form class="form-control" id="adder" method="post" onsubmit="return validateDriverForm()" action="/secure/addDriver">
    <fieldset>
        <legend>Add new driver</legend>
        <label>First Name</label>
        <input type="text" placeholder="Type something…" name="firstName">
        <span class="help-block">Example: Ivan</span>

        <label>Last Name</label>
        <input type="text" placeholder="Type something…" name="lastName">
        <span class="help-block">Example: Bezdomniy</span>

        <label>Personal Number</label>
        <input type="text" placeholder="Type something…" name="personalNumber">
        <span class="help-block">Example: abacaba, 11111, etc...</span>

        <button type="submit" class="btn">Submit</button>
    </fieldset>
</form>
</body>
</html>
