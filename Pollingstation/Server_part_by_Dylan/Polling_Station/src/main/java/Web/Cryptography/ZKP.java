package Web.Cryptography;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class ZKP {

    public static Map<String, String> generate1stZKP(ECPoint R, ECPoint Z, BigInteger r, ECPoint g1, ECPoint g2, boolean voteFlag, int topicID, int optionID, int receiptID) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        BigInteger modulus = g1.getCurve().getOrder();
        BigInteger bitLength = new BigInteger(String.valueOf(modulus.bitLength()));

        BigInteger w = RandomGenerator.getRandomBytes(bitLength, modulus.add(new BigInteger("-1")));

        BigInteger r1, r2, c1, c2;

        ECPoint t1, t2, t3, t4;

        if (voteFlag) {

            r1 = RandomGenerator.getRandomBytes(bitLength, modulus.add(new BigInteger("-1")));
            c1 = RandomGenerator.getRandomBytes(bitLength, modulus.add(new BigInteger("-1")));
            t1 = g1.multiply(r1).add(Z.multiply(c1));
            t2 = g2.multiply(r1).add(R.multiply(c1));
            t3 = g1.multiply(w);
            t4 = g2.multiply(w);

            String message = topicID + "," + optionID + "," + receiptID + "," + g1.getEncode() + "," + g2.getEncode() + "," + Z.getEncode() + "," + R.getEncode();
            message = message + "," + t1.getEncode() + "," + t2.getEncode() + "," + t3.getEncode() + "," + t4.getEncode();

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(message.getBytes("utf-8"));
            byte[] mybytes = md.digest();
            BigInteger c = new BigInteger(RandomGenerator.bytesToHex(mybytes), 16);
            c2 = c.subtract(c1).mod(modulus);
            r2 = w.subtract(c2.multiply(r)).mod(modulus);
        } else {

            r2 = RandomGenerator.getRandomBytes(bitLength, modulus.add(new BigInteger("-1")));
            c2 = RandomGenerator.getRandomBytes(bitLength, modulus.add(new BigInteger("-1")));
            t1 = g1.multiply(w);
            t2 = g2.multiply(w);
            t3 = g1.multiply(r2).add(Z.subtract(g1).multiply(c2));
            t4 = g2.multiply(r2).add(R.multiply(c2));

            String message = topicID + "," + optionID + "," + receiptID + "," + g1.getEncode() + "," + g2.getEncode() + "," + Z.getEncode() + "," + R.getEncode();
            message = message + "," + t1.getEncode() + "," + t2.getEncode() + "," + t3.getEncode() + "," + t4.getEncode();


            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(message.getBytes("utf-8"));
            byte[] mybytes = md.digest();
            BigInteger c = new BigInteger(RandomGenerator.bytesToHex(mybytes), 16);
            c1 = c.subtract(c2).mod(modulus);
            r1 = w.subtract(c1.multiply(r)).mod(modulus);
        }

        Map<String, String> ZKPvalue = new HashMap<>();
        ZKPvalue.put("c1", c1.toString());
        ZKPvalue.put("c2", c2.toString());
        ZKPvalue.put("r1", r1.toString());
        ZKPvalue.put("r2", r2.toString());
        return ZKPvalue;
    }

    public static boolean check1stZKP(ECPoint R, ECPoint Z, ECPoint g1, ECPoint g2, BigInteger c1, BigInteger c2, BigInteger r1, BigInteger r2, int topicID, int optionID, int receiptID) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        BigInteger modulus = g1.getCurve().getOrder();

        ECPoint t1 = g1.multiply(r1).add(Z.multiply(c1));
        ECPoint t2 = g2.multiply(r1).add(R.multiply(c1));
        ECPoint t3 = g1.multiply(r2).add(Z.subtract(g1).multiply(c2));
        ECPoint t4 = g2.multiply(r2).add(R.multiply(c2));

        String message = topicID + "," + optionID + "," + receiptID + "," + g1.getEncode() + "," + g2.getEncode() + "," + Z.getEncode() + "," + R.getEncode();
        message = message + "," + t1.getEncode() + "," + t2.getEncode() + "," + t3.getEncode() + "," + t4.getEncode();

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(message.getBytes("utf-8"));
        byte[] mybytes = md.digest();
        BigInteger c = new BigInteger(RandomGenerator.bytesToHex(mybytes), 16);

        if (c1.add(c2).mod(modulus).compareTo(c.mod(modulus)) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static Map<String, String> generate2ndZKP(BigInteger r, ECPoint g1, ECPoint g2, int electionid, int ballotid) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        BigInteger modulus = g1.getCurve().getOrder();
        BigInteger bitlength = new BigInteger(String.valueOf(modulus.bitLength()));
        BigInteger maximum = modulus.add(new BigInteger("1"));
        BigInteger w = RandomGenerator.getRandomBytes(bitlength, maximum);
        ECPoint t1 = g1.multiply(w);
        ECPoint t2 = g2.multiply(w);

        String message = electionid + "," + ballotid + "," + g1.getEncode() + "," + g2.getEncode() + "," + t1.getEncode() + "," + t2.getEncode();
        MessageDigest msd = MessageDigest.getInstance("SHA-512");
        msd.update(message.getBytes("UTF-8"));
        byte[] mdbyte = msd.digest();
        String md = RandomGenerator.bytesToHex(mdbyte);
        BigInteger c = new BigInteger(md, 16);
        BigInteger temp = c.multiply(r);
        BigInteger add = w.add(temp);
        BigInteger t = add.mod(modulus);

        Map<String, String> re = new HashMap<>();
        re.put("t", t.toString());
        re.put("t1", t1.getEncode());
        re.put("t2", t2.getEncode());

        return re;

    }

    public static boolean check2ndZKP(ECPoint R, ECPoint Z, ECPoint g1, ECPoint g2, ECPoint t1, ECPoint t2, BigInteger t, int electionid, int ballotid) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        BigInteger modulus = g1.getCurve().getOrder();
        String message = electionid + "," + ballotid + "," + g1.getEncode() + "," + g2.getEncode() + "," + t1.getEncode() + "," + t2.getEncode();
        Z = Z.subtract(g1);
        MessageDigest msd = MessageDigest.getInstance("SHA-512");
        msd.update(message.getBytes("UTF-8"));
        byte[] mdbyte = msd.digest();
        String md = RandomGenerator.bytesToHex(mdbyte);
        BigInteger c = new BigInteger(md, 16);
        ECPoint righttg1 = t1.add(Z.multiply(c.mod(modulus)));
        ECPoint righttg2 = t2.add(R.multiply(c.mod(modulus)));
        ECPoint lefttg1 = g1.multiply(t.mod(modulus));
        ECPoint lefttg2 = g2.multiply(t.mod(modulus));

        if (lefttg1.equals(righttg1) && lefttg2.equals(righttg2)) {
            return true;
        } else {
            return false;
        }

    }
}
