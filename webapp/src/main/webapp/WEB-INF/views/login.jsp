<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Logiweb</title>
    <link rel="stylesheet" href="../../resources/core/css/style.css">

    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body>
<script src="../../resources/core/js/validation.js"></script>
<section class="container">
    <div class="login">
        <h1>Login to Logiweb</h1>

        <%--onsubmit="return validateLoginForm()"--%>
        <%--form-control--%>
        <form id="login_form" method="post"
              action="<c:url value="/j_spring_security_check"></c:url>">
            <%--<select class="form-control" name="j_username">--%>
            <%--<option value="manager">Manager</option>--%>
            <%--<option value="driver">Driver</option>--%>
            <%--</select>--%>
            <%--<p><input type="text" name="login"  value="" placeholder="Username or Personal Identifier" required></p>--%>
            <p><input type="text" name="username" placeholder="username"></p>

            <p><input type="password" name="password" placeholder="password or PN"></p>

            <p class="remember_me">
                <label>
                    <input type="checkbox" name="_spring_security_remember_me" id="remember_me">
                    Remember me on this computer
                </label>
            </p>

            <p class="submit"><input name="submit" type="submit" value="Login"></p>
            <c:if test="${param.error == true}">
                <div class="alert alert-warning">
                    Invalid username or password.
                </div>
            </c:if>
        </form>
    </div>

    <%--<div class="login-help">--%>
    <%--<p>Forgot your password? <a href="/forgot">Click here to reset it</a>.</p>--%>
    <%--</div>--%>
</section>

<%--<section class="about">--%>
<%--<p class="about-links">--%>
<%--<a href="http://www.cssflow.com/snippets/login-form" target="_parent">View Article</a>--%>
<%--<a href="http://www.cssflow.com/snippets/login-form.zip" target="_parent">Download</a>--%>
<%--</p>--%>
<%--<p class="about-author">--%>
<%--&copy; 2012&ndash;2013 <a href="http://thibaut.me" target="_blank">Thibaut Courouble</a> ---%>
<%--<a href="http://www.cssflow.com/mit-license" target="_blank">MIT License</a><br>--%>
<%--Original PSD by <a href="http://www.premiumpixels.com/freebies/clean-simple-login-form-psd/" target="_blank">Orman Clark</a>--%>
<%--</section>--%>

</body>
</html>
