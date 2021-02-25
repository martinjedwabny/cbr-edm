import colibri.connector.EDMOntologyConnector;
import colibri.similarity.EDMKeySetGreedy;
import colibri.similarity.EDMOntDeep;
import colibri.similarity.EDMSetGreedy;
import es.ucm.fdi.gaia.jcolibri.casebase.LinealCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.*;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;

import java.util.Collection;

public class EDMCaseSolver implements StandardCBRApplication {

    EDMOntologyConnector _connector; // Object that reads XML files
    CBRCaseBase _caseBase; // Object that accesses CBR cases and provides indexing

    public EDMCaseSolver() {}

    // Initializes all class objects, searches for case base file
    public void configure() throws ExecutionException {
        try {
            this._connector = new EDMOntologyConnector();
            this._connector.initFromXMLfile(FileIO.findFile("main/resources/ontologyconfig.xml"));
            this._caseBase = new LinealCaseBase();
        } catch (Exception var2) {
            throw new ExecutionException(var2);
        }
    }

    // Loads cases into _caseBase object and prints them
    public CBRCaseBase preCycle() throws ExecutionException {
        this._caseBase.init(this._connector);

        for (CBRCase c : this._caseBase.getCases()) {
            System.out.println(c);
        }

        return this._caseBase;
    }

    public void cycle(CBRQuery query) throws ExecutionException {
        /*
        1. NNConfig 'simConfig' : nearest neighbors object, created to compare TravelDescription objects
        2. Stores :
            A. GlobalSimilarityFunction : how to combine sub-object similarities
            B. A dictionary 'Attribute' -> LocalSimilarityFunction (for simple attributes)
            C. A dictionary 'Attribute' -> GlobalSimilarityFunction (for compound attributes)
            D. A dictionary 'Attribute' -> Double (the weights)
         */
        NNConfig simConfig = new NNConfig();
        simConfig.setDescriptionSimFunction(new Average());
        /*
        3. An 'Attribute' stores a name/id and a class,
        4. 'addMapping' stores an 'Attribute -> LocalSimilarityFunction in the simConfig object
         */
        simConfig.addMapping(new Attribute("alternatives", EDMCaseDescription.class), new EDMSetGreedy(new EDMOntDeep()));

        /*
        5. Print query, retrieve 10 most similar cases and print them
         */
        System.out.println("Query:");
        System.out.println(query);
        System.out.println();
        /*
        6. 'eval' object collects the top 10 results using the 'simConfig' object
            obtained using NNScoringMethod.evaluateSimilarity function
         */
//        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(this._caseBase.getCases(), query, simConfig);
//        eval = SelectCases.selectTopKRR(eval, 10);
//        System.out.println("Retrieved cases:");
//        for (RetrievalResult nse : eval) {
//            System.out.println(nse);
//        }
    }

    public void postCycle() throws ExecutionException {
        this._caseBase.close();
    }
}
