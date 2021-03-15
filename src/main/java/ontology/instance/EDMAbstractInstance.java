package ontology.instance;

public abstract class EDMAbstractInstance {

    public String uri;
    public String shortName;
    public String className;

    public int hashCode(){
        return this.uri.hashCode();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String toString() {
        return this.getShortName();
    }

    public boolean equals(Object other) {
        return (other instanceof EDMAbstractInstance) && this.getShortName().equals(((EDMAbstractInstance) other).getShortName());
    }
}
