package cases;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.HashSet;
import java.util.Set;

public class EDMCaseDescription implements CaseComponent {

    private String id = "";

    private Set<EDMAlternative> alternatives;

    public EDMCaseDescription(Set<EDMAlternative> alternatives) {
        this.alternatives = alternatives;
    }

    public EDMCaseDescription(String uri) {
        this.setId(uri);
        this.alternatives  = new HashSet<>();
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-ALTERNATIVE").
                forEachRemaining((String s) -> this.alternatives.add(new EDMAlternative(s)));
    }

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

    public Set<EDMAlternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(Set<EDMAlternative> alternatives) {
        this.alternatives = alternatives;
    }
}

