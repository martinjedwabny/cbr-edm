package builder;

import cases.EDMAlternative;
import cases.EDMCaseDescription;
import cases.EDMInstance;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import es.ucm.fdi.gaia.ontobridge.OntoBridge;

import java.util.HashSet;
import java.util.Set;

public class EDMCaseDescriptionBuilder extends EDMAbstractInstanceBuilder{

    public EDMCaseDescription build(String uri) {
        EDMCaseDescription caseDescription = new EDMCaseDescription();
        this.setup(caseDescription, uri);
        buildAlternatives(uri, caseDescription);
        buildFeatures(uri, caseDescription);
        return caseDescription;
    }


    public EDMCaseDescription build(String caseName, Set<String> situationFeatures, Set<String> featuresAlternative1, Set<String> featuresAlternative2) {
        String a1Name = caseName+"-1";
        String a2Name = caseName+"-2";
        OntoBridge ob = OntoBridgeSingleton.getOntoBridge();
        if (ob.existsInstance(caseName, "EDM-CASE") ||
                ob.existsInstance(a1Name, "ALTERNATIVE") ||
                ob.existsInstance(a2Name, "ALTERNATIVE"))
            return null;
        ob.createInstance("EDM-CASE", caseName);
        ob.createInstance("ALTERNATIVE", a1Name);
        ob.createInstance("ALTERNATIVE", a2Name);
        ob.createOntProperty(caseName, "HAS-ALTERNATIVE", a1Name);
        ob.createOntProperty(caseName, "HAS-ALTERNATIVE", a2Name);
        for (String s : situationFeatures) {
            if (!ob.existsInstance(s)) {
                ob.createInstance("SITUATION-FEATURE", s);
            }
            ob.createOntProperty(caseName, "HAS-SITUATION-FEATURE", s);
        }
        for (String s : featuresAlternative1) {
            if (!ob.existsInstance(s)) {
                ob.createInstance("ALTERNATIVE-FEATURE", s);
            }
            ob.createOntProperty(a1Name, "HAS-ALTERNATIVE-FEATURE", s);
        }
        for (String s : featuresAlternative2) {
            if (!ob.existsInstance(s)) {
                ob.createInstance("ALTERNATIVE-FEATURE", s);
            }
            ob.createOntProperty(a2Name, "HAS-ALTERNATIVE-FEATURE", s);
        }
        String uri = ob.getURI(caseName);
        return build(uri);
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
