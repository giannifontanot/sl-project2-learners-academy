<!DOCTYPE html>
<html lang="en">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@include file="includes/header.jsp"%>
<body>
<%@include file="includes/topNav.jsp"%>

<br/>
<div class="container">

    <div class="row">
        <div class="col s12 m6">
            <h5 class="center">Welcome to Computer College</h5>

            <p class="light">In order to use the roster and update information you need to login first.</p>
            <form method="GET" action="./main-controller">
                <div class="card z-depth-1">
                    <div class="card-content primary-text-color">
                        <span class="card-title ">Admin Login</span>

                        <input type="text" name="username" placeholder="username" value=""/><br/>
                        <input type="text" name="password" placeholder="password" value=""/>
                        <input type="text" name="page" placeholder="dashboard" value="dashboard"/>
                    </div>
                    <div class="card-action">
                        <a class="waves-effect waves-light btn accent-color"
                           onclick="document.forms[0].submit()"><i
                                class="material-icons right">
                            forward</i>
                            login</a>
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>




<!--  Scripts-->
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="js/materialize.js"></script>
<script src="js/init.js"></script>

</body>
</html>




