import connector.EDMXMLFormatter;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;

public class EDMMain {
    public static void main(String[] args) {

//        String original = "src/main/resources/edm.owl";
//        String formatted = "src/main/resources/edm-formatted.owl";
//        EDMXMLFormatter.formatXMLNoAbbrev(original,formatted);

        try {
            /*
            1. Initialize CBR object
             */
            EDMCaseSolver cbr = new EDMCaseSolver();
            cbr.configure();
            cbr.preCycle();
            /*
            2. Create query with custom values
             */
//            EDMAlternative inaction = new EDMAlternative("inaction",
//                    Set.of( new EDMQuantifiableConsequence("save1", "SAVE"),
//                            new EDMQuantifiableConsequence("kill5", "KILL")),
//                    Set.of());
//            EDMAlternative action = new EDMAlternative("action",
//                    Set.of( new EDMQuantifiableConsequence("kill1", "KILL"),
//                            new EDMQuantifiableConsequence("save5", "SAVE"),
//                            new EDMInstance("personalForce","personalForce","ALTERNATIVE-FEATURE")),
//                    Set.of(new EDMCausality(
//                            new EDMQuantifiableConsequence("kill1", "KILL"),
//                            new EDMQuantifiableConsequence("save5", "SAVE"))));
//            EDMCaseDescription queryDesc = new EDMCaseDescription("query", Set.of(inaction,action));
//            CBRQuery query = new CBRQuery();
//            query.setDescription(queryDesc);
//            /*
//            3. Run query
//             */
//            cbr.cycle(query);
//            cbr.postCycle();
//            EDMCaseBaseDirectTranslator translator = new EDMCaseBaseDirectTranslator();
//            translator.translate(cbr.getCasesAndSolutions());
//            System.out.println(translator);
//            EDMILPSolver ilpSolver = new EDMILPSolver();
//            long time = System.nanoTime();
//            ilpSolver.solve(translator.getTranslationBK(), translator.getTranslationModes(), translator.getTranslationExamples());
//            double elapsedTimeInSecond = (double) (System.nanoTime() - time) / 1_000_000_000;
//            System.out.println("\nPopper result (" + String.format("%.2f", elapsedTimeInSecond) + " seconds):");
//            for (String s : ilpSolver.getResult())
//                System.out.println(s);
        } catch (ExecutionException var12) {
            System.out.println(var12.getMessage());
            var12.printStackTrace();
        } catch (Exception var13) {
            var13.printStackTrace();
        }

    }
}
