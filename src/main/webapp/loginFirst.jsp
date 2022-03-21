<!DOCTYPE html>
<html lang="en">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@include file="includes/header.jsp" %>
<body>
<%@include file="includes/topNav.jsp" %>

<br/>
<div class="container">
    <div class="col s12 m7">
        <h2 class="header red">Error When Trying to Login</h2>
        <p class="light">Type the correct User and Password</p>
        <div class="card horizontal">
            <div class="card-image">
                <img src="https://placeimg.com/500/250/tech">
            </div>
            <div class="card-stacked">
                <form method="GET" action="./main-controller">
                    <div class="card-content primary-text-color">
                        <span class="card-title ">Admin Login</span>
                        <input type="text" name="username" placeholder="username" value="admin"/><br/>
                        <input type="password" name="password" placeholder="password" value="admin"/>
                    </div>
                    <div class="card-action">
                        <a class="waves-effect waves-light btn accent-color right"
                           onclick="document.forms[0].submit()"><i
                                class="material-icons right">
                            forward</i>
                            login</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<!--  Scripts-->
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="js/materialize.js"></script>
<script src="js/init.js"></script>

</body>
</html>




