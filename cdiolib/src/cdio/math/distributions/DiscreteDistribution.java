package cdio.math.distributions;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscreteDistribution<T> {

    // Members:
    private DistributionClass m_class;
    private DistributionType m_type;
    private HashMap<T, Double>  m_prob_table;

    DiscreteDistribution() {
        m_class = DistributionClass.ContinuousDistribution;
        m_type = DistributionType.Null;
        m_prob_table = new HashMap<T, Double>();
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

    public double getPMF(T element){
        if(m_prob_table.isEmpty() || !(m_prob_table.containsKey(element)))
            return -1;
        else
            return m_prob_table.get(element);
    }

    public void setPMF(T element, double pmf) {
        if(pmf < 0)
            throw new IllegalArgumentException("PMF cannot be set to a negative value.");
        if(m_prob_table.containsKey(element))
            m_prob_table.replace(element, pmf);
        else
            m_prob_table.put(element, pmf);
    }

    public double getCDF(ArrayList<T> list_of_elements) {
        double cdf = 0d;
        for(T it: list_of_elements)
            cdf += getPMF(it);
        return cdf;
    }
}
