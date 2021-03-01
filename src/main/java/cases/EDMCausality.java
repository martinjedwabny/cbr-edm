package cases;

import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import ontology.instance.EDMAbstractInstance;
import ontology.instance.EDMInstance;

import java.util.Iterator;
import java.util.Objects;

public class EDMCausality extends EDMAbstractInstance {
    private EDMInstance cause, consequence;

    public EDMCausality(EDMInstance cause, EDMInstance consequence) {
        this.cause = cause;
        this.consequence = consequence;
    }

    public EDMCausality(String uri) {
        this.fromString(uri);
    }

    public EDMInstance getCause() {
        return cause;
    }

    public void setCause(EDMInstance cause) {
        this.cause = cause;
    }

    public EDMInstance getConsequence() {
        return consequence;
    }

    public void setConsequence(EDMInstance consequence) {
        this.consequence = consequence;
    }

    @Override
    public String toString() {
        return "<"  + cause + ", " + consequence + ">";
    }

    @Override
    public void fromString(String uri) {
        Iterator<String> it1 = OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-CCAUSE");
        String c1 = it1.hasNext() ? it1.next() : "";
        Iterator<String> it2 = OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-CCONSEQUENCE");
        String c2 = it2.hasNext() ? it2.next() : "";
        this.cause = new EDMInstance(c1);
        this.consequence = new EDMInstance(c2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EDMCausality that = (EDMCausality) o;
        return cause.equals(that.cause) && consequence.equals(that.consequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cause, consequence);
    }
}
