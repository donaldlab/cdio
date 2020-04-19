package cdio.strucs;

import cdio.math.space.SaupeTensorVector;
import libprotnmr.math.Quaternion;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RegionS3 {

    // Static data members:
    public static int BranchSize = 6;

    // Data members:
    public double phi_upper;
    public double psi_upper;
    public double theta_upper;
    public double phi_lower;
    public double psi_lower;
    public double theta_lower;
    public ArrayList<Quaternion> allowedRS3;



    // Member Methods:

    // Return list of acceptable regions in S3 given a region in R3 and s1 and s2 in pruning step 2:
    public ArrayList<RegionS3> getValidRegionsInS3(RegionR3 r, SaupeTensorVector s1, SaupeTensorVector s2) {
        ArrayList<RegionS3> candidates = this.branchEven(24, 24, 12);
        return boundAndPrune(candidates, r, s1, s2);
    }

    // Branch / divide current region evenly
    public ArrayList<RegionS3> branchEven(int Nx, int Ny, int Nz) {
        double phiWidth = getPhiWidth()/Nx;
        double psiWidth = getPsiWidth()/Ny;
        double thetaWidth = getThetaWidth()/Nz;

        ArrayList<RegionS3> branches = new ArrayList<>();
        for(int i=0; i<Nx; i++) {
            for(int j=0; j<Ny; j++) {
                for(int k=0; k<Nz; k++) {
                    RegionS3 r = new RegionS3(getPhiLower() + i*phiWidth, getPsiLower() + j*psiWidth, getThetaLower() + k*thetaWidth,
                            getPhiLower() +(i+1)*phiWidth, getPsiLower() + (j+1)*psiWidth, getThetaLower() + (k+1)*thetaWidth);
                    branches.add(r);
                }
            }
        }
        return branches;
    }

    // Bound and prune the branches of region using appropriate conditions:
    public ArrayList<RegionS3> boundAndPrune(ArrayList<RegionS3> in, RegionR3 r, SaupeTensorVector s1, SaupeTensorVector s2) {
        ArrayList<RegionS3> out = new ArrayList<RegionS3>();
        for(RegionS3 s: in) {
            ArrayList<Quaternion> samples = getS3PointsInRegion(s);
            double maxDa = getMaxDaInR3S3(samples, r, s1);
            double minDa = getMinDaInR3S3(samples, r, s1);
            double PC = s2.getPrincipleComponent();
            if(maxDa >= PC && PC >= minDa)
                out.add(s);
        }
        return out;
    }

    // Returns a list of 8 quaternions used to sample the region in S3:
    public ArrayList<Quaternion> getS3PointsInRegion(RegionS3 s) {
        ArrayList<Quaternion> samples = new ArrayList<Quaternion>();
        for(int i=1; i<=3; i+=2) {
            for(int j=1; j<=3; j+=2) {
                for(int k=1; k<=3; k+=2) {
                    double phi = s.phi_lower + i * getPhiWidth()/4.0;
                    double psi = s.psi_lower + j * getPsiWidth()/4.0;
                    double theta = s.theta_lower + k * getThetaWidth()/4.0;
                    double q0 = Math.cos(psi);
                    double q1 = Math.sin(psi) * Math.cos(theta);
                    double q2 = Math.sin(psi) * Math.sin(theta) * Math.cos(phi);
                    double q3 = Math.sin(psi) * Math.sin(theta) * Math.sin(phi);
                    Quaternion q = new Quaternion(q0, q1, q2, q3);
                    samples.add(q);
                }
            }
        }
        return samples;
    }

    // Returns the max Da in the region in S3:
    public double getMaxDaInR3S3(ArrayList<Quaternion> samples, RegionR3 r, SaupeTensorVector s1){
        double lambda1 = r.getXLower();
        double lambda2 = r.getYLower();
        double lambda3 = r.getZLower();

        double maxDa = Double.MAX_VALUE;

        for(Quaternion q: samples) {
            SaupeTensorVector ss = new SaupeTensorVector(s1);
            ss.rotateByQ(q);
            ss.averageByLambda(lambda1, lambda2, lambda3);
            double t = Math.abs(ss.getPrincipleComponent());
            if(t >= maxDa)
                maxDa = t;
        }
        return maxDa;
    }

    // Returns the min Da in the region in S3:
    public double getMinDaInR3S3(ArrayList<Quaternion> samples, RegionR3 r, SaupeTensorVector s1) {
        double lambda1 = r.getXUpper();
        double lambda2 = r.getYUpper();
        double lambda3 = r.getZUpper();

        double minDa = Double.MIN_VALUE;

        for(Quaternion q: samples) {
            SaupeTensorVector ss = new SaupeTensorVector(s1);
            ss.rotateByQ(q);
            ss.averageByLambda(lambda1, lambda2, lambda3);
            double t = Math.abs(ss.getPrincipleComponent());
            if(t <= minDa)
                minDa = t;
        }
        return minDa;
    }

    // Getters:
    public double getPhiWidth() {
        return phi_upper-phi_lower;
    }

    public double getPsiWidth() {
        return psi_upper-psi_lower;
    }

    public double getThetaWidth() {
        return theta_upper-theta_lower;
    }
    public static int getBranchSize() {
        return BranchSize;
    }

    public double getPhiUpper() {
        return phi_upper;
    }

    public double getPsiUpper() {
        return psi_upper;
    }

    public double getThetaUpper() {
        return theta_upper;
    }

    public double getPhiLower() {
        return phi_lower;
    }

    public double getPsiLower() {
        return psi_lower;
    }

    public double getThetaLower() {
        return theta_lower;
    }

    public void set(double a, double b, double c, double d, double e, double f) {
        phi_lower = a; phi_upper = d;
        psi_lower = b; psi_upper = e;
        theta_lower = c; theta_lower = f;
        allowedRS3 = null;
    }

    public RegionS3() {
        set(0d,0d,0d,2*Math.PI, Math.PI, Math.PI);
    }

    public RegionS3(double a, double b, double c, double d, double e, double f) {
        set(a,b,c,d,e,f);
    }

    public String keyString() {
        String key = String.format("(%4.4f,%4.4f;%4.4f,%4.4f;%4.4f,%4.4f)", phi_lower, phi_upper, psi_lower, psi_upper,
                theta_lower, theta_upper);
        return key;
    }

    // Static methods:
    public static int GetBranchSize() { return BranchSize; }
    public static void SetBranchSize(int s) { BranchSize = s; }
    public static RegionS3 FullSpace() {
        RegionS3 s = new RegionS3(0, 0, 0, 2*Math.PI, Math.PI, Math.PI);
        return s;
    }
}
