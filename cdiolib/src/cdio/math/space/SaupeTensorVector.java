package cdio.math.space;

import cdio.math.misc.BinghamNorm1F1;
import cdio.strucs.RegionR3;
import libprotnmr.math.Quaternion;


public class SaupeTensorVector extends Vector {

    public SaupeTensorVector(SaupeTensorVector other) {
        super(other);
    }

    // Member Methods:

    public SaupeTensorVector rotateByQ(Quaternion q) {
        Quaternion qL = new Quaternion(q);
        qL.conjugate();
        SquareMatrix OpQl = RotationMatrix.QuaternionToRotationMatrix(qL);
        OpQl = RotationMatrix.Q(OpQl);
        Vector t = Matrix.Multiply(OpQl, this);
        SaupeTensorVector ss = new SaupeTensorVector(this);
        for(int i=0; i<5; i++) {
            ss.data[i] = t.get(i, 0);
        }
        return ss;
    }

    public SaupeTensorVector averageByLambda(double lambda1, double lambda2, double lambda3) {
        SquareMatrix Q = new SquareMatrix(4);
        SquareMatrix A = new SquareMatrix(4);
        SquareMatrix E = new SquareMatrix(5);

        // TODO: Correct this!
        double[] lambda = { -(lambda1+lambda2+lambda3), lambda1, lambda2, lambda3 };
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

        Vector t = Matrix.Multiply(A, this);
        SaupeTensorVector ss = new SaupeTensorVector(this);
        for(int i=0; i<5; i++)
            ss.data[i] = t.get(i,0);

        return ss;
    }

    public SaupeTensorMatrix toSaupeTensorMatrix() {
        SaupeTensorMatrix S = new SaupeTensorMatrix();
        S.data[0][0] = this.get(0,0);
        S.data[1][1] = this.get(1, 0);
        S.data[2][2] = -(S.data[0][0] + S.data[1][1]);
        S.data[0][1] = S.data[1][0] = this.get(2,0);
        S.data[0][2] = S.data[2][0] = this.get(3,0);
        S.data[1][2] = S.data[2][1] = this.get(4,0);
        return S;
    }

    public double getPrincipleComponent() {         // Get Principle Component of s:
        return this.toSaupeTensorMatrix().getPrincipleComponent();
    }


    // Static Methods:
}
