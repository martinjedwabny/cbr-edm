package cases;

import ontology.instance.EDMAbstractInstance;

public class EDMInstance extends EDMAbstractInstance {
    public EDMInstance (){}
    public EDMInstance(String uri, String shortName, String className) {
        this.setUri(uri);
        this.setShortName(shortName);
        this.setClassName(className);
    }
}
