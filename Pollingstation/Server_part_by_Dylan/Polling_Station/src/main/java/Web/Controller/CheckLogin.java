package Web.Controller;

import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Web.Domain.Coordinator;
import Web.Repository.CoordinatorRepository;
@Controller
public class CheckLogin {

	@Autowired
	private CoordinatorRepository coo;
	
	@RequestMapping(value="/coordinate_check", method=RequestMethod.POST)
	@ResponseBody
	public boolean logincheck(@RequestParam(value="username") String username,@RequestParam(value="password") String password,HttpServletRequest request,HttpServletResponse response) {
		
		boolean check=false;
		Iterator<Coordinator> it= coo.findAll().iterator();
		while(it.hasNext()) {
			Coordinator temp=it.next();
			if(temp.getUsername().equals(username)&&temp.getPassword().equals(password)) {
				check=true;
				break;
			}
		}
		
		if(check==true) {
			Cookie cookieusername = new Cookie("username",username);
			//cookieusername.setDomain("localhost");
			cookieusername.setPath("/");
			cookieusername.setMaxAge(500);
			response.addCookie(cookieusername);
			//System.out.println("username: " + cookieusername.getName()+" age is:" +cookieusername.getMaxAge());
		}
		return check;
	}
	
	
	
}
