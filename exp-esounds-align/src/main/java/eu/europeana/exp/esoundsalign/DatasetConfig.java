/**
 * 
 */
package eu.europeana.exp.esoundsalign;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import com.github.jsonldjava.utils.JsonUtils;

import eu.europeana.ld.edm.EuropeanaDataUtils;
import eu.europeana.ld.jena.JenaUtils;
import static java.lang.ClassLoader.*;
import static org.apache.commons.io.IOUtils.closeQuietly;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 22 Apr 2016
 */
public class DatasetConfig
{
    private String                         _name;
    private String                         _data;
    private String                         _src;
    private String                         _map;
    private ProviderToEuropeanaURIResolver _resolver;

    public DatasetConfig(String name, String data
                       , String src, String map, String uris)
    {
        _name     = name;
        _data     = data;
        _src      = src;
        _map      = map;
        _resolver = createResolver(uris);
    }

    public String      getName()    { return _name; }
    public File        getData()
    {
        URL url = getSystemResource(_data);
        if ( url == null ) { return null; }
        return new File(url.getFile());
    }
    public InputStream getSource()
    {
        return (_src == null ? null : getSystemResourceAsStream(_src));
    }
    public InputStream getMapping()
    {
        return (_map == null ? null : getSystemResourceAsStream(_map));
    }
    public ProviderToEuropeanaURIResolver getResolver() { return _resolver; }

    public Model loadData()
    {
        return JenaUtils.loadAll(getData(), ModelFactory.createDefaultModel());
    }
    public Model getSourceAndMapping()
    {
        return loadSourceAndMapping(getSource(), getMapping());
    }

    private Model loadSourceAndMapping(InputStream... iss)
    {
        Model m = ModelFactory.createDefaultModel();
        for ( InputStream is : iss )
        {
            if ( is == null ) { continue; }

            try { m.read(is, "RDF/XML"); } finally { closeQuietly(is); }
        }
        return m;
    }


    private ProviderToEuropeanaURIResolver createResolver(String src)
    {
        List<String> ids = new ArrayList();
        if ( src == null ) { return new ProviderToEuropeanaURIResolver(ids); }

        try {
            InputStream is = ClassLoader.getSystemResourceAsStream(src);
            Map m = (Map)JsonUtils.fromInputStream(is);
            List l = ((List)((Map)((List)m.get("facets")).get(0)).get("fields"));

            for ( Object o : l )
            {
                ids.add(EuropeanaDataUtils.CHO_NS
                      + (String)((Map)o).get("label"));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return new ProviderToEuropeanaURIResolver(ids);
    }
}