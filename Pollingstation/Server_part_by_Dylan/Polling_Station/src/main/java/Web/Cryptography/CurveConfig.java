package Web.Cryptography;
import java.math.BigInteger;

public class CurveConfig {
	
	
	public CurveConfig() {
		
	}

    public static ECurve getNist256(){
        BigInteger prime = new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951");
        BigInteger size = new BigInteger("256");
        BigInteger a = new BigInteger("-3");
        BigInteger b = new BigInteger("5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b",16);
        BigInteger order = new BigInteger("115792089210356248762697446949407573529996955224135760342422259061068512044369");
        return new ECurve(prime,size,a,b,order);
    }

    public static ECPoint getGenerator(){
        BigInteger x = new BigInteger("6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296",16);
        BigInteger y = new BigInteger("4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5",16);
        return new ECPoint(getNist256(),x,y,false);
    }
}
