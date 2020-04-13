package cdio.math.space;

import libprotnmr.math.Quaternion;

public class RotationMatrix {

    // Static Methods:
    public static SquareMatrix QuaternionToRotationMatrix(Quaternion q) {
        double q1 = q.a;
        double q2 = q.b;
        double q3 = q.c;
        double q4 = q.d;
        double[][] r = new double[3][3];
        // TODO:
        r[0][0] = 0;
        r[0][1] = 0;
        r[0][2] = 0;
        r[1][0] = 0;
        r[1][1] = 0;
        r[1][2] = 0;
        r[2][0] = 0;
        r[2][1] = 0;
        r[2][2] = 0;
        return new SquareMatrix(r, 3);
    }

    public static Quaternion RotationMatrixToQuaternion(SquareMatrix R) {
        // TODO:
    }

    public static SquareMatrix Q(SquareMatrix S) {
        // TODO:
    }
}
