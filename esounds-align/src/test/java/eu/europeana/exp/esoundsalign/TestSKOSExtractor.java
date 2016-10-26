/**
 * 
 */
package eu.europeana.exp.esoundsalign;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.SKOS;

import eu.europeana.ld.edm.analysis.CHOAnalysis;
import eu.europeana.ld.skos.SKOSAnalysis;
import eu.europeana.ld.skos.RDF2SKOSExtractor;
import static eu.europeana.ld.jena.JenaUtils.*;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 10 Sep 2015
 */
public class TestSKOSExtractor
{
    private static File DIR
        = new File("C:\\Users\\Hugo\\Google Drive\\Europeana\\Vocabularies");

    public static final void main(String... args) throws IOException
    {
        runCNRS_MMSH();
    }

    private static void runNISV() throws IOException
    {
        File dir  = new File(DIR, "NISV");
        File rpt1 = new File(dir, "nisv.txt");
        File dst  = new File(dir, "nisv.concepts.rdf");
        File rpt2 = new File(dir, "nisv.concepts.txt");
        run(new File(dir, "items"), rpt1, dst);
        new SKOSAnalysis().analyse(dst).print(rpt2);
    }

    private static void runCNRS_CREM() throws IOException
    {
        File dir = new File(DIR, "CNRS\\CREM");
        File rpt1 = new File(dir, "cnrs.txt");
        File dst  = new File(dir, "cnrs.concepts.rdf");
        File rpt2 = new File(dir, "cnrs.concepts.txt");
        run(new File(dir, "items"), rpt1, dst);
        new SKOSAnalysis().analyse(dst).print(rpt2);
    }

    private static void runCNRS_MMSH() throws IOException
    {
        File dir = new File(DIR, "CNRS\\MMSH");
        File rpt1 = new File(dir, "cnrs.mmsh.txt");
        File dst  = new File(dir, "cnrs.mmsh.concepts.rdf");
        File rpt2 = new File(dir, "cnrs.mmsh.concepts.txt");
        run(new File(dir, "items"), rpt1, dst);
        new SKOSAnalysis().analyse(dst).print(rpt2);
    }

    private static void runBL_Keith() throws IOException
    {
        File dir  = new File(DIR, "BL\\keith");
        File rpt1 = new File(dir, "bl.keith.txt");
        File dst  = new File(dir, "bl.keith.concepts.rdf");
        File rpt2 = new File(dir, "bl.keith.concepts.txt");
        run(new File(dir, "items"), rpt1, dst);
        new SKOSAnalysis().analyse(dst).print(rpt2);
    }

    private static void runBL_Cook() throws IOException
    {
        File dir  = new File(DIR, "BL\\cook");
        File rpt1 = new File(dir, "bl.cook.txt");
        File dst  = new File(dir, "bl.cook.concepts.rdf");
        File rpt2 = new File(dir, "bl.cook.concepts.txt");
        run(new File(dir, "items"), rpt1, dst);
        new SKOSAnalysis().analyse(dst).print(rpt2);
    }

    private static void runBL_Asian() throws IOException
    {
        File dir  = new File(DIR, "BL\\asian");
        File rpt1 = new File(dir, "bl.asian.txt");
        File dst  = new File(dir, "bl.asian.concepts.rdf");
        File rpt2 = new File(dir, "bl.asian.concepts.txt");
        run(new File(dir, "items"), rpt1, dst);
        new SKOSAnalysis().analyse(dst).print(rpt2);
    }

    private static void run(File src, File rpt, File trg) throws IOException
    {
        String base = "http://www.europeanasounds.eu/data/concepts#";
        //String base = "http://mint-projects.image.ntua.gr/data/concepts#";
        Model mSrc = loadAll(src, "xml");
        Model mTrg = ModelFactory.createDefaultModel();
        new RDF2SKOSExtractor(base).extract(mSrc, mTrg);
        createScheme(mTrg, base);
        Map prefs = Collections.singletonMap("xmlbase", base);
        store(mTrg, trg, "RDF/XML-ABBREV", prefs);

        //Create stats
        new CHOAnalysis().analyse(mSrc).print(rpt);
    }

    private static void createScheme(Model m, String base)
    {
        String note = "A virtual scheme containing concepts that were extracted from a set of source records in order to be matched against a target vocabulary.";
        Resource rsrc = m.getResource(base + "ConceptScheme");
        rsrc.addProperty(SKOS.note, note);
    }
}
