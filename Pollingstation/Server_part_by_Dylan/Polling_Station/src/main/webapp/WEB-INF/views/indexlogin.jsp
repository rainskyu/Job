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
		  <form id="main-form" name="myform" action="/coordinator/main" method="post" class="form-horizontal">
		   <fieldset>
		   <!-- Text input-->
		   <div class="control-group">
  			   <label for="sessionID" class="control-label form-label">Username</label>
  			   <div class="controls">
    				<input id="username" name="username"  required="required" class="span3 text-input input-large" type="text" />
  			   </div>
		   </div>

		   <!-- Password input-->
		   <div class="control-group">
  			   <label for="ballot" class="control-label form-label">Password</label>
  			   <div class="controls">
    				<input id="password" name="password" required="required" class="span3 text-input input-large" type="text" />
  			   </div>
		   </div>

		   <!-- Buttons -->
		   <div id="button-area">
             <input id="view-results" type="button" value="Login" />
		   </div>
			</fieldset>
		</form>
	</div>
    

<footer>
     <a class="main-button"  href="/verify">Check Receipt?</a>  
</footer>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
      $(document).ready(function() {
         $("#view-results").click(function(){	 
        	var intRegex = new RegExp(/^[a-zA-Z0-9]/);
        	if($("#username").val() == "" && $("#password").val()==""){
        		alert("Please enter Username and Password");
        	}else{
        		if($("#username").val() == ""){
        			alert("Please enter Username");
        		}else
        		if($("#password").val() == ""){
        			alert("Please enter Password");
        		}else{
            		if(!intRegex.test($("#username").val())&&!intRegex.test($("#password").val())){
            			alert("Please enter valid Username and valid Pasword");
            		}else
            		if(!intRegex.test($("#username").val())){
            			alert("Please enter valid Username");
            		}else
            		if(!intRegex.test($("#password").val())){
            			alert("Please enter valid Pasword");
            		}else{
            			$.ajax({
            				type:"POST",
            	     		url:"/coordinate_check?username="+$("#username").val()+"&password="+$("#password").val(),
            				success: function(result){
            					if(result==true){
            						document.myform.submit();
            	        		}else{
            	        			alert("Please enter correct Username and Pasword");
            	        		}
            	        	}
            	        });
            		}
        		}
        	}
         });
	  });
</script>
</body>
</html>