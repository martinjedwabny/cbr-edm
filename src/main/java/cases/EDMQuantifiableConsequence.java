package cases;

import java.util.Objects;

public class EDMQuantifiableConsequence extends EDMInstance {

    private EDMInstance utility;

    public EDMQuantifiableConsequence() {}

    public EDMQuantifiableConsequence(String shortName, String className, EDMInstance utility) {
        this.setShortName(shortName);
        this.setClassName(className);
        this.setUtility(utility);
    }

    public EDMQuantifiableConsequence(String shortName, String className) {
        this.setShortName(shortName);
        this.setClassName(className);
    }

    public EDMInstance getUtility() {
        return utility;
    }

    public void setUtility(EDMInstance utility) {
        this.utility = utility;
    }

    @Override
    public String toString() {
        return this.getShortName() + "(" + this.utility + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        return (o instanceof EDMQuantifiableConsequence) && this.getShortName().equals(((EDMQuantifiableConsequence) o).getShortName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), utility);
    }
}
