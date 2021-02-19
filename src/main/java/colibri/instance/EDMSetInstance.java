package colibri.instance;

import es.ucm.fdi.gaia.jcolibri.exception.OntologyAccessException;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.Set;
import java.util.HashSet;

public class EDMSetInstance extends EDMAbstractInstance{

    private Set<EDMInstance> values;

    public void setValues(Set<EDMInstance> values) {
        this.values = values;
    }

    public Set<EDMInstance> getValues() {
        return this.values;
    }

    public EDMSetInstance() {
        this.values = new HashSet<>();
        this.shortName = "";
        this.uri = "";
    }

    public EDMSetInstance(Set<EDMInstance> values) {
        this.values = values;
        this.shortName = "";
        this.uri = "";
    }

    public void fromString(String uri) throws OntologyAccessException {
        String contentName = OntoBridgeSingleton.getOntoBridge().getShortName(uri);
        this.values.add(new EDMInstance(contentName));
    }

    public String toString() {
        return this.values.toString();
    }

    public boolean equals(Object o) {
        return o.getClass().equals(this.getClass()) && this.values.equals(((EDMSetInstance) o).values);
    }
}
