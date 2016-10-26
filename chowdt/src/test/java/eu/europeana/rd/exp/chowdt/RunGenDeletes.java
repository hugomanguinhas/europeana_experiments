/**
 * 
 */
package eu.europeana.rd.exp.chowdt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 4 Oct 2016
 */
public class RunGenDeletes
{
    private void load(File file, Collection<String> list) throws IOException
    {
        CSVParser parser = new CSVParser(new FileReader(file), CSVFormat.EXCEL);
        try {
            for ( CSVRecord record : parser )
            {
                if ( record.size() <= 2 ) { continue; }

                String uri = record.get(2).trim();
                if ( !uri.isEmpty() ) { list.add(uri); }
            }
        }
        finally { IOUtils.closeQuietly(parser); }
    }

    private void newDelete(PrintStream ps, String uri)
    {
        ps.println("curl -X DELETE --header 'Content-Type: application/json'"
                 + " --header 'Accept: application/json'"
                 + " -d '[ \"" + uri + "\" ]'"
                 + " 'http://annotations.europeana.eu/admin/annotation/deleteset?wskey=apiadmin&userToken=admin'");
    }

    public void run(File src, PrintStream ps, int start, int end) 
           throws IOException
    {
        Collection<String> list = new TreeSet();
        load(src, list);
        for ( int i = start; i <= end; i++ )
        {
            String uri = "http://data.europeana.eu/annotation/collections/" + i;
            if ( list.contains(uri) ) { continue; }
            newDelete(ps, uri);
        }
    }

    public static final void main(String[] args) throws IOException
    {
        File src = new File("D:\\work\\data\\wkd_anno\\links_anno.csv");
        File dst = new File("D:\\work\\data\\wkd_anno\\wikidata_clean_2.sh");
        PrintStream ps = new PrintStream(dst);
        try {
            new RunGenDeletes().run(src, ps, 66, 136013);
            ps.flush();
        }
        finally { IOUtils.closeQuietly(ps); }
    }
}
