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

public class LoginProcessServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//doPost(req, res);
		String email=req.getParameter("email");
		String pass=req.getParameter("pass");
		//System.out.println(email);
		//System.out.println(pass);
		UserVerification dbOperator=new UserVerification();
		
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
		
			 res.sendRedirect("../LoginSuccessfully.jsp");
		}else {
			
			 register citizen = dbOperator.checkcitizen(email, pass);
				HttpSession se=req.getSession();
				if(citizen!=null){
					se.setAttribute("citizen",citizen);
					//System.out.println("nic "+citizen.getNIC());
					res.sendRedirect("../LoginSuccessfully.jsp");
				}else{
					res.sendRedirect("../erroraccount.jsp?errorid=1");
				}
			 res.addCookie(new Cookie("email", email)); 
			 res.addCookie(new Cookie("pass", pass)); 
		}
		
		

	}
		
	
		
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
		//res.sendRedirect("/CO3098_CW2_yz360/LoginSuccessfully.jsp");
		/*String email=req.getParameter("email");
		String pass=req.getParameter("pass");
		//System.out.println(email);
		//System.out.println(pass);
		UserVerification dbOperator=new UserVerification();
		register citizen = dbOperator.checkcitizen(email, pass);
		HttpSession se=req.getSession();
		if(citizen!=null){
			se.setAttribute("citizen",citizen);
			res.sendRedirect("../LoginSuccessfully.jsp");
		}else{
			res.sendRedirect("../error.jsp?errorid=1");
		}*/
		//out.close();
		/*if(dbOperator.checkUser(email, pass)) {
			System.out.println("true");
		}else {
			System.out.println("false");
		}*/
		
	}

}
