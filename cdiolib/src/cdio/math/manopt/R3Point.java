package cdio.math.manopt;

import cdio.math.space.Vector;

public class R3Point {

    // Data members:
    public double x;
    public double y;
    public double z;

    public R3Point() {
        x = y = z = 0.0;
    }

    public R3Point(double x, double y, double z) {
        this.x = x; this.y = y; this.z = z;
    }

    public R3Point(R3Point other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    // Member methods:

    // Move along exp map in the locality with step alpha in direction of grad
    public void moveAlongGradient(double alpha, Vector gradient) {
        this.x = this.x + alpha * gradient.data[0];
        this.y = this.y + alpha * gradient.data[1];
        this.z = this.z + alpha * gradient.data[2];
    }


    // Static methods:
    public static R3Point GenerateRandomPoint() {
        return new R3Point((Math.random()*2-1)*10, (Math.random()*2-1)*10, (Math.random()*2-1)*10);
    }

}
