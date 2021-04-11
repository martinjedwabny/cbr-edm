package cases;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;

import java.util.Set;

public class EDMCaseDescription extends EDMAbstractInstance implements CaseComponent {

    private Set<EDMAlternative> alternatives;

    private Set<EDMInstance> situationFeatures;

    public EDMCaseDescription() {}

    public EDMCaseDescription(String shortName, Set<EDMAlternative> alternatives, Set<EDMInstance> situationFeatures) {
        this.shortName = shortName;
        this.alternatives = alternatives;
        this.situationFeatures = situationFeatures;
    }

    public String toString() {
        return "(" + this.getShortName() + ":" + this.alternatives + ":" + this.situationFeatures + ")";
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

    public Set<EDMInstance> getSituationFeatures() {
        return situationFeatures;
    }

    public void setSituationFeatures(Set<EDMInstance> situationFeatures) {
        this.situationFeatures = situationFeatures;
    }
}

