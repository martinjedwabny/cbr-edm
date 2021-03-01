package similarity;

import cases.EDMAlternative;
import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import ontology.similarity.EDMOntDeep;
import ontology.similarity.EDMSetGreedy;

public class EDMAlternativeSimilarityFunction implements LocalSimilarityFunction {

    private Double consequenceWeight, featureWeight, causalityWeight;

    public EDMAlternativeSimilarityFunction(Double consequenceWeight, Double featureWeight, Double causalityWeight) {
        this.consequenceWeight = consequenceWeight;
        this.featureWeight = featureWeight;
        this.causalityWeight = causalityWeight;
    }

    @Override
    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
        if ((caseObject == null) || (queryObject == null))
            return 0;
        if (!(caseObject instanceof EDMAlternative))
            throw new NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if (!(queryObject instanceof EDMAlternative))
            throw new NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());

        EDMAlternative i1 = (EDMAlternative) caseObject;
        EDMAlternative i2 = (EDMAlternative) queryObject;

        Double consequenceSim = new EDMSetGreedy(new EDMOntDeep()).compute(i1.getConsequences(), i2.getConsequences());
        Double featuresSim = new EDMSetGreedy(new EDMOntDeep()).compute(i1.getFeatures(), i2.getFeatures());
        Double causalitiesSim = new EDMSetGreedy(new Equal()).compute(i1.getCausalities(), i2.getCausalities());

        return (consequenceWeight * consequenceSim + featureWeight * featuresSim + causalityWeight * causalitiesSim) / (consequenceWeight + featureWeight + causalityWeight);
    }

    @Override
    public boolean isApplicable(Object o1, Object o2) {
        if ((o1 == null) && (o2 == null))
            return true;
        else if (o1 == null)
            return o2 instanceof EDMAlternative;
        else if (o2 == null)
            return o1 instanceof EDMAlternative;
        else
            return (o1 instanceof EDMAlternative) && (o2 instanceof EDMAlternative);
    }
}
