<%--
  Created by IntelliJ IDEA.
  User: viacheslav
  Date: 14.08.2015
  Time: 23:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit truck</title>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css">
</head>
<body>
<%@include file="/WEB-INF/views/navBar.html" %>
<script src="/resources/core/js/validation.js"></script>
<form id="adder" class="form-control" method="post" onsubmit="return validateTruckForm()" action="/secure/trucks/update">
    <fieldset>
        <legend>Edit truck: ${truck.registrationNumber}</legend>

        <label>old trucks registration number:</label>
        <input type="text" name="oldRegistrationNumber" value="${truck.registrationNumber}" readonly>
        <br>

        <label>crew size</label>
        <input required="true" type="text" value="${truck.crewSize}" name="crewSize">
        <span class="help-block">Ex: 2</span>

        <label>capacity</label>
        <input required="true" type="text" value="${truck.capacity}" name="capacity">
        <span class="help-block">Ex: 5000</span>

        <label>status:</label>
        <div class="radio">
            <label>
                <input type="radio" value="BROKEN" name="status">
                BROKEN
            </label>

        </div>
        <div class="radio">
            <label>
                <input type="radio" value="INTACT" name="status" checked>
                INTACT
            </label>
        </div>

        <label>new registration number</label>
        <input required="true" type="text" value="${truck.registrationNumber}" name="registrationNumber">
        <span class="help-block">Ex: xx11111</span>

        <button type="submit" class="btn">Submit</button>
    </fieldset>
</form>
</body>
</html>
