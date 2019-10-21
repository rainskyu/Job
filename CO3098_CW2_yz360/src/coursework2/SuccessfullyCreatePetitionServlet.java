package coursework2;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import coursework2.bean.UserVerification;
import coursework2.bean.petition;
import coursework2.bean.petitionDB;
import coursework2.bean.register;
import coursework2.bean.signDB;

public class SuccessfullyCreatePetitionServlet extends HttpServlet {
			
		
			public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
				doPost(req,res);
			}
			
			public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
				
				
				
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
						res.sendRedirect("/CO3098_CW2_yz360/Petition.jsp");
						se.setAttribute("creators", req.getParameter("creator"));
						petitionDB db = new petitionDB();
						signDB sdb = new signDB();
						petition pt = new petition();
						petitionDB p = new petitionDB();
						sdb.create();;
						try {
							if(req.getParameter("title")==null&& req.getParameter("content")==null) {
								
							}else{

								p.create();
								//System.out.println("CREATOR: "+req.getParameter("creator"));
								pt.setID(p.countID()+1);
								pt.setTitle(req.getParameter("title"));
								pt.setContent(req.getParameter("content"));
								pt.setCreator(req.getParameter("creator"));
								Date d = new Date(System.currentTimeMillis());
								pt.setDate(d);
								pt.setSign(0);
								pt.setComment(0);
								p.updateDatabase(pt);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						res.sendRedirect("../error.jsp?errorid=2");
					}
					
					 res.addCookie(new Cookie("email", email)); 
					 res.addCookie(new Cookie("pass", pass)); 
				}
				
				
				
				

			}
}
