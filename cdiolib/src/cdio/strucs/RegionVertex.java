package cdio.strucs;

import cdio.math.space.SaupeTensorVector;
import libprotnmr.math.Quaternion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RegionVertex {

    // Data members:
    public final RegionR3 region;
    public Quaternion qL;
    public Quaternion qR;


    public RegionVertex(RegionR3 r) {
        region = r;
        qL = qR = null;
    }

    // Member methods:

    public boolean isPrunable(SaupeTensorVector s1, SaupeTensorVector s2) { // Returns true if the region in R3 can be
        // pruned out for our purposes
        if(!this.region.isLegalRegion())
            return false;

        double PC1 = s2.getPrincipleComponent();
        if(getSLowerBound(s1) <= PC1 && getSUpperBound(s1) >= PC1) {
            return false;
        }
        else
            return true;
    }

    public double getSUpperBound(SaupeTensorVector s) { // Gets the upper bound of the PC of transformed s vector
        // after left rotation and averaging operation (refer to BnB algorithm : Qi et al. 2017)
        // TODO:

    }

    public double getSLowerBound(SaupeTensorVector s) { // Gets the lower bound of the PC of transformed s vector
        // after left rotation and averaging operation (refer to BnB algorithm : Qi et al. 2017)
        // TODO:

    }

    @Override
    public String toString() {
        return region.keyString();
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
