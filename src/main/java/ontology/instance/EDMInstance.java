package ontology.instance;

public class EDMInstance extends EDMAbstractInstance {

    public EDMInstance(){}

    public EDMInstance(String uri){
        fromString(uri);
    }

    public void fromString(String uri) {
        this.setUri(uri);
    }

    public String toString() {
        return this.shortName;
    }

    public boolean equals(Object other) {
        return (other instanceof EDMInstance) && this.shortName.equals(((EDMInstance) other).shortName);
    }
}
