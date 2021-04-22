package similarity;

import cases.EDMAbstractInstance;
import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import es.ucm.fdi.gaia.ontobridge.OntoBridge;

public class EDMEditDistanceSimilarityFunction  implements LocalSimilarityFunction {

    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
        if ((caseObject == null) || (queryObject == null))
            return 0;
        if (!(caseObject instanceof EDMAbstractInstance))
            throw new NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if (!(queryObject instanceof EDMAbstractInstance))
            throw new NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());

        EDMAbstractInstance i1 = (EDMAbstractInstance) caseObject;
        EDMAbstractInstance i2 = (EDMAbstractInstance) queryObject;

        double editDistance = editDistance(i1.getShortName(), i2.getShortName());

        double editSim = editDistance == 0 ? 1 : 1 - editDistance / Math.max(i1.getShortName().length(), i2.getShortName().length());

        return editSim;
    }

    private int editDistance(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int [][]DP = new int[2][len1 + 1];
        for (int i = 0; i <= len1; i++)
            DP[0][i] = i;
        for (int i = 1; i <= len2; i++) {
            for (int j = 0; j <= len1; j++) {
                if (j == 0) {
                    DP[i % 2][j] = i;
                } else if (str1.charAt(j - 1) == str2.charAt(i - 1)) {
                    DP[i % 2][j] = DP[(i - 1) % 2][j - 1];
                } else {
                    DP[i % 2][j] = 1 + Math.min(DP[(i - 1) % 2][j],
                            Math.min(DP[i % 2][j - 1],
                                    DP[(i - 1) % 2][j - 1]));
                }
            }
        }
        return DP[len2 % 2][len1];
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
