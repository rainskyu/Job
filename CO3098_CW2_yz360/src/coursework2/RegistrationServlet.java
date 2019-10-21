package coursework2;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coursework2.bean.citizenDB;

public class RegistrationServlet extends HttpServlet {
	

	
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		//doPost(req,res);
		citizenDB db = new citizenDB();
		db.create();
		//res.sendRedirect("/CO3098_CW2_yz360/RegisteredPage.jsp");
		String str=req.getParameter("email");
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		//System.out.println(db.checkemailinvalid(str));
		if(db.checkemailinvalid(str)==false&&str!=null&&(str.contains("@gmail.com")||str.contains("@hotmail.com")||str.contains("@yahoo.com")||str.contains("@Outlook.com"))) {
			//System.out.println("true");
		}else {
			//System.out.println("false");
			String s="check";
			out.println(s);
			s="";
		}
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		doGet(req,res);
		//register.ID++;

		
	}
	
}
