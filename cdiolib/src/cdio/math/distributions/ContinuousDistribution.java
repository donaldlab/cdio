package cdio.math.distributions;

public abstract class ContinuousDistribution<T> {

    // Members:
    private DistributionClass m_class;
    private DistributionType m_type;

    ContinuousDistribution() {
        m_class = DistributionClass.ContinuousDistribution;
        m_type = DistributionType.Null;
    }

    // Methods:
    public DistributionClass getDistributionClass() {
        return m_class;
    }

    public DistributionType getDistributionType() {
        return m_type;
    }

    public void setDistributionClass(DistributionClass _class) {
        m_class = _class;
    }

    public void setDistributionType(DistributionType type) {
        m_type = type;
    }

    // Abstract Methods:
    public abstract DiscreteDistribution generateDiscreteDistribution();
    public abstract double PDF(T x);

}
