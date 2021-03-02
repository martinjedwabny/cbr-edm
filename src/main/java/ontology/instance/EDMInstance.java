package ontology.instance;

public class EDMInstance extends EDMAbstractInstance {

    public EDMInstance(){}

    public EDMInstance(String uri){
        fromString(uri);
    }

    public EDMInstance(String shortName, String className){
        this.setName(shortName);
        this.setClassName(className);
    }

    public void fromString(String uri) {
        this.setName(uri);
    }

    public String toString() {
        return this.getShortName();
    }

    public boolean equals(Object other) {
        return (other instanceof EDMInstance) && this.getShortName().equals(((EDMInstance) other).getShortName());
    }
}
