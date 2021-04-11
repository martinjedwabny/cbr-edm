package cases;

import java.util.Objects;

public class EDMDutyMap extends EDMAbstractInstance{
    private EDMInstance feature, duty, gravity;

    public EDMInstance getFeature() {
        return feature;
    }

    public void setFeature(EDMInstance feature) {
        this.feature = feature;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EDMDutyMap that = (EDMDutyMap) o;
        return feature.equals(that.feature) && duty.equals(that.duty) && gravity.equals(that.gravity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feature, duty, gravity);
    }
}
