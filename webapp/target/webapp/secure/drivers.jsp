<%--
  Created by IntelliJ IDEA.
  User: viacheslav
  Date: 09.07.2015
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Drivers</title>
</head>
<body>
<table class="table table-striped">
    <c:forEach var="driver" items="${drivers}">
        <tr>
            <td>${driver.firstName} ${driver.lastName}</td>
            <td>${driver.personalNumber}</td>
            <td><a href="/secure/deleteDriver&driver=${driver.personalNumber}">delete</a></td>
        </tr>
    </c:forEach>
</table>

<hr>

<form id="adder" method="post" onsubmit="return validateDriverForm()" action="/secure/addDriver">
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
