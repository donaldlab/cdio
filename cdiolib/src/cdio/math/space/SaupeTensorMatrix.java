package cdio.math.space;

public class SaupeTensorMatrix extends SquareMatrix {

    // Data Members:
    public double GDO;
    public double ETA;


    // Member Methods:

    public Vector toVectorForm() {      // Convert S from matrix to vector form:
        double[] t = {this.data[0][0], this.data[1][1], this.data[0][1], this.data[1][2], this.data[0][2]};
        Vector v = new Vector(t);
        return v;
    }

    // Static Methods:

}
