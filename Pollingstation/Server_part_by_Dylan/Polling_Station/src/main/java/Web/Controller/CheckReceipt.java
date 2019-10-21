package Web.Controller;

import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import Web.Domain.Receipt;
import Web.Repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Web.Domain.Ballot;
import Web.Repository.BallotRepository;

@Controller
public class CheckReceipt {

	@Autowired
	private ReceiptRepository rr;
	
	@RequestMapping(value="/receiptcheck", method=RequestMethod.POST)
	@ResponseBody
	public boolean verifycheck(@RequestParam(value="sessionID") String electionid,@RequestParam(value="ballot") String ballot,HttpServletResponse response) {
		
	
		boolean check = false;		
		Iterator<Receipt> receipt = rr.findAll().iterator();
		while (receipt.hasNext()) {
			Receipt temp= receipt.next();
			if (temp.getBallot().get(0).getBallotid()==Integer.valueOf(ballot) && temp.getTopic().getId()==Integer.valueOf(electionid)) {
				check=true;
				break;
			}
		}
		if(check==true) {
			Cookie cookiesessionid = new Cookie("sessionID",electionid);
			Cookie cookieelectionid = new Cookie("electionid",electionid);
			Cookie cookieballotid = new Cookie("ballot",ballot);
			//cookieusername.setDomain("localhost");
			cookiesessionid.setPath("/");
			cookieelectionid.setPath("/");
			cookieballotid.setPath("/");
			cookiesessionid.setMaxAge(-1);
			cookieelectionid.setMaxAge(-1);
			cookieballotid.setMaxAge(-1);
			response.addCookie(cookiesessionid);
			response.addCookie(cookieballotid);
			response.addCookie(cookieelectionid);
		}
		return check;
	}
}
