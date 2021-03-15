package builder;

import cases.EDMCausality;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.Iterator;

public class EDMCausalityBuilder extends EDMAbstractInstanceBuilder {
    @Override
    public EDMCausality build(String uri) {
        EDMCausality causality = new EDMCausality();
        this.setup(causality, uri);
        Iterator<String> it1 = OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-CCAUSE");
        String c1 = it1.hasNext() ? it1.next() : "";
        Iterator<String> it2 = OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-CCONSEQUENCE");
        String c2 = it2.hasNext() ? it2.next() : "";
        EDMInstanceBuilder instanceBuilder = new EDMInstanceBuilder();
        causality.setCause(instanceBuilder.build(c1));
        causality.setConsequence(instanceBuilder.build(c2));
        return causality;
    }
}