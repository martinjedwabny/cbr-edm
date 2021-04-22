package builder;

import cases.*;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.HashSet;

public class EDMAlternativeBuilder extends EDMAbstractInstanceBuilder{

    public EDMAlternative build(String uri) {
        EDMAlternative instance = new EDMAlternative();
        this.setup(instance, uri);
        buildFeatures(uri, instance);
        buildVotes(uri, instance);
        buildDuties(instance);
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

    private void buildVotes(String uri, EDMAlternative instance) {
        EDMInstanceBuilder instanceBuilder = new EDMInstanceBuilder();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-VOTES").forEachRemaining((String s) -> {
            instance.setVotes(instanceBuilder.build(s));
        });
    }

    private void buildDuties(EDMAlternative instance) {
        instance.setDuties(new HashSet<>());
        EDMInstanceBuilder instanceBuilder = new EDMInstanceBuilder();
        EDMDutyFeatureBuilder dutyBuilder = new EDMDutyFeatureBuilder();
        OntoBridgeSingleton.getOntoBridge().listDeclaredInstances("DUTY-MAPPING").forEachRemaining((String uuri) -> {
            OntoBridgeSingleton.getOntoBridge().listPropertyValue(uuri,"HAS-DUTY-FEATURE").forEachRemaining((String s) -> {
                if (instance.getFeatures().contains(instanceBuilder.build(s))) {
                    EDMDutyFeature duty = dutyBuilder.build(uuri);
                    if (duty != null && duty.getDuty() != null && duty.getGravity() != null)
                        instance.getDuties().add(duty);
                }
            });
        });
    }
}
