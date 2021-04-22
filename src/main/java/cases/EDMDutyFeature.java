package cases;

import java.util.Objects;

public class EDMDutyFeature extends EDMAbstractInstance{
    private EDMInstance duty, gravity;

    public EDMInstance getDuty() {
        return duty;
    }

    public void setDuty(EDMInstance duty) {
        this.duty = duty;
    }

    public EDMInstance getGravity() {
        return gravity;
    }

    public void setGravity(EDMInstance gravity) {
        this.gravity = gravity;
    }

    @Override
    public String toString() {
        return "(" + this.duty + ", " + this.gravity + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EDMDutyFeature that = (EDMDutyFeature) o;
        return duty.equals(that.duty) && gravity.equals(that.gravity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duty, gravity);
    }
}
