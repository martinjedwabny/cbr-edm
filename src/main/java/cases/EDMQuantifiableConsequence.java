package cases;

import java.util.Objects;

public class EDMQuantifiableConsequence extends EDMInstance {

    private EDMInstance baseConsequence;

    private EDMInstance quantity;

    public EDMQuantifiableConsequence() {}

    public EDMInstance getQuantity() {
        return quantity;
    }

    public void setQuantity(EDMInstance quantity) {
        this.quantity = quantity;
    }

    public EDMInstance getBaseConsequence() {
        return baseConsequence;
    }

    public void setBaseConsequence(EDMInstance baseConsequence) {
        this.baseConsequence = baseConsequence;
    }

    @Override
    public String toString() {
        return this.getShortName() + "(" + this.quantity + ")";
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
        return Objects.hash(super.hashCode(), quantity);
    }
}
