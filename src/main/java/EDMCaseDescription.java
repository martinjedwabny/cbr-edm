//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import colibri.instance.EDMInstance;
import colibri.instance.EDMKeySetInstance;
import colibri.instance.EDMSetInstance;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.fdi.gaia.jcolibri.exception.OntologyAccessException;
import gate.util.Pair;

import java.util.Map;
import java.util.Set;

public class EDMCaseDescription implements CaseComponent {

    private EDMInstance mainConcept = new EDMInstance();
    private EDMSetInstance hasConsequenceInaction = new EDMSetInstance();
    private EDMSetInstance hasConsequenceAction = new EDMSetInstance();
    private EDMSetInstance hasFeaturesAction = new EDMSetInstance();
    private EDMSetInstance hasFeaturesInaction = new EDMSetInstance();
    private EDMKeySetInstance hasCausalityAction = new EDMKeySetInstance(Set.of("HAS-CAUSE","HAS-CONSEQUENCE"));
    private EDMKeySetInstance hasCausalityInaction = new EDMKeySetInstance(Set.of("HAS-CAUSE","HAS-CONSEQUENCE"));

    public EDMCaseDescription() {}

    public EDMCaseDescription(Set<String> consequencesInaction, Set<String> consequencesAction,
                              Set<String> featuresInaction, Set<String> featuresAction,
                              Set<Pair> hasCausalityInaction, Set<Pair> hasCausalityAction) {
        try {
            if (consequencesInaction != null) for (String s : consequencesInaction) this.hasConsequenceInaction.fromString(s);
            if (consequencesAction != null) for (String s : consequencesAction) this.hasConsequenceAction.fromString(s);
            if (featuresInaction != null) for (String s : featuresInaction) this.hasFeaturesInaction.fromString(s);
            if (featuresAction != null) for (String s : featuresAction) this.hasFeaturesAction.fromString(s);
            if (hasCausalityInaction != null) for (Pair p : hasCausalityInaction)
                this.hasCausalityInaction.getValues().put("c"+this.hasCausalityInaction.getValues().size(), Map.of(
                        "HAS-CAUSE", new EDMInstance(p.first.toString()),
                        "HAS-CONSEQUENCE", new EDMInstance(p.second.toString())));
            if (hasCausalityAction != null) for (Pair p : hasCausalityAction)
                this.hasCausalityAction.getValues().put("c"+this.hasCausalityAction.getValues().size(), Map.of(
                        "HAS-CAUSE", new EDMInstance(p.first.toString()),
                        "HAS-CONSEQUENCE", new EDMInstance(p.second.toString())));
        } catch (OntologyAccessException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return "("
                + this.mainConcept + ";"
                + this.hasConsequenceInaction + ";"
                + this.hasConsequenceAction + ";"
                + this.hasFeaturesInaction + ";"
                + this.hasFeaturesAction + ";"
                + this.hasCausalityInaction + ";"
                + this.hasCausalityAction + ";"
                + ")";
    }

    public EDMInstance getMainConcept() {
        return mainConcept;
    }

    public void setMainConcept(EDMInstance mainConcept) {
        this.mainConcept = mainConcept;
    }

    public EDMSetInstance getHasConsequenceInaction() {
        return hasConsequenceInaction;
    }

    public void setHasConsequenceInaction(EDMSetInstance hasConsequenceInaction) {
        this.hasConsequenceInaction = hasConsequenceInaction;
    }

    public EDMSetInstance getHasConsequenceAction() {
        return hasConsequenceAction;
    }

    public void setHasConsequenceAction(EDMSetInstance hasConsequenceAction) {
        this.hasConsequenceAction = hasConsequenceAction;
    }

    public EDMSetInstance getHasFeaturesAction() {
        return hasFeaturesAction;
    }

    public void setHasFeaturesAction(EDMSetInstance hasFeaturesAction) {
        this.hasFeaturesAction = hasFeaturesAction;
    }

    public EDMSetInstance getHasFeaturesInaction() {
        return hasFeaturesInaction;
    }

    public void setHasFeaturesInaction(EDMSetInstance hasFeaturesInaction) {
        this.hasFeaturesInaction = hasFeaturesInaction;
    }

    public EDMKeySetInstance getHasCausalityAction() {
        return hasCausalityAction;
    }

    public void setHasCausalityAction(EDMKeySetInstance hasCausalityAction) {
        this.hasCausalityAction = hasCausalityAction;
    }

    public EDMKeySetInstance getHasCausalityInaction() {
        return hasCausalityInaction;
    }

    public void setHasCausalityInaction(EDMKeySetInstance hasCausalityInaction) {
        this.hasCausalityInaction = hasCausalityInaction;
    }

    public Attribute getIdAttribute() {
        return new Attribute("mainConcept", this.getClass());
    }
}
