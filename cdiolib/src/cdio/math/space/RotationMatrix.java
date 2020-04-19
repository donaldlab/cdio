package cdio.math.space;

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
}
