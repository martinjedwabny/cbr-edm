package cases;

import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import ontology.instance.EDMAbstractInstance;
import ontology.instance.EDMInstance;
import org.mindswap.pellet.utils.Pair;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EDMAlternative extends EDMAbstractInstance {

    private Set<EDMInstance> consequences;

    private Set<EDMInstance> features;

    private Set<EDMCausality> causalities;

    public EDMAlternative(Set<String> consequences,Set<String> features,Set<Pair> causalities) {
        this.consequences = new HashSet<>();
        this.features = new HashSet<>();
        this.causalities = new HashSet<>();
        if (consequences != null)
            for (String s : consequences)
                this.consequences.add(new EDMInstance(s));
        if (features != null)
            for (String s : features)
                this.features.add(new EDMInstance(s));
        if (causalities != null)
            for (Pair p : causalities)
                this.causalities.add(new EDMCausality(
                        new EDMInstance(p.first.toString()),
                        new EDMInstance(p.second.toString())));
    }

    public EDMAlternative(String uri) {
        this.fromString(uri);
    }

    @Override
    public void fromString(String uri) {
        this.setUri(uri);
        this.consequences = new HashSet<>();
        this.features = new HashSet<>();
        this.causalities = new HashSet<>();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-CONSEQUENCE").forEachRemaining(
                (String s) -> this.consequences.add(new EDMInstance(s)));
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-FEATURE").forEachRemaining(
                (String s) -> this.features.add(new EDMInstance(s)));
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-CAUSALITY").forEachRemaining(
                (String s) -> this.causalities.add(new EDMCausality(s)));
    }

    public Set<EDMInstance> getConsequences() {
        return consequences;
    }

    public void setConsequences(Set<EDMInstance> consequences) {
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
                + this.shortName + ";"
                + this.consequences + ";"
                + this.features + ";"
                + this.causalities + ";"
                + ")";
    }

    @Override
    public int hashCode() {
        if (this.uri != null) return super.hashCode();
        return Objects.hash(uri, consequences, features, causalities);
    }
}
