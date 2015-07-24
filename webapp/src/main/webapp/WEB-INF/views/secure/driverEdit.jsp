<%--
  Created by IntelliJ IDEA.
  User: viacheslav
  Date: 12.07.2015
  Time: 0:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit driver</title>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css">
</head>
<body>
<form class="form-control" method="post" onsubmit="return validateDriverForm()" action="/secure/updateDriver">
    <fieldset>
        <legend>Edit driver: ${driver.personalNumber}</legend>

        <label>Old driver personal Identifier:</label>
        <input type="text" name="oldPersonalNumber" value="${driver.personalNumber}" readonly>
        <br>

        <label>First Name</label>
        <input type="text" value="${driver.firstName}" name="firstName">
        <span class="help-block">Example: Ivan</span>

        <label>Last Name</label>
        <input type="text" value="${driver.lastName}" name="lastName">
        <span class="help-block">Example: Bezdomniy</span>

        <label>Personal Number</label>
        <input type="text" value="${driver.personalNumber}" name="personalNumber">
        <span class="help-block">Example: abacaba, 11111, etc...</span>

        <button type="submit" class="btn">Submit</button>
    </fieldset>
</body>
</html>
