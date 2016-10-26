/**
 * 
 */
package eu.europeana.exp.esoundsalign;

import static org.apache.jena.vocabulary.SKOS.broadMatch;
import static org.apache.jena.vocabulary.SKOS.closeMatch;
import static org.apache.jena.vocabulary.SKOS.exactMatch;
import static org.apache.jena.vocabulary.SKOS.narrowMatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.jena.rdf.model.Property;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 6 May 2016
 */
public class ExperimentUtils
{
    public static List<Property> PROPERTIES_MATCH
        = Arrays.asList(exactMatch, closeMatch, broadMatch, narrowMatch);

    private static List<DatasetConfig> _datasets = loadDatasets();

    public static List<DatasetConfig> getDatasets() { return _datasets; }

    public static DatasetConfig getDataset(String name)
    {
        for ( DatasetConfig cfg : getDatasets() )
        {
            if ( cfg.getName().equals(name) ) { return cfg; }
        }
        return null;
    }

    private static List<DatasetConfig> loadDatasets()
    {
        List<DatasetConfig> list = new ArrayList();
        list.add(new DatasetConfig(
                   "Asian"
                 , "etc/data/blasian/bl.asian.zip"
                 , "etc/data/blasian/bl.asian.concepts.rdf"
                 , "etc/mappings/blasian2mimo.rdf"
                 , "etc/uris/bl.uris.json"));
        list.add(new DatasetConfig(
                   "Tim Cook"
                 , "etc/data/blcook/bl.cook.zip"
                 , "etc/data/blcook/bl.cook.concepts.rdf"
                 , "etc/mappings/blcook2mimo.rdf"
                 , "etc/uris/bl.uris.json"));
        list.add(new DatasetConfig(
                   "Keith Summers"
                 , "etc/data/blkeith/bl.keith.zip"
                 , "etc/data/blkeith/bl.keith.concepts.rdf"
                 , "etc/mappings/blkeith2mimo.rdf"
                 , "etc/uris/bl.uris.json"));
        list.add(new DatasetConfig(
                    "NISV"
                 , "etc/data/nisv/nisv.zip"
                 , "etc/data/nisv/nisv.concepts.rdf"
                 , "etc/mappings/nisv2mimo.rdf"
                 , "etc/uris/nisv.uris.json"));
        list.add(new DatasetConfig(
                   "MMSH"
                 , "etc/data/mmsh/mmsh.zip"
                 , "etc/data/mmsh/mmsh.concepts.rdf"
                 , "etc/mappings/mmsh2mimo.rdf"
                 , "etc/uris/mmsh.uris.json"));
        list.add(new DatasetConfig(
                    "CREM"
                 , "etc/data/crem/crem.zip"
                 , "etc/data/crem/crem.concepts.rdf"
                 , "etc/mappings/crem2mimo.rdf"
                 , "etc/uris/crem.uris.json"));
        return list;
    }
}
