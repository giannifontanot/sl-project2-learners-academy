<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body style="background-color: lightgray">
<h1><%= "Learner's Academy" %>
</h1>
<br/>
<a href="hello-servlet"></a>
<h2>Administration Login</h2>
<form method="POST" action="./main-controller">
    <input type="text" name="username" placeholder="username" value="" /><br />
    <input type="text" name="password" placeholder="password" value="" />
    <input type="text" name="page" placeholder="page" value="" />
    <button type="submit" name="submit">submit</button>
</form>