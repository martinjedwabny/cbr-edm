package cases;

import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import ontology.instance.EDMAbstractInstance;
import ontology.instance.EDMInstance;

import java.util.HashSet;
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

    public EDMAlternative(String uri) {
        this.fromString(uri);
    }

    @Override
    public void fromString(String uri) {
        this.setName(uri);
        this.consequences = new HashSet<>();
        this.features = new HashSet<>();
        this.causalities = new HashSet<>();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-CONSEQUENCE").forEachRemaining(
                (String s) -> this.consequences.add(new EDMConsequence(s)));
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-FEATURE").forEachRemaining(
                (String s) -> this.features.add(new EDMInstance(s)));
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-CAUSALITY").forEachRemaining(
                (String s) -> this.causalities.add(new EDMCausality(s)));
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
