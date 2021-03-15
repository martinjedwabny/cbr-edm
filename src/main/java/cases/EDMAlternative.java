package cases;

import ontology.instance.EDMAbstractInstance;

import java.util.Objects;
import java.util.Set;

public class EDMAlternative extends EDMAbstractInstance {

    private Set<EDMConsequence> consequences;

    private Set<EDMInstance> features;

    private Set<EDMCausality> causalities;

    public EDMAlternative(Set<EDMConsequence> consequences,Set<EDMInstance> features,Set<EDMCausality> causalities) {
        this.consequences = consequences;
        this.features = features;
        this.causalities = causalities;
    }

    public Set<EDMConsequence> getConsequences() {
        return consequences;
    }

    public void setConsequences(Set<EDMConsequence> consequences) {
        this.consequences = consequences;
    }

    public Set<EDMInstance> getFeatures() {
        return features;
    }

    public void setFeatures(Set<EDMInstance> features) {
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
        return "("
                + this.getShortName() + ";"
                + this.consequences + ";"
                + this.features + ";"
                + this.causalities + ";"
                + ")";
    }

    @Override
    public int hashCode() {
        if (this.getUri() != null) return super.hashCode();
        return Objects.hash(this.getUri(), consequences, features, causalities);
    }
}
