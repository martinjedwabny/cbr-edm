package cases;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import ontology.instance.EDMInstance;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.Iterator;

public class EDMCaseSolution implements CaseComponent {

    private String id = "";

    private EDMInstance alternative = new EDMInstance();

    public EDMCaseSolution(String uri) {
        this.setId(uri);
        for (Iterator<String> it = OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-SOLUTION"); it.hasNext(); ) {
            this.alternative = new EDMInstance(it.next());
        }
    }

    public String toString() {
        return "(" + this.id + ";" + this.alternative + ")";
    }

    public Attribute getIdAttribute() {
        return new Attribute("id", this.getClass());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = OntoBridgeSingleton.getOntoBridge().getShortName(id);
    }

    public EDMInstance getAlternative() {
        return this.alternative;
    }

    public void setAlternative(EDMInstance alternative) {
        this.alternative = alternative;
    }
}
