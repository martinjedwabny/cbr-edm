package builder;

import cases.EDMQuantifiableConsequence;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

public class EDMQuantifiableConsequenceBuilder extends EDMAbstractInstanceBuilder{
    @Override
    public EDMQuantifiableConsequence build(String uri) {
        EDMQuantifiableConsequence consequence = new EDMQuantifiableConsequence();
        this.setup(consequence, uri);
        buildBaseConsequence(uri, consequence);
        buildUtility(uri, consequence);
        return consequence;
    }

    private void buildUtility(String uri, EDMQuantifiableConsequence consequence) {
        EDMInstanceBuilder instanceBuilder = new EDMInstanceBuilder();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-QUANTITY").forEachRemaining(
                (String s) -> consequence.setQuantity(instanceBuilder.build(s)));
    }

    private void buildBaseConsequence(String uri, EDMQuantifiableConsequence consequence) {
        EDMInstanceBuilder instanceBuilder = new EDMInstanceBuilder();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-BASE-CONSEQUENCE").forEachRemaining(
                (String s) -> consequence.setBaseConsequence(instanceBuilder.build(s)));
    }
}
