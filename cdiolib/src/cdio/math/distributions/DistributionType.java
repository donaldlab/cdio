package cdio.math.distributions;

import cdio.math.space.SpaceType;

public enum DistributionType {

    // ****** Values ******
    Null(),
    BinghamDistribution(SpaceType.Hypersphere, 2),
    NormalDistribution(SpaceType.Real1D, 1),
    VMFDistribution(SpaceType.Hypersphere, 2),
    ;

    // ****** Members ******
    private final SpaceType m_space_type;
    private final int m_nparams;

    // ****** Methods ******

    DistributionType() {
        this.m_space_type = SpaceType.NullSpace;
        m_nparams = 0;
    }

    DistributionType(SpaceType type, int n) {
        this.m_space_type = type;
        this.m_nparams = n;
    }

    public SpaceType getSpaceType() {
        return m_space_type;
    }

    public int getParameterSize() {
        return m_nparams;
    }

}

