package translator;

import cases.*;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class EDMCaseBaseTranslator {

    private String translationBK = "";
    private String translationModes = "";
    private String translationExamples = "";

    private Integer maxVars = 3;
    private Integer maxBodyClauses = 3;
    private Integer maxAmountClauses = 3;

    public void translate(Map<EDMCaseDescription, EDMCaseSolution> cases) {
        HashSet<EDMAlternative> alternatives = new HashSet<>();
        HashMap<EDMInstance, HashSet<EDMAlternative>> betterThan = new HashMap<>();
        HashSet<EDMAbstractInstance> features = new HashSet<>();
        HashSet<EDMAbstractInstance> quantifiableConsequences = new HashSet<>();
        HashSet<EDMInstance> quantities = new HashSet<>();
        getPossiblyDuplicatedInstances(cases, alternatives, betterThan, features, quantifiableConsequences, quantities);

        HashMap<String,String> hasSuperclass = new HashMap<>();
        getSuperclasses(hasSuperclass, "FEATURE");

        StringBuilder bkBuilder = new StringBuilder();
        StringBuilder modesBuilder = new StringBuilder();
        StringBuilder examplesBuilder = new StringBuilder();

        getBKTranslation(alternatives, features, quantifiableConsequences, quantities, bkBuilder);

        getExamplesTranslation(betterThan, examplesBuilder);

        getModesTranslation(features, quantifiableConsequences, modesBuilder, maxVars, maxBodyClauses, maxAmountClauses);

        translationBK = bkBuilder.toString();
        translationExamples = examplesBuilder.toString();
        translationModes = modesBuilder.toString();
    }

    private void getModesTranslation(HashSet<EDMAbstractInstance> features, HashSet<EDMAbstractInstance> quantifiableConsequences, StringBuilder modesBuilder, Integer maxVars, Integer maxBodyClauses, Integer maxAmountClauses) {
        modesBuilder.append("max_vars(" + maxVars.toString() + ").\n");
        modesBuilder.append("max_body(" + maxBodyClauses.toString() + ").\n");
        modesBuilder.append("max_clauses(" + maxAmountClauses.toString() + ").\n\n");

        modesBuilder.append("modeh(better, 2).\n");
        modesBuilder.append("type(better, 0, element).\n");
        modesBuilder.append("type(better, 1, element).\n\n");

        for (EDMAbstractInstance instance : features) {
            modesBuilder.append("modeb(" + instance.getShortName() + ", 1).\n");
            modesBuilder.append("type(" + instance.getShortName() + ", 0, element).\n\n");
            modesBuilder.append("modeb(not_" + instance.getShortName() + ", 1).\n");
            modesBuilder.append("type(not_" + instance.getShortName() + ", 0, element).\n\n");
        }

        for (EDMAbstractInstance instance : quantifiableConsequences) {
            modesBuilder.append("modeb(" + instance.getShortName() + "More, 2).\n");
            modesBuilder.append("type(" + instance.getShortName() + ", 0, element).\n");
            modesBuilder.append("type(" + instance.getShortName() + ", 1, element).\n\n");
        }
    }

    private void getBKTranslation(HashSet<EDMAlternative> alternatives, HashSet<EDMAbstractInstance> features, HashSet<EDMAbstractInstance> quantifiableConsequences, HashSet<EDMInstance> quantities, StringBuilder bkBuilder) {
        for (EDMAlternative alternative : alternatives) {
            bkBuilder.append(getAlternativeTranslation(alternative));
        }

        bkBuilder.append("\n");

        for (EDMInstance quantity : quantities) {
            bkBuilder.append(getQuantityString(quantity));
        }

        bkBuilder.append("\n");

        for (EDMAlternative alternative : alternatives) {
            for (EDMAbstractInstance instance : alternative.getFeatures()) {
                if (!(instance instanceof EDMQuantifiableConsequence)) {
                    bkBuilder.append(getHasFeatureTranslation(alternative, instance));
                }
            }
        }

        bkBuilder.append("\n");

        for (EDMAlternative alternative : alternatives) {
            for (EDMAbstractInstance instance : alternative.getFeatures()) {
                if ((instance instanceof EDMQuantifiableConsequence)) {
                    bkBuilder.append(getHasQuantifiableConsequenceTranslation(alternative, (EDMQuantifiableConsequence) instance));
                }
            }
        }

        bkBuilder.append("\n");

        for (EDMAbstractInstance instance : features) {
            bkBuilder.append(getFeatureTranslation(instance));
            bkBuilder.append(getFeatureNotTranslation(instance));
        }

        bkBuilder.append("\n");

        for (EDMAbstractInstance instance : quantifiableConsequences) {
            bkBuilder.append(getQuantifiableConsequenceTranslation((EDMInstance) instance));
            bkBuilder.append(getQuantifiableConsequenceNotTranslation((EDMInstance) instance));
            bkBuilder.append(getQuantifiableConsequenceComparisonTranslation((EDMInstance) instance));
        }

        bkBuilder.append("\n");

        bkBuilder.append("greater(QA,QB) :- integer(QA), integer(QB), QA > QB.\n");
    }

    private void getExamplesTranslation(HashMap<EDMInstance, HashSet<EDMAlternative>> betterThan, StringBuilder examplesBuilder) {
        for (EDMInstance better : betterThan.keySet()) {
            for (EDMAlternative worse : betterThan.get(better)) {
                examplesBuilder.append("pos(better(" + better.getShortName() + ", " + worse.getShortName() + ")).\n");
                examplesBuilder.append("neg(better(" + worse.getShortName() + ", " + better.getShortName() + ")).\n");
            }
        }
    }

    private String getQuantityString(EDMInstance quantity) {
        return "quantity(" + quantity.getShortName() + ").\n";
    }

    private String getAlternativeTranslation(EDMAlternative alternative) {
        return "alternative(" + alternative.getShortName() + ").\n";
    }

    private String getFeatureNotTranslation(EDMAbstractInstance instance) {
        return "not_" + instance.getShortName() + "(A) :- alternative(A), not(hasFeature(A, " + instance.getShortName() + ")).\n";
    }

    private String getFeatureTranslation(EDMAbstractInstance instance) {
        return instance.getShortName() + "(A) :- alternative(A), hasFeature(A, " + instance.getShortName() + ").\n";
    }

    private String getQuantifiableConsequenceNotTranslation(EDMInstance instance) {
        return instance.getShortName() + "(A, 0) :- alternative(A), not((quantity(Q), hasFeature(A, " + instance.getShortName() + ", Q))).\n";
    }

    private String getQuantifiableConsequenceTranslation(EDMInstance instance) {
        return instance.getShortName() + "(A, Q) :- alternative(A), quantity(Q), hasFeature(A, " + instance.getShortName() + ", Q).\n";
    }

    private String getQuantifiableConsequenceComparisonTranslation(EDMInstance instance) {
        return instance.getShortName() + "More(A, B) :- alternative(A), alternative(B), " + instance.getShortName() + "(A, QA), " + instance.getShortName() + "(B, QB), greater(QA, QB).\n";
    }

    private String getHasFeatureTranslation(EDMAlternative alternative, EDMAbstractInstance instance) {
        return "hasFeature(" + alternative.getShortName() + ", " + instance.getShortName() + ").\n";
    }

    private String getHasQuantifiableConsequenceTranslation(EDMAlternative alternative, EDMQuantifiableConsequence instance) {
        return "hasFeature(" + alternative.getShortName() + ", " + instance.getBaseConsequence().getShortName() + ", " + instance.getQuantity().getShortName() + ").\n";
    }

    private void getSuperclasses(HashMap<String, String> hasSuperclass, String superclass) {
        OntoBridgeSingleton.getOntoBridge().listSubClasses(superclass, true).forEachRemaining((String subclass) -> {
            hasSuperclass.put(subclass, superclass);
            getSuperclasses(hasSuperclass, subclass);
        });
    }

    private void getPossiblyDuplicatedInstances(Map<EDMCaseDescription, EDMCaseSolution> cases, HashSet<EDMAlternative> alternatives, HashMap<EDMInstance, HashSet<EDMAlternative>> betterThan, HashSet<EDMAbstractInstance> features, HashSet<EDMAbstractInstance> quantifiableConsequences, HashSet<EDMInstance> quantities) {
        for (EDMCaseDescription description : cases.keySet()) {
            EDMCaseSolution solution = cases.get(description);
            for (EDMAlternative alternative : description.getAlternatives()) {
                alternatives.add(alternative);
                if (solution.getAlternative()!= null && !alternative.getShortName().equals(solution.getAlternative().getShortName())) {
                    betterThan.putIfAbsent(solution.getAlternative(), new HashSet<>());
                    betterThan.get(solution.getAlternative()).add(alternative);
                }
                for (EDMAbstractInstance feature : alternative.getFeatures()) {
                    if (feature instanceof EDMQuantifiableConsequence) {
                        quantities.add(((EDMQuantifiableConsequence) feature).getQuantity());
                        quantifiableConsequences.add(((EDMQuantifiableConsequence) feature).getBaseConsequence());
                    } else {
                        features.add(feature);
                    }
                }
            }
        }
    }

    public String getTranslationBK() {
        return translationBK;
    }

    public String getTranslationModes() {
        return translationModes;
    }

    public String getTranslationExamples() {
        return translationExamples;
    }

}
