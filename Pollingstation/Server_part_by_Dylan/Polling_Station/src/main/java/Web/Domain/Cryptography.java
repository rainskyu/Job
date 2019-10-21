package Web.Domain;

import java.util.ArrayList;

public class Cryptography {
	
	
	private long a;
	private long b;
	private long p;
	
	public Cryptography(long a,long b,long p) throws Exception{ 
		//ellipse equation: y^2 = x^3 + ax^2 + bx + c
		//and (4a^3 + 27b^2) mod p ≠ 0 mod p
		//long left = (4*a*a*a+27*b*b) % p;
		//long right = 0 % p;
			this.a=a;
			this.b=b;
			this.p=p;
	}
	
    public ArrayList search() {
        ArrayList<Long[]> list =new ArrayList<>();
        long left,right;
        for (long x = 0; x < p; x++) {//i=x   from 0 to p-1
            for (long y = 0; y < p; y++) {//j=y form 0 to p-1
                left = y * y % p;
                right = (x * x * x + a * x + b) % p; 
                if (left == right) {
                    list.add(new Long[] {x,y});
                    System.out.println("("+x+","+y+")");
                }
            }
        }
        return list;
    }
	
    public long getOrder() {
        return search().size()+1;
    }
    //椭圆曲线上的加法运算,R=P+Q
    public long[] add(long x1, long y1, long x2, long y2) {
        if (x1 == -1) {// O+P=P
            return new long[]{x2,y2};
        }
        if (x2 == -1) {// P+O=P
            return new long[]{x1,y1};
        }
        long[] point = new long[2];
        long ratio = 0;//系数    0<=ratio<=p-1
        if (x1 == x2) {
            if (y1 == y2) {// P==Q
                ratio = mod((3  *x1 * x1 + a) * (getMMI(2 * y1,p)),p);
            }else { // P+P=O
                return new long[] {-1,-1};
            }            
        }else {// P!=Q
            long mask = 1<<63;
            long flag = (((y2 - y1) & mask) ^ ((x2 - x1) & mask)) == 0 ? 1 : -1;
            ratio =  mod(flag * Math.abs(y2 - y1) * (getMMI(Math.abs(x2 - x1),p)),p);
        }
        point[0] = mod(ratio * ratio - x1 - x2, p);
        point[1] = mod(ratio * (x1 - point[0]) - y1,p);
        return point;
    }
    
	
	//椭圆曲线上的乘法运算,乘法运算定义为重复的加法运算:kP=P+P+P+...+P
    public long[] multiply(int x, int y, int times) {
        long[] point = {x,y};
        for (long i = 2; i <= times; i++) {
            point = add(point[0],point[1],x,y);
            if (point[0] == -1) {
                return point;
            }
        }
        return point;
    }

    //利用扩展欧几里算法得求模范元素,
	public long getMMI(long a, long b) {//modular multiplicative inverse
        long mmi = gcdExt(a,b)[1];
        while (mmi < 0) {
            mmi += b;
        }
        return mmi;
    }
	
	//利用欧几里得算法去求同余方程..:ax+by=g=gcd(a,b)  =>  tuple[1]x+tuple[2]y=gcd(a,b)
    public long[] gcdExt(long a, long b) {
        long ans;
        long[] tuple = new long[3];
        if (b == 0) {
            tuple[0] = a;
            tuple[1] = 1;
            tuple[2] = 0;
            return tuple;
        }
        long[] temp = gcdExt(b,a%b);
        ans = temp[0];
        tuple[0] = ans;
        tuple[1] = temp[2];
        tuple[2] = temp[1] - (a / b) * temp[2];
        return tuple;
    }
	
    public long mod(long a, long p) {
        long r = Math.abs(a) % p;
        return a >= 0 ? r : p - r;
    }
	
	


}