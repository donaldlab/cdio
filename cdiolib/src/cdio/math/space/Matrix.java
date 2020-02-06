package cdio.math.space;

import libprotnmr.math.Matrix3;

public abstract class Matrix  {

    // Methods in matrix:
    public abstract double get(int i, int j);
    public abstract void set(int i, int j, double val);
    public abstract int getNRows();
    public abstract int getNCols();
    public abstract void transpose();

    // Member methods:
    public boolean isValidIndex(int i, int j) {
        if(i >= getNRows() || j >= getNCols())
            return false;
        else
            return true;
    }

    // Static methods:
    public static boolean compatible(Matrix m1, Matrix m2) {
        return (m1.getNCols() == m2.getNRows());
    }

    public static SquareMatrix Multiply(SquareMatrix m1, SquareMatrix m2) {
        if(!compatible(m1, m2))
            throw new IllegalArgumentException("Incompatible matrices.");
        SquareMatrix out = new SquareMatrix(m1.getNRows());
        for(int i=0; i<m1.getNRows(); i++) {
            for(int j=0; j<m2.getNCols(); j++) {
                double val = 0d;
                for(int k=0; k<m1.getNRows(); k++)
                    val += m1.get(i,k) * m2.get(k, j);
                out.set(i,j,val);
            }
        }
        return out;
    }

    public static Vector Multiply(Vector v, SquareMatrix m) {
        if(!compatible(v, m))
            throw new IllegalArgumentException("Incompatible matrices.");
        Vector out = new Vector(m.getNCols(), Vector.VectorType.RowVector);
        for(int i=0; i<m.getNCols(); i++) {
            double val =0d;
            for(int k=0; k<m.getNCols(); k++) {
                val += v.get(0, k) * m.get(k, i);
            }
            out.set(0, i, val);
        }
        return out;
    }

    public static Vector Multiply(SquareMatrix m, Vector v) {
        if(!compatible(m, v))
            throw new IllegalArgumentException("Incompatible matrices.");
        Vector out = new Vector(m.getNCols(), Vector.VectorType.ColumnVector);
        for(int i=0; i<m.getNCols(); i++) {
            double val = 0d;
            for(int k=0; k<m.getNCols(); k++) {
                val += m.get(i, k) * v.get(k, 0);
            }
            out.set(i, 0, val);
        }
        return out;
    }

    public static double Multiply(Vector v1, Vector v2) {
        if(!compatible(v1, v2))
            throw new IllegalArgumentException("Incompatible matrices");
        double out =0d;
        for(int i=0; i<v1.getNCols(); i++)
            out += v1.get(0,i) * v2.get(i, 0);
        return out;
    }

    public static Matrix3 ToMatrix3(Matrix m) {
        if(m.getNRows()!= 3 || m.getNCols() != 3)
            throw new IllegalArgumentException("Cannot be converted to Matrix3.");

        return new Matrix3(m.get(0,0), m.get(0,1), m.get(0,2),
                           m.get(1,0), m.get(1,1), m.get(1,2),
                           m.get(2,0), m.get(1,2), m.get(2,2));
    }

}
