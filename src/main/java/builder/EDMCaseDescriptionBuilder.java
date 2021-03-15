package builder;

import cases.EDMAlternative;
import cases.EDMCaseDescription;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.HashSet;

public class EDMCaseDescriptionBuilder extends EDMAbstractInstanceBuilder{
    @Override
    public EDMCaseDescription build(String uri) {
        EDMCaseDescription caseDescription = new EDMCaseDescription();
        this.setup(caseDescription, uri);
        HashSet<EDMAlternative> alternatives  = new HashSet<>();
        EDMAlternativeBuilder alternativeBuilder = new EDMAlternativeBuilder();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-ALTERNATIVE").
                forEachRemaining((String s) -> alternatives.add(alternativeBuilder.build(s)));
        caseDescription.setAlternatives(alternatives);
        return caseDescription;
    }
}
