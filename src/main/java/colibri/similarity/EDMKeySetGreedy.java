package colibri.similarity;

import colibri.instance.EDMInstance;
import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EDMKeySetGreedy implements LocalSimilarityFunction {

    private LocalSimilarityFunction elementSimilarityFunction;

    public EDMKeySetGreedy(LocalSimilarityFunction elementSimilarityFunction){
        this.elementSimilarityFunction = elementSimilarityFunction;
    }

    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
//        if ((caseObject == null) || (queryObject == null))
            return 0;
//        if (!(caseObject instanceof EDMKeySetInstance))
//            throw new NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
//        if (!(queryObject instanceof EDMKeySetInstance))
//            throw new NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());
//
//        EDMKeySetInstance es1 = (EDMKeySetInstance) caseObject;
//        EDMKeySetInstance es2 = (EDMKeySetInstance) queryObject;
//
//        Collection<Map<String, EDMInstance>> s1 = es1.getValues().values();
//        Collection<Map<String, EDMInstance>> s2 = es2.getValues().values();
//
//        Set<String> keys = es1.getKeys();
//
//        if (s1.size() == 0 || s2.size() == 0) return 0.0;
//
//        if (s1.size() > s2.size()) {
//            s1 = s2;
//            s2 = es1.getValues().values();
//        }
//
//        Double sims = 0.0;
//        Double total = (double) s2.size();
//
//        HashMap<Map<String, EDMInstance>, Boolean> marked = new HashMap<>();
//        for (Map<String, EDMInstance> i : s2) marked.put(i,false);
//
//        for (Map<String, EDMInstance> i1 : s1) {
//            Double maxSim = 0.0;
//            Map<String, EDMInstance> mostSim = null;
//            for (Map<String, EDMInstance> i2 : s2) {
//                if (marked.get(i2)) continue;
//                Double sim = 0.0;
//                Double subtotal = (double) keys.size();
//                for (String k : keys) {
//                    if (!i2.containsKey(k)) throw new NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());
////                    System.out.println(k+" "+i1.get(k)+" "+i2.get(k)+" "+this.elementSimilarityFunction.compute(i1.get(k),i2.get(k)));
//                    sim += this.elementSimilarityFunction.compute(i1.get(k),i2.get(k));
//                }
//                sim = sim / subtotal;
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
//        System.out.println(es1.toString()+" "+es2.toString()+" "+(sims / total));
//
//        return sims / total;
    }

    /** Applicable to EDMKeySetInstance */
    public boolean isApplicable(Object o1, Object o2)
    {
//        if ((o1 == null) && (o2 == null))
            return true;
//        else if (o1 == null)
//            return o2 instanceof EDMKeySetInstance;
//        else if (o2 == null)
//            return o1 instanceof EDMKeySetInstance;
//        else
//            return (o1 instanceof EDMKeySetInstance) && (o2 instanceof EDMKeySetInstance);
    }

}


