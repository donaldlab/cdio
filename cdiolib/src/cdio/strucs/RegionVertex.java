package cdio.strucs;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class RegionVertex {

    // Data members:
    public final RegionR3 region;

    public RegionVertex(RegionR3 r) {
        region = r;
    }

    // Member methods:
    public static ArrayList<RegionVertex> ConvertToRegionVertices(ArrayList<RegionR3> regions) {
        ArrayList<RegionVertex> out = new ArrayList<RegionVertex>();
        for(RegionR3 region: regions) {
            RegionVertex v = new RegionVertex(region);
            out.add(v);
        }
        return out;
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
}
