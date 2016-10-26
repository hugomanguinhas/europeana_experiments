/**
 * 
 */
package eu.europeana.exp.esoundsalign;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import static org.apache.jena.vocabulary.RDF.*;
import static org.apache.jena.vocabulary.SKOS.*;
import static eu.europeana.exp.esoundsalign.ExperimentUtils.*;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 6 May 2016
 */
public class StatisticsGenerator
{
    

    public void generate(List<DatasetConfig> datasets, PrintStream ps)
           throws IOException
    {
        CSVPrinter p = new CSVPrinter(ps, CSVFormat.EXCEL);
        genDatasetTable(datasets, p);
        p.println();
        genSuccessTable(datasets, p);
    }

    private void genDatasetTable(List<DatasetConfig> datasets
                               , CSVPrinter p) throws IOException
    {
        p.printComment("Dataset table");

        for ( DatasetConfig cfg : datasets ) { p.print(cfg.getName()); }
        p.println();

        for ( DatasetConfig cfg : datasets )
        {
            p.print(getRecordsWithSubjects(cfg.getSourceAndMapping()));
        }
        p.println();

        for ( DatasetConfig cfg : datasets )
        {
            p.print(getRecordsWithEnrichments(cfg.getSourceAndMapping()));
        }

        p.println();
    }

    private void genSuccessTable(List<DatasetConfig> datasets
                               , CSVPrinter p) throws IOException
    {
        p.printComment("Success table");

        p.print("Alignment");
        for ( DatasetConfig cfg : datasets ) { p.print(cfg.getName()); }
        p.println();

        p.print("Aligned");
        for ( DatasetConfig cfg : datasets )
        {
            p.print(getMatchedSubjects(cfg.getSourceAndMapping()));
        }
        p.println();

        p.print("Unaligned");
        for ( DatasetConfig cfg : datasets )
        {
            p.print(getUnmatchedSubjects(cfg.getSourceAndMapping()));
        }
        p.println();

        p.print("Total");
        for ( DatasetConfig cfg : datasets )
        {
            p.print(getNumberOfSubjects(cfg.getSourceAndMapping()));
        }
        p.println();
    }

    private long getMatchedSubjects(Model model)
    {
        long count = 0;
        ResIterator iter = model.listResourcesWithProperty(type, Concept);
        while ( iter.hasNext() )
        {
            count += (hasMatch(iter.next()) ? 1 : 0);
        }
        return count;
    }

    private long getUnmatchedSubjects(Model model)
    {
        long count = 0;
        ResIterator iter = model.listResourcesWithProperty(type, Concept);
        try {
            while ( iter.hasNext() )
            {
                count += (!hasMatch(iter.next()) ? 1 : 0);
            }
        }
        finally { iter.close(); }
        return count;
    }

    private long getNumberOfSubjects(Model model)
    {
        long count = 0;
        ResIterator iter = model.listResourcesWithProperty(type, Concept);
        try {
            while ( iter.hasNext() ) { count++; iter.next(); }
        }
        finally { iter.close(); }
        return count;
    }

    private long getRecordsWithSubjects(Model model)
    {
        Collection<String> ids = new TreeSet();
        StmtIterator iter = model.listStatements(null, note, (RDFNode)null);
        while ( iter.hasNext() )
        {
            RDFNode node = iter.next().getObject();
            if ( node.isResource() ) { ids.add(node.asResource().getURI()); }
        }
        return ids.size();
    }

    private long getRecordsWithEnrichments(Model model)
    {
        Collection<String> ids = new TreeSet();
        ResIterator iter = model.listSubjectsWithProperty(type, Concept);
        try {
            while ( iter.hasNext() )
            {
                Resource r = iter.next();
                if ( !hasMatch(r) ) { continue; }

                NodeIterator iter2 = model.listObjectsOfProperty(r, note);
                while ( iter2.hasNext() )
                {
                    RDFNode node = iter2.next();
                    if ( !node.isURIResource() ) { continue; }

                    ids.add(node.asResource().getURI());
                }
            }
        }
        finally { iter.close(); }

        return ids.size();
    }

    private boolean hasMatch(Resource r)
    {
        for ( Property p : PROPERTIES_MATCH )
        {
            if ( r.hasProperty(p) ) { return true; }
        }
        return false;
    }
}
