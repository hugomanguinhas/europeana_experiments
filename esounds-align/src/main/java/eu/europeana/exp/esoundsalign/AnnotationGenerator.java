/**
 * 
 */
package eu.europeana.exp.esoundsalign;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;

import static org.apache.commons.io.IOUtils.*;
import static org.apache.jena.vocabulary.SKOS.*;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 21 Apr 2016
 */
public class AnnotationGenerator
{
    private List<Property> _properties
        = Arrays.asList(exactMatch, closeMatch, broadMatch, narrowMatch);
    private DatasetConfig _cfg;

    public AnnotationGenerator(DatasetConfig cfg) { _cfg = cfg; }

    public Collection<AnnotationResult> generate()
    {
        return generate(new ArrayList());
    }

    public Collection<AnnotationResult> generate(Collection<AnnotationResult> col)
    {
        Model m = _cfg.getSourceAndMapping();
        List<Resource> targets = new ArrayList(10);
        List<Resource> sources = new ArrayList(10);

        ResIterator iter = m.listResourcesWithProperty(RDF.type, Concept);
        try {
            while ( iter.hasNext() )
            {
                createAnnotation(col, iter.next(), sources, targets);
            }
        }
        finally { iter.close(); }

        return col;
    }

    protected boolean loadTargets(List<Resource> l
                                , Resource r, Property... props)
    {
        for ( Property p : props )
        {
            StmtIterator iter = r.listProperties(p);
            try {
                while ( iter.hasNext() ) { l.add(iter.next().getResource()); }
            }
            finally { iter.close(); }
        }
        return !l.isEmpty();
    }

    protected boolean loadSources(List<Resource> l
                                , Resource r, Property prop)
    {
        StmtIterator iter = r.listProperties(prop);
        try {
            while ( iter.hasNext() ) { l.add(iter.next().getResource()); }
        }
        finally { iter.close(); }

        return !l.isEmpty();
    }

    protected List<Resource> renameResources(List<Resource> l)
    {
        ListIterator<Resource> iter = l.listIterator();
        while(iter.hasNext())
        {
            Resource r = iter.next();
            String uri = _cfg.getResolver().resolve(r.getURI());
            System.out.println(r.getURI() + "=>" + uri);

            if ( uri == null ) { iter.remove(); continue; }

            iter.set(r.getModel().getResource(uri));
        }
        return l;
    }

    protected Literal getLabel(Resource r)
    {
        Statement stmt = r.getProperty(prefLabel);
        return (stmt == null ? null : stmt.getLiteral());
    }

    protected void createAnnotation(Collection<AnnotationResult> col
                                  , Resource r, List<Resource> sources
                                  , List<Resource> targets)
    {
        sources.clear(); targets.clear();
        if ( !loadSources(sources, r, note      ) ) { return; }

        Literal label = getLabel(r);
        for ( Property p : _properties)
        {
            if ( !loadTargets(targets, r, p) ) { continue; }

            createAnnotation(col, sources, targets, p, label);
        }
    }

    protected void createAnnotation(Collection<AnnotationResult> col
                                  , List<Resource> sources
                                  , List<Resource> targets
                                  , Property prop, Literal label)
    {
        for ( Resource s : sources )
        {
            for ( Resource t : targets )
            {
                String pid = s.getURI();
                //String eid = _cfg.getResolver().resolve(pid);
                col.add(new AnnotationResult(pid, pid, label
                                           , prop.getURI(), t.getURI()));
            }
        }
    }
}