package cdio.math.misc;

import java.util.Arrays;

public class BinghamNorm1F1 {

    // Data members:
    public static final int np = 1;
    public static final int nq = 1;
    public static final double[] p = {0.5};
    public static final double[] q = {2};
    public static final double alpha = 2;
    public static final int MAX = 1000;
    public static final int K = 0;
    public static final double epsilon = 0.00001;


    public static double Value(double[] gamma) {
        return JNIHyperGeometricFunction.HGF(1, MAX, K, alpha, np, p,
                nq, q, gamma.length, gamma, 0, null);
    }

    public static double[] Polynomial(double[] gamma) {
        // TODO: Java - C API JNI:
        return null;
    }

    public static double Jacobian(double[] gamma, int i) {
        double[] gammaP = Arrays.copyOf(gamma, gamma.length);
        gammaP[i] += epsilon;
        return (Value(gammaP) - Value(gamma)) / epsilon;
    }

    public static double Hessian(double[] gamma, int i, int j) {
        double[] gammaP1 = Arrays.copyOf(gamma, gamma.length);
        double[] gammaP2 = Arrays.copyOf(gamma, gamma.length);
        double[] gammaP3 = Arrays.copyOf(gamma, gamma.length);

        // Diagonal Hessians:
        if (i == j) {
            gammaP1[i] += epsilon;
            gammaP2[i] += 2 * epsilon;
            return (Value(gammaP2) + Value(gamma) - 2 * Value(gammaP1)) / (Value(gamma) * epsilon);
        } else {
            gammaP1[j] += epsilon;
            gammaP2[i] += epsilon;
            gammaP3[i] += epsilon;
            gammaP3[j] += epsilon;

            return (Value(gammaP3) - Value(gammaP2) - Value(gammaP1) + Value(gamma)) / (Value(gamma) * epsilon * epsilon);
        }
    }
}
