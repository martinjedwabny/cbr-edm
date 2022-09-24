package translator;
import cases.*;

import java.util.*;

public class EDMCaseBaseProbFOILTranslator {

    private String translationBK = "";
    private String translationModes = "";
    private String translationExamples = "";

    public EDMCaseBaseProbFOILTranslator() {}

    private Set<EDMCaseDescription> cases;
    private Set<Set<EDMAlternative>> alternatives = new HashSet<>();
    private HashMap<EDMAlternative, HashMap<EDMAlternative, Double>> alternativeProbability = new HashMap<>();
    private HashSet<EDMAbstractInstance> situationFeatures = new HashSet<>();
    private HashSet<EDMAbstractInstance> duties = new HashSet<>();
    private HashSet<EDMAbstractInstance> gravities = new HashSet<>();
    private HashSet<String> addedPredicates = new HashSet<>();

    public void translate(Set<EDMCaseDescription> cases) {
        this.cases = cases;
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
        modesBuilder.append("mode(hasDuty(+,c,c)).\n");
        modesBuilder.append("\n");

        modesBuilder.append("base(hasDuty(alternative,duty,gravity)).\n");
        modesBuilder.append("base(answer(situation,alternative)).\n");
        modesBuilder.append("\n");

        modesBuilder.append("option(negation, off).\n");
        modesBuilder.append("\n");

        modesBuilder.append("required(hasDuty).\n");
        modesBuilder.append("\n");

        modesBuilder.append("learn(answer/2).\n");
    }

    private void getBKTranslation(StringBuilder bkBuilder) {

        for (EDMCaseDescription cd : this.cases) {
            bkBuilder.append("situation(" + formatShortName(cd.getShortName()) + ").\n");
            addedPredicates.add("situation");
        }

        bkBuilder.append("\n");

        for (Set<EDMAlternative> as : alternatives) {
            for (EDMAlternative alternative : as) {
                bkBuilder.append("alternative(" + formatShortName(alternative.getShortName()) + ").\n");
                addedPredicates.add("alternative");
            }
        }

//        bkBuilder.append("\n");
//
//        for (EDMAbstractInstance feature : this.situationFeatures) {
//            bkBuilder.append("feature(" + formatShortName(feature.getShortName()) + ").\n");
//            addedPredicates.add("feature");
//        }

        bkBuilder.append("\n");

        for (EDMAbstractInstance duty : this.duties) {
            bkBuilder.append("duty(" + formatShortName(duty.getShortName()) + ").\n");
            addedPredicates.add("duty");
        }

        bkBuilder.append("\n");

        for (EDMAbstractInstance gravity : this.gravities) {
            bkBuilder.append("gravity(" + formatShortName(gravity.getShortName()) + ").\n");
            addedPredicates.add("gravity");
        }

//        bkBuilder.append("\n");
//
//        for (EDMCaseDescription cd : this.cases) {
//            for (EDMAbstractInstance feature : cd.getSituationFeatures()) {
//                bkBuilder.append("hasFeature(" + formatShortName(cd.getShortName()) + ", " + formatShortName(feature.getShortName()) + ").\n");
//                addedPredicates.add("hasFeature");
//            }
//        }

        bkBuilder.append("\n");

//        for (EDMCaseDescription cd : this.cases) {
//            for (EDMAlternative alternative : cd.getAlternatives()) {
//                bkBuilder.append("hasAlternative(" + formatShortName(cd.getShortName()) + ", " + formatShortName(alternative.getShortName()) + ").\n");
//                addedPredicates.add("hasAlternative");
//            }
//        }

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
                        translateDuty(bkBuilder, duty, a1, d1);
                        translateDuty(bkBuilder, duty, a2, d2);
                        addedPredicates.add("hasDuty");
                    }
                }
            }
        }

        bkBuilder.append("\n");
    }

    private void translateDuty(StringBuilder bkBuilder, EDMAbstractInstance duty, EDMAlternative a1, EDMDutyFeature d1) {
        List<String> gravities = new ArrayList<>();
        String g = formatShortName(d1.getGravity().getShortName());
        gravities.add(g);
        if (g.equals("\'extremely_good\'")) {
            gravities.add("\'really_good\'");
            gravities.add("\'good\'");
        } else if (g.equals("\'really_good\'")) {
            gravities.add("\'good\'");
        } else if (g.equals("\'extremely_bad\'")) {
            gravities.add("\'really_bad\'");
            gravities.add("\'bad\'");
        } else if (g.equals("\'really_bad\'")) {
            gravities.add("\'bad\'");
        }
        for (String gg : gravities) {
            bkBuilder.append("hasDuty(" + formatShortName(a1.getShortName()) + ", " + formatShortName(duty.getShortName()) + ", " + gg + ").\n");
        }
    }

    private void getExamplesTranslation(StringBuilder examplesBuilder) {
        for (EDMCaseDescription cd : this.cases) {
            List<EDMAlternative> alternativeList = new ArrayList<>(cd.getAlternatives());
            for (int i = 0; i < alternativeList.size(); i++) {
                EDMAlternative a1 = alternativeList.get(i);
                for (int j = i + 1; j < alternativeList.size(); j++) {
                    EDMAlternative a2 = alternativeList.get(j);
                    Double p1 = alternativeProbability.get(a1).get(a2);
                    Double p2 = alternativeProbability.get(a2).get(a1);
                    examplesBuilder.append(p1.toString() + "::answer(" + formatShortName(cd.getShortName()) + ", " + formatShortName(a1.getShortName()) + ").\n");
                    examplesBuilder.append(p2.toString() + "::answer(" + formatShortName(cd.getShortName()) + ", " + formatShortName(a2.getShortName()) + ").\n");
                }
            }
        }
    }

    private String formatShortName(String shortName) {
        return "\'" + shortName.replace('-','_').replace('.','_').toLowerCase() + "\'";
    }

    private void getPossiblyDuplicatedInstances(Set<EDMCaseDescription> cases) {
        for (EDMCaseDescription description : cases) {
            alternatives.add(description.getAlternatives());
            Double totalVotesSituation = 0.0;
            for (EDMInstance feature : description.getSituationFeatures())
                this.situationFeatures.add(feature);
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
                    gravities.add(duty.getGravity());
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

