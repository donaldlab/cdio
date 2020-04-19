package cdio.strucs;

import cdio.math.space.Matrix;
import cdio.math.space.RotationMatrix;
import cdio.math.space.SaupeTensorVector;
import cdio.math.space.SquareMatrix;
import libprotnmr.math.Quaternion;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RegionVertex {

    // Data members:
    public final RegionR3 regionR3;
    public ArrayList<RegionS3> prunedRegionsS3;


    public RegionVertex(RegionR3 r) {
        regionR3 = r;
        prunedRegionsS3 = null;
    }

    // Member methods:

    // Returns true if the region in R3 can be pruned out for our purposes in pruning step 1
    public boolean isPrunable1(SaupeTensorVector s1, SaupeTensorVector s2) throws IOException {
        if(!this.regionR3.isLegalRegion())
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
            // TODO: Inverse of Q instead of Q itself!!
            st.rotateByQ(q);
            st.averageByLambda(regionR3.getXLower(), regionR3.getYLower(), regionR3.getZLower());
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
            st.averageByLambda(regionR3.getXUpper(), regionR3.getYUpper(), regionR3.getZUpper());
            double t = Math.abs(st.getPrincipleComponent());
            if(t <= minDa)
                minDa = t;
        }

        return minDa;
    }


    @Override
    public String toString() {
        return regionR3.keyString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionVertex that = (RegionVertex) o;
        return (toString().equals(that.toString()));
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }


    // Static Methods:

    public static List<RegionVertex> ConvertToRegionVertices(List<RegionR3> regions) {
        List<RegionVertex> out = new ArrayList<RegionVertex>();
        for(RegionR3 region: regions) {
            RegionVertex v = new RegionVertex(region);
            out.add(v);
        }
        return out;
    }

}
