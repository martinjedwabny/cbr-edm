package colibri.instance;

import es.ucm.fdi.gaia.jcolibri.exception.OntologyAccessException;

public class EDMInstance extends EDMAbstractInstance {

    public EDMInstance(){}

    public EDMInstance(String uri) throws OntologyAccessException{
        fromString(uri);
    }

    public void fromString(String uri) {
        this.setUri(uri);
//        if(!OntoBridgeSingleton.getOntoBridge().existsInstance(uri))
//            throw new OntologyAccessException("Instance: "+ uri +" not found in loaded ontologies. Check names or OntoBridge configuration.");
    }

    public String toString() {
        return this.shortName;
    }

    public boolean equals(Object other) {
        return (other instanceof EDMInstance) && this.shortName.equals(((EDMInstance) other).uri);
    }
}
