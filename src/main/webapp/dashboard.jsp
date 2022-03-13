

<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@include file="includes/header.jsp"%>



<body>
<%@include file="includes/topNav.jsp"%>
<h3>Dashboard</h3>

<table width="70%">
    <tr>
        <td><div class="card-panel secondary-text-color">
        <a class="" href='./student-controller'>Student list</a>
        </div></td>
        <td><div class="card-panel secondary-text-color">
            <a href='./teacher-controller'>Teacher list</a>
        </div></td>
    </tr>
    <tr>
        <td><div class="card-panel secondary-text-color">
            <a href='./subject-controller'>Subject list</a>
        </div></td>
        <td><div class="card-panel secondary-text-color">
            <a href='./class-controller'>Class list</a>
        </div></td>
    </tr>
    <tr>
        <td><div class="card-panel secondary-text-color">
            <a href='./-controller'>Student Report</a>
        </div></td>
        <td><div class="card-panel secondary-text-color">
            <a href='./-controller'>Class Report</a>
        </div></td>
    </tr>
    <tr>
        <td><div class="card-panel secondary-text-color">
            <a href='./-controller'>Assign Subjects to Class</a>
        </div></td>
        <td><div class="card-panel secondary-text-color">
            <a href='./-controller'>Assign Teachers to Subject</a>
        </div></td>
    </tr>
</table>

<!--  Scripts-->
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="js/materialize.js"></script>
<script src="js/init.js"></script>

</body>
</html>
