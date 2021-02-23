
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import colibri.instance.EDMInstance;

public class EDMCaseSolution implements CaseComponent {
    EDMInstance mainConcept = new EDMInstance();
    EDMInstance alternative = new EDMInstance();

    public EDMCaseSolution() {
    }

    public String toString() {
        return "(" + this.mainConcept + ";" + this.alternative + ")";
    }

    public Attribute getIdAttribute() {
        return new Attribute("mainConcept", this.getClass());
    }

    public EDMInstance getMainConcept() {
        return this.mainConcept;
    }

    public void setMainConcept(EDMInstance mc) {
        this.mainConcept = mc;
    }

    public EDMInstance getAlternative() {
        return this.alternative;
    }

    public void setAlternative(EDMInstance alternative) {
        this.alternative = alternative;
    }
}
