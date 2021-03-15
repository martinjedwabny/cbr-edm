import cases.*;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import ontology.connector.EDMXMLFormatter;

import java.util.Set;

public class EDMMain {
    public static void main(String[] args) {

        String original = "src/main/resources/edm.owl";
        String formatted = "src/main/resources/edm-formatted.owl";
        EDMXMLFormatter.formatXMLNoAbbrev(original,formatted);


//        try {
//            /*
//            1. Initialize CBR object
//             */
//            EDMCaseSolver cbr = new EDMCaseSolver();
//            cbr.configure();
//            cbr.preCycle();
//            /*
//            2. Create query with custom values
//             */
//            EDMAlternative inaction = new EDMAlternative(
//                    Set.of( new EDMQuantifiableConsequence("save3", "SAVE", new EDMInstance("3","3","QUANTITY")),
//                            new EDMQuantifiableConsequence("kill5", "KILL")),
//                    Set.of());
//            EDMAlternative action = new EDMAlternative(
//                    Set.of( new EDMQuantifiableConsequence("kill1", "KILL"),
//                            new EDMQuantifiableConsequence("save5", "SAVE"),
//                            new EDMInstance("personalForce","personalForce","ALTERNATIVE-FEATURE")),
//                    Set.of(new EDMCausality(
//                            new EDMQuantifiableConsequence("kill1", "KILL"),
//                            new EDMQuantifiableConsequence("save5", "SAVE"))));
//            EDMCaseDescription queryDesc = new EDMCaseDescription(Set.of(inaction,action));
//            CBRQuery query = new CBRQuery();
//            query.setDescription(queryDesc);
//            /*
//            3. Run query
//             */
//            cbr.cycle(query);
//            cbr.postCycle();
//        } catch (ExecutionException var12) {
//            System.out.println(var12.getMessage());
//            var12.printStackTrace();
//        } catch (Exception var13) {
//            var13.printStackTrace();
//        }

    }
}
