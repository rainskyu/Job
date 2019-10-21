<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <link rel="stylesheet" href="/resources/css/bootstrap.css">
    <title>Generate Passcode</title>
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <div class="col-md-11 order-md-1">
        <h5 style="color:white;">The University Of Warwick E-Voting</h5>
    </div>
    <div class="col-md-1 order-md-1" align="right">
        <a class="btn btn-sm btn-outline-secondary" href="/loggingout" style="color:white;">Log out</a>
    </div>
</nav>
<div class="container" style="margin-top:100px; margin-left:600px;">
    <table class="TFtable" style=" margin-top: 0px;">
        <tr>
            <td><h3>Passcodes</h3></td>
        </tr>
        <c:forEach items="${passcodes}" var="Passcode">
            <tr>
                <td><code><c:out value="${Passcode.getPasscode()}"/></code></td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>