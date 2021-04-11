package translator;

import cases.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class EDMCaseBaseDirectTranslator {

    private String translationBK = "";
    private String translationModes = "";
    private String translationExamples = "";

    private Integer maxVars = 2;
    private Integer maxBodyClauses = 3;
    private Integer maxAmountClauses = 3;

    public void translate(Map<EDMCaseDescription, EDMCaseSolution> cases) {
        HashSet<EDMAlternative> alternatives = new HashSet<>();
        HashMap<EDMInstance, HashSet<EDMAlternative>> betterThan = new HashMap<>();
        HashSet<EDMAbstractInstance> features = new HashSet<>();
        getPossiblyDuplicatedInstances(cases, alternatives, betterThan, features);

        StringBuilder bkBuilder = new StringBuilder();
        StringBuilder modesBuilder = new StringBuilder();
        StringBuilder examplesBuilder = new StringBuilder();

        HashSet<String> addedPredicates = new HashSet<>();
        getBKTranslation(alternatives, features, addedPredicates, bkBuilder);

        getExamplesTranslation(betterThan, examplesBuilder);

        getModesTranslation(features, addedPredicates, modesBuilder);

        translationBK = bkBuilder.toString();
        translationExamples = examplesBuilder.toString();
        translationModes = modesBuilder.toString();
    }

    private void getModesTranslation(HashSet<EDMAbstractInstance> features, HashSet<String> addedPredicates, StringBuilder modesBuilder) {
        modesBuilder.append("max_vars(" + maxVars.toString() + ").\n");
        modesBuilder.append("max_body(" + maxBodyClauses.toString() + ").\n");
        modesBuilder.append("max_clauses(" + maxAmountClauses.toString() + ").\n\n");

        modesBuilder.append("modeh(better, 2).\n");
        modesBuilder.append("type(better, 0, element).\n");
        modesBuilder.append("type(better, 1, element).\n\n");

        for (EDMAbstractInstance instance : features) {
            if (addedPredicates.contains(instance.getShortName())) {
                modesBuilder.append("modeb(" + instance.getShortName() + ", 1).\n");
                modesBuilder.append("type(" + instance.getShortName() + ", 0, element).\n\n");
            }
            if (addedPredicates.contains("not_" + instance.getShortName())) {
                modesBuilder.append("modeb(not_" + instance.getShortName() + ", 1).\n");
                modesBuilder.append("type(not_" + instance.getShortName() + ", 0, element).\n\n");
            }
        }
    }

    private void getBKTranslation(HashSet<EDMAlternative> alternatives, HashSet<EDMAbstractInstance> features, HashSet<String> addedPredicates,StringBuilder bkBuilder) {

        // features

        for (EDMAbstractInstance feature : features) {
            for (EDMAlternative alternative : alternatives) {
                if (alternative.getFeatures().contains(feature)) {
                    bkBuilder.append(feature.getShortName() + "(" + alternative.getShortName() + ").\n");
                    addedPredicates.add(feature.getShortName());
                }
            }
        }

        bkBuilder.append("\n");

        // not features

        for (EDMAbstractInstance feature : features) {
            for (EDMAlternative alternative : alternatives) {
                if (!alternative.getFeatures().contains(feature)) {
                    bkBuilder.append("not_" + feature.getShortName() + "(" + alternative.getShortName() + ").\n");
                    addedPredicates.add("not_" + feature.getShortName());
                }
            }
        }
    }

    private void getExamplesTranslation(HashMap<EDMInstance, HashSet<EDMAlternative>> betterThan, StringBuilder examplesBuilder) {
        for (EDMInstance better : betterThan.keySet()) {
            for (EDMAlternative worse : betterThan.get(better)) {
                examplesBuilder.append("pos(better(" + better.getShortName() + ", " + worse.getShortName() + ")).\n");
                examplesBuilder.append("neg(better(" + worse.getShortName() + ", " + better.getShortName() + ")).\n");
            }
        }
    }

    private void getPossiblyDuplicatedInstances(Map<EDMCaseDescription, EDMCaseSolution> cases, HashSet<EDMAlternative> alternatives, HashMap<EDMInstance, HashSet<EDMAlternative>> betterThan, HashSet<EDMAbstractInstance> features) {
        for (EDMCaseDescription description : cases.keySet()) {
            EDMCaseSolution solution = cases.get(description);
            for (EDMAlternative alternative : description.getAlternatives()) {
                alternatives.add(alternative);
                if (solution.getAlternative()!= null && !alternative.getShortName().equals(solution.getAlternative().getShortName())) {
                    betterThan.putIfAbsent(solution.getAlternative(), new HashSet<>());
                    betterThan.get(solution.getAlternative()).add(alternative);
                }
                for (EDMAbstractInstance feature : alternative.getFeatures()) {
                    features.add(feature);
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

    @Override
    public String toString() {
        return "translationBK:\n" + translationBK + "\n" +
                "translationModes:\n" + translationModes + "\n" +
                "translationExamples:\n" + translationExamples + "\n";
    }
}

