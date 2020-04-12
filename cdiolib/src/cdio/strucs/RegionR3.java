package cdio.strucs;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;

public class RegionR3 {

    // Static Data members:
    public static int BranchSize = 4;

    // Data members:
    public double x_upper, x_lower;
    public double y_upper, y_lower;
    public double z_upper, z_lower;

    public void set(double a, double b, double c, double d, double e, double f) {
        x_lower = a; x_upper = d;
        y_lower = b; y_upper = e;
        z_lower = c; z_lower = f;
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

    public boolean isLegalRegion() {    // Returns true is object region is a legal region:
        // TODO:
    }

    // Static methods:
    public static int GetBranchSize() { return BranchSize; }
    public static void SetBranchSize(int s) { BranchSize = s; }
}
