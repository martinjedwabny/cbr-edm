package cases;

import ontology.instance.EDMAbstractInstance;
import ontology.instance.EDMDictionaryInstance;
import ontology.instance.EDMInstance;
import org.mindswap.pellet.utils.Pair;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class EDMAlternative extends EDMAbstractInstance {

    private Set<EDMInstance> consequences;

    private Set<EDMInstance> features;

    private Set<EDMCausality> causalities;

    public EDMAlternative() {}

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
        EDMDictionaryInstance values = getAlternativeFields(uri);
        this.consequences = values.getValues().containsKey("HAS-CONSEQUENCE") ?
                (Set) values.getValues().get("HAS-CONSEQUENCE") : new HashSet<>();
        this.features = values.getValues().containsKey("HAS-FEATURE") ?
                (Set) values.getValues().get("HAS-FEATURE") : new HashSet<>();
        this.causalities = values.getValues().containsKey("HAS-CAUSALITY") ?
                (Set) values.getValues().get("HAS-CAUSALITY") : new HashSet<>();
    }

    private EDMDictionaryInstance getAlternativeFields(String uri) {
        return new EDMDictionaryInstance(uri, Map.of(
            "HAS-CONSEQUENCE", EDMInstance::new,
            "HAS-FEATURE", EDMInstance::new,
            "HAS-CAUSALITY", EDMCausality::new));
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
