package cdio.math.manopt;

import cdio.math.space.Vector;

import java.io.IOException;

public class MPoint {

    // Data members:
    public R3Point lambda;
    public S3Point qL;
    public S3Point qR;

    public MPoint(R3Point lambda, S3Point qL, S3Point qR) {
        this.lambda = lambda;
        this.qL = qL;
        this.qR = qR;
    }

    // Member Methods:
    public void moveAlongGradient(double alpha, Vector gradient) {
        double[] r3grad = new double[3];
        r3grad[0] = gradient.data[0]; r3grad[1] = gradient.data[1]; r3grad[2] = gradient.data[2];
        Vector gradientR3 = new Vector(r3grad);
        this.lambda.moveAlongGradient(alpha, gradientR3);

        double[] s3grad = new double[4];
        s3grad[0] = gradient.data[3]; s3grad[1] = gradient.data[4]; s3grad[2] = gradient.data[5]; s3grad[3] = gradient.data[6];
        Vector gradientqL = new Vector(s3grad);
        this.qL.moveAlongGradient(alpha, gradientqL);

        s3grad[0] = gradient.data[7]; s3grad[1] = gradient.data[8]; s3grad[2] = gradient.data[9]; s3grad[3] = gradient.data[10];
        Vector gradientqR = new Vector(s3grad);
        this.qR.moveAlongGradient(alpha, gradientqR);
    }


    // Static Methods:
    public static MPoint GenerateRandomPoint() throws IOException {
        return new MPoint(R3Point.GenerateRandomPoint(), S3Point.GenerateRandomPoint(), S3Point.GenerateRandomPoint());
    }

}
