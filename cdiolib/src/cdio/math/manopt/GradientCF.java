package cdio.math.manopt;

import cdio.math.space.*;
import libprotnmr.math.Quaternion;

public class GradientCF {

    // Static Methods:

    // Computes the gradient at point p:
    public static Vector ComputeGradient(MPoint p, SaupeTensorVector s1, SaupeTensorVector s2) {
        Vector gradient = new Vector(11);

        SquareMatrix Ql = RotationMatrix.Q(RotationMatrix.QuaternionToRotationMatrix(p.qL.getQuaternion()));
        SquareMatrix EQ = RotationMatrix.ExpectationQ(p.lambda.x, p.lambda.y, p.lambda.z);
        SquareMatrix Qr = RotationMatrix.Q(RotationMatrix.QuaternionToRotationMatrix(p.qR.getQuaternion()));

        Vector ConstantFactor = ConstantFactor(s1, s2, Ql, EQ, Qr);

        SquareMatrix DQl0 = DelQInv(0, p.qL);
        SquareMatrix DQl1 = DelQInv(1, p.qL);
        SquareMatrix DQl2 = DelQInv(2, p.qL);
        SquareMatrix DQl3 = DelQInv(3, p.qL);

        SquareMatrix DQr0 = DelQInv(0, p.qR);
        SquareMatrix DQr1 = DelQInv(1, p.qR);
        SquareMatrix DQr2 = DelQInv(2, p.qR);
        SquareMatrix DQr3 = DelQInv(3, p.qR);

        SquareMatrix DL1 = DelL(1, p.lambda.x, p.lambda.y, p.lambda.z);
        SquareMatrix DL2 = DelL(2, p.lambda.x, p.lambda.y, p.lambda.z);
        SquareMatrix DL3 = DelL(3, p.lambda.x, p.lambda.y, p.lambda.z);

        // Compute Gradient wrt qL here:

        // del f / del qL0:
        Vector PDwrtQ0 = Matrix.Multiply(Qr, Matrix.Multiply(EQ, Matrix.Multiply(DQl0, s1)));
        double valPDwrtQ0 = 0;
        for (int i = 0; i < 5; i++)
            valPDwrtQ0 += PDwrtQ0.data[i] * ConstantFactor.data[i];
        gradient.data[0] = valPDwrtQ0;

        // del f / del qL1:
        Vector PDwrtQ1 = Matrix.Multiply(Qr, Matrix.Multiply(EQ, Matrix.Multiply(DQl1, s1)));
        double valPDwrtQ1 = 0;
        for (int i = 0; i < 5; i++)
            valPDwrtQ1 += PDwrtQ1.data[i] * ConstantFactor.data[i];
        gradient.data[1] = valPDwrtQ1;

        // del f / del qL2:
        Vector PDwrtQ2 = Matrix.Multiply(Qr, Matrix.Multiply(EQ, Matrix.Multiply(DQl2, s1)));
        double valPDwrtQ2 = 0;
        for (int i = 0; i < 5; i++)
            valPDwrtQ2 += PDwrtQ2.data[i] * ConstantFactor.data[i];
        gradient.data[2] = valPDwrtQ2;

        // del f / del qL3:
        Vector PDwrtQ3 = Matrix.Multiply(Qr, Matrix.Multiply(EQ, Matrix.Multiply(DQl3, s1)));
        double valPDwrtQ3 = 0;
        for (int i = 0; i < 5; i++)
            valPDwrtQ3 += PDwrtQ3.data[i] * ConstantFactor.data[i];
        gradient.data[3] = valPDwrtQ3;


        // del f / del lambda1:
        Vector PDwrtL1 = Matrix.Multiply(Qr, Matrix.Multiply(DL1, Matrix.Multiply(Ql, s1)));
        double valPDwrtL1 = 0;
        for(int i = 0; i < 5; i++)
            valPDwrtL1 += PDwrtL1.data[i] * ConstantFactor.data[i];
        gradient.data[4] = valPDwrtL1;

        // del f / del lambda2:
        Vector PDwrtL2 = Matrix.Multiply(Qr, Matrix.Multiply(DL2, Matrix.Multiply(Ql, s1)));
        double valPDwrtL2 = 0;
        for(int i = 0; i < 5; i++)
            valPDwrtL2 += PDwrtL2.data[i] * ConstantFactor.data[i];
        gradient.data[5] = valPDwrtL2;

        // del f / del lambda3:
        Vector PDwrtL3 = Matrix.Multiply(Qr, Matrix.Multiply(DL3, Matrix.Multiply(Ql, s1)));
        double valPDwrtL3 = 0;
        for(int i = 0; i < 5; i++)
            valPDwrtL3 += PDwrtL3.data[i] * ConstantFactor.data[i];
        gradient.data[6] = valPDwrtL3;


        // del f / del qR0:
        Vector PDwrtQr0 = Matrix.Multiply(DQr0, Matrix.Multiply(EQ, Matrix.Multiply(Ql, s1)));
        double valPDwrtQr0 = 0;
        for (int i = 0; i < 5; i++)
            valPDwrtQr0 += PDwrtQr0.data[i] * ConstantFactor.data[i];
        gradient.data[7] = valPDwrtQr0;

        // del f / del qR1:
        Vector PDwrtQr1 = Matrix.Multiply(DQr1, Matrix.Multiply(EQ, Matrix.Multiply(Ql, s1)));
        double valPDwrtQr1 = 0;
        for (int i = 0; i < 5; i++)
            valPDwrtQr1 += PDwrtQr1.data[i] * ConstantFactor.data[i];
        gradient.data[8] = valPDwrtQr1;

        // del f / del qR2:
        Vector PDwrtQr2 = Matrix.Multiply(DQr2, Matrix.Multiply(EQ, Matrix.Multiply(Ql, s1)));
        double valPDwrtQr2 = 0;
        for (int i = 0; i < 5; i++)
            valPDwrtQr2 += PDwrtQr2.data[i] * ConstantFactor.data[i];
        gradient.data[9] = valPDwrtQr2;

        // del f / del qR3:
        Vector PDwrtQr3 = Matrix.Multiply(DQr3, Matrix.Multiply(EQ, Matrix.Multiply(Ql, s1)));
        double valPDwrtQr3 = 0;
        for (int i = 0; i < 5; i++)
            valPDwrtQr3 += PDwrtQr3.data[i] * ConstantFactor.data[i];
        gradient.data[10] = valPDwrtQr3;


        return gradient;
    }

    // Calculates the Constant Factor = 2 * (Qr * E[Q] * Ql * s1 - s2)
    public static Vector ConstantFactor(SaupeTensorVector s1, SaupeTensorVector s2, SquareMatrix Ql,
                                        SquareMatrix EQ, SquareMatrix Qr) {
        SquareMatrix t = Matrix.Multiply(EQ, Ql);
        t = Matrix.Multiply(Qr, t);

        Vector s1transformed = Matrix.Multiply(s1, t);
        for(int i = 0; i < 5; i++) {
            s1transformed.data[i] -= s2.data[i];
            s1transformed.data[i] *= 2.0;
        }

        return s1transformed;
    }

    // Calculates the partial derivative of Q(q^-1) wrt q_i:
    public static SquareMatrix DelQInv(int i, S3Point q) {
        // TODO: Implement
    }


    // Calculates the partial derivative of E[Q] wrt lambda_i:
    public static SquareMatrix DelL(int i, double lambda1, double lambda2, double lambda3) {
        // TODO: Implement
    }

}
