package cases;

import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import ontology.instance.EDMInstance;

public class EDMConsequence extends EDMInstance {

    private EDMInstance utility;

    public EDMConsequence(String uri) {
        this.fromString(uri);
    }

    @Override
    public void fromString(String uri){
        this.setUri(uri);
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-UTILITY").forEachRemaining(
                (String s) -> this.utility = new EDMInstance(s));
    }

    @Override
    public String toString() {
        return this.shortName + "(" + this.utility + ")";
    }
}
