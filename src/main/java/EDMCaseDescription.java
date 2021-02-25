//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import colibri.instance.EDMSetInstance;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

public class EDMCaseDescription implements CaseComponent {

    private String id = "";

    private EDMSetInstance alternatives = new EDMSetInstance(EDMAlternative::new);

    public EDMCaseDescription() {}

    public String toString() {
        return "("
                + this.id + ";"
                + this.alternatives + ";"
                + ")";
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

    public EDMSetInstance getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(EDMSetInstance alternatives) {
        this.alternatives = alternatives;
    }
}

