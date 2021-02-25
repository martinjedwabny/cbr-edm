package colibri.similarity;

import colibri.instance.EDMInstance;
import colibri.instance.EDMSetInstance;
import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EDMSetGreedy implements LocalSimilarityFunction {

    private LocalSimilarityFunction elementSimilarityFunction;

    public EDMSetGreedy(LocalSimilarityFunction elementSimilarityFunction){
        this.elementSimilarityFunction = elementSimilarityFunction;
    }

    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
//        if ((caseObject == null) || (queryObject == null))
            return 0;
//        if (!(caseObject instanceof EDMSetInstance))
//            throw new NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
//        if (!(queryObject instanceof EDMSetInstance))
//            throw new NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());
//
//        EDMSetInstance es1 = (EDMSetInstance) caseObject;
//        EDMSetInstance es2 = (EDMSetInstance) queryObject;
//
//        Set<EDMInstance> s1 = es1.getValues();
//        Set<EDMInstance> s2 = es2.getValues();
//
//        if (s1.size() == 0 || s2.size() == 0)
//            return 0.0;
//
//        if (s1.size() > s2.size()) {
//            s1 = s2;
//            s2 = es1.getValues();
//        }
//
//        Double sims = 0.0;
//        Double total = (double) s2.size();
//
//        HashMap<EDMInstance, Boolean> marked = new HashMap<>();
//        for (EDMInstance i : s2) marked.put(i,false);
//
//        for (EDMInstance i1 : s1) {
//            Double maxSim = 0.0;
//            EDMInstance mostSim = null;
//            for (EDMInstance i2 : s2) {
//                if (marked.get(i2)) continue;
//                Double sim = this.elementSimilarityFunction.compute(i1,i2);
//                if (sim > maxSim) {
//                    maxSim = sim;
//                    mostSim = i2;
//                }
//            }
//            if (mostSim != null) {
//                marked.put(mostSim,true);
//            }
//            sims += maxSim;
//        }
//
////        System.out.println(es1.toString()+" "+es2.toString()+" "+(sims / total));
//
//        return sims / total;
    }

    /** Applicable to EDMSetInstance */
    public boolean isApplicable(Object o1, Object o2)
    {
//        if ((o1 == null) && (o2 == null))
            return true;
//        else if (o1 == null)
//            return o2 instanceof EDMSetInstance;
//        else if (o2 == null)
//            return o1 instanceof EDMSetInstance;
//        else
//            return (o1 instanceof EDMSetInstance) && (o2 instanceof EDMSetInstance);
    }

}


