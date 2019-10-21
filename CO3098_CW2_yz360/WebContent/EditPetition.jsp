<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="coursework2.bean.*"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
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
<title>EditPetition</title>
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

input[type=submit]:hover {
    background-color: #45a049;
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

button[type=button]:hover {
    background-color: #45a049;
}

div {
    border-radius: 5px;
    background-color: #f2f2f2;
    padding: 20px;
}
</style>
<body>
	<h1>Edit Petition</h1>
	<div>
	<form action="./servlets/UpdatePetition?id=<%=se.getAttribute("ids")%>&email=<%=u.getEmail()%>&password=<%=u.getPassword()%>" method="POST">
	<label >Title</label>
	<input type="text" id="t" name="title">
	<label>Content</label>
	<input type="text" id="c" name="content">
	<input type="submit" value="Submit">
	<button type="button" onclick="location.href='/CO3098_CW2_yz360/Petition.jsp'">Back</button>
	</form>
	</div>

</body>
</html>
<%}%>