package ontology.connector;

import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import cases.EDMCaseDescription;
import cases.EDMCaseSolution;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseBaseFilter;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Connector;
import es.ucm.fdi.gaia.jcolibri.connector.ontologyutils.OntologyInfo;
import es.ucm.fdi.gaia.jcolibri.exception.InitializingException;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;
import es.ucm.fdi.gaia.jcolibri.util.OntoBridgeSingleton;
import es.ucm.fdi.gaia.jcolibri.util.ProgressController;
import es.ucm.fdi.gaia.ontobridge.OntoBridge;
import es.ucm.fdi.gaia.ontobridge.OntologyDocument;

public class EDMOntologyConnector implements Connector {

    private boolean modified;
    private OntologyInfo mainOntologyInfo;
    private String caseMainConcept;

    private OntologyInfo getOntologyInfo(Node node)
    {
        OntologyInfo oi = new OntologyInfo();
        NodeList ontologyNodes = node.getChildNodes();
        for(int i=0; i<ontologyNodes.getLength(); i++)
        {
            Node n = ontologyNodes.item(i);
            if(n.getNodeName().equals("URL"))
                oi.setUrl(n.getTextContent());
            else if(n.getNodeName().equals("LocalCopy"))
                oi.setLocalCopy(n.getTextContent());
        }
        return oi;
    }

    /**
     * Initializes the connector from an XML config file.
     * This method reads the configuration and launches OntoBridge with the Pellet reasoner.
     * Then the ontologies are loaded into memory.
     *
     * @see Connector#initFromXMLfile(URL)
     */
    public void initFromXMLfile(URL file) throws InitializingException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file.openStream());

            /* Main Ontology Info */
            this.mainOntologyInfo = getOntologyInfo(doc.getElementsByTagName("MainOntology").item(0));

            /* Case Main Concept */
            this.caseMainConcept = doc.getElementsByTagName("CaseMainConcept").item(0).getTextContent();

            // Now let's initialize Ontobridge

            // Obtain a reference to OntoBridge
            OntoBridge ob = OntoBridgeSingleton.getOntoBridge();
            // Configure it to work with the Pellet reasoner
            ob.initWithOutReasoner();
            // Setup the main ontology
            OntologyDocument mainOnto = new OntologyDocument(this.mainOntologyInfo.getUrl(),
                    FileIO.findFile(this.mainOntologyInfo.getLocalCopy()).toExternalForm());

            // Load the ontology
            ob.loadOntology(mainOnto, List.of(), false);

            // Set modified to false
            this.modified = false;

        } catch (Exception e) {
            throw new InitializingException(e);
        }


    }


    /* (non-Javadoc)
     * @see jcolibri.cbrcore.Connector#retrieveAllCases()
     */
    public List<CBRCase> retrieveAllCases() {

        //Result list
        ArrayList<CBRCase> cases = new ArrayList<CBRCase>();

        //Obtain OntoBridge
        OntoBridge ob = OntoBridgeSingleton.getOntoBridge();

        ProgressController.init(this.getClass(), "Loading concepts", ProgressController.UNKNOWN_STEPS);

        //Obtain instances
        Iterator<String> caseInstances =  ob.listDeclaredInstances(this.caseMainConcept);
        while(caseInstances.hasNext())
        {
            String caseInstance = caseInstances.next();
            CBRCase _case = new CBRCase();

            try {
                //Map description
                CaseComponent description = retrieveCaseDescription(caseInstance);
                _case.setDescription(description);

                //Map solution
                CaseComponent cc = retrieveCaseSolution(caseInstance);
                _case.setSolution(cc);

                // If everything ok add the case to the list
                cases.add(_case);

            } catch (Exception e) {
                LogManager.getLogger(this.getClass()).error(e);
            }

            ProgressController.step(this.getClass());
        }
        ProgressController.finish(this.getClass());
        return cases;
    }

    private CaseComponent retrieveCaseDescription(String caseInstance) {
        return new EDMCaseDescription(caseInstance);
    }

    private CaseComponent retrieveCaseSolution(String caseInstance) {
        return new EDMCaseSolution(caseInstance);
    }

    /**
     * UnImplemented.
     * @see Connector#retrieveSomeCases(CaseBaseFilter)
     */
    public List<CBRCase> retrieveSomeCases(CaseBaseFilter filter) {
        LogManager.getLogger(this.getClass()).error("retrieveSomeCases(CaseBaseFilter) method is not yet implemented");
        return null;
    }

    /**
     * Stores cases into the ontology.
     * @see Connector#storeCases(Collection)
     */
    public void storeCases(Collection<CBRCase> cases) {

        if(cases.isEmpty())
            return;
        else
            modified = true;


        //Obtain OntoBridge
        OntoBridge ob = OntoBridgeSingleton.getOntoBridge();

        ProgressController.init(this.getClass(), "Storing concepts/cases", cases.size());
        for(CBRCase _case: cases)
        {
            try {
                if(!ob.existsInstance(_case.getID().toString(),this.caseMainConcept))
                    ob.createInstance(this.caseMainConcept, _case.getID().toString());
                createCaseComponent(_case.getDescription());
                createCaseComponent(_case.getSolution());
            } catch (Exception e) {
                LogManager.getLogger(this.getClass()).error("Error storing case: "+_case+". Cause: "+ e.getMessage());
            }
            ProgressController.step(this.getClass());
        }
        ProgressController.finish(this.getClass());
    }

    private void createCaseComponent(CaseComponent cc) throws Exception
    {
//        if(cc == null)
//            return;
//
//        OntoBridge ob = OntoBridgeSingleton.getOntoBridge();
//
//        String mainInstance = cc.getIdAttribute().getValue(cc).toString();
//
//        for(OntologyMapping om: maps)
//        {
//
//            Attribute at = new Attribute(om.getAttribute(), cc.getClass());
//            String instance = at.getValue(cc).toString();
//            if(!ob.existsInstance(instance,om.getConcept()))
//                ob.createInstance(om.getConcept(), instance);
//            ob.createOntProperty(mainInstance, om.getProperty(), instance);
//        }

    }

    public void createOntInstance(String instanceName, String instanceType) {
        OntoBridge ob = OntoBridgeSingleton.getOntoBridge();
        ob.createInstance(instanceType, instanceName);
    }

    /**
     * If there was any modification to the ontology, the owl file is replaced with a new one that contains the changes.
     * The new owl file is completely regenerated from scrach with the current content of the reasoner (not including the inferred model).
     * OntoBridge uses the RDF/XML-ABBREV syntax for the owl files.
     *
     * @see Connector#close()
     */
    public void close() {
        if(!modified)
            return;
        OntoBridge ob = OntoBridgeSingleton.getOntoBridge();
        try {
            ob.save(new FileWriter(FileIO.findFile(this.mainOntologyInfo.getLocalCopy()).getFile()));
        } catch (Exception e) {
            LogManager.getLogger(this.getClass()).error(e);
        }

    }

    /**
     * Deletes cases in the ontology. Only the main instance (case id mapped instance) is removed, so the instances mapped to attributes are keep.
     * @see Connector#deleteCases(Collection)
     */
    public void deleteCases(Collection<CBRCase> cases) {

        if(cases.isEmpty())
            return;
        else
            modified = true;

        OntoBridge ob = OntoBridgeSingleton.getOntoBridge();

        ProgressController.init(this.getClass(), "Deleting concepts/cases", cases.size());
        for(CBRCase _case: cases)
        {
            ob.delete(_case.getID().toString());
            ProgressController.step(this.getClass());
        }
        ProgressController.finish(this.getClass());

    }



    /**************************************************************/
    /*********** Public API for the connector configuration       */
    /**************************************************************/


    /**
     * @return Returns the caseMainConcept.
     */
    public String getCaseMainConcept() {
        return this.caseMainConcept;
    }

    /**
     * @return Returns the mainOntologyInfo.
     */
    public OntologyInfo getMainOntologyInfo() {
        return mainOntologyInfo;
    }

}
