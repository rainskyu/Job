<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="/resources/css/indexstyle.css" rel="stylesheet">
<title>The University Of Warwick E-Voting</title>
</head>
<script>
     	var timeinterval=5000;
     	var store=new Array();
     	store[0]="/resources/img/first.jpg";
     	store[1]="/resources/img/second.jpg";
     	store[2]="/resources/img/third.jpg";
     	store[3]="/resources/img/fourth.jpg";
     	setInterval(function(){image()}, timeinterval);
     	
     	var count=1;
     	function image(){
     		$("body").css("background-image","url("+store[count]+")");
     		$("body").css("transition","3s");
     		if(count==store.length-1){
     			count=0;
     		}else{
     			count++;
     		}
     	}	
</script>
<body style="background-image:url(/resources/img/first.jpg);">
    <div id="main" class="container-narrow">
      <!-- Main hero unit for a primary marketing message or call to action -->
      	<div id="logo">             
            <img src="/resources/img/logo.jpg"/>
       	</div>
            <h1 id="title">The University Of Warwick E-Voting</h1>    
            	
		<div id="main-form-group">
		 <!-- Start voting form -->
		  <form id="main-form" name="myform" action="/public/main_bulletin_board" method="post" class="form-horizontal">
		   <fieldset>
		   <!-- Text input-->
		   <div class="control-group">
  			   <label for="sessionID" class="control-label form-label">Election ID</label>
  			   <div class="controls">
    				<input id="sessionID" name="sessionID" required="required" class="span3 text-input input-large" type="text" />
  			   </div>
		   </div>

		   <!-- Password input-->
		   <div class="control-group">
  			   <label for="ballot" class="control-label form-label">Ballot ID</label>
  			   <div class="controls">
    				<input id="ballot" name="ballot" required="required" class="span3 text-input input-large" type="text" />
  			   </div>
		   </div>

		   <!-- Buttons -->
		   <div id="button-area">
             <input id="view-results" type="button" value="View Receipt" />
		   </div>
			</fieldset>
		</form>
		</div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
      $(document).ready(function() {
         $("#view-results").click(function(){	 
        	var intRegex = new RegExp( /^\d+$/);
        	if($("#sessionID").val()!="" && $("#ballot").val()==""){
        		if(!intRegex.test($("#sessionID").val())){
        			alert("Please enter valid Election ID");
        		}else{
        			
        			$.ajax({
        				type:"POST",
        	     		url:"/electioncheck?sessionID="+$("#sessionID").val(),
        				success: function(result){
        					if(result==true){
        						document.myform.submit();
        	        		}else{
        	        			alert("Please enter correct Election ID");
        	        		}
        	        	}
        	        });
        		}
        	}else
        	if($("#sessionID").val() != "" && $("#ballot").val() !=""){
        		if(!intRegex.test($("#sessionID").val())&&!intRegex.test($("#ballot").val())){
        			alert("Please enter valid Election ID and valid Ballot ID");
        		}else
        		if(!intRegex.test($("#sessionID").val())){
        			alert("Please enter valid Election ID");
        		}else
        		if(!intRegex.test($("#ballot").val())){
        			alert("Please enter valid Ballot ID");
        		}else{
        			$.ajax({
        				type:"POST",
        	     		url:"/receiptcheck?sessionID="+$("#sessionID").val()+"&ballot="+$("#ballot").val(),
        				success: function(result){
        					if(result==true){
        						$("#main-form").attr("action","/receipt/bulletin_board");
        						document.myform.submit();
        	        		}else{
        	        			alert("Please enter correct Election ID and correct Ballot ID");
        	        		}
        	        	}
        	        });
        		}
        	}else{
        		alert("Please enter Election ID");
        	}
         });
	  });
</script>
</body>
</html>