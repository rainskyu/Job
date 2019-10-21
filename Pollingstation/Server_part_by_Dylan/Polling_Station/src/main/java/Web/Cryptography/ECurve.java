package Web.Cryptography;

import java.math.BigInteger;

public class ECurve {

    private BigInteger prime;
    private BigInteger size;
    private BigInteger a;
    private BigInteger b;
    private BigInteger order;

    public ECurve(BigInteger prime, BigInteger size, BigInteger a, BigInteger b, BigInteger order) {
        this.prime = prime;
        this.size = size;
        this.a = a;
        this.b = b;
        this.order = order;
    }

    public BigInteger getPrime() {
        return prime;
    }

    public BigInteger getSize() {
        return size;
    }

    public BigInteger getA() {
        return a;
    }

    public BigInteger getB() {
        return b;
    }

    public BigInteger getOrder() {
        return order;
    }

    public boolean equals(ECurve eCurve) {
        if (!eCurve.getA().equals(this.a)) {
            return false;
        }
        if (!eCurve.getB().equals(this.b)) {
            return false;
        }
        if (!eCurve.getOrder().equals(this.order)) {
            return false;
        }
        if (!eCurve.getPrime().equals(this.prime)) {
            return false;
        }
        if (!eCurve.getSize().equals(this.size)) {
            return false;
        }
        return true;
    }

    public ECPoint getInfinity() {
        return new ECPoint(this, new BigInteger("0"), new BigInteger("0"), true);
    }


    public boolean contains(ECPoint ecPoint) {

        BigInteger expectedY = ecPoint.getX().pow(3).add(this.getA().multiply(ecPoint.getX())).add(this.getB()).mod(this.getPrime());
        return (ecPoint.getY().pow(2).mod(this.getPrime()).equals(expectedY));

    }

    public ECPoint getPoint(BigInteger x, BigInteger y) {
        return new ECPoint(this, x, y, false);
    }

}
