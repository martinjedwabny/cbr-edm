package builder;

import cases.EDMAlternative;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import cases.EDMAbstractInstance;

import java.util.HashSet;

public class EDMAlternativeBuilder extends EDMAbstractInstanceBuilder{

    public EDMAlternative build(String uri) {
        EDMAlternative instance = new EDMAlternative();
        this.setup(instance, uri);
        buildFeatures(uri, instance);
        return instance;
    }

    private void buildFeatures(String uri, EDMAlternative instance) {
        EDMInstanceBuilder instanceBuilder = new EDMInstanceBuilder();
        HashSet<EDMAbstractInstance> features = new HashSet<>();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-ALTERNATIVE-FEATURE").forEachRemaining((String s) -> {
            features.add(instanceBuilder.build(s));
        });
        instance.setFeatures(features);
    }
}
