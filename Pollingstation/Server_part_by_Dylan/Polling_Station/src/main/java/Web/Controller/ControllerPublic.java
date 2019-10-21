package Web.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import Web.Cryptography.CurveConfig;
import Web.Cryptography.ECurve;
import Web.Domain.Ballot;
import Web.Domain.Options;
import Web.Domain.Receipt;
import Web.Domain.Topic;
import Web.Repository.BallotRepository;
import Web.Repository.ReceiptRepository;
import Web.Repository.TopicRepository;

@Controller
@RequestMapping(value="/public")
public class ControllerPublic {

	@Autowired
	private BallotRepository br;
	
	@Autowired
	private TopicRepository tp;
	
	@Autowired
	private ReceiptRepository rr;
	
	
	
	@RequestMapping(value="/main_bulletin_board")
	public ModelAndView publicmain(@RequestParam(value="sessionID") String electionid,Model model) {
		
		model.addAttribute("electionid",electionid);
		Iterator <Topic> t = tp.findAll().iterator();
		while(t.hasNext()) {
			Topic temp = t.next();
			if(temp.getId()==Integer.valueOf(electionid)) {
				model.addAttribute("title",temp.getName());
				break;
			}
		}
		
		List <Options> opt = tp.findOne(Integer.valueOf(electionid)).getOptions();
		model.addAttribute("candidatelist",opt);
		
		return new ModelAndView("mainpublic_bulletin_board");
	}
	
	@RequestMapping("/download")
	//https://blog.csdn.net/xiongyouqiang/article/details/80488202
	public ResponseEntity<byte[]> download(@RequestParam(value="sessionID") String electionid,HttpServletRequest request) throws IOException {
		String path=result(Integer.valueOf(electionid));
		File file = new File(path);
		byte[] body = null;
		InputStream is = new FileInputStream(file);
		body = new byte[is.available()];
		is.read(body);
		is.close();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attchement;filename=" + file.getName());
		HttpStatus statusCode = HttpStatus.OK;
		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
		return entity;
	}
	@RequestMapping("/download_tool")
	//https://blog.csdn.net/xiongyouqiang/article/details/80488202
	public ResponseEntity<byte[]> downloadtool() throws IOException {
		
		File file = new File("bb_result\\verification.jar");
		byte[] body = null;
		InputStream is = new FileInputStream(file);
		body = new byte[is.available()];
		is.read(body);
		is.close();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attchement;filename=" + file.getName());
		HttpStatus statusCode = HttpStatus.OK;
		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
		return entity;
	}
	
	
	private String result(int electionid) throws IOException {
		ECurve cv = CurveConfig.getNist256();
		JSONObject json = new JSONObject();		
		Topic topic=tp.findOne(electionid);
		json.put("topicID",topic.getId());
		json.put("title",topic.getName());
		json.put("publickey", topic.getPublickey());
		json.put("curvePrime", cv.getPrime().toString());
		json.put("curveSize", cv.getSize().toString());
		json.put("curveA", cv.getA().toString());
		json.put("curveB", cv.getB().toString());
		json.put("curveOrder", cv.getOrder().toString());
		json.put("g1", topic.getG1());
		json.put("g2", topic.getG2());

		//generate receipt list for json
		json.put("ballots",generateBallotArray(topic.getBallot()));
		json.put("receipts",generateReceiptArray(topic.getReceipt()));
		json.put("options",generateOptionArray(topic.getOptions()));


		File folder = new File("bb_result");
		if(!folder.exists()) {
			folder.mkdir();
		}

		File result = new File("bb_result\\result"+electionid+".json");
		if(!result.exists()) {
			result.createNewFile();
		}
		BufferedWriter bw=new BufferedWriter(new FileWriter(result,false));
		bw.write(json.toJSONString());
		bw.flush();
		bw.close();
		return result.getPath();
	}


	private JSONArray generateReceiptArray(List<Receipt> receipts){
		JSONArray result = new JSONArray();
		for (Receipt r :receipts){
			JSONObject temp = new JSONObject();
			temp.put("id",r.getId());
			temp.put("stageTwoSign",r.getStageTwoSign());
			temp.put("stageOneSign",r.getStageOneSign());
			temp.put("stageOneHash",r.getStageOneHash());
			temp.put("t",r.getT());
			temp.put("t1",r.getT1());
			temp.put("t2",r.getT2());
			temp.put("state",r.getState());
			temp.put("ballotID",r.getBallot().get(0).getBallotid());
			result.add(temp);
		}
		return result;
	}

	private JSONArray generateOptionArray(List<Options> options){
		JSONArray result = new JSONArray();
		for (Options o: options){
			JSONObject temp = new JSONObject();
			temp.put("id",o.getId());
			temp.put("candidate",o.getOptions());
			temp.put("s",o.getS());
			temp.put("vote",o.getTally());
			result.add(temp);
		}
		return result;
	}


	private JSONArray generateBallotArray(List<Ballot> ballots){
		JSONArray result = new JSONArray();
		for (Ballot b: ballots){
			JSONObject temp = new JSONObject();
			temp.put("ballotId",b.getBallotid());
			temp.put("candidateID",b.getOptions().getId());
			temp.put("R",b.getR());
			temp.put("Z",b.getZ());
			temp.put("c1",b.getC1());
			temp.put("c2",b.getC2());
			temp.put("r1",b.getR1());
			temp.put("r2",b.getR2());
			temp.put("random",b.getRandom());
			temp.put("state",b.getState());
			temp.put("v",b.getV());
			result.add(temp);
		}
		return result;
	}
}
