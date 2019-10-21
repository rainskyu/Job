package Web.Controller;

import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Web.Domain.Receipt;
import Web.Repository.ReceiptRepository;

@Controller
public class CheckPublic {
	
	@Autowired
	private ReceiptRepository rr;
	
	@RequestMapping(value="/electioncheck", method=RequestMethod.POST)
	@ResponseBody
	public boolean mainbulletinboard(@RequestParam(value="sessionID") String electionid,HttpServletResponse response) {
		
		System.out.println(electionid);
		boolean check = false;		
		Iterator<Receipt> receipt = rr.findAll().iterator();
		while (receipt.hasNext()) {
			Receipt temp= receipt.next();
			if (temp.getTopic().getId()==Integer.valueOf(electionid)) {
				check=true;
				break;
			}
		}
		
		if(check==true) {
			Cookie cookieelectionid = new Cookie("electionid",electionid);
			//cookieusername.setDomain("localhost");
			cookieelectionid.setPath("/");
			cookieelectionid.setMaxAge(-1);
			response.addCookie(cookieelectionid);
			//System.out.println("username: " + cookieusername.getName()+" age is:" +cookieusername.getMaxAge());
		}
		
		return check;
	}

}
