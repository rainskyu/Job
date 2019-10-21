package coursework2;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import coursework2.bean.UserVerification;
import coursework2.bean.comment;
import coursework2.bean.commentDB;
import coursework2.bean.nicDB;
import coursework2.bean.petitionDB;
import coursework2.bean.register;

public class SuccessfullyAddCommentServlet extends HttpServlet{
	
	private int count=0;
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		
		//res.sendRedirect("/CO3098_CW2_yz360/Petition.jsp");
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
			 res.sendRedirect("/CO3098_CW2_yz360/Petition.jsp");
		}else {
			UserVerification dbOperator=new UserVerification();
			register citizen = dbOperator.checkcitizen2(email, pass);
			HttpSession se=req.getSession();
			if(citizen!=null){
				se.setAttribute("citizen",citizen);
				if(!req.getParameter("comment").equals("")) {
					commentDB db = new commentDB();
					petitionDB pdb=new petitionDB(); 
					comment c = new comment(db.countID()+1,req.getParameter("comment"),req.getParameter("creator"));
					db.create();
					db.Insert(c,Integer.valueOf(req.getParameter("id")));
					pdb.updatecomment(req.getParameter("id"), pdb.searchcomment(Integer.valueOf(req.getParameter("id")))+1);
					res.sendRedirect("/CO3098_CW2_yz360/Petition.jsp");
				}else {
					String str= new String("true");
					se.setAttribute("check2", str);
					res.sendRedirect("/CO3098_CW2_yz360/Petition.jsp");
				}
			}else{
				res.sendRedirect("../erroraccount.jsp?errorid=1");
			}
			 res.addCookie(new Cookie("email", email)); 
			 res.addCookie(new Cookie("pass", pass)); 
		}

		

		
		
		
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		doGet(req,res);
	}

}
