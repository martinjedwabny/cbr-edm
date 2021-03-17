package builder;

import cases.EDMAlternative;
import cases.EDMCausality;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import cases.EDMAbstractInstance;

import java.util.HashSet;

public class EDMAlternativeBuilder extends EDMAbstractInstanceBuilder{

    public EDMAlternative build(String uri) {
        EDMAlternative instance = new EDMAlternative();
        this.setup(instance, uri);
        buildFeatures(uri, instance);
        buildCausalities(uri, instance);
        return instance;
    }

    private void buildCausalities(String uri, EDMAlternative instance) {
        EDMCausalityBuilder causalityBuilder = new EDMCausalityBuilder();
        HashSet<EDMCausality> causalities = new HashSet<>();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-CAUSALITY").forEachRemaining(
                (String s) -> causalities.add(causalityBuilder.build(s)));
        instance.setCausalities(causalities);
    }

    private void buildFeatures(String uri, EDMAlternative instance) {
        EDMInstanceBuilder instanceBuilder = new EDMInstanceBuilder();
        EDMQuantifiableConsequenceBuilder qcBuilder = new EDMQuantifiableConsequenceBuilder();
        HashSet<EDMAbstractInstance> features = new HashSet<>();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-ALTERNATIVE-FEATURE").forEachRemaining((String s) -> {
            final String[] featureClassName = {""};
            OntoBridgeSingleton.getOntoBridge().listDeclaredBelongingClasses(s).forEachRemaining((String className) -> featureClassName[0] = className);
            if (OntoBridgeSingleton.getOntoBridge().isInstanceOf(s, "QUANTIFIABLE-CONSEQUENCE") || OntoBridgeSingleton.getOntoBridge().isSubClassOf(featureClassName[0], "QUANTIFIABLE-CONSEQUENCE"))
                features.add(qcBuilder.build(s));
            else
                features.add(instanceBuilder.build(s));
        });
        instance.setFeatures(features);
    }
}
