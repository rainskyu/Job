package coursework2;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class MainPageServlet extends HttpServlet {
		
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException {
			res.sendRedirect("/CO3098_CW2_yz360/MainPage.jsp");
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException {
			doGet(req, res);
	}
}