package similarity;

import cases.EDMAbstractInstance;
import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;

import java.util.HashMap;
import java.util.Set;

public class EDMSetGreedy implements LocalSimilarityFunction {

    private LocalSimilarityFunction elementSimilarityFunction;

    public EDMSetGreedy(LocalSimilarityFunction elementSimilarityFunction){
        this.elementSimilarityFunction = elementSimilarityFunction;
    }

    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
        if ((caseObject == null) || (queryObject == null))
            return 0;
        if (!(caseObject instanceof Set))
            throw new NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if (!(queryObject instanceof Set))
            throw new NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());

        Set<EDMAbstractInstance> s1 = (Set<EDMAbstractInstance>)caseObject;
        Set<EDMAbstractInstance> s2 = (Set<EDMAbstractInstance>)queryObject;

        if (s1.size() == 0 && s2.size() == 0)
            return 1.0;

        if (s1.size() == 0 || s2.size() == 0)
            return 0.0;

        if (s1.size() > s2.size()) {
            s1 = (Set<EDMAbstractInstance>)queryObject;
            s2 = (Set<EDMAbstractInstance>)caseObject;
        }

        Double sims = 0.0;
        Double total = (double) s2.size();

        HashMap<EDMAbstractInstance, Boolean> marked = new HashMap<>();
        for (EDMAbstractInstance i : s2) marked.put(i,false);

        for (EDMAbstractInstance i1 : s1) {
            Double maxSim = 0.0;
            EDMAbstractInstance mostSim = null;
            for (EDMAbstractInstance i2 : s2) {
                if (marked.get(i2)) continue;
                Double sim = this.elementSimilarityFunction.compute(i1,i2);
                if (sim > maxSim) {
                    maxSim = sim;
                    mostSim = i2;
                }
            }
            if (mostSim != null) {
                marked.put(mostSim,true);
            }
            sims += maxSim;
        }

//        System.out.println(s1.toString()+" "+s2.toString()+" "+(sims / total));

        return sims / total;
    }

    /** Applicable to EDMSetInstance */
    public boolean isApplicable(Object o1, Object o2)
    {
        if ((o1 == null) && (o2 == null))
            return true;
        else if (o1 == null)
            return o2 instanceof Set;
        else if (o2 == null)
            return o1 instanceof Set;
        else
            return (o1.getClass().getName().equals(o2.getClass().getName())) &&
                    (o1 instanceof Set) &&
                    (o2 instanceof Set);
    }

}


