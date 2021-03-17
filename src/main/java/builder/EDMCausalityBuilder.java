package builder;

import cases.EDMCausality;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.Iterator;

public class EDMCausalityBuilder extends EDMAbstractInstanceBuilder {
    @Override
    public EDMCausality build(String uri) {
        EDMCausality causality = new EDMCausality();
        this.setup(causality, uri);
        buildCauseAndConsequence(uri, causality);
        return causality;
    }

    private void buildCauseAndConsequence(String uri, EDMCausality causality) {
        Iterator<String> it1 = OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-CAUSALITY-CAUSE");
        Iterator<String> it2 = OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-CAUSALITY-CONSEQUENCE");
        if (it1.hasNext() && it2.hasNext()) {
            String c1 = it1.next();
            String c2 = it2.next();
            EDMInstanceBuilder instanceBuilder = new EDMInstanceBuilder();
            EDMQuantifiableConsequenceBuilder qcBuilder = new EDMQuantifiableConsequenceBuilder();
            if (OntoBridgeSingleton.getOntoBridge().isInstanceOf(c1, "QUANTIFIABLE-CONSEQUENCE"))
                causality.setCause(qcBuilder.build(c1).getBaseConsequence());
            else
                causality.setCause(instanceBuilder.build(c1));
            if (OntoBridgeSingleton.getOntoBridge().isInstanceOf(c2, "QUANTIFIABLE-CONSEQUENCE"))
                causality.setConsequence(qcBuilder.build(c2).getBaseConsequence());
            else
                causality.setConsequence(instanceBuilder.build(c2));
        }
    }
}