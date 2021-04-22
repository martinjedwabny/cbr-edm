package builder;

import cases.EDMDutyFeature;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

public class EDMDutyFeatureBuilder extends EDMAbstractInstanceBuilder{

    public EDMDutyFeature build(String uri) {
        EDMDutyFeature instance = new EDMDutyFeature();
        this.setup(instance, uri);
        build(uri, instance);
        return instance;
    }

    private void build(String uri, EDMDutyFeature instance) {
        EDMInstanceBuilder instanceBuilder = new EDMInstanceBuilder();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-DUTY").forEachRemaining((String s) -> {
            instance.setDuty(instanceBuilder.build(s));
        });
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-DUTY-GRAVITY").forEachRemaining((String s) -> {
            instance.setGravity(instanceBuilder.build(s));
        });
    }
}
