/**
 * 
 */
package eu.europeana.exp.esoundsalign;

import java.util.Collection;
import java.util.TreeSet;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.DC;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 3 Jun 2016
 */
public class RunAssembleData
{

    public static final void main(String[] args)
    {
        Collection col = new TreeSet();
        for ( DatasetConfig config : ExperimentUtils.getDatasets() )
        {
            Model model = config.loadData();
            StmtIterator iter = model.listStatements(null, DC.subject
                                                   , (RDFNode)null);
            while ( iter.hasNext() )
            {
                RDFNode node = iter.nextStatement().getObject();
                if ( !node.isResource() ) { continue; }

                col.add(node.asResource().getURI());
            }
            System.out.println(col);
            col.clear();
        }
    }
}
