package Web.Cryptography;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Signature {

    public static String sign(String message, BigInteger key) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        ECurve curve = CurveConfig.getNist256();
        BigInteger order = curve.getOrder();
        ECPoint myPoint = CurveConfig.getGenerator();
        BigInteger bitLength = new BigInteger(String.valueOf(order.bitLength()));


        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(message.getBytes("utf-8"));
        byte[] mybytes = md.digest();
        BigInteger c = new BigInteger(RandomGenerator.bytesToHex(mybytes), 16);

        BigInteger hashBits = new BigInteger(c.toString(2).substring(0, bitLength.intValueExact()), 2);

        BigInteger r, s, k;

        do {
            do {
                k = RandomGenerator.getRandomBytes(bitLength, order.add(new BigInteger("-1")));
                ECPoint tempPoint = myPoint.multiply(k);
                r = tempPoint.getX().mod(order);
            } while (r.compareTo(new BigInteger("0")) == 0);
            s = k.modInverse(order).multiply(hashBits.add(key.multiply(r))).mod(order);

        } while (s.compareTo(new BigInteger("0")) == 0);

        String base64r = Base64.getEncoder().encodeToString(r.toString().getBytes("utf-8"));
        String base64s = Base64.getEncoder().encodeToString(s.toString().getBytes("utf-8"));

        return base64r + "," + base64s;
    }
}
