package translator;
import cases.*;

import java.util.*;

public class EDMCaseBaseProbFOILTranslator {

    private String translationBK = "";
    private String translationModes = "";
    private String translationExamples = "";

    public EDMCaseBaseProbFOILTranslator() {}

    private Set<Set<EDMAlternative>> alternatives = new HashSet<>();
    private HashMap<EDMAlternative, HashMap<EDMAlternative, Double>> alternativeProbability = new HashMap<>();
    private HashSet<EDMAbstractInstance> duties = new HashSet<>();
    private HashSet<String> addedPredicates = new HashSet<>();

    public void translate(Set<EDMCaseDescription> cases) {
        getPossiblyDuplicatedInstances(cases);

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

        for (String s : addedPredicates) {
            modesBuilder.append("mode(" + s + "(+,+)).\n");
        }

        modesBuilder.append("\n");

        for (String s : addedPredicates) {
            modesBuilder.append("base(" + s + "(alternative,alternative)).\n");
        }

        modesBuilder.append("base(more_ethical(alternative,alternative)).\n");

        modesBuilder.append("\n");

        modesBuilder.append("learn(more_ethical/2).\n");
    }

    private void getBKTranslation(StringBuilder bkBuilder) {

        for (Set<EDMAlternative> as : alternatives) {
            for (EDMAlternative alternative : as) {
                bkBuilder.append("alternative(" + formatShortName(alternative.getShortName()) + ").\n");
            }
        }

        bkBuilder.append("\n");

        // duties
        for (EDMAbstractInstance duty : duties) {
            for (Set<EDMAlternative> as : alternatives) {
                List<EDMAlternative> alternativeList = new ArrayList<>(as);
                for (int i = 0; i < alternativeList.size(); i++) {
                    EDMAlternative a1 = alternativeList.get(i);
                    EDMDutyFeature d1 = a1.getDuties().stream().filter((d) -> d.getDuty().equals(duty)).findFirst().orElse(null);
                    if (d1 == null) continue;
                    for (int j = i + 1; j < alternativeList.size(); j++) {
                        EDMAlternative a2 = alternativeList.get(j);
                        EDMDutyFeature d2 = a2.getDuties().stream().filter((d) -> d.getDuty().equals(duty)).findFirst().orElse(null);
                        if (d2 == null) continue;
                        if (gravityIsHigher(d1.getGravity(), d2.getGravity())) {
                            bkBuilder.append("geq_" + formatShortName(duty.getShortName()) + "(" + formatShortName(a1.getShortName()) + ", " + formatShortName(a2.getShortName()) + ").\n");
                            addedPredicates.add("geq_" + formatShortName(duty.getShortName()));
                        }
                        if (gravityIsHigher(d2.getGravity(), d1.getGravity())) {
                            bkBuilder.append("geq_" + formatShortName(duty.getShortName()) + "(" + formatShortName(a2.getShortName()) + ", " + formatShortName(a1.getShortName()) + ").\n");
                            addedPredicates.add("geq_" + formatShortName(duty.getShortName()));
                        }
                    }
                }
            }
        }

        bkBuilder.append("\n");

        // not duties

        for (EDMAbstractInstance duty : duties) {
            for (Set<EDMAlternative> as : alternatives) {
                List<EDMAlternative> alternativeList = new ArrayList<>(as);
                for (int i = 0; i < alternativeList.size(); i++) {
                    EDMAlternative a1 = alternativeList.get(i);
                    EDMDutyFeature d1 = a1.getDuties().stream().filter((d) -> d.getDuty().equals(duty)).findFirst().orElse(null);
                    if (d1 == null) continue;
                    for (int j = i + 1; j < alternativeList.size(); j++) {
                        EDMAlternative a2 = alternativeList.get(j);
                        EDMDutyFeature d2 = a2.getDuties().stream().filter((d) -> d.getDuty().equals(duty)).findFirst().orElse(null);
                        if (d2 == null) continue;
                        if (!(gravityIsHigher(d1.getGravity(), d2.getGravity()) || gravityIsHigher(d2.getGravity(), d1.getGravity()))) {
                            bkBuilder.append("eq_" + formatShortName(duty.getShortName()) + "(" + formatShortName(a1.getShortName()) + ", " + formatShortName(a2.getShortName()) + ").\n");
                            bkBuilder.append("eq_" + formatShortName(duty.getShortName()) + "(" + formatShortName(a2.getShortName()) + ", " + formatShortName(a1.getShortName()) + ").\n");
                            addedPredicates.add("eq_" + formatShortName(duty.getShortName()));
                        }
                    }
                }
            }
        }
    }

    private boolean gravityIsHigher(EDMInstance gravity1, EDMInstance gravity2) {
        List<String> gravities = new ArrayList<String>(Arrays.asList("Extremely-bad","Really-bad","Bad","Neutral","Good","Really-good","Extremely-good"));
        return gravities.indexOf(gravity1.getShortName()) > gravities.indexOf(gravity2.getShortName());
    }

    private void getExamplesTranslation(StringBuilder examplesBuilder) {
        for (Set<EDMAlternative> as : alternatives) {
            List<EDMAlternative> alternativeList = new ArrayList<>(as);
            for (int i = 0; i < alternativeList.size(); i++) {
                EDMAlternative a1 = alternativeList.get(i);
                for (int j = i + 1; j < alternativeList.size(); j++) {
                    EDMAlternative a2 = alternativeList.get(j);
                    Double p1 = alternativeProbability.get(a1).get(a2);
                    Double p2 = alternativeProbability.get(a2).get(a1);
                    examplesBuilder.append(p1.toString() + "::more_ethical(" + formatShortName(a1.getShortName()) + ", " + formatShortName(a2.getShortName()) + ").\n");
                    examplesBuilder.append(p2.toString() + "::more_ethical(" + formatShortName(a2.getShortName()) + ", " + formatShortName(a1.getShortName()) + ").\n");
                }
            }
        }
    }

    private String formatShortName(String shortName) {
        return shortName.replace('-','_').replace('.','_');
    }

    private void getPossiblyDuplicatedInstances(Set<EDMCaseDescription> cases) {
        for (EDMCaseDescription description : cases) {
            alternatives.add(description.getAlternatives());
            Double totalVotesSituation = 0.0;
            for (EDMAlternative alternative : description.getAlternatives())
                totalVotesSituation += Double.valueOf(alternative.getVotes().getShortName());
            for (EDMAlternative alternative : description.getAlternatives()) {
                Double votesAlternative = Double.valueOf(alternative.getVotes().getShortName());
                alternativeProbability.put(alternative, new HashMap<>());
                for (EDMAlternative other : description.getAlternatives()) {
                    Double votesOther = Double.valueOf(other.getVotes().getShortName());
                    Double p = (((votesAlternative - votesOther) / totalVotesSituation) + 1) / 2;
                    alternativeProbability.get(alternative).put(other, p);
                }
                for (EDMDutyFeature duty : alternative.getDuties()) {
                    duties.add(duty.getDuty());
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

