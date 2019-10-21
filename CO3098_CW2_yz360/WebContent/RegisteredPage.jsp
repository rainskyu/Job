<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="coursework2.bean.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Registered Page</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<style>
input[type=text], select {
    width: 100%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}
input[type=password], select {
    width: 100%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

input[type=submit] {
    width: 100%;
    background-color: #4CAF50;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

input[type=submit]:hover {
    background-color: #45a049;
}

div {
    border-radius: 5px;
    background-color: #f2f2f2;
    padding: 20px;
}
</style>

		
<body>
	<h1>Registered Form</h1>
	
	<div>
	<form action="./servlets/SuccessfullyRegisteredServlet" method = "POST">
	<label >First Name</label>
	<input type="text" id="fname" name="firstname">
	<label>Last Name</label>
	<input type="text" id="lname" name="lastname">
	<label >Email</label> <button type = "button" id ="btn">Check Email</button>
	<script type="text/javascript">
		$(document).ready(function(){
		 $("#btn").click(function(){
		 $.ajax({
		 type: "GET",
		 url: "./servlets/RegistrationServlet?email="+$("#emails").val(),
		 success: function(result){
				if(result!=""){
	window.alert("Sorry The Email Address Has been Used OR Please Use Comman Email Address For Register in here, @gmail.com, @hotmail.com, @yahoo.com, @Outlook.com are all accept");
				}else{
					window.alert("Valid");
				}

		 }}); });
		});
	</script>
	<input type="text" id="emails" name="email"> 
	<label >Password</label>
	<input type="password" id="psw" name="password" >
	<label >Full Address</label>
	<input type="text" id="fa" name="fulladdress">
	<label >Date Of Birth</label>
	<input type="text" id="birth" name="dateofbirth">
	<label>NIC</label>
	<input type="text" id="nationalidentifycode" name="NIC">
	<input type="submit" value="Submit" id="ntm">
	</form>
	</div>

</body>
</html>