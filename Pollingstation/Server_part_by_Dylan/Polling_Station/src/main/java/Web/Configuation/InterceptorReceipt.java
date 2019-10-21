package Web.Configuation;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class InterceptorReceipt extends HandlerInterceptorAdapter{

	// Called before handler method invocation
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		
		Cookie[] cookies = req.getCookies();
		boolean check =false;
		if(cookies!=null) {
			for(Cookie cookie :cookies) {
				if(cookie.getName().equals("sessionID")) {
					check=true;
					break;
				}
			}
			if(check==true) {
				return true;
			}else {
				req.getRequestDispatcher("/error_noreceipt").forward(req,res);
				return false;
				
			}
		}else {
			req.getRequestDispatcher("/error_noreceipt").forward(req,res);
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
