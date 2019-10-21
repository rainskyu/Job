package coursework2;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coursework2.bean.MD5HashGenerator;
import coursework2.bean.citizenDB;
import coursework2.bean.nicDB;
import coursework2.bean.register;
public class SuccessfullyRegisteredServlet extends HttpServlet {
	

	  
	
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		//System.out.println("NIC" +req.getParameter("NIC"));
			nicDB db = new nicDB();
			citizenDB c = new citizenDB();
			String str=req.getParameter("email");
			try {
				if(db.checkNICvalid(req.getParameter("NIC"))&&db.checkNICused(req.getParameter("NIC"))&&!c.checkemailinvalid(req.getParameter("email"))&&req.getParameter("firstname")!=null&&req.getParameter("lastname")!=null&&c.checkemailinvalid(str)==false) {
					//System.out.println("check1");
					res.sendRedirect("/CO3098_CW2_yz360/SuccessfullyRegisteredPage.jsp");
					MD5HashGenerator sj = new MD5HashGenerator();
					
					register citizen = new register();
					c.create();
					citizen.setID(c.countID()+1);
					citizen.setFname(req.getParameter("firstname"));
					citizen.setSname(req.getParameter("lastname"));
					citizen.setEmail(req.getParameter("email"));
					citizen.setPassword(sj.getMD5Hash(req.getParameter("password")));
					citizen.setFulladdress(req.getParameter("fulladdress"));
					citizen.setDataofbirth(req.getParameter("dateofbirth"));
					citizen.setNIC(req.getParameter("NIC"));
					if(c.updateDatabase(citizen)) {
						db.updateused(req.getParameter("NIC"));
					}
				}else
				if(db.checkNICvalid(req.getParameter("NIC"))==true&&db.checkNICused(req.getParameter("NIC"))==false) {
					//System.out.println("check2");
					res.sendRedirect("../errornic.jsp?errorid=4");
				}else
				if(db.checkNICvalid(req.getParameter("NIC"))==false||db.checkNICused(req.getParameter("NIC"))==false){
					//System.out.println("check3");
					res.sendRedirect("../errornic.jsp?errorid=3");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException {
		doGet(req, res);
	}
	
	
	
}
