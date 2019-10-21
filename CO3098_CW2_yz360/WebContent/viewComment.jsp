<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="coursework2.bean.*"%>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
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
	<h2 align="center">Comment</h2>
	<button type="button" onclick="location.href='/CO3098_CW2_yz360/Petition.jsp'">Back</button>
	<table class="TFtable">
	<tr>
		<td><h3>Comment</h3></td>
		<td><h3>BY_MP</h3></td>
		<td><h3>PetitionId</h3></td>
	</tr>
	<%
	try{
	Class.forName("com.mysql.jdbc.Driver");
	String host="127.0.0.1";
	String database="test";
	String username="root";
	String password="";
	String query="SELECT * FROM COMMENTS";
	String conn_string="jdbc:mysql://"+host+"/"+database;
	Connection conn=DriverManager.getConnection(conn_string, username, password);
	Statement stmt=conn.createStatement();
	ResultSet rs=stmt.executeQuery(query);
	int count=1;
	String comment=null;
	String mps=null;
	int petition=0;
	while(rs.next()){
		if(String.valueOf(rs.getInt("PETITIONID")).equals(se.getAttribute("IDS"))){
			comment=rs.getString("COMMENT");
			mps=rs.getString("BY_MP");
			petition=rs.getInt("PETITIONID");%>
			
	<tr>
        <td><%=comment %></td>
        <td><%=mps %></td>
        <td><%=petition %></td>
	</tr>
		
		<% }
		
	%>
			

	<% }
	
	rs.close();
	stmt.close();
	conn.close();
	}catch(Exception e){
		e.printStackTrace();
	}%>
	
	</table>
	
</body>
</html>
<%}%>