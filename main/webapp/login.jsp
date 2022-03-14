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
    <%--<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>--%>
    <%--<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>--%>

    <link rel="stylesheet" href="css/style.css">
    <script type="text/javascript" src="js/loginPage.js"></script>
    <script src="js/jQueryFile.js"></script>

</head>
<body>
<%--
<form action="loginProcess" method="post">
&lt;%&ndash;<form>&ndash;%&gt;
    <label>Name</label>
    <input type="text" name="userName" id="userName" autocomplete="off" required><br><br>
    <label>Password</label>
    <input type="password" name="password" id="password" autocomplete="off" required><br><br>
    &lt;%&ndash;<button type="button" onclick="logIn()">LoginAction</button>&ndash;%&gt;
    <button type="submit">LoginAction</button>
</form>--%>

<form action="loginProcess" method="post">

<div id="formWrapper">

    <div id="form">
        <div class="logo">
            <h1 class="text-center head">Login</h1>
        </div>
        <div class="form-item">
            <p class="formLabel">User Name</p>
            <input type="text" name="userName" id="userName" class="form-style" autocomplete="off" required/>
        </div>
        <div class="form-item">
            <p class="formLabel">Password</p>
            <input type="password" name="password" id="password" class="form-style" required />
            <!-- <div class="pw-view"><i class="fa fa-eye"></i></div> -->
            <%--<p><a href="#" ><small>Forgot Password ?</small></a></p>--%>
        </div>
        <div class="form-item">
            <%--<p class="pull-left"><a href="#"><small>Register</small></a></p>--%>
            <input type="submit" class="login pull-right" value="Log In">
            <div class="clear-fix"></div>

        </div>
    </div>
</div>

</form>

</body>
</html>
