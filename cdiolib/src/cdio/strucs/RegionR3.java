package cdio.strucs;

import cdio.math.space.SaupeTensorVector;
import libprotnmr.math.Quaternion;

import java.io.IOException;
import java.util.ArrayList;

public class RegionR3 {

    // Static Data members:
    public static int BranchSize = 4;

    // Data members:
    public double x_upper, x_lower;
    public double y_upper, y_lower;
    public double z_upper, z_lower;
    public ArrayList<RegionS3> allowedLS3;


    public void set(double a, double b, double c, double d, double e, double f) {
        x_lower = a; x_upper = d;
        y_lower = b; y_upper = e;
        z_lower = c; z_lower = f;
        allowedLS3 = null;
    }

    public RegionR3() {
        set(0d,0d,0d,0d,0d,0d);
    }

    public RegionR3(double a, double b, double c, double d, double e, double f) {
        set(a,b,c,d,e,f);
    }

    // Member methods:
    public double getXUpper() {
        return x_upper;
    }

    public void setXUpper(double x_upper) {
        this.x_upper = x_upper;
    }

    public double getXLower() {
        return x_lower;
    }

    public void setXLower(double x_lower) {
        this.x_lower = x_lower;
    }

    public double getYUpper() {
        return y_upper;
    }

    public void setYUpper(double y_upper) {
        this.y_upper = y_upper;
    }

    public double getYLower() {
        return y_lower;
    }

    public void setYLower(double y_lower) {
        this.y_lower = y_lower;
    }

    public double getZUpper() {
        return z_upper;
    }

    public void setZUpper(double z_upper) {
        this.z_upper = z_upper;
    }

    public double getZLower() {
        return z_lower;
    }

    public void setZLower(double z_lower) {
        this.z_lower = z_lower;
    }

    public double getXWidth() {
        return x_upper-x_lower;
    }

    public double getYWidth() {
        return y_upper-y_lower;
    }

    public double getZWidth() {
        return z_upper-z_lower;
    }

    // Branch / divide current region evenly
    public ArrayList<RegionR3>  branchEven(int Nx, int Ny, int Nz) {
        double xWidth = getXWidth()/Nx;
        double yWidth = getYWidth()/Ny;
        double zWidth = getZWidth()/Nz;

        ArrayList<RegionR3> branches = new ArrayList<>();
        for(int i=0; i<Nx; i++) {
            for(int j=0; j<Ny; j++) {
                for(int k=0; k<Nz; k++) {
                    RegionR3 r = new RegionR3(getXLower() + i*xWidth, getYLower() + j*yWidth, getZLower() + k*zWidth,
                            getXLower() +(i+1)*xWidth, getYLower() + (j+1)*yWidth, getZLower() + (k+1)*zWidth);
                    branches.add(r);
                }
            }
        }
        return branches;
    }

    // Branch / divide current region evenly
    public ArrayList<RegionR3> branchEven(double xWidth, double yWidth, double zWidth) {
        int Nx = (int) (getXWidth()/xWidth);
        int Ny = (int) (getYWidth()/yWidth);
        int Nz = (int) (getZWidth()/zWidth);
        return branchEven(Nx, Ny, Nz);
    }

    public String keyString() {
        String key = String.format("(%4.4f,%4.4f;%4.4f,%4.4f;%4.4f,%4.4f)", x_lower, x_upper, y_lower, y_upper,
                z_lower, z_upper);
        return key;
    }

    // Returns true if the current region is a legal region (bound exists):
    public boolean isLegalRegion() {    // Returns true is object region is a legal region:
        double U1, U2, U3, U4, L1, L2, L3, L4;
        U2 = getXUpper(); L2 = getXLower();
        U3 = getYUpper(); L3 = getYLower();
        U4 = getZUpper(); L4 = getZLower();
        U1 = -(U2 + U3 + U4); L1 = -(L2 + L3 + L4);

        if((L1 >= U4) && (L4 >= U3) && (L3 >= U2))
            return true;
        else
            return false;
    }

    // Returns true if the region in R3 can be pruned out for our purposes in pruning step 1
    public boolean isPrunable1(SaupeTensorVector s1, SaupeTensorVector s2) throws IOException {
        if(!this.isLegalRegion())
            return false;

        double PC1 = s2.getPrincipleComponent();
        if(getSLowerBoundOnR3(s1) <= PC1 && getSUpperBoundOnR3(s1) >= PC1) {
            return false;
        }
        else
            return true;
    }

    // Gets the upper bound of the PC of transformed s vector
    // after left rotation and averaging operation (refer to BnB algorithm : Qi et al. 2017)
    public double getSUpperBoundOnR3(SaupeTensorVector s) throws IOException  {
        ThreeSphereSampler.Initialize();
        double maxDa = 0;
        for(Quaternion q : ThreeSphereSampler.QList) {
            SaupeTensorVector st = new SaupeTensorVector(s);
            st.rotateByQ(q);
            st.averageByLambda(getXLower(), getYLower(), getZLower());
            double t = Math.abs(st.getPrincipleComponent());
            if(t >= maxDa)
                maxDa = t;
        }

        return maxDa;
    }

    // Gets the lower bound of the PC of transformed s vector
    // after left rotation and averaging operation (refer to BnB algorithm : Qi et al. 2017)
    public double getSLowerBoundOnR3(SaupeTensorVector s) throws IOException {
        ThreeSphereSampler.Initialize();
        double minDa = 0;
        for(Quaternion q : ThreeSphereSampler.QList) {
            SaupeTensorVector st = new SaupeTensorVector(s);
            st.rotateByQ(q);
            st.averageByLambda(getXUpper(), getYUpper(), getZUpper());
            double t = Math.abs(st.getPrincipleComponent());
            if(t <= minDa)
                minDa = t;
        }
        return minDa;
    }

    // Prunes the given region by branch and bound sampling on Full Space of S3 with the center of this region as
    // the point in R3 (lambda1, lambda2, lambda3)
    public void pruneStep2(SaupeTensorVector s1, SaupeTensorVector s2) {
        RegionS3 qLSpace = new RegionS3();
        allowedLS3 = qLSpace.getValidRegionsInS3(this, s1, s2);
    }

    // Static methods:
    public static int GetBranchSize() { return BranchSize; }
    public static void SetBranchSize(int s) { BranchSize = s; }
}
