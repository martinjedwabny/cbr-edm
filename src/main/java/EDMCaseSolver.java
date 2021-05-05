import cases.*;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import connector.EDMOntologyConnector;
import es.ucm.fdi.gaia.jcolibri.casebase.LinealCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.*;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;
import similarity.EDMEditDistanceSimilarityFunction;
import similarity.EDMSetGreedySimilarityFunction;
import similarity.EDMAlternativeSimilarityFunction;

import java.util.*;
import java.util.stream.Collectors;

public class EDMCaseSolver implements StandardCBRApplication {

    private EDMOntologyConnector _connector; // Object that reads XML files
    private CBRCaseBase _caseBase; // Object that accesses CBR cases and provides indexing
    private List<CBRCase> _results;
    private Integer K;

    public EDMCaseSolver(Integer K) throws ExecutionException {
        this.K = K;
        this.configure();
        this.preCycle();
    }

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

//        System.out.println("\nLoaded "+this._caseBase.getCases().size()+" cases successfully.");
//        for (CBRCase c : this._caseBase.getCases()) {
//            System.out.println(c);
//        }

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
        simConfig.addMapping(new Attribute("alternatives", EDMCaseDescription.class),
                new EDMSetGreedySimilarityFunction(new EDMAlternativeSimilarityFunction()));

        simConfig.addMapping(new Attribute("situationFeatures", EDMCaseDescription.class),
                new EDMSetGreedySimilarityFunction(new EDMEditDistanceSimilarityFunction()));

        /*
        5. Print query, retrieve 10 most similar cases and print them
         */
//        System.out.println("\nQuery: "+query.getDescription());
//        System.out.println();
        /*
        6. 'eval' object collects the top 10 results using the 'simConfig' object
            obtained using NNScoringMethod.evaluateSimilarity function
         */
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(this._caseBase.getCases(), query, simConfig);
        eval = SelectCases.selectTopKRR(eval, this.K);
        _results = eval.stream().map((rr) -> rr.get_case()).collect(Collectors.toList());
    }

    public void postCycle() throws ExecutionException {
        this._caseBase.close();
    }

    public Set<EDMCaseDescription> getResults() {
        HashSet<EDMCaseDescription> s = new HashSet();
        this._results.forEach((c) -> s.add((EDMCaseDescription)c.getDescription()));
        return s;
    }
}
