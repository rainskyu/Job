<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>The University Of Warwick E-Voting</title>
<link rel="stylesheet" type="text/css" href="/resources/css/mainstyle.css"/>
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="/resources/css/extra-css.css"/>
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
<h1>Bulletin Board</h1>
</div>
<h4>Election ${electionid} - ${title} </h4>
<div>
	<p style="margin-top: 60px;margin-right:700px">Click to save the <a href="/public/download?sessionID=${electionid}">receipt</a> file for verification</p>
	<p style="margin-right:733px;margin-bottom: -30px;">Click to download the <a href="/public/download_tool">verification tool</a></p>
	<div style="margin-top: 40px;margin-left: 100px;">
	<table class="TFtable" border="0">
	<tr>
		<td>Candidate</td>
		<td>Tally</td>
	</tr>
	<c:forEach items="${candidatelist}" var="candidate">
		<tr>
			<td><c:out value="${candidate.getOptions()}"/></td>
			<td><c:out value="${candidate.getTally()}"/></td>
		</tr>	
	</c:forEach>
	</table>
	</div>
</div>
</div>
<div id = "maintext">
	<p style="margin-left:457px">Please enter the Ballot ID to view the receipt</p>
	<form id="main-form" name="myform" action="/receipt/bulletin_board" method="post" class="form-horizontal" style = "margin-left: 650px;margin-top: 80px;">
	  	<div class="controls" style="margin-left:130px">
    	 	<label style="font-size:20px">Ballot ID</label>
    	 	<input id="ballot" name="ballot" required="required" class="span3 text-input input-large" type="text" style="margin-top:4px;margin-left:10px;height:30px;width:250px" />
		</div>
		<div class="controls" style="margin-left:130px">
    	 	<input id="sessionID" name="sessionID" required="required" class="span3 text-input input-large" type="hidden" style="margin-top:4px;margin-left:10px;height:30px;width:250px" value="${electionid}"/>
		</div>
		<div id="button-area" style="margin-top: 20px;margin-left: 285px;">
             <input id="view-results" type="button" value="View Receipt" />
		</div>
	</form>
	<script>
    $(document).ready(function() {
       $("#view-results").click(function(){	 
      	var intRegex = new RegExp(/^[a-zA-Z0-9]/);
      	if($("#ballot").val() == ""){
      		alert("Please enter ballot id");
      	}else{ 
       		if(!intRegex.test($("#ballot").val())){
       			alert("Please enter valid ballot id");
       		}else{
	       		 $(document).ready(function() {
	       	         $("#view-results").click(function(){	
	       	        	 $.ajax({
	       	 				type:"POST",
	       	 	     		url:"/receiptcheck?sessionID="+${electionid}+"&ballot="+$("#ballot").val(),
	       	 				success: function(result){
	       	 					if(result==true){
	       	 						document.myform.submit();
	       	 	        		}else{
	       	 	        			alert("Please enter correct Ballot ID");
	       	 	        		}
	       	 	        	}
	       	 	        });
	       	         });
	       		 });
      		}
      	}
       });
	  });

	
	</script>
</div>
</div>


</body>
</html>