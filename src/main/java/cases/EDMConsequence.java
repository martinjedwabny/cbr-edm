package cases;

import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import ontology.instance.EDMInstance;

import java.util.Objects;

public class EDMConsequence extends EDMInstance {

    private EDMInstance utility;

    public EDMConsequence(String shortName, String className, Integer utility) {
        this.setName(shortName);
        this.setClassName(className);
        this.utility = new EDMInstance(utility.toString());
    }

    public EDMConsequence(String uri) {
        this.fromString(uri);
    }

    @Override
    public void fromString(String uri){
        this.setName(uri);
        OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, "HAS-UTILITY").forEachRemaining(
                (String s) -> this.utility = new EDMInstance(s));
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
        EDMConsequence that = (EDMConsequence) o;
        return Objects.equals(utility, that.utility);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), utility);
    }
}
