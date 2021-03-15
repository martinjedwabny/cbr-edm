package cases;

import ontology.instance.EDMAbstractInstance;

import java.util.Objects;
import java.util.Set;

public class EDMAlternative extends EDMAbstractInstance {

    private Set<EDMAbstractInstance> features;

    private Set<EDMCausality> causalities;

    public EDMAlternative() {}

    public EDMAlternative(Set<EDMAbstractInstance> features, Set<EDMCausality> causalities) {
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
        return "(" + this.getShortName() + ";" + this.features + ";" + this.causalities + ";" + ")";
    }

    @Override
    public int hashCode() {
        if (this.getUri() != null) return super.hashCode();
        return Objects.hash(this.getUri(), features, causalities);
    }
}
