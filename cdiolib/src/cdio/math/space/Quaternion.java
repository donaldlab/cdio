package cdio.math.space;

public class Quaternion extends libprotnmr.math.Quaternion {

    public Quaternion() {
        super();
    }

    public Quaternion(double a, double b, double c, double d) {
        super(a,b,c,d);
    }

    public Quaternion(Quaternion other){
        super(other.a, other.b, other.c, other.d);
    }

    // Override methods:
    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        else if(!(other instanceof Quaternion))
            return false;
        else {
            Quaternion o = (Quaternion) other;
            return ((this.a==o.a) && (this.b == o.b) &&(this.c==o.c) && (this.d==o.d));
        }
    }

    @Override
    public int hashCode() {
        String hash_string = this.a + "," + this.b + "," + this.c + "," + this.d;
        return hash_string.hashCode();
    }

    // Methods:
    public RowVector getAsRowVector() {
        return new RowVector(this);
    }

    public ColumnVector getAsColumnVector(){
        return new ColumnVector(this);
    }

}
