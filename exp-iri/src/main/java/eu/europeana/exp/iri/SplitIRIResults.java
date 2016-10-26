/**
 * 
 */
package eu.europeana.exp.iri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.jena.iri.IRIFactory;
import org.apache.jena.iri.Violation;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 20 Oct 2016
 */
public class SplitIRIResults
{
    private Map<String,CSVPrinter> _printers = new HashMap();

    public void split(File file) throws IOException
    {
        CSVParser parser = CSVParser.parse(file, Charset.forName("UTF-8"), CSVFormat.EXCEL);
        try {
            for ( CSVRecord record : parser )
            {
                CSVPrinter printer = getOutput(file, record.get(3));
                String uri = record.get(1);
                printer.printRecord(record.get(0), uri, isURIValid(uri), isIRIValid(uri));
            }
            flushOutputs();
        }
        finally { closeOutputs(); parser.close(); }
    }

    private boolean isURIValid(String uri)
    {
        try                          { new URI(uri); return true; }
        catch (URISyntaxException e) { return false;              }
    }

    private boolean isIRIValid(String uri)
    {
        IRIFactory f = IRIFactory.iriImplementation();
        Iterator<Violation> iter = f.create(uri).violations(false);
        while ( iter.hasNext() )
        {
            if ( iter.next().isError() ) { return false; }
        }
        return true;
    }

    private void flushOutputs() throws IOException
    {
        for ( CSVPrinter p : _printers.values() ) { p.flush(); }
    }

    private void closeOutputs()
    {
        for ( CSVPrinter p : _printers.values() ) { IOUtils.closeQuietly(p); }
    }

    private CSVPrinter getOutput(File base, String errType)
            throws IOException
    {
        CSVPrinter out = _printers.get(errType);
        if ( out != null ) { return out; }

        File file = new File(base.getParentFile(), getFilename(base, errType));
        out = new CSVPrinter(new PrintStream(file, "UTF-8"), CSVFormat.EXCEL);
        _printers.put(errType, out);
        return out;
    }

    private String getFilename(File base, String errType)
    {
        String name = base.getName();
        int i = name.lastIndexOf('.');
        if ( i < 0 ) { return name + "." + errType + ".csv"; }
        return name.substring(0, i) + "." + errType + name.substring(i);
    }

    public static final void main(String[] args) throws IOException
    {
        File file = new File("D:\\work\\data\\iri\\iris_entities_report.csv");
        new SplitIRIResults().split(file);
    }
}
