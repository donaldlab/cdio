package cdio.math.space;

public class Vector {

    // Members:
    public int dim;
    public double[] data;

    public Vector(){
        this.dim = 0;
        this.data = null;
    }

    public Vector(int dim, double[] data){
        if(dim > data.length)
            throw new IllegalArgumentException("Data cannot have fewer elements than dim.");
        else if(dim <= 0)
            throw new IllegalArgumentException("Dim must be > 0.");
        else {
            this.data = new double[dim];
            for(int i=0; i<dim; i++)
                this.data[i] = data[i];
            this.dim = dim;
        }
    }

    public Vector(Vector other) {
        this.dim = other.dim;
        this.data = new double[dim];
        for(int i=0; i<dim; i++)
            this.data[i] = other.data[i];
    }

    public Vector(Quaternion q) {
       this.dim = 4;
       this.data = new double[4];
       this.data[0] = q.a; this.data[1] = q.b; this.data[2] = q.c; this.data[3] = q.d;
    }

    // Methods:
    public int getDim() {
        return dim;
    }

    public double[] getData() {
        double[] data_copy = new double[this.dim];
        for(int i=0; i<this.dim; i++)
            data_copy[i] = this.data[i];
        return data_copy;
    }

    public void setData(double data[]) {
        this.dim = data.length;
        this.data = new double[this.dim];
        for(int i=0; i<this.dim; i++)
            this.data[i] = data[i];
    }

    // Static Methods:
    public static double Multiply(RowVector rv, ColumnVector cv) {
        if(rv.getDim() != cv.getDim())
            throw new IllegalArgumentException("Incompatible vectors.");

        double out = 0.0d;
        for(int i=0; i<rv.getDim(); i++)
            out += rv.data[i] * cv.data[cv.getDim()-i-1];

        return out;
    }

}
