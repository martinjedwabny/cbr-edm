package similarity;

import cases.EDMAlternative;
import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import ontology.instance.EDMSetInstance;
import ontology.similarity.EDMOntDeep;
import ontology.similarity.EDMSetGreedy;

import java.util.Set;

public class EDMAlternativeSimilarityFunction implements LocalSimilarityFunction {

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

        Double consequenceSim = new EDMSetGreedy(new EDMOntDeep()).compute(new EDMSetInstance((Set)i1.getConsequences()), new EDMSetInstance((Set)i2.getConsequences()));
        Double featuresSim = new EDMSetGreedy(new EDMOntDeep()).compute(new EDMSetInstance((Set)i1.getFeatures()), new EDMSetInstance((Set)i2.getFeatures()));
        Double causalitiesSim = new EDMSetGreedy(new Equal()).compute(new EDMSetInstance((Set)i1.getCausalities()), new EDMSetInstance((Set)i2.getCausalities()));

        return (consequenceSim + featuresSim + causalitiesSim) / 3.0;
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
