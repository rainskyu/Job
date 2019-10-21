package Web.Cryptography;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;

public class RandomGenerator {
    public static BigInteger getRandomBytes(BigInteger bitLength, BigInteger maximum) {
        SecureRandom random = new SecureRandom();

        byte[] randomBytes = new byte[(bitLength.intValueExact() / 8) + 1];
        BigInteger randomNumber;

        do {
            random.nextBytes(randomBytes);
            randomNumber = new BigInteger(bytesToHex(randomBytes), 16);
        } while (randomNumber.compareTo(maximum) > 0 || randomNumber.compareTo(new BigInteger("0")) == 0);

        return randomNumber;
    }

    public static ECPoint decode(String encodeStr, ECurve curve) {
        try {
            String[] pointCoord = encodeStr.split(",");
            String x = new String(Base64.getDecoder().decode(pointCoord[0]), "utf-8");
            String y = new String(Base64.getDecoder().decode(pointCoord[1]), "utf-8");
            return new ECPoint(curve, new BigInteger(x), new BigInteger(y), false);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error :" + e.getMessage());
            return null;
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer hashCodeBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            hashCodeBuffer.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return hashCodeBuffer.toString();
    }

}
