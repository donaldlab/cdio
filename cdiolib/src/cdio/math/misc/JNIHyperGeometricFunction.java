package cdio.math.misc;

public class JNIHyperGeometricFunction {
    static {System.loadLibrary("hgf");}

    public static native double HGF(int nmatrixargs, int MAX_param, int K_param, double alpha_param,
                                    int p_len_param, double []p_param, int q_len_param, double[] q_param,
                                    int x_len_param, double[] x_param, int y_len_param, double[] y_param) ;
}
