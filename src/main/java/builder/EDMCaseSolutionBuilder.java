package builder;

import cases.EDMCaseSolution;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.Iterator;

public class EDMCaseSolutionBuilder extends EDMAbstractInstanceBuilder{

    @Override
    public EDMCaseSolution build(String uri) {
        EDMCaseSolution caseSolution = new EDMCaseSolution();
        this.setup(caseSolution, uri);
        EDMInstanceBuilder instanceBuilder = new EDMInstanceBuilder();
        for (Iterator<String> it = OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-SOLUTION"); it.hasNext(); ) {
            caseSolution.setAlternative(instanceBuilder.build(it.next()));
        }
        return caseSolution;
    }
}
