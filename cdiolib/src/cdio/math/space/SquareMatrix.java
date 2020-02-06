package cdio.math.space;

import libprotnmr.math.CompareReal;
import libprotnmr.math.Matrix4;

import java.util.Arrays;

public class SquareMatrix extends Matrix {

    // members:
    public double[][] data;
    public int dimension;

    // Initializers:
    SquareMatrix() {
        dimension = 0;
        data = null;
    }

    SquareMatrix(int i) {
        dimension = i;
        data = new double[dimension][dimension];
        Arrays.fill(data, 0d);
    }

    SquareMatrix(SquareMatrix other) {
        dimension = other.dimension;
        data = new double[dimension][dimension];
        for(int i=0; i<dimension; i++)
            for(int j=0; j<dimension; j++)
                data[i][j] = other.data[i][j];
    }

    // methods:

    public boolean isInvertible() {
        return !(CompareReal.eq(getDet(), 0.0, 1e-8));
    }

    public void invert(){
        // TODO:
    }

    private double getMinorDeterminant(int r, int c){
        SquareMatrix minor = new SquareMatrix(dimension-1);
        int mr, mc;
        mr = mc = 0;
        for(int i=0; i<dimension; i++) {
            if(i == r)
                continue;
            for(int j=0; j<dimension; j++){
                if(j == c)
                    continue;

                minor.data[mr][mc] = data[i][j];
                mc += 1;
            }
            mr += 1;
        }
        return minor.getDet();

    }

    public double getDet() {
        if(dimension == 2) {
            return data[0][0] * data[1][1] - data[1][0] * data[0][1];
        }
        double det = 0f;
        int sign = 1;
        for(int i=0; i<dimension; i++) {
            det += sign * getMinorDeterminant(0, i);
            sign *= -1;
        }
        return det;
    }

    public double getTrace() {
        double sum = 0d;
        for(int i=0; i<dimension; i++)
            sum += data[i][i];
        return sum;
    }

    @Override
    public void set(int i, int j, double val) {
        if(!(isValidIndex(i,j)))
            throw new IllegalArgumentException("Invalid index.");
        this.data[i][j] = val;
    }

    @Override
    public double get(int i, int j) {
        if(!(isValidIndex(i,j)))
            throw new IllegalArgumentException("Invalid index.");
        return data[i][j];
    }

    @Override
    public int getNCols() {
        return dimension;
    }

    @Override
    public int getNRows() {
        return dimension;
    }


    // transpose in place
    @Override
    public void transpose() {
        for (int i = 0; i < dimension - 1; i++) {
            for (int j = i + 1; j < dimension; j++) {
                double t = data[i][j];
                data[i][j] = data[j][i];
                data[j][i] = t;
            }
        }
    }

    public SquareMatrix getTranspose() {
        SquareMatrix t = new SquareMatrix(this);
        t.transpose();
        return t;
    }




}
