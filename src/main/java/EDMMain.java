import cases.EDMAlternative;
import cases.EDMCaseDescription;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import org.mindswap.pellet.utils.Pair;

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
                    Set.of("save1","kill5"),
                    null,
                    null);
            EDMAlternative action = new EDMAlternative(
                    Set.of("kill1", "save5"),
                    Set.of("personalForce"),
                    Set.of( new Pair<>("kill1","save5")));
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
