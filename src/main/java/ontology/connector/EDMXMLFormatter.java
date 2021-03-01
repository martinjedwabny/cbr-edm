package ontology.connector;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EDMXMLFormatter {
    public static void formatXMLNoAbbrev(String in, String out) {
        String fIn ="RDF/XML-ABBREV";
        String fOut ="RDF/XML";
        try {
            Model model = ModelFactory.createDefaultModel() ;
            model.read(new File(in).toURI().toURL().toString(), fIn) ;
            model.removeAll(
                    null,
                    RDF.type,
                    ResourceFactory.createResource("http://www.w3.org/2002/07/owl#NamedIndividual"));
            FileWriter writer = new FileWriter(out);
            model.write(writer, fOut) ;
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
