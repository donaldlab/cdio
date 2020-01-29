package cdio.math.space;

public class Real3D {

    // Members:
    public Real1D x;
    public  Real1D y;
    public  Real1D z;

    // Methods:
    public Real3D() {
        x = new Real1D(0.0d);
        y = new Real1D(0.0d);
        z = new Real1D(0.0d);
    }

    // TODO *********** COMPLETE CODE ******
    public Real3D(Real1D a, Real1D b, Real1D c) {
        x = new Real1D(a);
        y = new Real1D(b);
        z = new Real1D(c);
    }

    public Real3D(double a, double b, double c) {
        x = new Real1D(a);
        y = new Real1D(b);
        z = new Real1D(c);
    }

    public Real3D(Real3D other) {
        this.x = new Real1D(other.x);
        this.y = new Real1D(other.y);
        this.z = new Real1D(other.z);
    }

    // Override Methods:

    // Static Methods:
}
