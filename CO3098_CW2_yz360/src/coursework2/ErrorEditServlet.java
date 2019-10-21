package coursework2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import coursework2.bean.UserVerification;
import coursework2.bean.register;

public class ErrorEditServlet extends HttpServlet{
	
	
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		doPost(req,res);
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		String email=req.getParameter("email");
		String pass=req.getParameter("password");
		UserVerification dbOperator=new UserVerification();
		register citizen = dbOperator.checkcitizen2(email, pass);
		HttpSession se=req.getSession();
		if(citizen!=null){
			se.setAttribute("citizen",citizen);
			res.sendRedirect("../erroredit.jsp");
			se.setAttribute("ids", req.getParameter("id"));
		}else{
			res.sendRedirect("../error.jsp?errorid=2");
		}
		
		
		
	
	}

}
