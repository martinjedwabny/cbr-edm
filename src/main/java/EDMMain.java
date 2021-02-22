import colibri.instance.EDMInstance;
import colibri.similarity.EDMOntDeep;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import gate.util.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
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
            EDMCaseDescription queryDesc = new EDMCaseDescription(
                    Set.of("save1","kill5"),
                    Set.of("kill1", "save5"),
                    null,
                    Set.of("personalForce"),
                    null,
                    Set.of( new Pair("kill1","save5")));
            CBRQuery query = new CBRQuery();
            query.setDescription(queryDesc);
            /*
            3. Run query
             */
            cbr.cycle(query);
            cbr.postCycle();
//            System.out.println("Sim: "+new EDMOntDeep().compute(new EDMInstance("http://gaia.fdi.ucm.es/ontologies/vacation.owl#infectMany"), new EDMInstance("http://gaia.fdi.ucm.es/ontologies/vacation.owl#kill1")));
//            System.out.println("Sim: "+new EDMOntDeep().compute(new EDMInstance("http://gaia.fdi.ucm.es/ontologies/vacation.owl#kill5"), new EDMInstance("http://gaia.fdi.ucm.es/ontologies/vacation.owl#kill1")));
//            System.out.println("Sim: "+new EDMOntDeep().compute(new EDMInstance("http://gaia.fdi.ucm.es/ontologies/vacation.owl#kill1"), new EDMInstance("http://gaia.fdi.ucm.es/ontologies/vacation.owl#kill1")));
        } catch (ExecutionException var12) {
            System.out.println(var12.getMessage());
            var12.printStackTrace();
        } catch (Exception var13) {
            var13.printStackTrace();
        }

    }
}
