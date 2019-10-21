package coursework2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import coursework2.bean.UserVerification;
import coursework2.bean.register;

public class ViewCommentServlet extends HttpServlet{

	
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		
		
		String email=req.getParameter("email");
		String pass=req.getParameter("password");
		if(email==null&&pass==null) {
			Cookie[] cookies = req.getCookies(); 
			 for (int i = 0 ; i < cookies.length ; i++) {
				 if (cookies[i].getName().equals("email"))
				  email = cookies[i].getValue();
				} 
			 for (int i = 0 ; i < cookies.length ; i++) {
				 if (cookies[i].getName().equals("pass"))
				  pass = cookies[i].getValue();
				}
			 res.sendRedirect("/CO3098_CW2_yz360/viewComment.jsp");
		}else {
	
			UserVerification dbOperator=new UserVerification();
			register citizen = dbOperator.checkcitizen2(email, pass);
			HttpSession se=req.getSession();
			if(citizen!=null){
				se.setAttribute("citizen",citizen);
				res.sendRedirect("/CO3098_CW2_yz360/viewComment.jsp");
				se.setAttribute("IDS", req.getParameter("id"));
			}else{
				res.sendRedirect("../error.jsp?errorid=1");
			}
			
			 res.addCookie(new Cookie("email", email)); 
			 res.addCookie(new Cookie("pass", pass)); 
		}

		

	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		doGet(req,res);
	}
}
