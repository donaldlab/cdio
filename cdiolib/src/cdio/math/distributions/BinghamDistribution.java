package cdio.math.distributions;

import cdio.math.space.Matrix;
import libprotnmr.math.Quaternion;

public class BinghamDistribution extends ContinuousDistribution<Quaternion>{

    // Data Members:
    public Quaternion qL;
    public Quaternion qR;
    public double lambda1;
    public double lambda2;
    public double lambda3;

    // Initializers:

    // TODO:
    public BinghamDistribution() {}

    // TODO:
    public BinghamDistribution(Quaternion qL, double lambda1, double lambda2, double lambda3, Quaternion qR) {
        this.qL = qL;
        this.qR = qR;
        this.lambda1 = lambda1;
        this.lambda2 = lambda2;
        this.lambda3 = lambda3;
    }

    // Member Methods:
    @Override
    public DiscreteDistribution generateDiscreteDistribution() {
        return null;
    }

    @Override
    public double PDF(Quaternion x) {
        return 0;
    }

    // TODO:
    // Output the Qavg(x) where qi ~ S^3 based of definition of Q in CDIO algorithm:
    public double[][] QAverage(Quaternion x) {
        double[][] out = new double[5][5];
        return out;
    }

    // TODO:
    // Output the Q(x) where qi ~ S^3 based of definition of Q in CDIO algorithm:
    public double[][] QMatrix(Quaternion x) {
        double[][] out = new double[5][5];
        return out;
    }

    // Static Methods:

    // Outputs the Probability Density of the quaternion q based on Bingham(qL, gamma, qR):
    public static double PDF(Quaternion qL, double[] gamma, Quaternion qR, Quaternion q) {
        // Returns the PDF of RotM(q) given the other 3 params of Bingham Distribution
        // TODO:
        return 0;
    }
}
