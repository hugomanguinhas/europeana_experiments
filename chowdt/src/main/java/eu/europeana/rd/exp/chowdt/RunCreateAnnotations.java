/**
 * 
 */
package eu.europeana.rd.exp.chowdt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore.Entry;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.io.IOUtils;

import com.github.jsonldjava.utils.JsonUtils;
import com.jayway.jsonpath.JsonPath;

import eu.europeana.anno.api.AnnotationAPI;
import eu.europeana.anno.api.AnnotationUtils;
import eu.europeana.anno.api.config.AnnotationConfig;
import eu.europeana.anno.api.config.SoftwareAgent;
import eu.europeana.anno.api.impl.AnnotationAPIimpl;
import eu.europeana.anno.api.impl.ParallelAnnotationAPI;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 30 May 2016
 */
public class RunCreateAnnotations implements WikidataCHOExpConstants
{

    public static final void main(String[] args) throws IOException
    {
        if ( args.length < 2 )
        {
            System.out.println("missing arguments!"); return;
        }

        File file1 = new File(args[0]);
        File file2 = new File(args[1]);

        Collection<String> uris = getSameAs();
        EntrySet           set  = new EntrySet().loadFromCVS(file1);
        filter(set, uris);

        Properties cfg = new Properties();
        cfg.load(ClassLoader.getSystemResourceAsStream(KEY_CONFIG));

        AnnotationConfig acfg = getAnnotationConfig(cfg);

        try {
            AnnotationAPI<Map> api = new AnnotationAPIimpl(acfg);
            /*
            AnnotationAPI<Map> api = new ParallelAnnotationAPI(
                    acfg, Executors.newFixedThreadPool(20));
                    */
            new WikidataAnnoGenerator(api).generate(set, file2);
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    private static Collection<String> getSameAs() throws IOException
    {
        InputStream is = ClassLoader.getSystemResourceAsStream(
            "etc/chowdt/search.json");
        try {
            Map m = (Map)JsonUtils.fromInputStream(is, "UTF-8");
            if ( m == null ) { return Collections.EMPTY_SET; }
            List<String> col = (List<String>)
                JsonPath.read(m, "$.facets[0].fields[*].label");
            ListIterator<String> iter = col.listIterator();
            while ( iter.hasNext() )
            {
                iter.set("http://data.europeana.eu/item" + iter.next());
            }
            return col;
        }
        finally { IOUtils.closeQuietly(is); }
    }

    private static void filter(EntrySet set, Collection<String> uris)
    {
        Iterator<EntrySet.Entry> iter = set.iterator();
        while ( iter.hasNext() )
        {
            if ( uris.contains(iter.next().cho) ) { 
                iter.remove();
            }
        }
    }

    private static AnnotationConfig getAnnotationConfig(Properties cfg)
    {
        AnnotationConfig acfg = AnnotationUtils.buildConfig(
            cfg, "chowdt.annotation.");
        acfg.setProperty("indexOnCreate", "false");
        return acfg;
    }

}
