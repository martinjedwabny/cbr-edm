//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import colibri.instance.EDMDictionaryInstance;
import colibri.instance.EDMInstance;
import colibri.instance.EDMKeySetInstance;
import colibri.instance.EDMSetInstance;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.fdi.gaia.jcolibri.exception.OntologyAccessException;
import gate.util.Pair;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EDMCaseDescription implements CaseComponent {

    private String id = "";
    private EDMDictionaryInstance alternatives = new EDMDictionaryInstance(new HashSet<>());

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
}

