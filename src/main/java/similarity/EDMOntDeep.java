package similarity;

import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import es.ucm.fdi.gaia.ontobridge.OntoBridge;
import cases.EDMAbstractInstance;

public class EDMOntDeep implements LocalSimilarityFunction {

    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
        if ((caseObject == null) || (queryObject == null))
            return 0;
        if (!(caseObject instanceof EDMAbstractInstance))
            throw new NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if (!(queryObject instanceof EDMAbstractInstance))
            throw new NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());

        EDMAbstractInstance i1 = (EDMAbstractInstance) caseObject;
        EDMAbstractInstance i2 = (EDMAbstractInstance) queryObject;

        if (i1.equals(i2))
            return 1;

        OntoBridge ob = OntoBridgeSingleton.getOntoBridge();

        double up = 0.0;
        try {
            up = ob.maxProfLCS(i1.getShortName(), i2.getShortName());
        } catch (NullPointerException e) {
            System.out.println("Null Pointer : maxProfLCS(" + i1.toString()+", "+i2.toString()+")");
        }
        double down;

        int prof1 = ob.profInstance(i1.getShortName());
        int prof2 = ob.profInstance(i2.getShortName());
        if (prof1 > prof2)
            down = prof1;
        else
            down = prof2;

//        System.out.println("maxProfLCS(" + i1.toString()+", "+i2.toString()+") = "+ up / down);

        return up / down;
    }

    /** Applicable to EDMAbstractInstance */
    public boolean isApplicable(Object o1, Object o2)
    {
        if ((o1 == null) && (o2 == null))
            return true;
        else if (o1 == null)
            return o2 instanceof EDMAbstractInstance;
        else if (o2 == null)
            return o1 instanceof EDMAbstractInstance;
        else
            return (o1 instanceof EDMAbstractInstance) && (o2 instanceof EDMAbstractInstance);
    }

}
