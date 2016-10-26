/**
 * 
 */
package eu.europeana.exp.esoundsalign;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static eu.europeana.ld.jena.JenaUtils.*;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 9 Sep 2015
 */
public class RunMI2SKOS
{

    public static final void main(String... args) throws TransformerException, IOException
    {
        File dir = new File("C:\\Users\\Hugo\\Google Drive\\Europeana\\Vocabularies\\CNRS\\InstrumentsMusique");

        File src = new File(dir, "Instrumentsmusique.xml");
        File dst = new File(dir, "Instrumentsmusique_skos.xml");
        File map = new File(dir, "im2skos.xsl");

        StreamSource xslt = new StreamSource(map);

        Transformer t;
        t = TransformerFactory.newInstance().newTransformer(xslt);
        t.transform(new StreamSource(src), new StreamResult(dst));

        store(load(dst), System.out, "RDF/XML");
    }
}
