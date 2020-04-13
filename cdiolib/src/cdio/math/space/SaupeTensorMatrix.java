package cdio.math.space;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

import java.security.Signature;

public class SaupeTensorMatrix extends SquareMatrix {

    // Data Members:
    public double GDO;
    public double ETA;

    public SaupeTensorMatrix() {
        super(3);
    }

    public SaupeTensorMatrix(SaupeTensorMatrix other) {
        super(other);
    }


    // Member Methods :
    public Vector toVectorForm() {      // Convert S from matrix to vector form:
        double[] t = {this.data[0][0], this.data[1][1], this.data[0][1], this.data[1][2], this.data[0][2]};
        Vector v = new Vector(t);
        return v;
    }

    public double getPrincipleComponent() {         // Get Principle Component of s:
        Matrix m = new Matrix(this.data);
        SingularValueDecomposition svd = new SingularValueDecomposition(m);
        Matrix U = svd.getU();
        Matrix S = svd.getS();
        Matrix V = svd.getV();
        double[] eigenValues = new double[3];
        for(int i=0; i<3; i++) {
            eigenValues[i] = U.get(0,i) * V.get(0,i) + U.get(1,i) * V.get(1,i) + U.get(2,i) * V.get(2,i);
            eigenValues[i] *= S.get(i, i);
        }
        int max_index = 0;
        for(int i=1; i<3; i++) {
            if(Math.abs(eigenValues[i]) > Math.abs(eigenValues[max_index]))
                max_index = i;
        }
        return eigenValues[max_index];
    }

    // Static Methods:

}
