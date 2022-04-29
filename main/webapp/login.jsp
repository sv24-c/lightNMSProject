<%--
  Created by IntelliJ IDEA.
  User: smit
  Date: 10/3/22
  Time: 10:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

    <link rel="stylesheet" href="css/style.css">

    <link rel="stylesheet" href="/css/toastr.min.css">

    <script src="js/jQueryFile.js"></script>

    <script type="text/javascript" src="/js/main.js"></script>

    <script type="text/javascript" src="js/loginPage.js"></script>

    <script type="text/javascript" src="/js/mainDashboard.js"></script>

    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

    <script type="text/javascript" src="/js/toastr.min.js"></script>

    <script>

        $(document).ready(function () {

            login.onload();

        });

    </script>
</head>
<body>

<form id="loginForm" method="post" onsubmit="return false">

    <div id="formWrapper">

        <div id="form">

            <div class="logo">
                <h1 class="text-center head">Login</h1>
            </div>

            <div id="validationText" style="display: none">
                Wrong Username or Password
            </div>

            <div class="form-item">
                <p class="formLabel">User Name</p>
                <input type="text" name="userName" id="userName" class="form-style" autocomplete="off" required/>
            </div>
            <div class="form-item">
                <p class="formLabel">Password</p>
                <input type="password" name="password" id="password" class="form-style" required />
                <br><input type="checkbox" id="show-password"> <label> Show Password</label>
            </div>

            <div class="form-item">
                <input type="submit" class="login pull-right" id="submit-btn" value="Log In" onclick="login.onLogin()">
                <%--<div class="clear-fix"></div>--%>
            </div>
        </div>
    </div>

</form>

</body>
</html>
