package colibri.similarity;

import colibri.instance.EDMInstance;
import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import es.ucm.fdi.gaia.ontobridge.OntoBridge;

public class EDMOntDeep implements LocalSimilarityFunction {

    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
        if ((caseObject == null) || (queryObject == null))
            return 0;
        if (!(caseObject instanceof EDMInstance))
            throw new NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if (!(queryObject instanceof EDMInstance))
            throw new NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());

        EDMInstance i1 = (EDMInstance) caseObject;
        EDMInstance i2 = (EDMInstance) queryObject;

        if (i1.equals(i2))
            return 1;

        OntoBridge ob = OntoBridgeSingleton.getOntoBridge();

        double up = ob.maxProfLCS(i1.toString(), i2.toString());
        double down;

        int prof1 = ob.profInstance(i1.toString());
        int prof2 = ob.profInstance(i2.toString());
        if (prof1 > prof2)
            down = prof1;
        else
            down = prof2;

        return up / down;
    }

    /** Applicable to EDMInstance */
    public boolean isApplicable(Object o1, Object o2)
    {
        if ((o1 == null) && (o2 == null))
            return true;
        else if (o1 == null)
            return o2 instanceof EDMInstance;
        else if (o2 == null)
            return o1 instanceof EDMInstance;
        else
            return (o1 instanceof EDMInstance) && (o2 instanceof EDMInstance);
    }

}
