import cases.*;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import connector.EDMOntologyConnector;
import es.ucm.fdi.gaia.jcolibri.casebase.LinealCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.*;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;
import similarity.EDMSetGreedy;
import similarity.EDMAlternativeSimilarityFunction;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EDMCaseSolver implements StandardCBRApplication {

    private EDMOntologyConnector _connector; // Object that reads XML files
    private CBRCaseBase _caseBase; // Object that accesses CBR cases and provides indexing
    private List<EDMDutyMap> _dutyMappings;

    public EDMCaseSolver() {}

    // Initializes all class objects, searches for case base file
    public void configure() throws ExecutionException {
        try {
            this._connector = new EDMOntologyConnector();
            this._connector.initFromXMLfile(FileIO.findFile("main/resources/ontologyconfig.xml"));
            this._caseBase = new LinealCaseBase();
            this._dutyMappings = this._connector.retrieveDutyMaps();
        } catch (Exception var2) {
            throw new ExecutionException(var2);
        }
    }

    // Loads cases into _caseBase object and prints them
    public CBRCaseBase preCycle() throws ExecutionException {
        this._caseBase.init(this._connector);

        System.out.println("\nCase Base:");
        for (CBRCase c : this._caseBase.getCases()) {
            System.out.println(c);
        }

        return this._caseBase;
    }

    public void cycle(CBRQuery query) throws ExecutionException {
        loadNewObjectsToOntology(query);
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
                new EDMSetGreedy(new EDMAlternativeSimilarityFunction()));

        /*
        5. Print query, retrieve 10 most similar cases and print them
         */
        System.out.println("\nQuery:");
        System.out.println(query);
        System.out.println();
        /*
        6. 'eval' object collects the top 10 results using the 'simConfig' object
            obtained using NNScoringMethod.evaluateSimilarity function
         */
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(this._caseBase.getCases(), query, simConfig);
        eval = SelectCases.selectTopKRR(eval, 10);
        System.out.println("Retrieved cases:");
        for (RetrievalResult nse : eval) {
            System.out.println(nse);
        }
    }

    private void loadNewObjectsToOntology(CBRQuery query) {
        EDMCaseDescription caseDescription = (EDMCaseDescription) query.getDescription();
        for (EDMAlternative a : caseDescription.getAlternatives()) {
            for (EDMAbstractInstance f : a.getFeatures()) {
                if (!OntoBridgeSingleton.getOntoBridge().existsInstance(f.getShortName()))
                    OntoBridgeSingleton.getOntoBridge().createInstance(f.getClassName(), f.getShortName());
            }
        }
    }

    public void postCycle() throws ExecutionException {
        this._caseBase.close();
    }

    public Map<EDMCaseDescription, EDMCaseSolution> getCasesAndSolutions() {
        HashMap<EDMCaseDescription, EDMCaseSolution> map = new HashMap();
        this._caseBase.getCases().forEach((c) -> map.put((EDMCaseDescription)c.getDescription(), (EDMCaseSolution)c.getSolution()));
        return map;
    }

    public List<EDMDutyMap> getDutyMappings(){
        return this._dutyMappings;
    }
}
