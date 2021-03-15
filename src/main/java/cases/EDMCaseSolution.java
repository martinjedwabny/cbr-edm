package cases;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import ontology.instance.EDMAbstractInstance;

public class EDMCaseSolution extends EDMAbstractInstance implements CaseComponent {

    private EDMInstance alternative;

    public EDMCaseSolution() {}

    public String toString() {
        return "(" + this.shortName + ":" + this.alternative + ")";
    }

    public Attribute getIdAttribute() {
        return new Attribute("shortName", this.getClass());
    }

    public String getId() {
        return shortName;
    }

    public void setId(String id) {
        this.shortName = OntoBridgeSingleton.getOntoBridge().getShortName(id);
    }

    public EDMInstance getAlternative() {
        return this.alternative;
    }

    public void setAlternative(EDMInstance alternative) {
        this.alternative = alternative;
    }
}
