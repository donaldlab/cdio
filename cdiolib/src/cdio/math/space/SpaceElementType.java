package cdio.math.space;

public enum SpaceElementType {

    // ******* Values *******
    Null,
    Quaternion,
    RotationMatrix,
    Real1D,
    Real2D,
    Real3D
    ;

    // Type1 ==> Type2
    public static boolean isConvertible(SpaceElementType type1, SpaceElementType type2) {
        switch(type1) {
            case Null:
                if(type2 == Null)
                    return true;
                else
                    return false;

            case Quaternion:
                if(type2 == Quaternion || type2 == RotationMatrix)
                    return true;
                else
                    return false;

            case RotationMatrix:
                if(type2 == RotationMatrix || type2 == Quaternion)
                    return true;
                else
                    return false;

            case Real1D:
                if(type2 == Real1D)
                    return true;
                else
                    return false;

            case Real2D:
                if(type2 == Real2D)
                    return true;
                else
                    return false;

            case Real3D:
                if(type2 == Real3D)
                    return true;
                else
                    return false;

            default:
                // Is this the best idea???
                return false;
        }
    }

    // Type1 => Type2
    public static boolean isUniquelyConvertible(SpaceElementType type1, SpaceElementType type2) {
        switch (type1) {
            case Null:
                if(type2 == Null)
                    return true;
                else
                    return false;

            case Real1D:
                if(type2 == Real1D)
                    return true;
                else
                    return false;

            case Real2D:
                if(type2 == Real2D)
                    return true;
                else
                    return false;

            case Real3D:
                if(type2 == Real3D)
                    return true;
                else
                    return false;

                // Check this properly
            case Quaternion:
                if(type2 == Quaternion)
                    return true;
                else
                    return false;

                // Check this properly
            case RotationMatrix:
                if(type2 == RotationMatrix)
                    return true;
                else
                    return false;

            default:
                return false;
        }
    }
}
