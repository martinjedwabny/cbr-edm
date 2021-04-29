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
        modesBuilder.append("mode(hasFeature(+,c)).\n");
        modesBuilder.append("mode(hasDuty(+,c)).\n");
        modesBuilder.append("\n");

        modesBuilder.append("base(hasFeature(situation,feature)).\n");
        modesBuilder.append("base(hasDuty(alternative,duty)).\n");
        modesBuilder.append("base(more_stringent(alternative,alternative,situation)).\n");
        modesBuilder.append("\n");

        modesBuilder.append("option(negation, off).\n");
        modesBuilder.append("\n");

        modesBuilder.append("required(hasDuty).\n");
        modesBuilder.append("\n");

        modesBuilder.append("learn(more_stringent/3).\n");
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

        bkBuilder.append("\n");

        for (EDMAbstractInstance feature : this.situationFeatures) {
            bkBuilder.append("feature(" + formatShortName(feature.getShortName()) + ").\n");
            addedPredicates.add("feature");
        }

        bkBuilder.append("\n");

        for (EDMAbstractInstance duty : this.duties) {
            bkBuilder.append("duty(" + formatShortName(duty.getShortName()) + ").\n");
            addedPredicates.add("duty");
        }

        bkBuilder.append("\n");

        for (EDMCaseDescription cd : this.cases) {
            for (EDMAbstractInstance feature : cd.getSituationFeatures()) {
                bkBuilder.append("hasFeature(" + formatShortName(cd.getShortName()) + ", " + formatShortName(feature.getShortName()) + ").\n");
                addedPredicates.add("hasFeature");
            }
        }

        bkBuilder.append("\n");

        for (EDMCaseDescription cd : this.cases) {
            for (EDMAlternative alternative : cd.getAlternatives()) {
                bkBuilder.append("hasAlternative(" + formatShortName(cd.getShortName()) + ", " + formatShortName(alternative.getShortName()) + ").\n");
                addedPredicates.add("hasAlternative");
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
                            bkBuilder.append("hasDuty(" + formatShortName(a1.getShortName()) + ", " + formatShortName(duty.getShortName()) + ").\n");
                            addedPredicates.add("hasDuty");
                        }
                        if (gravityIsHigher(d2.getGravity(), d1.getGravity())) {
                            bkBuilder.append("hasDuty(" + formatShortName(a2.getShortName()) + ", " + formatShortName(duty.getShortName()) + ").\n");
                            addedPredicates.add("hasDuty");
                        }
                    }
                }
            }
        }

        bkBuilder.append("\n");
    }

    private boolean gravityIsHigher(EDMInstance gravity1, EDMInstance gravity2) {
        List<String> gravities = new ArrayList<String>(Arrays.asList("Extremely-bad","Really-bad","Bad","Neutral","Good","Really-good","Extremely-good"));
        return gravities.indexOf(gravity1.getShortName()) > gravities.indexOf(gravity2.getShortName());
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
                    examplesBuilder.append(p1.toString() + "::more_stringent(" + formatShortName(a1.getShortName()) + ", " + formatShortName(a2.getShortName()) + ", " + formatShortName(cd.getShortName()) + ").\n");
                    examplesBuilder.append(p2.toString() + "::more_stringent(" + formatShortName(a2.getShortName()) + ", " + formatShortName(a1.getShortName()) + ", " + formatShortName(cd.getShortName()) + ").\n");
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

