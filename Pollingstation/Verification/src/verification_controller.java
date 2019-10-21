import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class verification_controller {

    @FXML
    private Button verifys;

    @FXML
    private Button verifyab;

    @FXML
    private Button verifytr;

    @FXML
    private Button verifyzkp;

    @FXML
    private Button verifyeqkp;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button tallyresult;

    @FXML
    private Label verifytallyincorrect;

    @FXML
    private Label verifytallycorrect;

    @FXML
    private Label verifyauditincorrect;

    @FXML
    private Label verifyauditcorrect;

    @FXML
    private Label verifysignatureincorrect;

    @FXML
    private Label verifysignaturecorrect;

    @FXML
    private Label verifyzkpincorrect;

    @FXML
    private Label verifyzkpcorrect;

    @FXML
    private Label verifyszkpincorrect;

    @FXML
    private Label verifyszkpcorrect;

    private String path;
    private JSONObject jsonObj;


    @FXML
    public void verifysecondzeroknowledgeproof() throws IOException, NoSuchAlgorithmException {
        boolean check = true;
        JSONArray receiptsArray = jsonObj.getJSONArray("receipts");
        JSONArray ballotsArray = jsonObj.getJSONArray("ballots");
        ECPoint g1 = getG1();
        ECPoint g2 = getG2();
        int topicID = jsonObj.getInteger("topicID");

        for (int i = 0; i < receiptsArray.size(); i++) {
            JSONObject tempReceipt = receiptsArray.getJSONObject(i);
            if (tempReceipt.getString("state").equals(" ")) {
                ECPoint R = null;
                ECPoint Z = null;
                boolean firstR = true;
                boolean firstZ = true;
                ECPoint t1 = pointOnCurve(tempReceipt.getString("t1"));
                ECPoint t2 = pointOnCurve(tempReceipt.getString("t2"));
                BigInteger t = new BigInteger(tempReceipt.getString("t"));
                int ballotID = tempReceipt.getInteger("ballotID");
                for (int j = 0; j < ballotsArray.size(); j++) {
                    JSONObject tempBallot = ballotsArray.getJSONObject(j);
                    if (tempBallot.getInteger("ballotId") == ballotID) {
                        if (firstR) {
                            R = pointOnCurve(tempBallot.getString("R"));
                            firstR = false;
                        } else {
                            R = R.add(pointOnCurve(tempBallot.getString("R")));
                        }
                        if (firstZ) {
                            Z = pointOnCurve(tempBallot.getString("Z"));
                            firstZ = false;
                        } else {
                            Z = Z.add(pointOnCurve(tempBallot.getString("Z")));
                        }
                    }
                }

                try {
                    check = check && check2ndZKP(R, Z, g1, g2, t1, t2, t, topicID, ballotID);
                    System.out.println("secondZKP : " + check2ndZKP(R, Z, g1, g2, t1, t2, t, topicID, ballotID));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        if (check == true) {
            verifytallyincorrect.setVisible(false);
            verifytallycorrect.setVisible(false);
            verifysignatureincorrect.setVisible(false);
            verifysignaturecorrect.setVisible(false);
            verifyauditincorrect.setVisible(false);
            verifyauditcorrect.setVisible(false);
            verifyszkpcorrect.setVisible(true);
            verifyszkpincorrect.setVisible(false);
            verifyzkpcorrect.setVisible(false);
            verifyzkpincorrect.setVisible(false);
        } else {
            verifytallyincorrect.setVisible(false);
            verifytallycorrect.setVisible(false);
            verifysignatureincorrect.setVisible(false);
            verifysignaturecorrect.setVisible(false);
            verifyauditincorrect.setVisible(false);
            verifyauditcorrect.setVisible(false);
            verifyszkpcorrect.setVisible(false);
            verifyszkpincorrect.setVisible(true);
            verifyzkpcorrect.setVisible(false);
            verifyzkpincorrect.setVisible(false);
        }

    }

    private boolean check2ndZKP(ECPoint R, ECPoint Z, ECPoint g1, ECPoint g2, ECPoint t1, ECPoint t2, BigInteger t, int electionid, int ballotid) throws Exception {
        BigInteger modulus = new BigInteger(jsonObj.getString("curveOrder"));
        String message = electionid + "," + ballotid + "," + getEncode(g1) + "," + getEncode(g2) + "," + getEncode(t1) + "," + getEncode(t2);
        Z = Z.subtract(g1);
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(message.getBytes("utf-8"));
        byte[] mybytes = md.digest();
        BigInteger hashBits = new BigInteger(1, mybytes);
        ECPoint righttg1 = t1.add(Z.multiply(hashBits.mod(modulus)));
        ECPoint righttg2 = t2.add(R.multiply(hashBits.mod(modulus)));
        ECPoint lefttg1 = g1.multiply(t.mod(modulus));
        ECPoint lefttg2 = g2.multiply(t.mod(modulus));

        if (lefttg1.equals(righttg1) && lefttg2.equals(righttg2)) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    public void verifyzeroknowledgeproof() throws IOException, NoSuchAlgorithmException {

        boolean check = true;
        JSONArray ballotsArray = jsonObj.getJSONArray("ballots");
        ECPoint g1 = getG1();
        ECPoint g2 = getG2();
        int topicID = jsonObj.getInteger("topicID");

        for (int i = 0; i < ballotsArray.size(); i++) {
            JSONObject temp = ballotsArray.getJSONObject(i);
            ECPoint R = pointOnCurve(temp.getString("R"));
            ECPoint Z = pointOnCurve(temp.getString("Z"));
            BigInteger c1 = new BigInteger(temp.getString("c1"));
            BigInteger c2 = new BigInteger(temp.getString("c2"));
            BigInteger r1 = new BigInteger(temp.getString("r1"));
            BigInteger r2 = new BigInteger(temp.getString("r2"));
            int optionID = temp.getInteger("candidateID");
            int ballotID = temp.getInteger("ballotId");

            try {
                check = check && check1stZKP(R, Z, g1, g2, c1, c2, r1, r2, topicID, optionID, ballotID);
                System.out.println("fisrtZKP : " + check1stZKP(R, Z, g1, g2, c1, c2, r1, r2, topicID, optionID, ballotID));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        if (check == true) {
            verifytallyincorrect.setVisible(false);
            verifytallycorrect.setVisible(false);
            verifysignatureincorrect.setVisible(false);
            verifysignaturecorrect.setVisible(false);
            verifyauditincorrect.setVisible(false);
            verifyauditcorrect.setVisible(false);
            verifyszkpcorrect.setVisible(false);
            verifyszkpincorrect.setVisible(false);
            verifyzkpcorrect.setVisible(true);
            verifyzkpincorrect.setVisible(false);
        } else {
            verifytallyincorrect.setVisible(false);
            verifytallycorrect.setVisible(false);
            verifysignatureincorrect.setVisible(false);
            verifysignaturecorrect.setVisible(false);
            verifyauditincorrect.setVisible(false);
            verifyauditcorrect.setVisible(false);
            verifyszkpcorrect.setVisible(false);
            verifyszkpincorrect.setVisible(false);
            verifyzkpcorrect.setVisible(false);
            verifyzkpincorrect.setVisible(true);
        }

    }

    private boolean check1stZKP(ECPoint R, ECPoint Z, ECPoint g1, ECPoint g2, BigInteger c1, BigInteger c2, BigInteger r1, BigInteger r2, int topicID, int optionID, int ballotID) throws Exception {
        BigInteger modulus = new BigInteger(jsonObj.getString("curveOrder"));

        ECPoint t1 = g1.multiply(r1).add(Z.multiply(c1));
        ECPoint t2 = g2.multiply(r1).add(R.multiply(c1));
        ECPoint t3 = g1.multiply(r2).add(Z.subtract(g1).multiply(c2));
        ECPoint t4 = g2.multiply(r2).add(R.multiply(c2));

        String message = topicID + "," + optionID + "," + ballotID + "," + getEncode(g1) + "," + getEncode(g2) + "," + getEncode(Z) + "," + getEncode(R);
        message = message + "," + getEncode(t1) + "," + getEncode(t2) + "," + getEncode(t3) + "," + getEncode(t4);

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(message.getBytes("utf-8"));
        byte[] mybytes = md.digest();
        BigInteger hashBits = new BigInteger(1, mybytes);


        if (c1.add(c2).mod(modulus).compareTo(hashBits.mod(modulus)) == 0) {
            return true;
        } else {
            return false;
        }
    }


    @FXML
    public void showresult() throws NumberFormatException, ClassNotFoundException, IOException {

        JSONArray optionArray = jsonObj.getJSONArray("options");
        int y = 295;
        for (int i = 0 ; i < optionArray.size(); i ++){
            JSONObject tempOption = optionArray.getJSONObject(i);
            String save = "Candidate: "+tempOption.get("candidate") + "   Tally: "+tempOption.get("vote");
            Label label = new Label();
            label.setText(save);
            label.setLayoutX(582);
            label.setLayoutY(y);
            y=y+50;
            pane.getChildren().add(label);
        }

    }

    @FXML
    public void verifyauditballot() throws IOException {

        boolean check = true;
        ECPoint g1 = getG1();
        ECPoint g2 = getG2();

        JSONArray ballotArray = jsonObj.getJSONArray("ballots");
        for (int i = 0; i < ballotArray.size() ; i++){
            JSONObject tempBallot = ballotArray.getJSONObject(i);
            if (tempBallot.getString("state").equals("Audited")){
                ECPoint R = pointOnCurve(tempBallot.getString("R"));
                ECPoint Z = pointOnCurve(tempBallot.getString("Z"));
                BigInteger random = tempBallot.getBigInteger("random");
                BigInteger v = tempBallot.getBigInteger("v");

                check = check && (R.equals(g2.multiply(random)) || !Z.equals(g1.multiply(random.add(v))));
                System.out.println("id : " + i + " result: " + check);
            }
        }

        if (check == true) {
            verifytallyincorrect.setVisible(false);
            verifytallycorrect.setVisible(false);
            verifysignatureincorrect.setVisible(false);
            verifysignaturecorrect.setVisible(false);
            verifyauditincorrect.setVisible(false);
            verifyauditcorrect.setVisible(true);
            verifyszkpcorrect.setVisible(false);
            verifyszkpincorrect.setVisible(false);
            verifyzkpcorrect.setVisible(false);
            verifyzkpincorrect.setVisible(false);
        } else {
            verifytallyincorrect.setVisible(false);
            verifytallycorrect.setVisible(false);
            verifysignatureincorrect.setVisible(false);
            verifysignaturecorrect.setVisible(false);
            verifyauditincorrect.setVisible(true);
            verifyauditcorrect.setVisible(false);
            verifyszkpcorrect.setVisible(false);
            verifyszkpincorrect.setVisible(false);
            verifyzkpcorrect.setVisible(false);
            verifyzkpincorrect.setVisible(false);
        }
    }

    @FXML
    public void verifytally() throws IOException {
        boolean check = true;
        ECPoint g1 = getG1();
        ECPoint g2 = getG2();

        JSONArray optionArray = jsonObj.getJSONArray("options");
        for (int i = 0; i < optionArray.size() ; i++){
            JSONObject tempOption = optionArray.getJSONObject(i);
            BigInteger v = tempOption.getBigInteger("vote");

            if (v.compareTo(new BigInteger("0"))>0) {
                System.out.println(i);
                int optionID = tempOption.getInteger("id");
                BigInteger s = tempOption.getBigInteger("s");
                JSONArray ballotArray = jsonObj.getJSONArray("ballots");
                ECPoint R = null;
                ECPoint Z = null;
                boolean first = true;


                for (int j = 0; j < ballotArray.size(); j++) {
                    JSONObject tempBallot = ballotArray.getJSONObject(j);
                    if (tempBallot.getInteger("candidateID") == optionID && tempBallot.getString("state").equals("Confirmed")) {
                        if (first) {
                            R = pointOnCurve(tempBallot.getString("R"));
                            Z = pointOnCurve(tempBallot.getString("Z"));
                            first = false;
                        } else {
                            R = R.add(pointOnCurve(tempBallot.getString("R")));
                            Z = Z.add(pointOnCurve(tempBallot.getString("Z")));
                        }
                    }
                }
                check = check && R.equals(g2.multiply(s)) && Z.equals(g1.multiply(s.add(v)));
                System.out.println("id: " + i + " result: " + check);
            }
        }


        if (check == true) {
            verifytallyincorrect.setVisible(false);
            verifytallycorrect.setVisible(true);
            verifysignatureincorrect.setVisible(false);
            verifysignaturecorrect.setVisible(false);
            verifyauditincorrect.setVisible(false);
            verifyauditcorrect.setVisible(false);
            verifyszkpcorrect.setVisible(false);
            verifyszkpincorrect.setVisible(false);
            verifyzkpcorrect.setVisible(false);
            verifyzkpincorrect.setVisible(false);
        } else {
            verifytallyincorrect.setVisible(true);
            verifytallycorrect.setVisible(false);
            verifysignatureincorrect.setVisible(false);
            verifysignaturecorrect.setVisible(false);
            verifyauditincorrect.setVisible(false);
            verifyauditcorrect.setVisible(false);
            verifyszkpcorrect.setVisible(false);
            verifyszkpincorrect.setVisible(false);
            verifyzkpcorrect.setVisible(false);
            verifyzkpincorrect.setVisible(false);
        }

    }

    @FXML
    public void verifysignature() throws IOException {
        boolean check = true;
        ECPoint publicKey = pointOnCurve(jsonObj.getString("publickey"));
        BigInteger order = new BigInteger(jsonObj.getString("curveOrder"));

        JSONArray receiptArray = jsonObj.getJSONArray("receipts");
        for (int i = 0; i < receiptArray.size(); i++) {
            JSONObject tempReceipt = receiptArray.getJSONObject(i);
            String signature = tempReceipt.getString("stageOneSign");
            int ballotID = tempReceipt.getInteger("ballotID");
            //generate stage One info
            JSONObject stageOneJSON = new JSONObject();
            stageOneJSON.put("head", "dreipvoting");
            stageOneJSON.put("topicID", jsonObj.getIntValue("topicID"));
            stageOneJSON.put("stage", "stageOne");
            JSONArray arrayForSignOne = generateArrayForSign(ballotID, false);
            stageOneJSON.put("ballots", arrayForSignOne);

            check = check && digitalSignature(publicKey, signature, stageOneJSON.toString(), order);
            System.out.println("receiptID " + i + " stage One " + check);

            //generate stage two info
            JSONObject stageTwoJSON = new JSONObject();
            stageTwoJSON.put("head", "dreipvoting");
            stageTwoJSON.put("topicID", jsonObj.getIntValue("topicID"));
            stageTwoJSON.put("ballotID",ballotID);
            stageTwoJSON.put("stage", "stageOne");
            JSONArray arrayForSignTwo = generateArrayForSign(ballotID, true);
            stageTwoJSON.put("ballots", arrayForSignTwo);
            check = check && digitalSignature(publicKey, signature, stageOneJSON.toString(), order);
            System.out.println("receiptID " + i + " stage two " + check);
        }


        if (check == true) {
            verifytallyincorrect.setVisible(false);
            verifytallycorrect.setVisible(false);
            verifysignatureincorrect.setVisible(false);
            verifysignaturecorrect.setVisible(true);
            verifyauditincorrect.setVisible(false);
            verifyauditcorrect.setVisible(false);
            verifyszkpcorrect.setVisible(false);
            verifyszkpincorrect.setVisible(false);
            verifyzkpcorrect.setVisible(false);
            verifyzkpincorrect.setVisible(false);
        } else {
            verifytallyincorrect.setVisible(false);
            verifytallycorrect.setVisible(false);
            verifysignatureincorrect.setVisible(true);
            verifysignaturecorrect.setVisible(false);
            verifyauditincorrect.setVisible(false);
            verifyauditcorrect.setVisible(false);
            verifyszkpcorrect.setVisible(false);
            verifyszkpincorrect.setVisible(false);
            verifyzkpcorrect.setVisible(false);
            verifyzkpincorrect.setVisible(false);
        }
    }

    private JSONArray generateArrayForSign(int ballotID, boolean isStageTwo) {
        JSONArray result = new JSONArray();
        JSONArray ballotArray = jsonObj.getJSONArray("ballots");
        for (int i = 0; i < ballotArray.size(); i++) {
            JSONObject tempBallot = ballotArray.getJSONObject(i);
            if (tempBallot.getInteger("ballotId") == ballotID) {
                JSONObject temp = new JSONObject();
                temp.put("R", tempBallot.getString("R"));
                temp.put("Z", tempBallot.getString("Z"));
                temp.put("ballotid", tempBallot.getIntValue("ballotId"));
                temp.put("c1", tempBallot.getString("c1"));
                temp.put("c2", tempBallot.getString("c2"));
                temp.put("r1", tempBallot.getString("r1"));
                temp.put("r2", tempBallot.getString("r2"));
                temp.put("title", jsonObj.getString("title"));
                if (isStageTwo) {
                    temp.put("state", tempBallot.getString("state"));
                }
                result.add(temp);
            }
        }
        return result;
    }


    private boolean digitalSignature(ECPoint publicKey, String signature, String message, BigInteger order) {

        try {
            ECPoint g1 = getG1();
            String[] tempArray = signature.split(",");
            String tempx = new String(Base64.getDecoder().decode(tempArray[0]), "UTF-8");
            String tempy = new String(Base64.getDecoder().decode(tempArray[1]), "UTF-8");

            BigInteger r = new BigInteger(tempx, 10);

            BigInteger s = new BigInteger(tempy, 10);

            BigInteger w = s.modInverse(order);

            byte[] receiptXML = message.getBytes();
            //System.out.println(message);
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            receiptXML = md.digest(receiptXML);
            BigInteger hashBits = new BigInteger(1, receiptXML);
            String tempString = hashBits.toString(2);
            tempString = tempString.substring(0, order.bitLength());
            hashBits = new BigInteger(tempString, 2);

            BigInteger u1 = hashBits.multiply(w).mod(order);
            BigInteger u2 = r.multiply(w).mod(order);
            ECPoint v = g1.multiply(u1).add(publicKey.multiply(u2));

            if (r.mod(order).equals(v.getX().toBigInteger().mod(order))) {

                System.out.println("Signature Checking Succeed.");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("Failed.");
        return false;

    }


    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }


    private ECPoint getG1() throws IOException {

        ECPoint g1 = pointOnCurve(jsonObj.getString("g1"));

        return g1;
    }

    private ECPoint getG2() throws IOException {
        ECPoint g2 = pointOnCurve(jsonObj.getString("g2"));

        return g2;
    }

    private ECPoint pointOnCurve(String coordinator) throws IOException {

        String[] tempArray = coordinator.split(",");
        byte[] decodedx = Base64.getDecoder().decode(tempArray[0].getBytes());
        byte[] decodedy = Base64.getDecoder().decode(tempArray[1]);
        BigInteger x = new BigInteger(new String(decodedx, "UTF-8"));
        BigInteger y = new BigInteger(new String(decodedy, "UTF-8"));
        ECPoint point = curve().createPoint(x, y);

        return point;
    }

    private ECCurve curve() {
        BigInteger prime = new BigInteger(jsonObj.getString("curvePrime"));
        BigInteger a = prime.add(new BigInteger(jsonObj.getString("curveA")));
        BigInteger b = new BigInteger(jsonObj.getString("curveB"));
        ECCurve curve = new ECCurve.Fp(prime, a, b);
        return curve;
    }

    public void setJSON() throws IOException {
        File file = new File(getPath());
        FileReader read = new FileReader(file);
        BufferedReader reader = new BufferedReader(read);
        String jsonobject = reader.readLine();
        this.jsonObj = JSONObject.parseObject(jsonobject);
    }

    private static String getEncode(ECPoint ecpoint) throws UnsupportedEncodingException {

        BigInteger tempx = ecpoint.getX().toBigInteger();
        BigInteger tempy = ecpoint.getY().toBigInteger();
        String x = new String(Base64.getEncoder().encode(tempx.toString().getBytes()), "UTF-8");
        String y = new String(Base64.getEncoder().encode(tempy.toString().getBytes()), "UTF-8");
        StringBuilder sb = new StringBuilder();
        sb.append(x);
        sb.append(",");
        sb.append(y);
        return sb.toString();
    }


}
