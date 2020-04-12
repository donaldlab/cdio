package cdio.math.distributions;

import cdio.math.space.Matrix;
import libprotnmr.math.Quaternion;

public class BinghamDistribution extends ContinuousDistribution<Quaternion>{

    // Data Members:
    public Quaternion qL;
    public Quaternion qR;
    public Matrix gamma;

    // Initializers:

    // TODO:
    public BinghamDistribution() {}

    // TODO:
    public BinghamDistribution(double[] qL, double[] gamma, double[] qR) {
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
    }

    // TODO:
    // Output the Q(x) where qi ~ S^3 based of definition of Q in CDIO algorithm:
    public double[][] QMatrix(Quaternion x) {
        double[][] out = new double[5][5];
    }

    // Static Methods:

    // Outputs the Probability Density of the quaternion q based on Bingham(qL, gamma, qR):
    public static double PDF(Quaternion qL, double[] gamma, Quaternion qR, Quaternion q) {
        // Returns the PDF of RotM(q) given the other 3 params of Bingham Distribution
        // TODO:
    }
}
