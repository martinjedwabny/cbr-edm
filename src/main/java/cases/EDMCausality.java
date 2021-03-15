package cases;

import ontology.instance.EDMAbstractInstance;

import java.util.Objects;

public class EDMCausality extends EDMAbstractInstance {
    private EDMInstance cause, consequence;

    public EDMCausality() {}

    public EDMCausality(EDMInstance cause, EDMInstance consequence) {
        this.cause = cause;
        this.consequence = consequence;
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
