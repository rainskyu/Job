package Web.Cryptography;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.alibaba.fastjson.JSONObject;

public class g1andg2 {

	
	



	public g1andg2() {
		
	}
	
	
	public String getG1() {
		ECPoint G1 = CurveConfig.getGenerator();
		String g1=G1.getEncode();
		return g1;
	}
	
	
	
	public String getG2(int electionid) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest messagedigest = MessageDigest.getInstance("SHA-256");
		ECurve cv = CurveConfig.getNist256();
		int padding = 0;
		BigInteger legendre=new BigInteger("0");
		BigInteger prime = new BigInteger("0");
		BigInteger z = new BigInteger("0");
		BigInteger x = new BigInteger("0");
		BigInteger y = new BigInteger("0");
		ECPoint mypoint = new ECPoint(cv,x,y,false);
		JSONObject j = new JSONObject();
		j.put("electionid", electionid);
		j.put("prime", cv.getPrime());
		j.put("size", cv.getSize());
		j.put("a", cv.getA());
		j.put("b", cv.getB());
		j.put("order", cv.getOrder());
		do {
			do {
				j.put("padding", padding);
				String jsoncv = j.toJSONString();
				byte[] jasonbyte = jsoncv.getBytes("UTF-8");
				messagedigest.update(jasonbyte);
				byte[] digestjson = messagedigest.digest();
				String md = RandomGenerator.bytesToHex(digestjson);
				prime = cv.getPrime();
				BigInteger mdb = new BigInteger(md,16);
				x = mdb.mod(prime);
				BigInteger firstz = x.modPow(new BigInteger("3"), prime);
				BigInteger secondz = x.multiply(cv.getA());
				BigInteger temp =firstz.add(secondz);
				BigInteger temp2 = temp.add(cv.getB());
				z = temp2.mod(prime);
				BigInteger temp3 = prime.subtract(new BigInteger("1"));
				BigInteger templs = temp3.divide(new BigInteger("2"));
				legendre = z.modPow(templs, prime);
				padding++;
			}while(legendre.compareTo(new BigInteger("1"))!=0);
			BigInteger temp4 = prime.add(new BigInteger("1"));
			BigInteger tempy = temp4.divide(new BigInteger("4"));
			y = z.modPow(tempy, prime);
			mypoint = cv.getPoint(x, y);
		}while (mypoint.isInfinity()||mypoint.multiply(new BigInteger("1")).isInfinity());
		
		
		return mypoint.getEncode();
	}
	
	/*private byte[] toByteArray (Object obj) {      
		byte[] bytes = null;      
		ByteArrayOutputStream bos = new ByteArrayOutputStream();      
		try {        
		    ObjectOutputStream oos = new ObjectOutputStream(bos);         
		    oos.writeObject(obj);        
		    oos.flush();         
		    bytes = bos.toByteArray ();      
		    oos.close();         
		    bos.close();        
		} catch (IOException ex) {        
		    ex.printStackTrace();   
		}      
		return bytes;    
	}   
	        
	private Object toObject (byte[] bytes) {      
	    Object obj = null;      
	    try {        
	        ByteArrayInputStream bis = new ByteArrayInputStream (bytes);        
	        ObjectInputStream ois = new ObjectInputStream (bis);        
	        obj = ois.readObject();      
	        ois.close();   
	        bis.close();   
	    } catch (IOException ex) {        
	        ex.printStackTrace();   
	    } catch (ClassNotFoundException ex) {        
	        ex.printStackTrace();   
	    }      
	    return obj;    
	}
	public static void main(String[] args) throws Exception{
		
		g1andg2 c= new g1andg2();
		c.getG2();
		
		
		
	}*/
	
}
