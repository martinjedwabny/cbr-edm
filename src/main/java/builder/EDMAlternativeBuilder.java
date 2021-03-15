package builder;

import cases.EDMAlternative;
import cases.EDMCausality;
import cases.EDMConsequence;
import cases.EDMInstance;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.HashSet;

public class EDMAlternativeBuilder extends EDMAbstractInstanceBuilder{

    public EDMAlternative build(String uri) {
        EDMInstanceBuilder instanceBuilder = new EDMInstanceBuilder();
        EDMConsequenceBuilder consequenceBuilder = new EDMConsequenceBuilder();
        EDMCausalityBuilder causalityBuilder = new EDMCausalityBuilder();
        HashSet<EDMConsequence> consequences = new HashSet<>();
        HashSet<EDMInstance> features = new HashSet<>();
        HashSet<EDMCausality> causalities = new HashSet<>();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-CONSEQUENCE").forEachRemaining(
                (String s) -> consequences.add(consequenceBuilder.build(s)));
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-FEATURE").forEachRemaining(
                (String s) -> features.add(instanceBuilder.build(s)));
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-CAUSALITY").forEachRemaining(
                (String s) -> causalities.add(causalityBuilder.build(s)));
        EDMAlternative instance = new EDMAlternative(consequences, features, causalities);
        this.setup(instance, uri);
        return instance;
    }
}
