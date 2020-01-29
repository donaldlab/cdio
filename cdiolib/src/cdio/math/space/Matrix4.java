package cdio.math.space;

public class Matrix4 extends libprotnmr.math.Matrix4 {

    // Override methods:

    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        else if(!(other instanceof Matrix4))
            return false;
        else {
            Matrix4 o = (Matrix4) other;
            for(int i=0; i<4; i++) {
                for(int j=0; j<4; j++) {
                   if(this.data[i][j] != o.data[i][j])
                       return false;
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
