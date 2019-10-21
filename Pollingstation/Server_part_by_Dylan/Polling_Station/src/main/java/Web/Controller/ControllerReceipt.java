package Web.Controller;

import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Web.Domain.Ballot;
import Web.Domain.Receipt;
import Web.Domain.Topic;
import Web.Repository.BallotRepository;
import Web.Repository.ReceiptRepository;
import Web.Repository.TopicRepository;

@Controller
@RequestMapping(value="/receipt")
public class ControllerReceipt {

	
	@Autowired
	private BallotRepository br;
	
	@Autowired
	private ReceiptRepository rr;
	
	@Autowired
	private TopicRepository tp;
	
	@RequestMapping(value="/bulletin_board")
	public ModelAndView bulletinboard(Model model,HttpServletRequest request) {
		
		String electionid ="";
		String ballotid="";
		if(request.getParameter("sessionID")==null) {
			Cookie[] cookies =request.getCookies();
			for (Cookie cookie:cookies) {
				if(cookie.getName().equals("sessionID")) {
					electionid=cookie.getValue();
				}else
				if(cookie.getName().equals("ballot")) {
					ballotid=cookie.getValue();
				}
			}
		}else {
			electionid=request.getParameter("sessionID");
			ballotid=request.getParameter("ballot");
		}
		
		
		
		Iterator<Receipt> receipt_iterator = rr.findAll().iterator();
		
		while(receipt_iterator.hasNext()) {
			Receipt temp=receipt_iterator.next();
			if(temp.getBallot().get(0).getBallotid()==Integer.valueOf(ballotid) && temp.getTopic().getId()==Integer.valueOf(electionid)){
				model.addAttribute("ballot",temp.getBallot().get(0).getBallotid());
				model.addAttribute("sessionID",temp.getTopic().getId());
				String comfirm_code = temp.getStageOneHash();
				StringBuffer sb = new StringBuffer();
				int count=0;
				int count2=1;
				for(int i=0;i<comfirm_code.length();i++) {
					sb.append(comfirm_code.charAt(i));
					count++;
					if(count==5) {
						model.addAttribute("receipt"+count2,sb.toString());
						count2++;
						count=0;
						sb.delete(0, sb.length());
					}
				}
				if(temp.getState().contains("Cancelled")) {
					model.addAttribute("CandidatesTitle",temp.getState());
					model.addAttribute("type","Cancelled");
				}else {
					model.addAttribute("type","Confirmed");
				}
			}

		}
		return new ModelAndView("mainreceipt");
	}
	

	

	
	

}
