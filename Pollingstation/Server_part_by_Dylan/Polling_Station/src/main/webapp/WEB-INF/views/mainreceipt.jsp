<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Classroom Voting Bulletin Board Page for Ballot ${ballot}</title>
<link rel="stylesheet" type="text/css" href="/resources/css/mainstyle.css"/>
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/resources/css/extra-css.css">
<link rel="stylesheet" type="text/css" href="/resources/css/receipt.css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<div class="col-md-4 order-md-1">
			 <h5 style="color:white;">The University Of Warwick E-Voting</h5>
		</div>
		<div class="col-md-8 order-md-1" align="right">
		 		<a class="btn btn-sm btn-outline-secondary" href="#" style="color:white;">Sign in</a>
		</div>
	</nav>
<div id="page">
<div class="jumbotron">
<div id="masthead">
<h1>Receipt</h1>
</div>
<p id="button"> <a href= "/public/main_bulletin_board?sessionID=${sessionID}">Return to Main Bulletin Board</a> </p>
</div>
<div id = "maintext">
<hr>
<h2 id="title"> <strong>${title}</strong></h2>
<br>
<table border = "0">
<tr>
<td>
Election ID:
</td>
<td><td>
<td>
${sessionID}
</td>
</tr>
<tr>
<td>
Ballot ID:
</td>
<td><td>
<td>
${ballot}
</td>
</tr>
<tr>
<td>
Confirmation Code:
</td>
</tr>
</table>
<br>
<div id="receiptString">
<p><strong>${receipt1}   ${receipt2}   ${receipt3}   ${receipt4}   ${receipt5}</strong></p>
<br>
<p><strong>${receipt6}   ${receipt7}   ${receipt8}   ${receipt9}   ${receipt10}</strong></p>
<br>
<p style="font-size:20px"><strong>${CandidatesTitle}</strong></p>
</div>

<h2 id="state">Ballot: ${type}<h2>
</div>
</div>


</body>
</html>