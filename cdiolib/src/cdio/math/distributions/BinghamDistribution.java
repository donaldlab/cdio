package cdio.math.distributions;

import cdio.math.space.Matrix;
import libprotnmr.math.Quaternion;

public class BinghamDistribution extends ContinuousDistribution<Quaternion>{

    // Data Members:
    public Quaternion qL;
    public Quaternion qR;
    public Matrix gamma;

    // Initializers:
    public BinghamDistribution() {}

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

    // Static Methods:
    public static double PDF(Quaternion qL, double[] gamma, Quaternion qR, Quaternion q) {}
}
