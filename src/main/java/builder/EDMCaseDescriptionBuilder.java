package builder;

import cases.EDMAlternative;
import cases.EDMCaseDescription;
import cases.EDMInstance;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.HashSet;

public class EDMCaseDescriptionBuilder extends EDMAbstractInstanceBuilder{
    @Override
    public EDMCaseDescription build(String uri) {
        EDMCaseDescription caseDescription = new EDMCaseDescription();
        this.setup(caseDescription, uri);
        buildAlternatives(uri, caseDescription);
        buildFeatures(uri, caseDescription);
        return caseDescription;
    }

    private void buildAlternatives(String uri, EDMCaseDescription caseDescription) {
        HashSet<EDMAlternative> alternatives  = new HashSet<>();
        EDMAlternativeBuilder alternativeBuilder = new EDMAlternativeBuilder();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-ALTERNATIVE").
                forEachRemaining((String s) -> alternatives.add(alternativeBuilder.build(s)));
        caseDescription.setAlternatives(alternatives);
    }

    private void buildFeatures(String uri, EDMCaseDescription caseDescription) {
        HashSet<EDMInstance> features  = new HashSet<>();
        EDMInstanceBuilder featureBuilder = new EDMInstanceBuilder();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-SITUATION-FEATURE").
                forEachRemaining((String s) -> features.add(featureBuilder.build(s)));
        caseDescription.setSituationFeatures(features);
    }
}
