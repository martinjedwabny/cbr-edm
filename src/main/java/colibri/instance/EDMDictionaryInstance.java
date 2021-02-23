package colibri.instance;

import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public abstract class EDMDictionaryInstance extends EDMAbstractInstance{

    protected Set<String> keys;

    protected Map<String,EDMAbstractInstance> values;

    protected abstract void addProperty(String key, String value);

    public EDMDictionaryInstance(Set<String> keys) {
        this.keys = keys;
        this.values = new HashMap<>();
        this.shortName = "";
        this.uri = "";
    }

    public void fromString(String uri) {
        this.setUri(uri);
        for (String property : this.keys) {
            for (Iterator<String> it2 = OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, property); it2.hasNext(); ) {
                String value = it2.next();
                this.addProperty(property, value);
            }
        }
    }

    public String toString() {
        return this.values.toString();
    }

    public boolean equals(Object o) {
        return o.getClass().equals(this.getClass()) && this.values.values().equals(((EDMDictionaryInstance) o).values.values());
    }


    public Set<String> getKeys() {
        return keys;
    }

    public void setKeys(Set<String> keys) {
        this.keys = keys;
    }

    public Map<String, EDMAbstractInstance> getValues() {
        return values;
    }

    public void setValues(Map<String, EDMAbstractInstance> values) {
        this.values = values;
    }

}
