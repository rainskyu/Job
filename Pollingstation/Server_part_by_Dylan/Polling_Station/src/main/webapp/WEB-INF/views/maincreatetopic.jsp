<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<link rel="stylesheet" href="/resources/css/bootstrap.css">
<title>Create A Topic</title>
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
	<div class="container" style="margin-top: 200px; margin-left: 560px">
		<div class="row" >	
			<div class="col-md-8 order-md-1">
				<form:form action="/coordinator/add" commandName="topic" method ="POST" name ="myform">
					<div class="row">
						<div class="col-md-6 mb-3">
							<label>Topic name</label>
							<form:input class="form-control" type="text" id="name" path="name" required="required"/>
						</div>
						<div class="col-md-6 mb-3">
							<label>Duration/Days</label>
							<form:input class="form-control" type="text" id="duration" path="duration"  required="required"/>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 mb-3">
							<label>Topic password</label>
							<form:input class="form-control" type="text" id="password" path="password" required="required"/>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3 mb-3">
							<input type="button" value = "Add Option" id ="btnadd" class="btn btn-success">
						</div>
					</div>
					<div class="row" id="add">
					
					</div>
					<script>
						$(document).ready(function(){
							var max = 10;
							var add = $("#add");
							var count=0
							$("#btnadd").click(function(){
								if (count<max){	
									count++;
									$(add).append('<div class="col-md-3 mb3"><label>Option '+count+'</label><input class="form-control" type="text" id="options'+count+'" name="options'+count+'" value="" required="required"/></div>');
								}
								return false;
							});
						});
					</script>
					<div class="row">
						<div class="col-md-2 mb-3">
							<label>Day</label>
							<select id="day" name="day" class="form-control" required="required">
								<option value="" style="display:none"></option>
								<c:forEach	items="${day}" var="d">
									<option value="${d}">${d}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-2 mb-3">
							<label>Month</label>
							<select id="month" name="month" class="form-control" required="required">
								<option value="" style="display:none"></option>
								<c:forEach	items="${month}" var="m">
									<option value="${m}">${m}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-2 mb-3">
							<label>Year</label>
							<select id="year" name="year" class="form-control" required="required">
								<option value="" style="display:none"></option>
								<c:forEach	items="${year}" var="y">
									<option value="${y}">${y}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-2 mb-3">
							<label>Hour</label>
							<select id="hour" name="hour" class="form-control" required="required">
								<option value="" style="display:none"></option>
								<c:forEach	items="${hour}" var="h">
									<option value="${h}">${h}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-2 mb-3">
							<label>Minutes</label>
							<select id="minutes" name="minutes" class="form-control" required="required">
								<option value="" style="display:none"></option>
								<c:forEach	items="${minutes}" var="m">
									<option value="${m}">${m}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-2 mb-3">
							<label>Second</label>
							<select id="seconds" name="seconds" class="form-control" required="required">
								<option value="" style="display:none"></option>
								<c:forEach	items="${seconds}" var="s">
									<option value="${s}">${s}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3 mb-3">
							<input type="button" value = "submit" id ="btnsubmit" class="btn btn-success">
						</div>
					</div>
					<script>
					  $(document).ready(function() {
						 $("#btnsubmit").click(function(){
							 var stringRegex = new RegExp(/^[a-zA-Z0-9]/);
							 var intRegex = new RegExp(/^[0-9]/);
							 var myDiv = document.getElementById("add");
							 var div_s = myDiv.getElementsByTagName("div");
							 if($("#month").val()==null||$("#day").val()==null||$("#day").val()==null||div_s.length=="0"||$("#minutes").val()==null||$("#hour").val()==null||$("#name").val()==null||$("#duration").val()==null||$("#seconds").val()==null){
								 alert("Please filling topic detail");
							 }else{
								 if(!stringRegex.test($("#name").val())){
									 alert("Please enter valid topic name");
								 }else
								 if(!intRegex.test($("#duration").val())){
									 alert("Please enter number as duration");
								 }else
								 if(div_s.length<=1){
									 alert("Please add options (At least two)");
								 }else
							     if(($("#month").val()=="4"||$("#month").val()=="6"||$("#month").val()=="9"||$("#month").val()=="11")&&$("#day").val()=="31"){
							    	alert("Invalid date, there is no 31st in this month"); 
							     }else
							     if($("#month").val()=="2"&&($("#day").val()=="30"||$("#day").val()=="31"||($("#day").val()=="29"&&((parseInt($("#year").val())%4==0&&parseInt($("#year").val())%100!=0)||parseInt($("#year").val())%400==0)))){
							    	 alert("Invalid data, there is no 30th and 31st in Feburary or there is no 29th in Feburary in this year");
							     }else{
							    	 $.ajax({
							    		 type:"POST",
							    		 url:"/coordinator/check_data?year="+$("#year").val()+"&month="+$("#month").val()+"&day="+$("#day").val()+"&hour="+$("#hour").val()+"&minutes="+$("#minutes").val()+"&seconds="+$("#seconds").val(),
							    		 success: function(result){
			            					if(result==true){
			            						document.myform.submit();
			            	        		}else{
			            	        			alert("Date is behind the current date");
			            	        		}
							    		 }
							    	 });
							     }
							 }
						 });
					  });
					</script>
				</form:form>
			</div>		
		</div>
	</div>
</body>
</html>