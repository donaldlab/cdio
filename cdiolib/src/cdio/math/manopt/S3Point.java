package cdio.math.manopt;

import cdio.math.space.Vector;
import cdio.strucs.ThreeSphereSampler;
import libprotnmr.math.Quaternion;

import java.io.IOException;

public class S3Point {

    // Data members:
    public double w;
    public double x;
    public double y;
    public double z;

    public S3Point() {
        w = x = y = z = 0.0;
    }

    public S3Point(double w, double x, double y, double z) {
        this.x = x; this.y = y; this.z = z; this.w = w;
    }

    public S3Point(S3Point other) {
        this.w = other.w;
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    // Move along exp map in the locality with step alpha in direction of grad
    public void moveAlongGradient(double alpha, Vector gradient) {
        double norm = gradient.getNorm();
        double cos = Math.cos(alpha * norm);
        double sin = Math.sin(alpha * norm);
        this.x = cos * this.x + gradient.data[0] * sin / norm;
        this.y = cos * this.y + gradient.data[1] * sin / norm;
        this.z = cos * this.z + gradient.data[2] * sin / norm;
    }

    public Quaternion getQuaternion() {
        return new Quaternion(w, x, y, z);
    }

    // Static methods:

    public static S3Point GenerateRandomPoint() throws IOException {
        ThreeSphereSampler.Initialize();
        int i = (int)Math.random()*ThreeSphereSampler.QList.size();
        return new S3Point(ThreeSphereSampler.QList.get(i).a, ThreeSphereSampler.QList.get(i).b,
                ThreeSphereSampler.QList.get(i).c, ThreeSphereSampler.QList.get(i).d);

    }
}
