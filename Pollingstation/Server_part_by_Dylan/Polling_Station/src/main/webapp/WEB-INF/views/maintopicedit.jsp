<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<link rel="stylesheet" href="/resources/css/bootstrap.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>Account Register</title>
</head>

<body class="bg-light">
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<div class="col-md-11 order-md-1">
			<h5 style="color:white;">The University Of Warwick E-Voting</h5>
		</div>
	    <div class="col-md-1 order-md-1" align="right">
			 <a class="btn btn-sm btn-outline-secondary" href="/loggingout" style="color:white;">Log out</a>
		</div>
	</nav>
	<div class="container">
		<div class="row" style="margin-top:200px">	
			<div class="col-sm-12 my-auto">
				<form:form action="/coordinator/modified" commandName="topic" method ="POST">
					<div class="row">
						<div class="col-md-6 mb-3">
							<label>Topic name</label>
							<form:input class="form-control" type="text" id="name" path="name" value="${topic_name}" />
						</div>
						<div class="col-md-6 mb-3">
							<label>Duration/Days</label>
							<form:input class="form-control" type="text" id="duration" path="duration" value ="${duration}" />
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 mb-3">
							<label>Password</label>
							<form:input class="form-control" type="text" id="password" path="password" value ="${password}"/>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3 mb-3">
							<input type="button" value = "Add Option" id ="btnadd" class="btn btn-success">
						</div>
					</div>
					<div class="row" id ="options">
					<!-- https://stackoverflow.com/questions/6600738/use-jstl-foreach-loops-varstatus-as-an-id resource for varstatus -->
						<c:forEach items="${options}" var="i" varStatus="count">
							<div class="col-md-5 mb-3">
								<label>Option ${count.count} </label>
								<input class="form-control" type="text" id="options${count.count}" name ="options${count.count}" value ="${i.getOptions()}" />
								<button class="btn" id="btn${count.count}"><i class="fa fa-close"></i></button>
								<script>
									$(document).ready(function(){
										var size = ${end};
										var deletes = $("#btn${count.count}");
										$(deletes).click(function(){
											if(size>1){
												$(this).parent('div').remove();
											}
											return false;
										});
									});
								</script>
							</div>		
						</c:forEach>
					</div>
					<script>
						$(document).ready(function(){
							var max = 10;
							var options = $("#options");
							var size = ${end};
							$("#btnadd").click(function(){
								if (size<max){	
									size++;
									$(options).append('<div class="col-md-5 mb3"><label>Option '+size+'</label><input class="form-control" type="text" id="options'+size+'" name="options'+size+'" value=""/></div>');
								}
								return false;
							});
						});
					</script>

					<div class="row">
						<div class="col-md-3 mb-3">
							<input type="submit" value = "submit" id ="btnsubmit" class="btn btn-success">
						</div>
					</div>
					<form:hidden path="id" readonly="true" value="${id}"/>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>