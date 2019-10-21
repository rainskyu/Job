<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="coursework2.bean.*"%>
<% 
HttpSession se=request.getSession();
register u=null;
if(se.getAttribute("citizen")!=null){
 	u=(register)se.getAttribute("citizen");
}else{
	response.sendRedirect("error.jsp?errorid=2");
}
if(u!=null){
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>CreatePetition</title>
</head>
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
input[type=button] {
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
	<h1>Create Petition</h1>
	<div>
	<form action="./servlets/SuccessfullyCreatePetitionServlet?creator=<%=se.getAttribute("creator")%>&email=<%=u.getEmail()%>&password=<%=u.getPassword()%>" method="POST">
	<label >Title</label>
	<input type="text" id="t" name="title">
	<label>Content</label>
	<input type="text" id="c" name="content">
	<input type="submit" value="Submit">
	<input type="button" value="Back" onclick="location.href='/CO3098_CW2_yz360/LoginSuccessfully.jsp'">
	</form>
	</div>

</body>
</html>
<%}%>