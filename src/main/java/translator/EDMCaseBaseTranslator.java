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
        HashMap<EDMCausality, HashSet<EDMAlternative>> causalities = new HashMap<>();
        getPossiblyDuplicatedInstances(cases, alternatives, betterThan, features, quantifiableConsequences, quantities, causalities);

//        HashMap<String,String> hasSuperclass = new HashMap<>();
//        getSuperclasses(hasSuperclass, "FEATURE");

        StringBuilder bkBuilder = new StringBuilder();
        StringBuilder modesBuilder = new StringBuilder();
        StringBuilder examplesBuilder = new StringBuilder();

        getBKTranslation(alternatives, features, quantifiableConsequences, quantities, causalities, bkBuilder);

//        getKBSuperclassesTranslation(hasSuperclass, bkBuilder);

        getExamplesTranslation(betterThan, examplesBuilder);

        getModesTranslation(features, quantifiableConsequences, causalities, modesBuilder, maxVars, maxBodyClauses, maxAmountClauses);

        translationBK = bkBuilder.toString();
        translationExamples = examplesBuilder.toString();
        translationModes = modesBuilder.toString();
    }

    private void getKBSuperclassesTranslation(HashMap<String, String> hasSuperclass, StringBuilder bkBuilder) {
        for (String subclass : hasSuperclass.keySet())
            bkBuilder.append(hasSuperclass.get(subclass) + "(A) :- " + subclass + "(A).\n");
    }

    private void getModesTranslation(HashSet<EDMAbstractInstance> features, HashSet<EDMAbstractInstance> quantifiableConsequences, HashMap<EDMCausality, HashSet<EDMAlternative>> causalities, StringBuilder modesBuilder, Integer maxVars, Integer maxBodyClauses, Integer maxAmountClauses) {
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

        for (EDMCausality causality : causalities.keySet()) {
            modesBuilder.append("modeb(" + getCausalityName(causality) + ", 1).\n");
            modesBuilder.append("type(" + getCausalityName(causality) + ", 0, element).\n\n");
            modesBuilder.append("modeb(not_" + getCausalityName(causality) + ", 1).\n");
            modesBuilder.append("type(not_" + getCausalityName(causality) + ", 0, element).\n\n");
        }
    }

    private void getBKTranslation(HashSet<EDMAlternative> alternatives, HashSet<EDMAbstractInstance> features, HashSet<EDMAbstractInstance> quantifiableConsequences, HashSet<EDMInstance> quantities, HashMap<EDMCausality, HashSet<EDMAlternative>> causalities, StringBuilder bkBuilder) {
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

        for (EDMCausality causality : causalities.keySet()) {
            String causalityName = getCausalityName(causality);
            for (EDMAlternative alternative : causalities.get(causality)) {
                bkBuilder.append(causalityName + "(" + alternative.getShortName() + ").\n");
            }
            bkBuilder.append("not_" + causalityName + "(A) :- alternative(A), not(" + causalityName + "(A)).\n");
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

    private String getCausalityName(EDMCausality causality) {
        return "causes_" + causality.getCause().getShortName() + "_" + causality.getConsequence().getShortName();
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
            subclass = OntoBridgeSingleton.getOntoBridge().getShortName(subclass);
            hasSuperclass.put(subclass, superclass);
            getSuperclasses(hasSuperclass, subclass);
        });
        OntoBridgeSingleton.getOntoBridge().listInstances(superclass).forEachRemaining((String instance) -> {
            instance = OntoBridgeSingleton.getOntoBridge().getShortName(instance);
            hasSuperclass.put(instance, superclass);
        });
    }

    private void getPossiblyDuplicatedInstances(Map<EDMCaseDescription, EDMCaseSolution> cases, HashSet<EDMAlternative> alternatives, HashMap<EDMInstance, HashSet<EDMAlternative>> betterThan, HashSet<EDMAbstractInstance> features, HashSet<EDMAbstractInstance> quantifiableConsequences, HashSet<EDMInstance> quantities, HashMap<EDMCausality, HashSet<EDMAlternative>> causalities) {
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
        for (EDMAlternative alternative : alternatives) {
            for (EDMCausality causality : alternative.getCausalities()) {
                causalities.putIfAbsent(causality, new HashSet<>());
                causalities.get(causality).add(alternative);
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
