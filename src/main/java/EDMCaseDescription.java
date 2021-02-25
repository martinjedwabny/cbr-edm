//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import colibri.instance.EDMAbstractInstance;
import colibri.instance.EDMSetInstance;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import org.mindswap.pellet.utils.Pair;

import java.util.Set;

public class EDMCaseDescription implements CaseComponent {

    private String id = "";

    private EDMSetInstance alternatives = new EDMSetInstance(EDMAlternative::new);

    public EDMCaseDescription() {}

    public EDMCaseDescription(Set<String> consequencesInaction, Set<String> consequencesAction,
                              Set<String> featuresInaction, Set<String> featuresAction,
                              Set<Pair> causalitiesInaction, Set<Pair> causalitiesAction) {
        this.alternatives = new EDMSetInstance(Set.of(
                new EDMAlternative(consequencesInaction, featuresInaction, causalitiesInaction),
                new EDMAlternative(consequencesAction, featuresAction, causalitiesAction)
        ));
    }

    public String toString() {
        return "("
                + this.id + ";"
                + this.alternatives + ";"
                + ")";
    }

    public Attribute getIdAttribute() {
        return new Attribute("id", this.getClass());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = OntoBridgeSingleton.getOntoBridge().getShortName(id);
    }

    public EDMSetInstance getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(EDMSetInstance alternatives) {
        this.alternatives = alternatives;
    }
}

