package ontology.similarity;

import ontology.instance.EDMAbstractInstance;
import ontology.instance.EDMDictionaryInstance;
import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;

import java.util.*;

public class EDMDictionaryGreedy implements LocalSimilarityFunction {

    private LocalSimilarityFunction elementSimilarityFunction;

    public EDMDictionaryGreedy(LocalSimilarityFunction elementSimilarityFunction){
        this.elementSimilarityFunction = elementSimilarityFunction;
    }

    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
        if ((caseObject == null) || (queryObject == null))
            return 0;
        if (!(caseObject instanceof EDMDictionaryInstance))
            throw new NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if (!(queryObject instanceof EDMDictionaryInstance))
            throw new NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());

        Map<String,Set<EDMAbstractInstance>> es1 = ((EDMDictionaryInstance) caseObject).getValues();
        Map<String,Set<EDMAbstractInstance>> es2 = ((EDMDictionaryInstance) queryObject).getValues();

        if (es1.isEmpty() || es2.isEmpty())
            return (es1.isEmpty() && es2.isEmpty()) ? 1.0 : 0.0;

        if (es1.size() > es2.size()) {
            es1 = ((EDMDictionaryInstance) queryObject).getValues();
            es2 = ((EDMDictionaryInstance) caseObject).getValues();
        }

        Set<String> keys = es1.keySet();

        Double totalSim = 0.0;
        Double totalKeys = (double) Math.max(es1.keySet().size(), es2.keySet().size());

        for (String key : keys) {
            Double keySim = 0.0;
            Set<EDMAbstractInstance> v1 = es1.get(key);
            Set<EDMAbstractInstance> v2 = es2.get(key);
            if (v1.size() == 0 || v2.size() == 0) {
                totalSim += (v1.size() == 0 && v2.size() == 0) ? 1.0 : 0.0;
                continue;
            }
            if (v1.size() > v2.size()) {
                v1 = es2.get(key);
                v2 = es1.get(key);
            }
            Double setSize = (double) Math.max(v1.size(), v2.size());
            Set<EDMAbstractInstance> marked = new HashSet<>();
            for (EDMAbstractInstance i1 : v1) {
                Double maxSim = 0.0;
                EDMAbstractInstance mostSim = null;
                for (EDMAbstractInstance i2 : v2) {
                    if (marked.contains(i2)) continue;
                    Double sim = this.elementSimilarityFunction.compute(i1,i2);
                    if (mostSim == null || maxSim < sim) {
                        mostSim = i2;
                        maxSim = sim;
                    }
                }
                if (mostSim != null) {
                    marked.add(mostSim);
                }
                keySim += maxSim;
            }
            totalSim += keySim / setSize;
        }

//        System.out.println(es1.toString()+" "+es2.toString()+" "+(totalSim / totalKeys));

        return totalSim / totalKeys;
    }

    /** Applicable to EDMDictionaryInstance */
    public boolean isApplicable(Object o1, Object o2)
    {
        if ((o1 == null) && (o2 == null))
            return true;
        else if (o1 == null)
            return o2 instanceof EDMDictionaryInstance;
        else if (o2 == null)
            return o1 instanceof EDMDictionaryInstance;
        else
            return (o1 instanceof EDMDictionaryInstance) && (o2 instanceof EDMDictionaryInstance);
    }

}


