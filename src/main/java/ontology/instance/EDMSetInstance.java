package ontology.instance;

import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;

public class EDMSetInstance extends EDMAbstractInstance{

    private Set<EDMAbstractInstance> values;

    private Function<String, EDMAbstractInstance> generateValue;

    public void setValues(Set<EDMAbstractInstance> values) {
        this.values = values;
    }

    public Set<EDMAbstractInstance> getValues() {
        return this.values;
    }

    public EDMSetInstance(Set<EDMAbstractInstance> values) {
        this.values = values;
        this.shortName = "";
        this.uri = "";
        this.generateValue = EDMInstance::new;
    }

    public EDMSetInstance(Function<String, EDMAbstractInstance> generateValue) {
        this.values = new HashSet<>();
        this.shortName = "";
        this.uri = "";
        this.generateValue = generateValue;
    }

    public void fromString(String uri) {
        this.values.add(this.generateValue.apply(uri));
    }

    public String toString() {
        return this.values.toString();
    }

    public boolean equals(Object o) {
        return o.getClass().equals(this.getClass()) && this.values.equals(((EDMSetInstance) o).values);
    }
}
