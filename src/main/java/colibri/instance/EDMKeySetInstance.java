package colibri.instance;

import es.ucm.fdi.gaia.jcolibri.exception.OntologyAccessException;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public class EDMKeySetInstance extends EDMAbstractInstance{

    private Set<String> keys;
    private Map<String,Map<String,EDMInstance>> values;

    public Set<String> getKeys() {
        return keys;
    }

    public void setKeys(Set<String> keys) {
        this.keys = keys;
    }

    public Map<String, Map<String, EDMInstance>> getValues() {
        return values;
    }

    public void setValues(Map<String, Map<String, EDMInstance>> values) {
        this.values = values;
    }

    public EDMKeySetInstance(Set<String> keys) {
        this.keys = keys;
        this.values = new HashMap<>();
        this.shortName = "";
        this.uri = "";
    }

    public void fromString(String uri) throws OntologyAccessException {
        String contentName = OntoBridgeSingleton.getOntoBridge().getShortName(uri);
        this.values.put(contentName, new HashMap<>());
        for (String property : this.keys) {
            for (Iterator<String> it2 = OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, property); it2.hasNext(); ) {
                String value = it2.next();
                this.values.get(contentName).put(property, new EDMInstance(value));
            }
        }
    }

    public String toString() {
        return this.values.toString();
    }

    public boolean equals(Object o) {
        return o.getClass().equals(this.getClass()) && this.values.values().equals(((EDMKeySetInstance) o).values.values());
    }
}
