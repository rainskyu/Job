package Web.Configuation;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import Web.Repository.CoordinatorRepository;

public class InterceptorCoordinator extends HandlerInterceptorAdapter  {
	
	
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		Cookie[] cookies = request.getCookies();
		boolean check =false;
		if(cookies!=null) {
			for(Cookie cookie :cookies) {
				if(cookie.getName().equals("username")) {
					cookie.setMaxAge(500);
					cookie.setPath("/");
					response.addCookie(cookie);
					check=true;
					break;
				}
			}
			if(check==true) {
				return true;
			}else {
				request.getRequestDispatcher("/error_needLogin").forward(request,response);
				return false;
				
			}
		}else {
			request.getRequestDispatcher("/error_needLogin").forward(request,response);
			return false;	
		}
    }


	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView model)  throws Exception {
		
	}
 

   @Override
   public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex)  throws Exception {
     
   }
	

}