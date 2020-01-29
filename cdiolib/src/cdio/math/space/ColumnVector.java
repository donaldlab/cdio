package cdio.math.space;

public class ColumnVector extends Vector{

    // Members:
    public ColumnVector() {
        super();
    }

    public ColumnVector(int dim, double[] data) {
        super(dim, data);
    }

    public ColumnVector(Vector other) {
        super(other);
    }

    public ColumnVector(Quaternion q) {
        super(q);
    }

    public ColumnVector(int dim){
        super();
        this.dim = dim;
        this.data = new double[dim];
    }
}
