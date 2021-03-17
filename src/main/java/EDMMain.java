import cases.*;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import translator.EDMCaseBaseTranslator;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Set;

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
            EDMCaseBaseTranslator translator = new EDMCaseBaseTranslator();
            translator.translate(cbr.getCasesAndSolutions());
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("bk.pl"), "utf-8"))) {
                writer.write(translator.getTranslationBK());
            }
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("modes.pl"), "utf-8"))) {
                writer.write(translator.getTranslationModes());
            }
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("examples.pl"), "utf-8"))) {
                writer.write(translator.getTranslationExamples());
            }
        } catch (ExecutionException var12) {
            System.out.println(var12.getMessage());
            var12.printStackTrace();
        } catch (Exception var13) {
            var13.printStackTrace();
        }

    }
}
