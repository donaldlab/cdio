package cdio.math.space;

import java.util.Objects;

public class Real1D {

    // Members:
    public double val;

    public Real1D() {
       val = 0.0d;
    }

    public Real1D(Real1D other) {
        this.val = other.val;
    }

    public Real1D(double val) {
        this.val = val;
    }

    // Methods:
    public void add(Real1D other) {
        this.val += other.val;
    }

    public void subtract(Real1D other) {
        this.val -= other.val;
    }

    public void multiply(Real1D other) {
        this.val *= other.val;
    }

    public void divide(Real1D other) {
        if(other.val == 0.0d)
            throw new IllegalArgumentException("0.0d is not a legal argument for divide(Real1D other)");
        else
            this.val /= other.val;
    }

    // Override Methods:

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Real1D real1D = (Real1D) o;
        return Double.compare(real1D.val, val) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }


    // Static Methods:
    public static Real1D Add(Real1D a, Real1D b) {
        return new Real1D(a.val + b.val);
    }

    public static Real1D Subtract(Real1D a, Real1D b) {
        return new Real1D(a.val - b.val);
    }

    public static Real1D Multiply(Real1D a, Real1D b) {
        return new Real1D(a.val * b.val);
    }

    public static Real1D Divide(Real1D a, Real1D b) {
        if(b.val == 0.0f)
            throw new IllegalArgumentException("0.0d is not a legal second argument to Divide(Real1d, Real1D)");
        else
            return new Real1D(a.val + b.val);
    }

    public static boolean Equal(Real1D a, Real1D b) {
        return a.val == b.val;
    }
    public static boolean GreaterThan(Real1D a, Real1D b) {
        return a.val > b.val;
    }
    public static boolean LesserThan(Real1D a, Real1D b) {
        return a.val < b.val;
    }
}
