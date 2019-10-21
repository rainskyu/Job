package Web.Controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import Web.Cryptography.CurveConfig;
import Web.Cryptography.ECurve;
import Web.Cryptography.RandomGenerator;
import Web.Cryptography.g1andg2;
import Web.Domain.Ballot;
import Web.Domain.Coordinator;
import Web.Domain.Options;
import Web.Domain.Passcode;
import Web.Domain.Topic;
import Web.Repository.CoordinatorRepository;
import Web.Repository.OptionRepository;
import Web.Repository.PasscodeRepository;
import Web.Repository.TopicRepository;

@Controller
@RequestMapping(value="/coordinator")
public class ControllerCoordinator {

	@Autowired
	private TopicRepository tp;
	
	@Autowired
	private OptionRepository or;
	
	@Autowired
	private CoordinatorRepository cr;
	
	@Autowired
	private PasscodeRepository pr;
	
	
	
	@RequestMapping(value="/main")
	public ModelAndView main(@ModelAttribute("topic") Coordinator cc,HttpServletRequest request,Model model) throws IOException {
		
		Cookie[] cookies =request.getCookies();
		String username="";
		for (Cookie cookie:cookies) {
			if(cookie.getName().equals("username")) {
				username=cookie.getValue();
			}
		}
		/*List <Topic> list = new ArrayList<>();
		Iterator<Coordinator> coordinator_iterator = cr.findAll().iterator();
		while(coordinator_iterator.hasNext()) {
			Coordinator temp=coordinator_iterator.next();
			if(temp.getUsername().equals(username)) {
				list=temp.getTopic();
			}
			
		}
		
		model.addAttribute("topiclist",list);*/
		List <Topic> list = new ArrayList<>();
		Iterator<Topic> topic_iterator = tp.findAll().iterator();
		while(topic_iterator.hasNext()) {
			Topic temp=topic_iterator.next();
			if(temp.getCoordinator().getUsername().equals(username)) {
				list.add(temp);
			}
			
		}
		
		model.addAttribute("topiclist",list);
		return new ModelAndView("maincoordinator");
	}
	
	
	@RequestMapping(value="/topicdetail")
	public ModelAndView topicdetail(Model model, @RequestParam("topicid") String id,@ModelAttribute("topic") Topic topic) throws ParseException {
		
		
		Iterator <Topic> firstcheck = tp.findAll().iterator();
		Topic checktime=null;
		while (firstcheck.hasNext()) {
			Topic temp = firstcheck.next();
			if(temp.getId()==Integer.valueOf(id)) {
				checktime=temp;
				break;
			}
		}
		
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
		Calendar entrycheck = Calendar.getInstance();
		Date stime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(checktime.getStime());
		Date ctime= entrycheck.getTime();
		
		if (ctime.compareTo(stime)<0){
			
			Iterator<Topic> topici = tp.findAll().iterator();
			while(topici.hasNext()) {
				Topic temp=topici.next();
				if (String.valueOf(temp.getId()).equals(id)) {
					topic=temp;
					break;
				}
			}
			model.addAttribute("topic_name",topic.getName());
			model.addAttribute("password",topic.getPassword());
			model.addAttribute("duration",topic.getDuration());
			model.addAttribute("options",topic.getOptions());
			model.addAttribute("end",topic.getOptions().size());
			model.addAttribute("id",id);
			
			
			return new ModelAndView("maintopicedit");
			
		}else {
			
			model.addAttribute("hint",true);
			
			List <Topic> list = new ArrayList<>();
			Iterator<Topic> topici = tp.findAll().iterator();
			while(topici.hasNext()) {
				Topic temp=topici.next();
				list.add(temp);
			}
			
			model.addAttribute("topiclist",list);
			return new ModelAndView("maincoordinator");
		}
	}
	
	
	
	@RequestMapping(value="/modified" ,method=RequestMethod.POST)
	public ModelAndView modify(@ModelAttribute("topic") Topic topic,@RequestParam Map<String,String> all,Model model,HttpServletRequest request) throws ParseException {
		
		int sizeoption = tp.findOne(topic.getId()).getOptions().size();
		//System.out.println(sizeoption);
		Iterator <String> allparameterscount = all.keySet().iterator();
		int numberofoptions=0;
		while (allparameterscount.hasNext()) {
			String temp = allparameterscount.next();
			if(temp.contains("options")) {
				numberofoptions++;
			}
		}
		
		if(numberofoptions>sizeoption) {
			int id=0;
			Iterator <Options> iterator_option = or.findAll().iterator();
			while(iterator_option.hasNext()) {
				Options temp = iterator_option.next();
				if(temp.getId()>id) {
					id=temp.getId();
				}
			}
			id++;
			List <Options> original = tp.findOne(topic.getId()).getOptions();
			int count=0;
			Iterator <String> allparameter = all.keySet().iterator();
			while (allparameter.hasNext()) {
				String temp = allparameter.next();
				if (temp.contains("options")) {
					if(count<sizeoption) {
						original.get(count).setOptions(all.get(temp));
						count++;
					}else{
						Options option = new Options();
						option.setId(id);
						option.setOptions(all.get(temp));
						option.setS("0");
						option.setTally(0);
						//option.setTopic(topic);
						original.add(option);
						id++;
					}
				}
			}
			topic.setOptions(original);
		}else
		if(numberofoptions<sizeoption) {
			List <Options> original = tp.findOne(topic.getId()).getOptions();
			int id=original.get(0).getId();
			Iterator <Options> countid = original.iterator();
			while(countid.hasNext()) {
				Options temp = countid.next();
				if(id>temp.getId()) {
					id=temp.getId();
				}
			}
			List <Options> news = new ArrayList<>();
			Iterator <String> allparameter = all.keySet().iterator();
			while (allparameter.hasNext()) {
				String temp = allparameter.next();
				if (temp.contains("options")) {
					Options option = new Options();
					option.setId(id);
					option.setOptions(all.get(temp));
					option.setS("0");
					option.setTally(0);
					//option.setTopic(topic);
					news.add(option);
					id++;
				}
			}
			topic.setOptions(news);
		}else {
			List <Options> original = tp.findOne(topic.getId()).getOptions();
			int count=0;
			Iterator <String> allparameter = all.keySet().iterator();
			while (allparameter.hasNext()) {
				String temp = allparameter.next();
				if (temp.contains("options")) {
					original.get(count).setOptions(all.get(temp));
					count++;
				}
			}
			topic.setOptions(original);
		}

		String rstime="";
		Iterator<Topic> topic_iterator = tp.findAll().iterator();
		while(topic_iterator.hasNext()) {
			Topic temp=topic_iterator.next();
			if(temp.getId()==topic.getId()) {
				rstime=temp.getStime();
			}
		}
		
		
		Calendar cal = Calendar.getInstance();
		Date time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rstime);
		cal.setTime(time);
		cal.add(Calendar.DATE, Integer.valueOf(topic.getDuration()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		topic.setEtime(format.format(cal.getTime()));
		topic.setStime(rstime);
		topic.setState("Pending");
		Iterator <Coordinator> coordinator = cr.findAll().iterator();

		
		
		Cookie[] cookies =request.getCookies();
		String username="";
		for (Cookie cookie:cookies) {
			if(cookie.getName().equals("username")) {
				username=cookie.getValue();
			}
		}
		
		while(coordinator.hasNext()) {
			Coordinator temp=coordinator.next();
			if(temp.getUsername().equals(username)) {
				topic.setCoordinator(temp);
				
			}
		}
		
		
		//System.out.println(topic.getId());
		for(Options b :topic.getOptions()){
			System.out.println(b.getOptions());
		}
		tp.delete(topic.getId());
		tp.save(topic);
		List <Topic> list = new ArrayList<>();
		Iterator<Topic> topici = tp.findAll().iterator();
		while(topici.hasNext()) {
			Topic temp=topici.next();
			if(temp.getCoordinator().getUsername().equals(username)) {
				list.add(temp);
			}
		}
		
		model.addAttribute("topiclist",list);
		return new ModelAndView("maincoordinator");
	}
	
	@RequestMapping(value="/delete" ,method=RequestMethod.GET)
	public ModelAndView delete(@RequestParam("topicid") String id,Model model,HttpServletRequest request) {
		
		Cookie[] cookies =request.getCookies();
		String username="";
		for (Cookie cookie:cookies) {
			if(cookie.getName().equals("username")) {
				username=cookie.getValue();
			}
		}
		
		tp.delete(Integer.valueOf(id));
		
		/*List <Topic> list = new ArrayList<>();
		Iterator<Coordinator> coordinator_iterator = cr.findAll().iterator();
		while(coordinator_iterator.hasNext()) {
			Coordinator temp=coordinator_iterator.next();
			if(temp.getUsername().equals(username)) {
				list=temp.getTopic();
			}
			
		}
		
		model.addAttribute("topiclist",list);*/
		
		List <Topic> list = new ArrayList<>();
		Iterator<Topic> topic_iterator = tp.findAll().iterator();
		while(topic_iterator.hasNext()) {
			Topic temp=topic_iterator.next();
			if(temp.getCoordinator().getUsername().equals(username)) {
				list.add(temp);
			}
			
		}
		model.addAttribute("topiclist",list);
		return new ModelAndView("maincoordinator");
		
	}
	
	@RequestMapping(value="/create")
	public ModelAndView create(@ModelAttribute("topic") Topic topic,Model model) {
		List<String> day = new ArrayList<String>();
		for(int i=1;i<=31;i++) {
			day.add(String.valueOf(i));
		}
		
		List<String> month = new ArrayList<String>();
		for(int i=1;i<=12;i++) {
			month.add(String.valueOf(i));
		}
		
		List<String> year = new ArrayList<String>();
		for(int i=2019;i<=2100;i++) {
			year.add(String.valueOf(i));
		}
		
		List<String> hour = new ArrayList<String>();
		for(int i=0;i<=23;i++) {
			hour.add(String.valueOf(i));
		}
		
		List<String> minutes = new ArrayList<String>();
		for(int i=0;i<=59;i++) {
			minutes.add(String.valueOf(i));
		}
		
		List<String> seconds = new ArrayList<String>();
		for(int i=0;i<=59;i++) {
			seconds.add(String.valueOf(i));
		}
		
		model.addAttribute("day", day);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("hour", hour);
		model.addAttribute("minutes", minutes);
		model.addAttribute("seconds", minutes);
		return new ModelAndView("maincreatetopic");
	}
	
	@RequestMapping(value="/add")
	public ModelAndView add(@ModelAttribute("topic") Topic topic,Model model,@RequestParam Map<String,String> all,HttpServletRequest request,HttpServletResponse response) throws NoSuchAlgorithmException, ServletException, IOException {
		try {
			
		
		int option_id=0;
		Iterator <Options> iterator_option = or.findAll().iterator();
		while(iterator_option.hasNext()) {
			Options temp = iterator_option.next();
			if(temp.getId()>option_id) {
				option_id=temp.getId();
			}
		}
		option_id++;
		Iterator <Topic> iterator_topic = tp.findAll().iterator();
		int topic_id=0;
		while(iterator_topic.hasNext()) {
			Topic temp = iterator_topic.next();
			if(temp.getId()>topic_id) {
				topic_id=temp.getId();
			}
		}
		topic_id++;
		topic.setId(topic_id);
		
		List<Options> options = new ArrayList<>();
		Iterator <String> allparameter = all.keySet().iterator();
		
		int year=0;
		int month=0;
		int day=0;
		int hour=0;
		int minutes=0;
		int seconds=0;
		while(allparameter.hasNext()) {
			String temp = allparameter.next();
			if (temp.contains("options")) {
				Options tempopt = new Options();
				tempopt.setId(option_id);
				tempopt.setOptions(all.get(temp));
				option_id++;
				tempopt.setS("0");
				tempopt.setTally(0);
				options.add(tempopt);
			}else
			if(temp.contains("year")) {
				year=Integer.valueOf(all.get(temp));
			}else
			if(temp.contains("month")) {
				month=Integer.valueOf(all.get(temp));
			}else
			if(temp.contains("day")) {
				day=Integer.valueOf(all.get(temp));
			}else
			if(temp.contains("hour")) {
				hour=Integer.valueOf(all.get(temp));
			}else
			if(temp.contains("minutes")) {
				minutes=Integer.valueOf(all.get(temp));
			}else
			if(temp.contains("seconds")) {
				seconds=Integer.valueOf(all.get(temp));
			}
		}
		topic.setOptions(options);
		
		Calendar date = Calendar.getInstance();
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
		date.set(year, month-1, day, hour, minutes, seconds);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		topic.setStime(format.format(date.getTime()));
		date.add(Calendar.DATE, Integer.valueOf(topic.getDuration()));
		topic.setEtime(format.format(date.getTime()));
		topic.setState("Pending");
		Cookie[] cookies =request.getCookies();
		String username="";
		for (Cookie cookie:cookies) {
			if(cookie.getName().equals("username")) {
				username=cookie.getValue();
			}
		}
		
		/*Iterator <Coordinator> coordinator = cr.findAll().iterator();
		while(coordinator.hasNext()) {
			Coordinator temp=coordinator.next();
			if(temp.getUsername().equals(username)) {
				//topic.setCoordinator(temp);
				List<Topic> topic_list = temp.getTopic();
				topic_list.add(topic);
				temp.setTopic(topic_list);
				cr.save(temp);
				
			}
		}*/
		
		Iterator <Coordinator> coordinator = cr.findAll().iterator();
		while(coordinator.hasNext()) {
			Coordinator temp=coordinator.next();
			if(temp.getUsername().equals(username)) {
				topic.setCoordinator(temp);
				
			}
		}
		ECurve cv = CurveConfig.getNist256();
		g1andg2 g1g2= new g1andg2();
		topic.setG1(g1g2.getG1());
		topic.setG2(g1g2.getG2(topic.getId()));

		BigInteger bprk = RandomGenerator.getRandomBytes(new BigInteger(String.valueOf(cv.getOrder().bitLength())), CurveConfig.getNist256().getOrder());
		String prk=bprk.toString();
		String puk = CurveConfig.getGenerator().multiply(bprk).getEncode();
		topic.setPrivatekey(prk.toString());
		topic.setPublickey(puk.toString());
		
		tp.save(topic);
		/*List <Topic> list = new ArrayList<>();
		Iterator<Coordinator> coordinator_iterator = cr.findAll().iterator();
		while(coordinator_iterator.hasNext()) {
			Coordinator temp=coordinator_iterator.next();
			if(temp.getUsername().equals(username)) {
				list=temp.getTopic();
			}
			
		}
		
		model.addAttribute("topiclist",list);*/
		List <Topic> list = new ArrayList<>();
		Iterator<Topic> topic_iterator = tp.findAll().iterator();
		while(topic_iterator.hasNext()) {
			Topic temp=topic_iterator.next();
			if(temp.getCoordinator().getUsername().equals(username)) {
				list.add(temp);
			}
			
		}
		
		model.addAttribute("topiclist",list);
			return new ModelAndView("maincoordinator");
		}catch(Exception e) {
			Cookie[] cookies =request.getCookies();
			String username="";
			for (Cookie cookie:cookies) {
				if(cookie.getName().equals("username")) {
					username=cookie.getValue();
				}
			}
			List <Topic> list = new ArrayList<>();
			Iterator<Topic> topic_iterator = tp.findAll().iterator();
			while(topic_iterator.hasNext()) {
				Topic temp=topic_iterator.next();
				if(temp.getCoordinator().getUsername().equals(username)) {
					list.add(temp);
				}
				
			}
			
			model.addAttribute("topiclist",list);
			return new ModelAndView("maincoordinator");
		}
	}
	
	@RequestMapping(value="/check_data",method=RequestMethod.POST)
	@ResponseBody
	public boolean checkdata(@RequestParam("year") String year,@RequestParam("month") String month,@RequestParam("day") String day,@RequestParam("hour") String hour,@RequestParam("minutes") String minutes,@RequestParam("seconds") String seconds) {
		Calendar date = Calendar.getInstance();
		date.set(Integer.valueOf(year), Integer.valueOf(month)-1, Integer.valueOf(day), Integer.valueOf(hour), Integer.valueOf(minutes), Integer.valueOf(seconds));
		Calendar current = Calendar.getInstance();
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
		if(date.compareTo(current)>0) {
			return true;
		}else {
			return false;
		}
	}
	
	@RequestMapping(value="/generate_passcode")
	public ModelAndView generate_passcode(@RequestParam("topicid")String topicid,Model model) {
		
		Iterator <Passcode> passcode_iterator = pr.findAll().iterator();
		boolean generate =true;
		boolean check=false;
		Passcode tps = new Passcode();
		while(generate) {
			String passcode=generate();
			while(passcode_iterator.hasNext()) {
				Passcode temp = passcode_iterator.next();
				if(temp.getPasscode().equals(passcode)) {
					check=true;
				}
			}
			if(check==false) {
				tps.setPasscode(passcode);
				Iterator <Passcode> tp_iterator = pr.findAll().iterator();
				int id=0;
				while(tp_iterator.hasNext()) {
					Passcode temp = tp_iterator.next();
					if(temp.getId()>id) {
						id=temp.getId();
					}
				}
				id++;
				tps.setId(id);
				tps.setState(true);
				/*Calendar current = Calendar.getInstance();
				TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
				current.add(Calendar.HOUR, 1);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				tps.setExipretime(format.format(current.getTime()));*/
				Iterator<Topic> topic_iterator = tp.findAll().iterator();
				while(topic_iterator.hasNext()) {
					Topic temp=topic_iterator.next();
					if(temp.getId()==Integer.valueOf(topicid)) {
						tps.setTopic(temp);
						temp.getPasscode().add(tps);
						tp.save(temp);
						break;
					}
				}
				//pr.save(tps);
				generate=false;
			}
		}
		
		model.addAttribute("passcode",tps.getPasscode());
		//model.addAttribute("time",tps.getExipretime());
		return new ModelAndView ("mainpasscode");
	}

	@RequestMapping(value="/test_passcode")
	public ModelAndView generatePasscode(@RequestParam("topicid")String topicid, Model model){
		List<Passcode> generated = new ArrayList<>();
		Topic topic = tp.findOne(Integer.parseInt(topicid));
		for (int i = 0 ; i < 50 ; i++){
			Passcode temp = new Passcode();
			temp.setPasscode(generate());
			temp.setTopic(topic);
			temp.setState(true);
			generated.add(temp);
			pr.save(temp);
		}

		topic.getPasscode().addAll(generated);
		tp.save(topic);

		for (Passcode p : generated){
			StringBuffer passcode = new StringBuffer();
			passcode.append(p.getPasscode().substring(0,4));
			passcode.append("-");
			passcode.append(p.getPasscode().substring(4,8));
			passcode.append("-");
			passcode.append(p.getPasscode().substring(8,12));
			p.setPasscode(passcode.toString());
		}

		model.addAttribute("passcodes", generated);

		return new ModelAndView("test");
	}
	
	
	private String generate() {
		String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random=new Random();
	     StringBuffer sb=new StringBuffer();
	     for(int i=0;i<12;i++){
	       int number=random.nextInt(36);
	       sb.append(str.charAt(number));
	     }
	     return sb.toString();
	}
	
}
