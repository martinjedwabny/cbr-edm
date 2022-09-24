import builder.EDMCaseDescriptionBuilder;
import cases.EDMCaseDescription;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import ilp.EDMProbFOILSolver;
import translator.EDMCaseBaseProbFOILTranslator;

import java.util.Set;

public class EDMMain {

    final private static Integer K = 30;

    public static void main(String[] args) {

//        String original = "src/main/resources/edm.owl";
//        String formatted = "src/main/resources/edm-formatted.owl";
//        EDMXMLFormatter.formatXMLNoAbbrev(original,formatted);

        try {
            for (int K = 30; K <= 30; K++) {
                System.out.println("K="+ K);
                double e = 0;
                for (int i = 0; i < 5; i++) {
                    e += test(K);
                }
                e = e / 5.0;
//                System.out.println("\nResult (" + String.format("%.2f", e) + " seconds):");
            }
            test(60);
        } catch (ExecutionException var12) {
            System.out.println(var12.getMessage());
            var12.printStackTrace();
        } catch (Exception var13) {
            var13.printStackTrace();
        }

    }

    private static void testQuality(int K) throws Exception {
        EDMCaseSolver cbr = new EDMCaseSolver(K);
        CBRQuery query = getCbrQuery();
        cbr.cycle(query);
        EDMCaseBaseProbFOILTranslator translator = translateProbFOIL(cbr);
        runILPSolver(translator);
        cbr.postCycle();
    }

    private static double test(int K) throws Exception {
        long time = System.nanoTime();
        EDMCaseSolver cbr = new EDMCaseSolver(K);
        CBRQuery query = getCbrQuery();
        cbr.cycle(query);
        EDMCaseBaseProbFOILTranslator translator = translateProbFOIL(cbr);
        runILPSolver(translator);
        cbr.postCycle();
        double elapsedTimeInSecond = (double) (System.nanoTime() - time) / 1_000_000_000;
        return elapsedTimeInSecond;
    }

    private static void runILPSolver(EDMCaseBaseProbFOILTranslator translator) throws Exception {
        EDMProbFOILSolver ilpSolver = new EDMProbFOILSolver();
        ilpSolver.solve(translator.getTranslationBK(), translator.getTranslationModes(), translator.getTranslationExamples());
        for (String s : ilpSolver.getResult())
            System.out.println(s);
    }

    private static EDMCaseBaseProbFOILTranslator translateProbFOIL(EDMCaseSolver cbr) {
        EDMCaseBaseProbFOILTranslator translator = new EDMCaseBaseProbFOILTranslator();
        translator.translate(cbr.getResults());
//        System.out.println(K.toString()+" examples translated.");
//        System.out.println(translator);
        return translator;
    }

    private static void printMostSimilarCases(EDMCaseSolver cbr) {
        System.out.println(K + " most similar cases:");
        for(EDMCaseDescription r : cbr.getResults())
            System.out.println(r);
    }

    private static CBRQuery getCbrQuery() {
//        EDMAlternative a1 = new EDMAlternative();
//        a1.setFeatures(Set.of(new EDMInstance("prevent_harm()")));
//        EDMAlternative a2 = new EDMAlternative();
//        a2.setFeatures(Set.of(new EDMInstance("comply_law()")));
//        EDMCaseDescription queryDesc = new EDMCaseDescription("new-problem", Set.of(a1,a2), Set.of());
        EDMCaseDescription queryDesc = new EDMCaseDescriptionBuilder().build(
                "new-problem",
                Set.of("has_something_to_hide()"),
                Set.of("kill()"),
                Set.of("comply_law()"));
        CBRQuery query = new CBRQuery();
        query.setDescription(queryDesc);
        return query;
    }
}
