<%--
  Created by IntelliJ IDEA.
  User: viacheslav
  Date: 04.07.2015
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<head>
    <title>Order information</title>
</head>
<body>
<p>driver: <c:out value="${driver}" /></p>
<p>truck: <c:out value="${truck}" /></p>
<p>order: <c:out value="${order}" /></p>

<table>
    <c:forEach var="codriver" items="${codrivers}">
        <tr><td>${codriver.personalNumber}</td></tr>
    </c:forEach>
</table>

<table>
    <c:forEach var="cargo" items="${cargos}">
        <tr><td>${cargoo.denomination}</td></tr>
    </c:forEach>
</table>

</body>
</html>
