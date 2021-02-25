//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import colibri.instance.EDMDictionaryInstance;
import colibri.instance.EDMInstance;
import colibri.instance.EDMSetInstance;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EDMCaseDescription implements CaseComponent {

    private String id = "";
    private EDMSetInstance alternatives = new EDMSetInstance(this::createNewAlternative);

    private EDMDictionaryInstance createNewAlternative(String uri) {
        return new EDMDictionaryInstance(uri, Map.of(
                "HAS-CONSEQUENCE", (String uuri) -> new EDMInstance(uuri),
                "HAS-FEATURE", (String uuri) -> new EDMInstance(uuri),
                "HAS-CAUSALITY", (String uuri) -> new EDMDictionaryInstance(uuri, Map.of(
                        "HAS-CCAUSE", (String uuuri) -> new EDMInstance(uuuri),
                        "HAS-CCONSEQUENCE", (String uuuri) -> new EDMInstance(uuuri)
                ))));
    }

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

