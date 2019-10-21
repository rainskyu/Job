package coursework2;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import coursework2.bean.*;

public class LoginServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			res.sendRedirect("/CO3098_CW2_yz360/Login.jsp");
	}
			
		
			public void doPost(HttpServletRequest req, 
					HttpServletResponse res) 
			        throws ServletException, IOException {
			doGet(req, res);
			}
			

	
		/*public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
			res.sendRedirect("/CO3098_CW2_yz360/Login.jsp");
		}
		public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException {
			doGet(req, res);
		}*/
			
		/*	String email=req.getParameter("email");
			String pass=req.getParameter("pass");

			UserVerification dbOperator=new UserVerification();
			if(dbOperator.checkUser(email, pass)) {
				res.sendRedirect("/CO3098_CW2_yz360/LoginSuccessfully");
			}else {
				res.sendRedirect("/CO3098_CW2_yz360/error?errorid=1");
			}
			
			
			}*/
}
