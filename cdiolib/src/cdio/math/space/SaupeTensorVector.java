package cdio.math.space;

import cdio.math.misc.BinghamNorm1F1;
import cdio.strucs.RegionR3;
import libprotnmr.math.Quaternion;


public class SaupeTensorVector extends Vector {

    public SaupeTensorVector(SaupeTensorVector other) {
        super(other);
    }

    // Member Methods:

    public SaupeTensorVector rotateByQ(Quaternion qL) {
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
        SquareMatrix A = new SquareMatrix(4);
        SquareMatrix E = new SquareMatrix(5);

        double[] lambda = {lambda1+lambda2+lambda3, lambda1-lambda2-lambda3, lambda2-lambda1-lambda3,
                            lambda3-lambda1-lambda2};
        double PDFGamma = BinghamNorm1F1.Value(lambda);

        for(int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                A.data[i][j] = BinghamNorm1F1.Hessian(lambda, i, j)/PDFGamma;

        //TODO: Define everything properly!
        E.data[0][0] = A.data[0][0];
        E.data[1][1] = A.data[0][0];
        E.data[2][2] = A.data[0][0];
        E.data[3][3] = A.data[0][0];
        E.data[4][4] = A.data[0][0];
        E.data[0][1] = A.data[0][0];
        E.data[1][0] = A.data[0][0];

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
