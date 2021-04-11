package builder;

import cases.EDMDutyMap;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

public class EDMDutyMapBuilder extends EDMAbstractInstanceBuilder{

    public EDMDutyMap build(String uri) {
        EDMDutyMap instance = new EDMDutyMap();
        this.setup(instance, uri);
        build(uri, instance);
        return instance;
    }

    private void build(String uri, EDMDutyMap instance) {
        EDMInstanceBuilder instanceBuilder = new EDMInstanceBuilder();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-DUTY").forEachRemaining((String s) -> {
            instance.setDuty(instanceBuilder.build(s));
        });
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-DUTY-FEATURE").forEachRemaining((String s) -> {
            instance.setFeature(instanceBuilder.build(s));
        });
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri,"HAS-DUTY-GRAVITY").forEachRemaining((String s) -> {
            instance.setGravity(instanceBuilder.build(s));
        });
    }
}
