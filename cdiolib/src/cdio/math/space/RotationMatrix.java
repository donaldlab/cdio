package cdio.math.space;

import cdio.math.distributions.BinghamDistribution;
import cdio.math.misc.BinghamNorm1F1;
import libprotnmr.math.Quaternion;

public class RotationMatrix {

    // Static Methods:

    // Converts a quaternion to its rotation matrix representation:
    public static SquareMatrix QuaternionToRotationMatrix(Quaternion q) {
        double a = q.a;
        double b = q.b;
        double c = q.c;
        double d = q.d;
        double[][] r = new double[3][3];

        r[0][0] = a*a + b*b - c*c - d*d;
        r[1][1] = a*a - b*b + c*c - d*d;
        r[2][2] = a*a - b*b - c*c + d*d;
        r[0][1] = 2*b*c - 2*a*d;
        r[1][2] = 2*c*d - 2*a*b;
        r[2][0] = 2*b*d - 2*a*c;
        r[0][2] = 2*b*d + 2*a*c;
        r[1][0] = 2*b*c + 2*a*d;
        r[2][1] = 2*c*d + 2*a*b;

        return new SquareMatrix(r, 3);
    }

    // Converts a rotation matrix to its quaternion representation:
    public static Quaternion RotationMatrixToQuaternion(SquareMatrix R) {
        // TODO:
        // !WARN: ILL-DEFINED OPERATION
        return new Quaternion();
    }

    // Returns the 5*5 Q form of a 3*3 square matrix (such as a rotation matrix):
    public static SquareMatrix Q(SquareMatrix S) {
        double x1 = S.get(0,0);
        double x2 = S.get(1,0);
        double x3 = S.get(2,0);
        double y1 = S.get(0,1);
        double y2 = S.get(1,1);
        double y3 = S.get(2,1);
        double z1 = S.get(0,2);
        double z2 = S.get(1,2);
        double z3 = S.get(2,2);

        double[][] m = new double[5][5];

        m[0][0] = x1*x1 - x3*x3;
        m[1][0] = y1*y1 - y3*y3;
        m[2][0] = x1*y1 - x3*y3;
        m[3][0] = x1*z1 - x3*z3;
        m[4][0] = y1*z1 - y3*z3;

        m[0][1] = x2*x2 - x3*x3;
        m[1][1] = y2*y2 - y3*y3;
        m[2][1] = x2*y2 - x3*y3;
        m[3][1] = x2*z2 - x3*z3;
        m[4][1] = y2*z2 - y3*z3;

        m[0][2] = 2*x1*x2;
        m[1][2] = 2*y1*y2;
        m[2][2] = x1*y2 + x2*y1;
        m[3][2] = x1*z2 + x2*z1;
        m[4][2] = y1*z2 + y2*z1;

        m[0][3] = 2*x1*x3;
        m[1][3] = 2*y1*y3;
        m[2][3] = x1*y3 + x3*y1;
        m[3][3] = x1*z3 + x3*z1;
        m[4][3] = y1*z3 + y3*z1;

        m[0][4] = 2*x2*x3;
        m[1][4] = 2*y2*y3;
        m[2][4] = x2*y3 + x3*y2;
        m[3][4] = x2*z3 + x3*z2;
        m[4][4] = y2*z3 + y3*z2;

        SquareMatrix Q = new SquareMatrix(5);
        for(int i =0; i<5; i++)
            for(int j=0; j<5; j++)
                Q.data[i][j] = m[i][j];

        return Q;
    }

    public static SquareMatrix ExpectationQ(double lambda1, double lambda2, double lambda3) {

        SquareMatrix E = new SquareMatrix(5);

        double[] lambda = new double[4];
        lambda[0] = -(lambda1 + lambda2 + lambda3);
        lambda[1] = lambda1;
        lambda[2] = lambda2;
        lambda[3] = lambda3;

        double PDFGamma = BinghamNorm1F1.Value(lambda);

        double q11 = BinghamNorm1F1.Hessian(lambda, 0, 0)/PDFGamma;
        double q22 = BinghamNorm1F1.Hessian(lambda, 1, 1)/PDFGamma;
        double q33 = BinghamNorm1F1.Hessian(lambda, 2, 2)/PDFGamma;
        double q44 = BinghamNorm1F1.Hessian(lambda, 3, 3)/PDFGamma;
        double q12 = BinghamNorm1F1.Hessian(lambda, 0, 1)/PDFGamma;
        double q13 = BinghamNorm1F1.Hessian(lambda, 0, 2)/PDFGamma;
        double q14 = BinghamNorm1F1.Hessian(lambda, 0, 3)/PDFGamma;
        double q23 = BinghamNorm1F1.Hessian(lambda, 1, 2)/PDFGamma;
        double q24 = BinghamNorm1F1.Hessian(lambda, 1, 3)/PDFGamma;
        double q34 = BinghamNorm1F1.Hessian(lambda, 2, 3)/PDFGamma;

        double a12 = (q11 + q22 - 6*q12)/3.0;
        double a13 = (q11 + q33 - 6*q13)/3.0;
        double a14 = (q11 + q44 - 6*q14)/3.0;
        double a23 = (q22 + q33 - 6*q23)/3.0;
        double a24 = (q22 + q44 - 6*q24)/3.0;
        double a34 = (q33 + q44 - 6*q34)/3.0;


        E.data[0][0] = -a12 - a34 +3*a13 + 3*a24 + a14 + a23;
        E.data[1][1] = 3*a12 + 3*a34 - a13 - a24 + a14 + a23;
        E.data[2][2] = 3*a14 - 3*a23;
        E.data[3][3] = 3*a13 - 3*a24;
        E.data[4][4] = 3*a12 - 3*a34;
        E.data[0][1] = 2*a13 + 2*a24 - 2*a14 - 2*a23;
        E.data[1][0] = 2*a12 + 2*a34 - 2*a14 - 2*a23;

        return E;
    }
}
