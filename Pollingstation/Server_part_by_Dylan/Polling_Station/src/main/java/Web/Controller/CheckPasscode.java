package Web.Controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import Web.Domain.Passcode;
import Web.Repository.PasscodeRepository;

@Controller
public class CheckPasscode {

	
	/*@Autowired
	private PasscodeRepository passcode;
	
	
	@RequestMapping(value="/confirmpasscode",method=RequestMethod.POST)
	@ResponseBody
	public String confirm(@RequestBody String json) {
		
		String res="false";
		Iterator<Passcode> passcode_iterator = passcode.findAll().iterator();
		while(passcode_iterator.hasNext()) {
			Passcode temp=passcode_iterator.next();
			if(temp.getPasscode().equals(json)){
				res="true";
				break;
			}
		}
		return res;
	}
*/


	
}
