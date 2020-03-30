package cdio.math.space;

import libprotnmr.geom.Vector3;

import java.util.Arrays;

public class Vector extends Matrix {

    // members:

    public enum VectorType {
        RowVector, ColumnVector
    }

    public int dimension;
    public double[] data;
    public VectorType type;


    // initializers:

    private void set(double[] data) {
        this.dimension = data.length;
        this.data = new double[data.length];
        for(int i=0; i<dimension; i++)
            this.data[i] = data[i];
    }

    public Vector(){
        dimension=0;
        data = null;
        type = VectorType.RowVector;
    }

    public Vector(int dimension) {
        this.dimension = dimension;
        data = new double[dimension];
        Arrays.fill(data, 0);
        type = VectorType.RowVector;
    }

    public Vector(int dimension, VectorType type) {
        this.dimension = dimension;
        data = new double[dimension];
        Arrays.fill(data, 0);
        this.type = type;
    }

    public Vector(double[] data) {
        set(data);
        this.type = VectorType.RowVector;
    }

    public Vector(double[] data, VectorType type) {
        set(data);
        this.type = type;
    }

    public Vector(Vector other) {
        set(other.data);
        this.type = other.type;
    }

    // methods:

    public boolean isRowVector() {
        return this.type == VectorType.RowVector;
    }

    public boolean isColumnVector() {
        return this.type == VectorType.ColumnVector;
    }

    @Override
    public void transpose() {
        if(isRowVector())
            this.type = VectorType.ColumnVector;
        else
            this.type = VectorType.RowVector;
    }

    @Override
    public double get(int i, int j) {
        if(!(isValidIndex(i,j)))
            throw new IllegalArgumentException("Invalid index.");
        if(type == VectorType.RowVector)
            return  data[i];
        else
            return data[j];
    }

    @Override
    public void set(int i, int j, double val) {
        if(!(isValidIndex(i,j)))
            throw new IllegalArgumentException("Invalid index.");
        if(type == VectorType.RowVector)
            data[i] = val;
        else
            data[j] = val;
    }

    @Override
    public int getNRows() {
        if(isRowVector())
            return dimension;
        else
            return 1;
    }

    @Override
    public int getNCols() {
        if(isColumnVector())
            return dimension;
        else
            return 1;
    }

    public Vector getTranspose() {
        Vector v = new Vector(this);
        v.transpose();
        return v;
    }

    // static methods:
    public static Vector3 ToVector3(Vector v) {
        if(v.data.length != 3)
            throw new IllegalArgumentException("Cannot convert to Vector3.");

        if(v.isColumnVector()) {
            return new Vector3(v.get(0,0), v.get(1,0), v.get(2,0));
        }
        else {
            return new Vector3(v.get(0,0), v.get(0,1), v.get(0,2));
        }
    }

}

