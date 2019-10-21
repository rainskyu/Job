package Web.Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import Web.Domain.Coordinator;

@Controller
@RequestMapping(value="/")
public class ControllerIndex {
	
	
	@RequestMapping(value="/verify")
	public ModelAndView verify() {
		
		return new ModelAndView("indexverify");
	}
	
	@RequestMapping(value="/login")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();
		boolean check =false;
		if(cookies!=null) {
			for(Cookie cookie :cookies) {
				if(cookie.getName().equals("username")) {
					check=true;
					//System.out.println(cookie.getMaxAge());
					break;
				}
			}
			if(check==true) {
				request.getRequestDispatcher("/coordinator/main").forward(request,response);
				return null;
			}else {
				return new ModelAndView("indexlogin");
			}
		}else {
			return new ModelAndView("indexlogin");
		}
	}
	
	@RequestMapping(value="/publicverify")
	public ModelAndView publicverifybulletinboard() {
		return new ModelAndView("indexpublic");
	}
	
	
	@RequestMapping(value="/loggingout")
	public ModelAndView loggingout(Coordinator cc, HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie:cookies) {
			if(cookie.getName().equals("username")) {
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		
		return new ModelAndView("indexlogingout");
	}
	
	
	@RequestMapping(value="/error_needLogin")
	public ModelAndView needlogin() {
		
		return new ModelAndView("indexerrorhavenotlogin");
	}
	
	@RequestMapping(value="/error_noreceipt")
	public ModelAndView needreceipt() {
		
		return new ModelAndView("indexerrorhavenotloginreceipt");
	}
	
	@RequestMapping(value="/error_electionid")
	public ModelAndView needelectionid() {
		
		return new ModelAndView("indexerrorhavenotloginpublic");
	}
	

}
