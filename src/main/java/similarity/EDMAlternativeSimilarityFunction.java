package similarity;

import cases.EDMAlternative;
import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;

public class EDMAlternativeSimilarityFunction implements LocalSimilarityFunction {

    private Double featureWeight, causalityWeight;

    public EDMAlternativeSimilarityFunction(Double featureWeight, Double causalityWeight) {
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

        Double featuresSim = new EDMSetGreedy(new EDMOntDeep()).compute(i1.getFeatures(), i2.getFeatures());
        Double causalitiesSim = new EDMSetGreedy(new Equal()).compute(i1.getCausalities(), i2.getCausalities());

        return (featureWeight * featuresSim + causalityWeight * causalitiesSim) / (featureWeight + causalityWeight);
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
