<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="coursework2.bean.*"%>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="coursework2.bean.petition" %>
<%@ page import="java.sql.Date" %>
<%HttpSession se=request.getSession();
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
<title>Petition information</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>


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
		
	<h2 align="center">Petition Information</h2>
	<button type="button" onclick="location.href='/CO3098_CW2_yz360/LoginSuccessfully.jsp'">Back</button>
	<table class="TFtable">
	<tr>
		<td><h3>Id</h3></td>
		<td><h3>Title</h3></td>
		<td><h3>Content</h3></td>
		<td><h3>Date</h3></td>
		<td><h3>Creator</h3></td>
		<td><h3>Sign</h3></td>
		<td><h3>Comment</h3></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<%
	try{
	Class.forName("com.mysql.jdbc.Driver");
	String host="127.0.0.1";
	String database="test";
	String username="root";
	String password="";
	String query="SELECT * FROM PETITIONS";
	String conn_string="jdbc:mysql://"+host+"/"+database;
	Connection conn=DriverManager.getConnection(conn_string, username, password);
	Statement stmt=conn.createStatement();
	ResultSet rs=stmt.executeQuery(query);
	Date d = new Date(System.currentTimeMillis());
	int count=1;
	boolean check=false;
	while(rs.next()){
		if(d.getDate()-rs.getDate(4).getDate()<7){
	%>
	<script type="text/javascript">
		$(document).ready(function(){
		 $("#btn<%=rs.getInt("ID")%>").click(function(){
		 $.ajax({
		 type: "POST",
		 url: "./servlets/SignServlet?content=<%=rs.getString("CONTENT") %>&title=<%=rs.getString("TITLE") %>&id=<%=rs.getInt("ID")%>&creator=<%=se.getAttribute("creators") %>&email=<%=u.getEmail()%>&password=<%=u.getPassword()%>",
		 success: function(){
			 $("#sign<%=rs.getInt("ID")%>").html(<%=rs.getInt("SIGN") %>+1); 
			 $("#btn<%=rs.getInt("ID")%>").attr("disabled",true)
		 }}); });
		});
	</script>
	<tr>
        <td align="center"><%=rs.getInt("ID")%></td>
        <td align="center"><%=rs.getString("TITLE")%></td>
        <td align="center"><%=rs.getString("CONTENT")%></td>
        <td align="center"><%=rs.getDate("DATE") %></td>
        <td align="center"><%=rs.getString("CREATOR") %></td>
        <td align="center"><div id="sign<%=rs.getInt("ID")%>"><%=rs.getInt("SIGN") %></div></td>
        <td align="center"><%=rs.getInt("COMMENT") %></br><input type="button" onclick="location.href='./servlets/ViewCommentServlet?id=<%=rs.getInt("ID")%>&creator=<%=se.getAttribute("creators")%>&email=<%=u.getEmail()%>&password=<%=u.getPassword()%>'" value="viewComment" /></td>
        <td align="center"><input type="button" onclick="location.href='./servlets/CommentServlet?id=<%=rs.getInt("ID")%>&creator=<%=se.getAttribute("creators")%>&email=<%=u.getEmail()%>&password=<%=u.getPassword()%>'" value="Comment" /></td>
        <td align="center"><input type="button" onclick="location.href='./servlets/EditServlet?id=<%=rs.getInt("ID")%>&email=<%=u.getEmail()%>&password=<%=u.getPassword()%>'" value="Edit" /></td>
        <td align="center"><input type="button" id="btn<%=rs.getInt("ID")%>" value="Sign" <% 
        String str="SELECT * FROM SIGN";
        Statement stmt2=conn.createStatement();
		ResultSet rs2=stmt2.executeQuery(str);
		while(rs2.next()){
			if(rs2.getInt("PETITIONID")==rs.getInt("ID")&&se.getAttribute("creators").equals(rs2.getString("NIC"))){	
				check=true;
				break;
			}
		}%> <%if(check==true){%><%="disabled" %><% check=false;} %>></td>
	</tr>
	
	<% count++;
		}
	}
    
	
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