package ontology.instance;

import es.ucm.fdi.gaia.jcolibri.connector.TypeAdaptor;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

public abstract class EDMAbstractInstance implements TypeAdaptor {

    protected String uri;
    protected String shortName;

    public int hashCode(){
        if (this.uri == null)
            this.uri = OntoBridgeSingleton.getOntoBridge().getURI(this.shortName);
        return this.uri.hashCode();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
        this.shortName = OntoBridgeSingleton.getOntoBridge().getShortName(uri);
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
        this.uri = OntoBridgeSingleton.getOntoBridge().getURI(shortName);
    }
}
