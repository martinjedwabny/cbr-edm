package cases;

import java.util.Objects;
import java.util.Set;

public class EDMAlternative extends EDMAbstractInstance {

    private Set<EDMAbstractInstance> features;
    private EDMInstance votes;
    private Set<EDMDutyFeature> duties;

    public EDMInstance getVotes() {
        return votes;
    }

    public void setVotes(EDMInstance votes) {
        this.votes = votes;
    }

    public EDMAlternative() {}

    public Set<EDMAbstractInstance> getFeatures() {
        return features;
    }

    public void setFeatures(Set<EDMAbstractInstance> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "(" + this.getShortName() + ":" + this.features + ":" + this.duties + ":" + this.votes + ")";
    }

    @Override
    public int hashCode() {
        if (this.getShortName() != null) return super.hashCode();
        return Objects.hash(features);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EDMAlternative that = (EDMAlternative) o;
        return Objects.equals(features, that.features);
    }

    public Set<EDMDutyFeature> getDuties() {
        return duties;
    }

    public void setDuties(Set<EDMDutyFeature> duties) {
        this.duties = duties;
    }
}
