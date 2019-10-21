package coursework2;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import coursework2.bean.UserVerification;
import coursework2.bean.petitionDB;
import coursework2.bean.register;
import coursework2.bean.sign;
import coursework2.bean.signDB;

public class SignServlet extends HttpServlet{
	
	
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		doPost(req,res);
		//System.out.println("count "+req.getParameter("count"));

	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		//doGet(req,res);
		signDB sdb = new signDB();
		String email=req.getParameter("email");
		String pass=req.getParameter("password");
		UserVerification dbOperator=new UserVerification();
		register citizen = dbOperator.checkcitizen2(email, pass);
		HttpSession se=req.getSession();
		if(citizen!=null){
			se.setAttribute("citizen",citizen);
			try {
				if(sdb.search(Integer.valueOf(req.getParameter("id")),req.getParameter("creator"))) {
					petitionDB ptb = new petitionDB();
					ptb.updateSign(req.getParameter("id"), ptb.searchsign(Integer.valueOf(req.getParameter("id")))+1);
					sdb.updatesign(req.getParameter("creator"),Integer.valueOf(req.getParameter("id")),sdb.searchId(req.getParameter("creator"))+1);
					res.sendRedirect("/CO3098_CW2_yz360/Petition.jsp");
				}else {
					String str= new String("true");
					se.setAttribute("check", true);
					res.sendRedirect("/CO3098_CW2_yz360/Petition.jsp");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			res.sendRedirect("../error.jsp?errorid=2");
		}


		
		/*petitionDB ptb = new petitionDB();
		ptb.updateSign(req.getParameter("id"), count);*/
		
		//System.out.println(req.getParameter("id"));
	}


	

}
