package cdio.math.space;

import java.util.Arrays;

public class Matrix {

    // Members:
    public int nrows, ncols;
    public double[][] data;

    public Matrix() {
        this.nrows = 0;
        this.ncols = 0;
        this.data = null;
    }

    public Matrix(int nrows, int ncols) {
        this.nrows = nrows;
        this.ncols = ncols;
        this.data = new double[nrows][ncols];
        Arrays.fill(this.data, 0);
    }

    public Matrix(double[][] data){
        if(data == null)
            throw new IllegalArgumentException("Cannot initialize with null matrix.");
        this.nrows = data.length;
        this.ncols = data[0].length;
        this.data = new double[nrows][ncols];

        for(int i=0; i<nrows; i++)
            for(int j=0; j<ncols; j++)
                this.data[i][j] = data[i][j];
    }

    public Matrix(RowVector v){
        this.nrows = 1;
        this.ncols = v.dim;
        this.data = new double[nrows][ncols];
        for(int j=0; j<ncols; j++)
            this.data[0][j] = v.data[j];
    }

    public Matrix(ColumnVector v){
        this.nrows = v.dim;
        this.ncols = 1;
        this.data = new double[nrows][ncols];
        for(int j=0; j<ncols; j++)
            this.data[j][0] = v.data[j];
    }

    public Matrix(Matrix other){
        this.nrows = other.nrows;
        this.ncols = other.ncols;
        this.data = new double[nrows][ncols];

        for(int i=0; i<nrows; i++)
            for(int j=0; j<ncols; j++)
                this.data[i][j] = other.data[i][j];
    }

    // Member Methods:
    public RowVector getAsRowVector(){
        if(nrows != 1)
            throw new IllegalArgumentException("Only applicable if matrix has 1 row.");

        RowVector v = new RowVector(this.ncols);
        for(int i=0; i<this.ncols; i++)
            v.data[i] = this.data[0][i];
        return v;
    }

    public ColumnVector getAsColumnVector(){
        if(ncols != 1)
            throw new IllegalArgumentException("Only  applicable if matrix has 1 column.");

        ColumnVector v = new ColumnVector(this.nrows);
        for(int i=0; i<this.nrows;i++)
            v.data[i] = this.data[i][0];
        return v;
    }


    // Static Methods:
    public static Matrix Multiply(Matrix m1, Matrix m2) {
        if(m1.ncols != m2.nrows)
            throw new IllegalArgumentException("Matrices not compatible for Multiplication.");

        Matrix m3 = new Matrix(m1.nrows, m2.ncols);

        for(int i=0; i<m1.nrows; i++) {
            for(int j=0; j<m2.ncols; j++) {
                for(int k=0; k<m1.ncols; k++)
                    m3.data[i][j] += m1.data[i][k] * m2.data[k][j];
            }
        }
        return m3;
    }

    public static RowVector Multiply(RowVector v, Matrix m) {
        Matrix mt = new Matrix(v);
        Matrix prod =  Multiply(mt, m);
        return prod.getAsRowVector();
    }

    public static ColumnVector Multiply(Matrix m, ColumnVector v) {
        Matrix mt = new Matrix(v);
        Matrix prod =  Multiply(m, mt);
        return prod.getAsColumnVector();
    }
}
