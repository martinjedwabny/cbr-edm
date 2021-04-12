package translator;

import cases.*;

import java.util.*;

/* Things to consider:
1. feature(Action),
2. not_feature(Action),
3. moreBeneficence(Action1,Action2),
4. not_moreBeneficence(Action1,Action2).
 */

public class EDMCaseBaseDirectTranslator {

    private String translationBK = "";
    private String translationModes = "";
    private String translationExamples = "";

    private Integer maxVars = 2;
    private Integer maxBodyClauses = 3;
    private Integer maxAmountClauses = 3;

    public EDMCaseBaseDirectTranslator(Integer maxVars, Integer maxBodyClauses, Integer maxAmountClauses) {
        this.maxVars = maxVars;
        this.maxBodyClauses = maxBodyClauses;
        this.maxAmountClauses = maxAmountClauses;
    }

    private Set<Set<EDMAlternative>> alternatives = new HashSet<>();
    private HashMap<EDMInstance, HashSet<EDMAlternative>> betterThan = new HashMap<>();
    private HashSet<EDMAbstractInstance> duties = new HashSet<>();
    private HashMap<EDMAlternative, HashSet<EDMDutyMap>> alternativeToDuties = new HashMap<>();
    private HashSet<String> addedPredicates = new HashSet<>();

    public void translate(Map<EDMCaseDescription, EDMCaseSolution> cases, List<EDMDutyMap> dutyMappings) {
        getPossiblyDuplicatedInstances(cases, dutyMappings);

        StringBuilder bkBuilder = new StringBuilder();
        StringBuilder modesBuilder = new StringBuilder();
        StringBuilder examplesBuilder = new StringBuilder();

        getBKTranslation(bkBuilder);
        getExamplesTranslation(examplesBuilder);
        getModesTranslation(modesBuilder);

        translationBK = bkBuilder.toString();
        translationExamples = examplesBuilder.toString();
        translationModes = modesBuilder.toString();
    }

    private void getModesTranslation(StringBuilder modesBuilder) {
        modesBuilder.append("max_vars(" + maxVars.toString() + ").\n");
        modesBuilder.append("max_body(" + maxBodyClauses.toString() + ").\n");
        modesBuilder.append("max_clauses(" + maxAmountClauses.toString() + ").\n\n");

        modesBuilder.append("modeh(better, 2).\n");
        modesBuilder.append("type(better, 0, element).\n");
        modesBuilder.append("type(better, 1, element).\n\n");

        for (String s : addedPredicates) {
            modesBuilder.append("modeb(" + s + ", " + 2 + ").\n");
            modesBuilder.append("type(" + s + ", 0, element).\n");
            modesBuilder.append("type(" + s + ", 1, element).\n\n");
        }
    }

    private void getBKTranslation(StringBuilder bkBuilder) {

        // duties
        for (EDMAbstractInstance duty : duties) {
            for (Set<EDMAlternative> as : alternatives) {
                List<EDMAlternative> alternativeList = new ArrayList<>(as);
                for (int i = 0; i < alternativeList.size(); i++) {
                    EDMAlternative a1 = alternativeList.get(i);
                    if (alternativeToDuties.get(a1) == null) continue;
                    EDMDutyMap d1 = alternativeToDuties.get(a1).stream().filter((d) -> d.getDuty().equals(duty)).findFirst().orElse(null);
                    if (d1 == null) continue;
                    for (int j = i + 1; j < alternativeList.size(); j++) {
                        EDMAlternative a2 = alternativeList.get(j);
                        if (alternativeToDuties.get(a2) == null) continue;
                        EDMDutyMap d2 = alternativeToDuties.get(a2).stream().filter((d) -> d.getDuty().equals(duty)).findFirst().orElse(null);
                        if (d2 == null) continue;
                        if (gravityIsHigher(d1.getGravity(), d2.getGravity())) {
                            bkBuilder.append("better_respect_" + formatShortName(duty.getShortName()) + "(" + formatShortName(a1.getShortName()) + ", " + formatShortName(a2.getShortName()) + ").\n");
                            addedPredicates.add("better_respect_" + formatShortName(duty.getShortName()));
                        }
                        if (gravityIsHigher(d2.getGravity(), d1.getGravity())) {
                            bkBuilder.append("better_respect_" + formatShortName(duty.getShortName()) + "(" + formatShortName(a2.getShortName()) + ", " + formatShortName(a1.getShortName()) + ").\n");
                            addedPredicates.add("better_respect_" + formatShortName(duty.getShortName()));
                        }
                    }
                }
            }
//            bkBuilder.append("\n");
        }

        // not duties

        for (EDMAbstractInstance duty : duties) {
            for (Set<EDMAlternative> as : alternatives) {
                List<EDMAlternative> alternativeList = new ArrayList<>(as);
                for (int i = 0; i < alternativeList.size(); i++) {
                    EDMAlternative a1 = alternativeList.get(i);
                    if (alternativeToDuties.get(a1) == null) continue;
                    EDMDutyMap d1 = alternativeToDuties.get(a1).stream().filter((d) -> d.getDuty().equals(duty)).findFirst().orElse(null);
                    if (d1 == null) continue;
                    for (int j = i + 1; j < alternativeList.size(); j++) {
                        EDMAlternative a2 = alternativeList.get(j);
                        if (alternativeToDuties.get(a2) == null) continue;
                        EDMDutyMap d2 = alternativeToDuties.get(a2).stream().filter((d) -> d.getDuty().equals(duty)).findFirst().orElse(null);
                        if (d2 == null) continue;
                        if (!(gravityIsHigher(d1.getGravity(), d2.getGravity()) || gravityIsHigher(d2.getGravity(), d1.getGravity()))) {
                            bkBuilder.append("equal_" + formatShortName(duty.getShortName()) + "(" + formatShortName(a1.getShortName()) + ", " + formatShortName(a2.getShortName()) + ").\n");
                            bkBuilder.append("equal_" + formatShortName(duty.getShortName()) + "(" + formatShortName(a2.getShortName()) + ", " + formatShortName(a1.getShortName()) + ").\n");
                            addedPredicates.add("equal_" + formatShortName(duty.getShortName()));
                        }
                    }
                }
            }
//            bkBuilder.append("\n");
            }
    }

    private boolean gravityIsHigher(EDMInstance gravity1, EDMInstance gravity2) {
        List<String> gravities = new ArrayList<String>(Arrays.asList("Extremely-bad","Really-bad","Bad","Neutral","Good","Really-good","Extremely-good"));
        return gravities.indexOf(gravity1.getShortName()) > gravities.indexOf(gravity2.getShortName());
    }

    private void getExamplesTranslation(StringBuilder examplesBuilder) {
        for (EDMInstance better : betterThan.keySet()) {
            for (EDMAlternative worse : betterThan.get(better)) {
                examplesBuilder.append("pos(better(" + formatShortName(better.getShortName()) + ", " + formatShortName(worse.getShortName()) + ")).\n");
                examplesBuilder.append("neg(better(" + formatShortName(worse.getShortName()) + ", " + formatShortName(better.getShortName()) + ")).\n");
            }
        }
    }

    private String formatShortName(String shortName) {
        return shortName.replace('-','_').replace('.','_');
    }

    private void getPossiblyDuplicatedInstances(Map<EDMCaseDescription, EDMCaseSolution> cases, List<EDMDutyMap> dutyMappings) {
        HashMap<EDMInstance, EDMDutyMap> featureToDuty = new HashMap<>();
        for (EDMDutyMap dutyMap : dutyMappings)
            featureToDuty.put(dutyMap.getFeature(), dutyMap);
        for (EDMCaseDescription description : cases.keySet()) {
            EDMCaseSolution solution = cases.get(description);
            alternatives.add(description.getAlternatives());
            for (EDMAlternative alternative : description.getAlternatives()) {
                if (solution.getAlternative()!= null && !alternative.getShortName().equals(solution.getAlternative().getShortName())) {
                    betterThan.putIfAbsent(solution.getAlternative(), new HashSet<>());
                    betterThan.get(solution.getAlternative()).add(alternative);
                }
                for (EDMAbstractInstance feature : alternative.getFeatures()) {
                    if (featureToDuty.containsKey(feature)) {
                        duties.add(featureToDuty.get(feature).getDuty());
                        alternativeToDuties.putIfAbsent(alternative, new HashSet<>());
                        alternativeToDuties.get(alternative).add(featureToDuty.get(feature));
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

    @Override
    public String toString() {
        return "translationBK:\n" + translationBK + "\n" +
                "translationModes:\n" + translationModes + "\n" +
                "translationExamples:\n" + translationExamples + "\n";
    }
}

