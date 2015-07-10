<%--
  Created by IntelliJ IDEA.
  User: viacheslav
  Date: 10.07.2015
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Trucks</title>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css">
</head>
<body>


<c:if test="${fn:length(trucks) > 0}">
    <script src="resources/core/js/validation.js"></script>
    <table class="table table-striped">
        <c:forEach var="truck" items="${trucks}">
            <tr>
                <td>${truck.crewSize}</td>
                <td> ${truck.capacity}</td>
                <td> ${truck.status}</td>
                <td>${truck.registrationNumber}</td>
                <td>
                    <form method="post"
                          action="/secure/deleteTruck?registrationNumber=${truck.registrationNumber}">
                        <button type="submit" class="btn btn-danger">delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

</c:if>

<%--<hr>--%>

<form class="form-control" id="adder" method="post" onsubmit="return validateTruckForm()" action="/secure/addTruck">
    <fieldset>
        <legend>Add new truck</legend>
        <label>Crew size</label>
        <input type="text" placeholder="Type something…" name="crewSize">
        <span class="help-block">Example: 1</span>

        <label>Registration number</label>
        <input type="text" placeholder="Type something…" name="registrationNumber">
        <span class="help-block">Example: xx77777</span>

        <label>capacity (kg)</label>
        <input type="text" placeholder="Type something…" name="capacity">
        <span class="help-block">Example: 5000</span>

        <button type="submit" class="btn">Submit</button>
    </fieldset>
</form>
</body>
</html>
