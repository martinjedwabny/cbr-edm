package cases;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import ontology.instance.EDMAbstractInstance;

import java.util.Set;

public class EDMCaseDescription extends EDMAbstractInstance implements CaseComponent {

    private Set<EDMAlternative> alternatives;

    public EDMCaseDescription() {}

    public EDMCaseDescription(String shortName, Set<EDMAlternative> alternatives) {
        this.shortName = shortName;
        this.alternatives = alternatives;
    }

    public String toString() {
        return "(" + this.getShortName() + ":" + this.alternatives + ")";
    }

    public Attribute getIdAttribute() {
        return new Attribute("shortName", this.getClass());
    }

    public Set<EDMAlternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(Set<EDMAlternative> alternatives) {
        this.alternatives = alternatives;
    }
}

