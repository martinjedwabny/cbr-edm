package cases;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import ontology.instance.EDMInstance;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

public class EDMCaseSolution implements CaseComponent {

    private String id = "";

    private EDMInstance alternative = new EDMInstance();

    public EDMCaseSolution() {}

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
