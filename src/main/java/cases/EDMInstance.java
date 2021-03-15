package cases;

import ontology.instance.EDMAbstractInstance;

public class EDMInstance extends EDMAbstractInstance {
    public EDMInstance (){}
    public EDMInstance(String uri, String shortName, String className) {
        this.setUri(uri);
        this.setShortName(shortName);
        this.setClassName(className);
    }

    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        return (other instanceof EDMInstance) && this.getShortName().equals(((EDMInstance) other).getShortName());
    }
}
