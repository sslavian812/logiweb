<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: viacheslav
  Date: 02.07.2015
  Time: 2:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>welcome!</title>
</head>
<body>

your login is : <c:out value="${login}" />
<br/>
yout password is : <c:out value="${password}"/>

</body>
</html>
