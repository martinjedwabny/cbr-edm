package builder;

import cases.EDMInstance;

public class EDMInstanceBuilder extends EDMAbstractInstanceBuilder {

    public EDMInstance build(String uri) {
        EDMInstance instance = new EDMInstance();
        this.setup(instance, uri);
        return instance;
    }
}
