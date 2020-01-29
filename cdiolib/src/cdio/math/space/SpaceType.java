package cdio.math.space;

import cdio.math.space.SpaceElementType;

public enum SpaceType {

    // ****** Values ********
    NullSpace(SpaceElementType.Null),
    RotationSpace(SpaceElementType.RotationMatrix),
    Hypersphere(SpaceElementType.Quaternion),
    Sphere(SpaceElementType.Real3D),
    Circle(SpaceElementType.Real2D),
    Real1D(SpaceElementType.Real1D),
    Real2D(SpaceElementType.Real2D),
    Real3D(SpaceElementType.Real3D)
    ;

    private final SpaceElementType m_element_type;

    // ******** Members *******
    SpaceType() {
        m_element_type = SpaceElementType.Null;
    }

    SpaceType(SpaceElementType type) {
        m_element_type = type;
    }

    public SpaceElementType getSpaceElementType() {
        return m_element_type;
    }
}
