<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<html>
<head><title>Error</title></head>
<style type="text/css">
	.TFtable{
		width:100%; 
		border-collapse:collapse; 
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
	
	input[type=text], select {
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
button[type=button] {
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

<h1>Error</h1>
<%
String errorMsg="Access denied";
String errorid=request.getParameter("errorid");
if(errorid!=null){
	if(errorid.equals("1")){
		errorMsg+= " - Wrong password or email.";
	}else 
	if(errorid.equals("2")){
		errorMsg+=" - Session expired.Please Login Again";
	}else
	if(errorid.equals("3")){
		errorMsg+= " - Invalid NIC number";
	}else
	if(errorid.equals("4")){
		errorMsg+= " - This NIC number has been registered already";
	}else
	if(errorid.equals("5")){
		errorMsg+= " - You can not edit the peititon after someone sign the petition";
	}else
	{
		errorMsg+=" - You are not authorized to access this page.";
	}
}else{
	 errorMsg+=" - An unexpected error has occurred.";
}
%>
<div>
	<label for="link"><%=errorMsg%></label>
	<button type="button" onclick="location.href='./Login.jsp'">login</button>
	<button type="button" onclick="location.href='./MainPage.jsp'">MainPage</button>
	
</div>
</body>
</html>
