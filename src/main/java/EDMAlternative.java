import colibri.instance.EDMAbstractInstance;
import colibri.instance.EDMDictionaryInstance;
import colibri.instance.EDMInstance;
import colibri.instance.EDMSetInstance;
import org.mindswap.pellet.utils.Pair;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EDMAlternative extends EDMAbstractInstance {

    private Set<EDMInstance> consequences;

    private Set<EDMInstance> features;

    private Set<EDMCausality> causalities;

    public EDMAlternative() {}

    public EDMAlternative(String uri) {
        this.fromString(uri);
    }

    @Override
    public void fromString(String uri) {
        this.setUri(uri);
        EDMDictionaryInstance values = new EDMDictionaryInstance(uri, Map.of(
                "HAS-CONSEQUENCE", EDMInstance::new,
                "HAS-FEATURE", EDMInstance::new,
                "HAS-CAUSALITY", (String uuri) -> new EDMDictionaryInstance(uuri, Map.of(
                        "HAS-CCAUSE", EDMInstance::new,
                        "HAS-CCONSEQUENCE", EDMInstance::new
                ))));
        this.consequences = (Set) values.getValues().get("HAS-CONSEQUENCE");
        if (this.consequences == null) this.consequences = new HashSet<>();
        this.features = (Set) values.getValues().get("HAS-FEATURE");
        if (this.features == null) this.features = new HashSet<>();
        Set<EDMDictionaryInstance> causalities = (Set)values.getValues().get("HAS-CAUSALITY");
        if (causalities == null || causalities.isEmpty())
            this.causalities = new HashSet<>();
        else
            this.causalities = causalities.stream().map((EDMDictionaryInstance dict) ->
                new EDMCausality(
                        (EDMInstance) ((Set)dict.getValues().get("HAS-CCAUSE")).iterator().next(),
                        (EDMInstance) ((Set)dict.getValues().get("HAS-CCONSEQUENCE")).iterator().next()))
                .collect(Collectors.toSet());
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

}
