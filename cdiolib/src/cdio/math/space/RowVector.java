package cdio.math.space;

public class RowVector extends Vector {

    // Members:
    public RowVector() {
        super();
    }

    public RowVector(int dim){super(); this.dim = dim; this.data = new double[dim];}

    public RowVector(int dim, double[] data) {
        super(dim, data);
    }

    public RowVector(Vector other) {
        super(other);
    }

    public RowVector(Quaternion q) {
        super(q);
    }

    // Methods:
}
