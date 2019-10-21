<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="coursework2.bean.*"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<head><title>View</title>
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

input[type=button]:hover {
    background-color: #45a049;
}

div {
    border-radius: 5px;
    background-color: #f2f2f2;
    padding: 20px;
}
</style>

<body>
<h1>Hello</h1>
<h2>Welcome, <%= u.getNIC()%>!</h2><br/>
<div align="center">
<input type="button" onclick="location.href='./servlets/CreatePetition?creator=<%=u.getNIC()%>&email=<%=u.getEmail()%>&password=<%=u.getPassword()%> '" value="CreatePetition" />
<input type="button" onclick="location.href='./servlets/SuccessfullyCreatePetitionServlet?creator=<%=u.getNIC()%>&email=<%=u.getEmail()%>&password=<%=u.getPassword()%>'" value="ViewPetition" />
<input type="button" onclick="location.href='./servlets/LogoutServlet'" value="Logout" />
</div>
</body>
</html>
<%}%>

