import cases.EDMAlternative;
import cases.EDMCaseDescription;
import cases.EDMCausality;
import cases.EDMConsequence;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import ontology.instance.EDMInstance;

import java.util.Map;
import java.util.Set;

public class EDMMain {
    public static void main(String[] args) {
        /*
        String original = "src/main/resources/edm.owl";
        String formatted = "src/main/resources/edm-formatted.owl";
        EDMXMLFormatter.formatXMLNoAbbrev(original,formatted);
        */

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
            EDMAlternative inaction = new EDMAlternative(
                    Set.of( new EDMConsequence("save3", "HIGH-GRAVITY", 3),
                            new EDMConsequence("kill5")),
                    Set.of(),
                    Set.of());
            EDMAlternative action = new EDMAlternative(
                    Set.of( new EDMConsequence("kill1"),
                            new EDMConsequence("save5")),
                    Set.of(new EDMInstance("personalForce")),
                    Set.of(new EDMCausality(new EDMConsequence("kill1"),new EDMConsequence("save5"))));
            EDMCaseDescription queryDesc = new EDMCaseDescription(Set.of(inaction,action));
            CBRQuery query = new CBRQuery();
            query.setDescription(queryDesc);
            /*
            3. Run query
             */
            cbr.cycle(query);
            cbr.postCycle();
        } catch (ExecutionException var12) {
            System.out.println(var12.getMessage());
            var12.printStackTrace();
        } catch (Exception var13) {
            var13.printStackTrace();
        }

    }
}
