import colibri.instance.EDMInstance;

public class EDMCausality {
    EDMInstance cause, consequence;

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
}
