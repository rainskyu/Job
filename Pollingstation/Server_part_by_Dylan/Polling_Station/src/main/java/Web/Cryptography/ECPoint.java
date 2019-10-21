package Web.Cryptography;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Base64;


public class ECPoint {

    private ECurve eCurve;
    private BigInteger x;
    private BigInteger y;
    private boolean infinity;

    public ECPoint(ECurve eCurve, BigInteger x, BigInteger y, boolean infinity) {
        this.eCurve = eCurve;
        this.x = x;
        this.y = y;
        this.infinity = infinity;
    }

    public ECurve getCurve() {
        return eCurve;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    public boolean isInfinity() {
        return infinity;
    }

    public boolean equals(ECPoint ecPoint) {
        if (!ecPoint.getCurve().equals(this.eCurve)) {
            return false;
        }
        if (!ecPoint.getX().equals(this.x)) {
            return false;
        }
        if (!ecPoint.getY().equals(this.y)) {
            return false;
        }
        if (ecPoint.isInfinity() != this.isInfinity()) {
            return false;
        }
        return true;
    }

    public boolean checkCurve(ECPoint ecPoint) {
        return this.eCurve.equals(ecPoint.getCurve());
    }

    public ECPoint add(ECPoint ecPoint) {
        if (this.checkCurve(ecPoint)) {
            if (this.isInfinity()) {
                //clone?
                //yep
                return ecPoint.clone();
            }
            if (ecPoint.isInfinity()) {
                //clone?
                //yep
                return this.clone();
            }
            BigInteger lambda;
            if (this.equals(ecPoint)) {
                lambda = this.getX().multiply(this.getX()).multiply(new BigInteger("3")).add(this.getCurve().getA()).multiply(this.getY().multiply(new BigInteger("2")).modInverse(this.getCurve().getPrime()));
            } else {
                lambda = ecPoint.getY().subtract(this.getY()).multiply(ecPoint.getX().subtract(this.getX()).modInverse(this.getCurve().getPrime()));
            }
            BigInteger x = lambda.multiply(lambda).subtract(this.getX().add(ecPoint.getX())).mod(this.getCurve().getPrime());
            BigInteger y = lambda.multiply(this.getX().subtract(x)).subtract(this.getY()).mod(this.getCurve().getPrime());
            return this.getCurve().getPoint(x, y);

        } else {
            return null;
        }

    }

    public ECPoint subtract(ECPoint ecPoint) {
        if (this.checkCurve(ecPoint)) {
            if (this.isInfinity()) {
                //Do we need clone here?
                //of course
                return ecPoint.clone();
            }

            if (ecPoint.isInfinity()) {
                return this.getCurve().getPoint(this.getX(), this.getY().multiply(new BigInteger("-1")));
            }

            if (this.equals(ecPoint)) {
                return this.getCurve().getInfinity();
            } else {

                BigInteger lambda = this.getCurve().getPrime().subtract(ecPoint.getY()).subtract(this.getY()).multiply(ecPoint.getX().subtract(this.getX()).modInverse(this.getCurve().getPrime()));
                BigInteger x = lambda.multiply(lambda).subtract(this.getX().add(ecPoint.getX())).mod(this.getCurve().getPrime());
                BigInteger y = lambda.multiply(this.getX().subtract(x)).subtract(this.getY()).mod(this.getCurve().getPrime());

                return this.getCurve().getPoint(x, y);
            }
        } else {
            return null;
        }
    }


    public ECPoint multiply(BigInteger scalar) {
        if (this.isInfinity()) {
            //clone?
            return this.clone();
        }

        ECPoint temp1 = this.getCurve().getInfinity();
        ECPoint temp2 = this.clone();

        int i = this.getCurve().getSize().intValueExact();
        char[] j = String.format("%" + i + "s", scalar.toString(2)).replace(' ', '0').toCharArray();

        for (int k = 0; k < i; k++) {
            if (j[k] == '0') {
                temp2 = temp1.add(temp2);
                temp1 = temp1.add(temp1);
            } else {
                temp1 = temp1.add(temp2);
                temp2 = temp2.add(temp2);
            }
        }
        return temp1;
    }

    public String getEncode() {
        try {
            if (this.isInfinity()) {
                return Base64.getEncoder().encodeToString("inf".getBytes("utf-8"));
            } else {
                StringBuilder encode = new StringBuilder();
                encode.append(Base64.getEncoder().encodeToString(this.getX().toString().getBytes("utf-8")));
                encode.append(",");
                encode.append(Base64.getEncoder().encodeToString(this.getY().toString().getBytes("utf-8")));
                return encode.toString();
            }
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }


    @Override
    public String toString() {
        if (infinity) {
            return "inf";
        } else {
            return "x : " + this.getX().toString() + "\n"
                    + "y : " + this.getY().toString();
        }
    }

    @Override
    public ECPoint clone() {
        return new ECPoint(this.getCurve(), this.getX(), this.getY(), this.isInfinity());
    }

}
