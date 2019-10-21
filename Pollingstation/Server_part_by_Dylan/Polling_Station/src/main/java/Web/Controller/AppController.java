package Web.Controller;



import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import Web.Cryptography.*;
import Web.Domain.Options;
import Web.Domain.Passcode;
import Web.Domain.Receipt;
import Web.Domain.Topic;
import Web.Repository.*;
import Web.Domain.Ballot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/app")
public class AppController {


    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private PasscodeRepository passcodeRepository;

    @Autowired
    private BallotRepository ballotRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private OptionRepository optionRepository;


    @RequestMapping(value = "/confirmPassword", method = RequestMethod.POST)
    @ResponseBody
    public String confirmPassword(@RequestBody String json) {
        String res;
        System.out.println(new Date() + json);

        JSONObject jsonObj = JSONObject.parseObject(json);
        int electionID = jsonObj.getInteger("electionID");
        String password = jsonObj.getString("password");

        Date endTime = null;
        Topic topic = topicRepository.findOne(electionID);
        try {
            endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(topic.getEtime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //todo need check the state of topic
        if (topic.getPassword().equals(password) && endTime.after(new Date())) {
            jsonObj.clear();
            jsonObj.put("result", "true");
            jsonObj.put("title", topic.getName());
            res = jsonObj.toString();
        } else {
            jsonObj.clear();
            jsonObj.put("result", "false");
            res = jsonObj.toString();
        }
        return res;

    }

    @RequestMapping(value = "/confirmPasscode", method = RequestMethod.POST)
    @ResponseBody
    public String comfirmPasscode(@RequestBody String json) {
        String res = "";

        System.out.println(new Date() + json);

        JSONObject jsonObj = JSONObject.parseObject(json);
        int electionID = jsonObj.getInteger("electionID");
        String password = jsonObj.getString("password");
        String passcode = jsonObj.getString("passcode");
        //Find all valid passcode;
        List<String> pclist = findPasscodes(electionID);

        if (password.equals(topicRepository.findOne(electionID).getPassword()) && pclist.contains(passcode)) {
            //Find all valid options;
            List<String> optionsList = findOptions(electionID);

            jsonObj.clear();
            jsonObj.put("result", "true");
            jsonObj.put("options", JSONObject.toJSONString(optionsList));
            res = jsonObj.toString();
        } else {
            jsonObj.clear();
            jsonObj.put("result", "false");
            res = jsonObj.toString();
        }

        return res;
    }


    @RequestMapping(value = "/voteStageOne", method = RequestMethod.POST)
    @ResponseBody
    public String voteStageOne(@RequestBody String json) {
        String res = "";

        System.out.println(new Date() + json);

        JSONObject jsonObj = JSONObject.parseObject(json);
        int topicID = jsonObj.getInteger("electionID");
        String passcode = jsonObj.getString("passcode");
        String option = jsonObj.getString("candidate");
        Topic topic = topicRepository.findOne(topicID);


        List<String> pclist = new ArrayList<>();
        for (Passcode i : topic.getPasscode()) {
            if (i.getState())
                pclist.add(i.getPasscode());
        }


        //change passcode state
        if (topic != null && jsonObj.get("type").equals("stageOne") && pclist.contains(passcode)) {
            List<String> optionsList = new ArrayList<>();
            for (Options o : topic.getOptions()) {
                optionsList.add(o.getOptions());
            }

            int passcodeId = findPasscodeID(topicID, passcode);
            if (optionsList.contains(option)) {
                jsonObj.clear();
                jsonObj.put("result", true);
                jsonObj = generateBallot(topicID, passcodeId, option);
                res = jsonObj.toString();
            } else {
                jsonObj.clear();
                jsonObj.put("result", false);
                res = jsonObj.toString();
            }
        } else {
            jsonObj.clear();
            jsonObj.put("result", false);
            res = jsonObj.toString();
        }
        return res;
    }

    @RequestMapping(value = "/voteStageTwo", method = RequestMethod.POST)
    @ResponseBody
    public String voteStageTwo(@RequestBody String json) {

        System.out.println(new Date() + json);

        JSONObject jsonObj = JSONObject.parseObject(json);
        int topicID = jsonObj.getInteger("electionID");
        int ballotID = jsonObj.getInteger("ballotID");

        String passcode = jsonObj.getString("passcode");
        String audit = jsonObj.getString("result");

        if (jsonObj.get("type").equals("stageTwo")) {
            if (audit.equals("confirm")){
                castVote(topicID,ballotID);
                jsonObj.clear();
                jsonObj.put("result","confirm");
                //change receipt state

                int passcodeID = findPasscodeID(topicID,passcode);
                Passcode p = passcodeRepository.findOne(passcodeID);
                p.setState(false);
                passcodeRepository.save(p);


            }else{
                String option = jsonObj.getString("candidate");
                int optionID = findOptionID(topicID,option);
                auditVote(topicID,ballotID,optionID);
                jsonObj.clear();
                jsonObj.put("result","cancel");
            }

        }

        return jsonObj.toString();
    }


    @RequestMapping(value = "/connectionTest", method = RequestMethod.GET)
    @ResponseBody
    public String connectionTest() {
        return "true";
    }


/*    @RequestMapping(value = "/testOnly", method = RequestMethod.GET)
    @ResponseBody
    public String test() {

        String res = "";
        List<Ballot> bList = new ArrayList<>();
        for (Ballot b : ballotRepository.findAll()){
            if(b.getBallotid()==1){
                bList.add(b);
            }
        }

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("head", "dreipvoting");
        jsonObj.put("topicID", 1);
        jsonObj.put("stage","stageOne");
        JSONArray arrayForSign = generateListForSign(bList,true);
        jsonObj.put("ballots",arrayForSign);


        /*boolean check = true;
        //check 1st ZKP
        Iterator<Receipt> receiptIterator = receiptRepository.findAll().iterator();
        ECurve curve = CurveConfig.getGenerator().getCurve();
        while (receiptIterator.hasNext()){
            //check 1st ZKP
            Receipt temp = receiptIterator.next();
            ECPoint g1 = RandomGenerator.decode(temp.getTopic().getG1(),curve);
            ECPoint g2 = RandomGenerator.decode(temp.getTopic().getG2(),curve);
            for (int i = 0 ; i < temp.getBallot().size(); i++){
                ECPoint R = RandomGenerator.decode(temp.getBallot().get(i).getR(),curve);
                ECPoint Z = RandomGenerator.decode(temp.getBallot().get(i).getZ(),curve);
                BigInteger c1 = new BigInteger(temp.getBallot().get(i).getC1());
                BigInteger c2 = new BigInteger(temp.getBallot().get(i).getC2());
                BigInteger r1 = new BigInteger(temp.getBallot().get(i).getR1());
                BigInteger r2 = new BigInteger(temp.getBallot().get(i).getR2());
                try {
                    boolean tempcheck = ZKP.check1stZKP(R,Z, g1, g2, c1, c2, r1, r2, temp.getTopic().getId(), temp.getBallot().get(i).getOptions().getId(), temp.getBallot().get(i).getBallotid());
                    check = check && tempcheck;
                    if (tempcheck){
                        System.out.println("topicID : "+ temp.getTopic().getId() + "ballotID :" + temp.getBallot().get(i).getId()+ " 1st success ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            //check 2nd ZKP
            if (temp.getState().equals(" ")) {
                ECPoint R2 = null;
                ECPoint Z2 = null ;
                boolean firstR = true;
                boolean firstZ = true;
                ECPoint t1 = RandomGenerator.decode(temp.getT1(),curve);
                ECPoint t2 = RandomGenerator.decode(temp.getT2(),curve);
                BigInteger t = new BigInteger(temp.getT());
                for (int i = 0 ; i < temp.getBallot().size(); i ++){
                    if(firstR){
                        R2 = RandomGenerator.decode(temp.getBallot().get(i).getR(),curve);
                        firstR =false;
                    }else{
                        R2 = R2.add(RandomGenerator.decode(temp.getBallot().get(i).getR(),curve));
                    }
                    if(firstZ){
                        Z2 = RandomGenerator.decode(temp.getBallot().get(i).getZ(),curve);
                        firstZ =false;
                    }else{
                        Z2 = Z2.add(RandomGenerator.decode(temp.getBallot().get(i).getZ(),curve));
                    }
                }

                try {
                    boolean tempcheck = ZKP.check2ndZKP(R2, Z2, g1, g2, t1, t2, t, temp.getTopic().getId(), temp.getBallot().get(0).getBallotid());
                    check = check && tempcheck;
                    if (tempcheck) {
                        System.out.println("topicID : " + temp.getTopic().getId() + "receiptID :" + temp.getId() + " 2nd success ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        //check option sum and tally



        return jsonObj.toString();
    }
*/




    private List<String> findPasscodes(int topicID) {
        Topic topic = topicRepository.findOne(topicID);
        List<String> resultList = new ArrayList<>();
        for (Passcode i : topic.getPasscode()) {
            if (i.getState())
                resultList.add(i.getPasscode());
        }
        return resultList;
    }

    private int findPasscodeID(int topicID, String passcode) {
        Topic topic = topicRepository.findOne(topicID);
        for (Passcode p : topic.getPasscode()) {
            if (p.getPasscode().equals(passcode) && p.getState()) {
                return p.getId();
            }
        }
        throw new NullPointerException("Passcode Error");
    }

    private List<String> findOptions(int topicID) {
        Topic topic = topicRepository.findOne(topicID);
        List<String> resultList = new ArrayList<>();
        for (Options option : topic.getOptions()) {
            resultList.add(option.getOptions());
        }
        return resultList;
    }

    private int findOptionID(int topicID, String option) {
        Topic topic = topicRepository.findOne(topicID);
        for (Options o : topic.getOptions()) {
            if (o.getOptions().equals(option)) {
                return o.getId();
            }
        }
        throw new NullPointerException("Option name error");
    }


    private JSONObject generateBallot(int topicID, int passcodeID, String option) {

        //get g1 g1 curve and privateKey;
        Topic topic = topicRepository.findOne(topicID);
        ECurve curve = CurveConfig.getNist256();
        String encodeG1 = topic.getG1();
        String encodeG2 = topic.getG2();
        ECPoint g1 = RandomGenerator.decode(encodeG1, curve);
        ECPoint g2 = RandomGenerator.decode(encodeG2, curve);
        BigInteger order = curve.getOrder();
        BigInteger bitLength = new BigInteger(String.valueOf(order.bitLength()));
        BigInteger privateKey = new BigInteger(topic.getPrivatekey());


        //generate new ballots
        int ballotID = (int) (receiptRepository.count() + 1);
        List<Ballot> bList = new ArrayList<>();


        for (Options o : topic.getOptions()) {
            BigInteger r = RandomGenerator.getRandomBytes(bitLength, order.add(new BigInteger("-1")));
            ECPoint R = g2.multiply(r);
            Map<String, String> ZKPvalue = null;
            ECPoint Z;
            Ballot ballot = new Ballot();
            if (o.getOptions().equals(option)) {
                Z = g1.multiply(r).add(g1);
                try {
                    ZKPvalue = ZKP.generate1stZKP(R, Z, r, g1, g2, true, topicID, o.getId(), ballotID);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ballot.setV(1);
            } else {
                Z = g1.multiply(r);
                try {
                    ZKPvalue = ZKP.generate1stZKP(R, Z, r, g1, g2, false, topicID, o.getId(), ballotID);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ballot.setV(0);
            }
            ballot.setTitle(topic.getName());
            ballot.setBallotid(ballotID);
            ballot.setRandom(r.toString());
            ballot.setR(R.getEncode());
            ballot.setZ(Z.getEncode());
            ballot.setC1(ZKPvalue.get("c1"));
            ballot.setC2(ZKPvalue.get("c2"));
            ballot.setR1(ZKPvalue.get("r1"));
            ballot.setR2(ZKPvalue.get("r2"));
            ballot.setState("initialise");
            ballot.setTopic(topic);
            ballot.setOptions(o);
            ballotRepository.save(ballot);
            bList.add(ballot);
        }


        // generate stage one signature
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("head", "dreipvoting");
        jsonObj.put("topicID", topicID);
        jsonObj.put("stage","stageOne");
        JSONArray arrayForSign = generateListForSign(bList,false);
        jsonObj.put("ballots",arrayForSign);

        System.out.println(jsonObj.toString());
        String sign = null;
        try {
            sign = Signature.sign(jsonObj.toString(), privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //generate confirmation code
        byte[] mybytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(sign.getBytes("utf-8"));
            mybytes = md.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String confirmCode = Base32.encode(mybytes).substring(0, 50);


        // generate new receipt
        Receipt receipt = new Receipt();
        //receipt.setElectionid(topicID);
        receipt.setTopic(topic);
        //receipt.setBallotid(ballotID);
        receipt.getBallot().addAll(bList);
        receipt.setStageOneSign(sign);
        receipt.setStageOneHash(confirmCode);
        receipt.setPasscodeid(passcodeID);
        receipt.setState("initialise");
        receiptRepository.save(receipt);

        jsonObj.clear();
        jsonObj.put("ballotID", ballotID);
        jsonObj.put("confirmationCode", confirmCode);

        //add ballot and receipt into topic
        topic.getReceipt().add(receipt);
        topic.getBallot().addAll(bList);
        topicRepository.save(topic);

        return jsonObj;
    }



    private void castVote(int topicID, int ballotID) {

        //get g1 g2 curve
        Topic topic = topicRepository.findOne(topicID);
        ECurve curve = CurveConfig.getNist256();
        String encodeG1 = topic.getG1();
        String encodeG2 = topic.getG2();
        ECPoint g1 = RandomGenerator.decode(encodeG1, curve);
        ECPoint g2 = RandomGenerator.decode(encodeG2, curve);
        BigInteger privateKey = new BigInteger(topic.getPrivatekey());
        BigInteger r = new BigInteger("0");

        //find receipt and set status
        Receipt receipt = null;
        Iterator<Receipt> rIterator = receiptRepository.findAll().iterator();
        while (rIterator.hasNext()) {
            Receipt tempR = rIterator.next();
            if (tempR.getBallot().get(0).getBallotid()== ballotID) {
                receipt = tempR;
                break;
            }
        }
        receipt.setState(" ");


        // find all ballots ans set state
        Iterator<Ballot> bIterator = ballotRepository.findAll().iterator();
        List<Ballot> bList = new ArrayList<>();
        while (bIterator.hasNext()) {
            Ballot tempB = bIterator.next();
            if (tempB.getBallotid() == ballotID) {
                tempB.setState("Confirmed");
                bList.add(tempB);
            }
        }

        //count tally and sum and remove ballots' tally and s
        for (Options o : topicRepository.findOne(topicID).getOptions()) {
            int tally = o.getTally();
            BigInteger s = new BigInteger(o.getS());
            for (Ballot b : bList) {
                if (b.getOptions().equals(o)) {
                    tally = tally + b.getV();
                    s = s.add(new BigInteger(b.getRandom()));
                    r = r.add(new BigInteger(b.getRandom()));
                    b.setV(0);
                    b.setRandom("");
                }
            }
            o.setTally(tally);
            o.setS(s.toString());
            optionRepository.save(o);
        }


        //generate 2nd ZKP
        Map<String,String> ZKPvalue = null;
        try {
            ZKPvalue = ZKP.generate2ndZKP(r,g1,g2,topicID,ballotID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        receipt.setT(ZKPvalue.get("t"));
        receipt.setT1(ZKPvalue.get("t1"));
        receipt.setT2(ZKPvalue.get("t2"));

        //generate signature
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("head","dreipvoting");
        jsonObj.put("topicID",topicID);
        jsonObj.put("ballotID",ballotID);
        jsonObj.put("stage","stageTwo");
        JSONArray arrayForSign = generateListForSign(bList,true);
        jsonObj.put("ballots",arrayForSign);

        System.out.println(jsonObj.toString());
        String sign = null;
        try {
            sign = Signature.sign(jsonObj.toString(),privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        receipt.setStageTwoSign(sign);

        //save ballot
        ballotRepository.save(bList);

        //save receipt
        receiptRepository.save(receipt);

    }


    private void auditVote(int topicID,int ballotID, int optionID) {

        BigInteger privateKey = new BigInteger(topicRepository.findOne(topicID).getPrivatekey());


        JSONObject jsonObj = new JSONObject();
        jsonObj.put("head","dreipvoting");
        jsonObj.put("topicID",topicID);
        jsonObj.put("ballotID", ballotID);
        jsonObj.put("stage","stageTwo");


        // change the state of all ballots
        Iterator<Ballot> bIterator = ballotRepository.findAll().iterator();
        List<Ballot> bList = new ArrayList<>();
        while (bIterator.hasNext()) {
            Ballot tempB = bIterator.next();
            if (tempB.getBallotid() == ballotID) {
                tempB.setState("Audited");
                bList.add(tempB);
            }
        }

        JSONArray arrayForSign = generateListForSign(bList,true);
        jsonObj.put("ballots", arrayForSign);
        System.out.println(jsonObj.toString());

        //sign
        String sign = null;
        try {
            sign = Signature.sign(jsonObj.toString(),privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //change the state of the receipt
        Receipt receipt = null;
        Iterator<Receipt> rIterator = receiptRepository.findAll().iterator();
        while (rIterator.hasNext()) {
            Receipt tempR = rIterator.next();
            if (tempR.getBallot().get(0).getBallotid() == ballotID) {
                receipt = tempR;
                break;
            }
        }

        receipt.setStageTwoSign(sign);
        receipt.setState("Ballot Cancelled : " + optionRepository.findOne(optionID).getOptions());

        receiptRepository.save(receipt);
        ballotRepository.save(bList);

    }

    private JSONArray generateListForSign(List<Ballot> bList, boolean isStageTwo){
        JSONArray arrayForSign = new JSONArray();
        for (Ballot b : bList){
            JSONObject temp = new JSONObject();
            temp.put("title", b.getTitle());
            temp.put("ballotid",b.getBallotid());
            temp.put("R",b.getR());
            temp.put("Z",b.getZ());
            temp.put("c1",b.getC1());
            temp.put("c2",b.getC2());
            temp.put("r1",b.getR1());
            temp.put("r2",b.getR2());
            if (isStageTwo){
                temp.put("state",b.getState());
            }
            arrayForSign.add(temp);
        }
        return arrayForSign;
    }

}
