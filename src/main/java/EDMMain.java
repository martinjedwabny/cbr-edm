import cases.EDMAlternative;
import cases.EDMCaseDescription;
import cases.EDMInstance;
import connector.EDMXMLFormatter;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import ilp.EDMILPSolver;
import translator.EDMCaseBaseDirectTranslator;

import java.util.Set;

public class EDMMain {

    private static Integer K = 1;
    private static Integer maxVars = 2;
    private static Integer maxBodyClauses = 3;
    private static Integer maxAmountClauses = 3;

    public static void main(String[] args) {

//        String original = "src/main/resources/edm.owl";
//        String formatted = "src/main/resources/edm-formatted.owl";
//        EDMXMLFormatter.formatXMLNoAbbrev(original,formatted);

        try {
            // 1. Initialize CBR object
            EDMCaseSolver cbr = new EDMCaseSolver(K);
            // 2. Create query with custom values
            CBRQuery query = getCbrQuery();
            // 3. Run query
            cbr.cycle(query);
            printMostSimilarCases(cbr);
            EDMCaseBaseDirectTranslator translator = translateSimilarCases(cbr);
            runILPSolver(translator);
            cbr.postCycle();
        } catch (ExecutionException var12) {
            System.out.println(var12.getMessage());
            var12.printStackTrace();
        } catch (Exception var13) {
            var13.printStackTrace();
        }

    }

    private static void runILPSolver(EDMCaseBaseDirectTranslator translator) throws Exception {
        EDMILPSolver ilpSolver = new EDMILPSolver();
        long time = System.nanoTime();
        ilpSolver.solve(translator.getTranslationBK(), translator.getTranslationModes(), translator.getTranslationExamples());
        double elapsedTimeInSecond = (double) (System.nanoTime() - time) / 1_000_000_000;
        System.out.println("\nPopper result (" + String.format("%.2f", elapsedTimeInSecond) + " seconds):");
        for (String s : ilpSolver.getResult())
            System.out.println(s);
    }

    private static EDMCaseBaseDirectTranslator translateSimilarCases(EDMCaseSolver cbr) {
        EDMCaseBaseDirectTranslator translator = new EDMCaseBaseDirectTranslator(maxVars, maxBodyClauses, maxAmountClauses);
        translator.translate(cbr.getResults(), cbr.getDutyMappings());
        System.out.println("\n"+"Translation finished.");
        System.out.println("\n"+"Duties:");
        System.out.println(translator.getTranslationBK());
        System.out.println("Past experiences:");
        System.out.println(translator.getTranslationExamples());
        return translator;
    }

    private static void printMostSimilarCases(EDMCaseSolver cbr) {
        System.out.println(K + " most similar cases:");
        for(EDMCaseDescription r : cbr.getResults().keySet())
            System.out.println(r);
    }

    private static CBRQuery getCbrQuery() {
        EDMAlternative a1 = new EDMAlternative();
        a1.setFeatures(Set.of(new EDMInstance("prevent_harm()")));
        EDMAlternative a2 = new EDMAlternative();
        a2.setFeatures(Set.of(new EDMInstance("prevent_harm()"), new EDMInstance("comply_law()")));
        EDMCaseDescription queryDesc = new EDMCaseDescription("new-problem", Set.of(a1,a2), Set.of());
        CBRQuery query = new CBRQuery();
        query.setDescription(queryDesc);
        return query;
    }
}
