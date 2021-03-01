package ontology.instance;

import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;

import java.util.*;
import java.util.function.Function;

public class EDMDictionaryInstance extends EDMAbstractInstance{

    protected Map<String,Set<EDMAbstractInstance>> values;

    private Map<String, Function<String, EDMAbstractInstance>> keyToGenerator;

    public EDMDictionaryInstance(String uri, Map<String, Function<String, EDMAbstractInstance>> keyToGenerator) {
        this.values = new HashMap<>();
        this.keyToGenerator = keyToGenerator;
        this.fromString(uri);
    }

    public void fromString(String uri) {
        this.setUri(uri);
        for (String property : this.keyToGenerator.keySet()) {
            for (Iterator<String> it2 = OntoBridgeSingleton.getOntoBridge().listPropertyValue(uri, property); it2.hasNext(); ) {
                String value = it2.next();
                if (!this.values.containsKey(property)) this.values.put(property, new HashSet<>());
                this.values.get(property).add(this.keyToGenerator.get(property).apply(value));
            }
        }
    }

    public String toString() {
        return this.shortName + "=" +this.values.toString();
    }

    public boolean equals(Object o) {
        return o.getClass().equals(this.getClass()) && this.values.values().equals(((EDMDictionaryInstance) o).values.values());
    }

    public Map<String, Function<String, EDMAbstractInstance>> getKeyToGenerator() {
        return keyToGenerator;
    }

    public void setKeyToGenerator(Map<String, Function<String, EDMAbstractInstance>> keyToGenerator) {
        this.keyToGenerator = keyToGenerator;
    }

    public Map<String, Set<EDMAbstractInstance>> getValues() {
        return values;
    }

    public void setValues(Map<String, Set<EDMAbstractInstance>> values) {
        this.values = values;
    }

}
