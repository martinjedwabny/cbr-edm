package builder;

import cases.EDMConsequence;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

public class EDMConsequenceBuilder extends EDMAbstractInstanceBuilder{
    @Override
    public EDMConsequence build(String uri) {
        EDMConsequence consequence = new EDMConsequence();
        this.setup(consequence, uri);
        EDMInstanceBuilder instanceBuilder = new EDMInstanceBuilder();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-UTILITY").forEachRemaining(
                (String s) -> consequence.setUtility(instanceBuilder.build(s)));
        return consequence;
    }
}
