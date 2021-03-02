package ontology.instance;

import es.ucm.fdi.gaia.jcolibri.connector.TypeAdaptor;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

public abstract class EDMAbstractInstance implements TypeAdaptor {

    private String uri;
    private String shortName;
    private String className;

    public int hashCode(){
        if (this.uri == null)
            this.uri = OntoBridgeSingleton.getOntoBridge().getURI(this.shortName);
        return this.uri.hashCode();
    }

    public String getUri() {
        return uri;
    }

    public void setName(String name) {
        if (name.contains("#")) {
            this.uri = name;
            this.shortName = OntoBridgeSingleton.getOntoBridge().getShortName(name);
        } else {
            this.uri = OntoBridgeSingleton.getOntoBridge().getURI(name);
            this.shortName = name;
        }
        OntoBridgeSingleton.getOntoBridge().listDeclaredBelongingClasses(uri).forEachRemaining((s) -> this.className = s);
    }

    public String getShortName() {
        return shortName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
