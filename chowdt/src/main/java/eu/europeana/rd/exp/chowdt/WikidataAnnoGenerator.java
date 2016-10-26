/**
 * 
 */
package eu.europeana.rd.exp.chowdt;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;

import eu.europeana.anno.api.AnnotationAPI;
import eu.europeana.ld.deref.DereferenceChecker;
import static org.apache.commons.io.IOUtils.*;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 21 Mar 2016
 */
public class WikidataAnnoGenerator implements AnnotationAPI.Callback<Map>
{
    private static CSVFormat _format  = CSVFormat.EXCEL;
    private static String    _charset = "UTF-8";

    private AnnotationAPI<Map> _api;
    private boolean            _override = false;
    private DereferenceChecker _checker  = new DereferenceChecker(false);
    private CSVPrinter         _printer;

    public WikidataAnnoGenerator(AnnotationAPI<Map> api) { _api = api; }

    public void generate(EntrySet set, File file) throws IOException
    {
        _printer = null;
        try {
            _printer = new CSVPrinter(new PrintStream(file, _charset), _format);
            for ( EntrySet.Entry entry : set ) { newTag(entry); }
            _printer.flush();
        }
        finally { /*closeQuietly(_printer);*/ }
    }

    public void generate(File file) throws IOException
    {
        generate(new EntrySet().loadFromCVS(file), file);
    }

    private void newTag(EntrySet.Entry entry)
    {
        if ( !_override && checkAnnotation(entry.anno) ) {
            print(entry.cho, entry.wdt, entry.anno);
            return;
        }

        _api.newObjectLinking(this, entry.cho, "sameAs", entry.wdt);
    }

    private boolean checkAnnotation(String uri)
    {
        if ( StringUtils.isEmpty(uri) ) { return false; }

        Boolean b = _checker.check(uri + "?wskey=apidemo");
        return (b == null ? false : b );
    }

    protected synchronized void print(String cho, String wdt, String anno)
    {
        try { _printer.printRecord(cho, wdt, anno); _printer.flush(); }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(Map result)
    {
        String id = ( result == null ? null : (String)result.get("id") );
        if ( id == null ) { return; }

        Map r = (Map)((Map)result.get("body")).get("@graph");
        String cho = (String)r.get("id");
        String wdt = (String)r.get("sameAs");
        print(cho, wdt, id);
    }

    @Override
    public void handleError(Throwable t)
    {
        t.printStackTrace();
    }
}
