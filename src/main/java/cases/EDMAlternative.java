package cases;

import ontology.instance.EDMAbstractInstance;

import java.util.Objects;
import java.util.Set;

public class EDMAlternative extends EDMAbstractInstance {

    private Set<EDMAbstractInstance> features;

    private Set<EDMCausality> causalities;

    public EDMAlternative() {}

    public EDMAlternative(String shortName, Set<EDMAbstractInstance> features, Set<EDMCausality> causalities) {
        this.shortName = shortName;
        this.features = features;
        this.causalities = causalities;
    }

    public Set<EDMAbstractInstance> getFeatures() {
        return features;
    }

    public void setFeatures(Set<EDMAbstractInstance> features) {
        this.features = features;
    }

    public Set<EDMCausality> getCausalities() {
        return causalities;
    }

    public void setCausalities(Set<EDMCausality> causalities) {
        this.causalities = causalities;
    }

    @Override
    public String toString() {
        return "(" + this.getShortName() + ":" + this.features + "," + this.causalities + ")";
    }

    @Override
    public int hashCode() {
        if (this.getShortName() != null) return super.hashCode();
        return Objects.hash(features, causalities);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EDMAlternative that = (EDMAlternative) o;
        return Objects.equals(features, that.features) && Objects.equals(causalities, that.causalities);
    }
}
