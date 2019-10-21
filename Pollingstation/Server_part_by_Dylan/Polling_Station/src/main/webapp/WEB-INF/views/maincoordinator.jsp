<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
	.TFtable{
		width:100%; 
		border-collapse:collapse;
		margin-top:75px;
		text-align:center;
	}
	.TFtable td{ 
		padding:7px; border:#4e95f4 1px solid;
	}
	/* provide some minimal visual accomodation for IE8 and below */
	.TFtable tr{
		background: #b8d1f3;
	}
	/*  Define the background color for all the ODD background rows  */
	.TFtable tr:nth-child(odd){ 
		background: #b8d1f3;
	}
	/*  Define the background color for all the EVEN background rows  */
	.TFtable tr:nth-child(even){
		background: #dae5f4;
	}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<link rel="stylesheet" href="/resources/css/bootstrap.css">
<title>Coordinator</title>
</head>
<body style="background-color:#f8f9fa">
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<div class="col-md-11 order-md-1">
			<h5 style="color:white;">The University Of Warwick E-Voting</h5>
		</div>
	    <div class="col-md-1 order-md-1" align="right">
			 <a class="btn btn-sm btn-outline-secondary" href="/loggingout" style="color:white;">Log out</a>
		</div>
	</nav>
	<div class="container">
		<div class="row" style="margin-top: 60px; margin-left: 1000px">
			<div class="col-md-3 mb-3">
				<input type="button" value = "Add Topic" id ="btnadd" class="btn btn-success" onclick="location.href='/coordinator/create'">
			</div>
		</div>
		<table class="TFtable" style=" margin-top: 0px;">
				<tr>
		  			<td><h3>Election ID</h3></td>
		  			<td><h3>Topic Name</h3></td>
		  			<td><h3>Duration</h3></td>
		 			<td><h3>Start time</h3></td>
		 			<td><h3>Exipre time</h3></td>
		 			<td><h3>State</h3></td>
		  			<td><h3></h3></td>
		  			<td><h3></h3></td>
		  			<td><h3></h3></td>
		  			<td><h3></h3></td>
				</tr>
				<c:forEach items="${topiclist}" var="topic">
					<tr>
						<td><c:out value="${topic.getId()}"/></td>
						<td><c:out value="${topic.getName()}"/></td>
						<td><c:out value="${topic.getDuration()}"/></td>
						<td><c:out value="${topic.getStime()}"/></td>
						<td><c:out value="${topic.getEtime()}"/></td>
						<td><c:out value="${topic.getState()}"/></td>
						<td><a href="/coordinator/topicdetail?topicid=${topic.getId()}" id="edit${topic.getId()}">Edit</a></td>
						<td><a href="/coordinator/delete?topicid=${topic.getId()}" id="delete${topic.getId()}">Delete</a></td>
<%--						<td><a href="/coordinator/generate_passcode?topicid=${topic.getId()}" id="generatepasscode${topic.getId()}">Generate Passcode</a></td>--%>
						<td><a href="/coordinator/test_passcode?topicid=${topic.getId()}" id="generatepasscode${topic.getId()}">Generate Passcode</a></td>
						<td><a href="" id="bulletinboard${topic.getId()}">Bulletin Board</a></td>
						
					</tr>
				</c:forEach>
		</table>
		<script> 
			var hint = ${hint}
			$(document).ready(function(){
				if (hint==true){
					alert("Topic has started, Can not modify now");
				}
				
			});
		</script>

	</div> 
</body>
</html>