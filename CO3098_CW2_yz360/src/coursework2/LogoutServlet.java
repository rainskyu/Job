package coursework2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

				public class LogoutServlet extends HttpServlet {
					public void doGet(HttpServletRequest req, 
					        HttpServletResponse res) 
				        throws ServletException, IOException {
					HttpSession se=req.getSession();
					se.removeAttribute("citizen");
					res.sendRedirect("/CO3098_CW2_yz360/Login.jsp");
				}
				public void doPost(HttpServletRequest req, 
						HttpServletResponse res) 
				        throws ServletException, IOException {
				doGet(req, res);}
}
