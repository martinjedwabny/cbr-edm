package builder;

import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import cases.EDMAbstractInstance;

import java.util.Iterator;

public abstract class EDMAbstractInstanceBuilder {

    public abstract EDMAbstractInstance build(String uri);

    protected void setup(EDMAbstractInstance instance, String uri) {
        String shortName = OntoBridgeSingleton.getOntoBridge().getShortName(uri);
        String className = "";
        for (Iterator<String> it = OntoBridgeSingleton.getOntoBridge().listDeclaredBelongingClasses(uri); it.hasNext(); ) {
            className = it.next();
        }
        instance.setUri(uri);
        instance.setShortName(shortName);
        instance.setClassName(className);
    }
}
