package cdio.math.distributions;

import cdio.math.space.*;

public class BinghamDistribution extends ContinuousDistribution<Quaternion> {

    // Members:
    public Matrix Gamma;
    public Quaternion Ql;
    public Quaternion Qr;

    public BinghamDistribution() {
        Gamma = new Matrix();
        Ql = new Quaternion();
        Qr = new Quaternion();
    }

    public BinghamDistribution(BinghamDistribution other) {
        this.Gamma = new Matrix(other.Gamma);
        this.Ql = new Quaternion(other.Ql);
        this.Qr = new Quaternion(other.Qr);
    }

    public BinghamDistribution(Matrix gamma, Quaternion ql, Quaternion qr) {
        this.Gamma = new Matrix(gamma);
        this.Ql = new Quaternion(ql);
        this.Qr = new Quaternion(qr);
    }

    // Methods:
    public Matrix getGamma() {
        return Gamma;
    }

    public void setGamma(Matrix gamma) {
        Gamma = gamma;
    }

    public Quaternion getQl() {
        return Ql;
    }

    public void setQl(Quaternion ql) {
        Ql = ql;
    }

    public Quaternion getQr() {
        return Qr;
    }

    public void setQr(Quaternion qr) {
        Qr = qr;
    }

    @Override
    public DiscreteDistribution generateDiscreteDistribution() {
        return null;
    }

    // TODO: Rewrite with correct definition of PDF
    @Override
    public double PDF(Quaternion q) {
        RowVector xqT = new RowVector(q);
        ColumnVector xq = new ColumnVector(q);
        RowVector m = Matrix.Multiply(xqT, this.Gamma);
        return Vector.Multiply(xqT, xq);
    }

    public double Entropy() {
       return 0;
    }

    public double CrossEntropy(BinghamDistribution other){
        return 0;
    }

    public double KLDivergence(BinghamDistribution other) {
        return this.Entropy() + this.CrossEntropy(other);
    }

    public BinghamDistribution convolve(BinghamDistribution other) {
        BinghamDistribution convolved = new BinghamDistribution();
        return convolved;
    }

    // Static Methods:
    public static double PMF(BinghamDistribution B, Quaternion x) {
        return 0;
    }

    public static double PMF(Real3D Gamma, Quaternion Ql, Quaternion Qr, Quaternion x) {
        return 0;
    }

    public static double Entropy(BinghamDistribution B) { return B.Entropy();}

    public static double KLDivergence(BinghamDistribution B1, BinghamDistribution B2) {
        return B1.KLDivergence(B2);
    }
}
