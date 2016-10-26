/**
 * 
 */
package eu.europeana.exp.esoundsalign;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import eu.europeana.exp.esoundsalign.DatasetConfig;
import eu.europeana.exp.esoundsalign.AnnotationGenerator;
import eu.europeana.exp.esoundsalign.AnnotationResult;

import static org.apache.commons.io.IOUtils.*;
import static eu.europeana.exp.esoundsalign.ExperimentUtils.*;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 21 Apr 2016
 */
public class RunGenerationAnnotations
{
    public static final void main(String[] args) throws IOException
    {
        run(getDataset("Asian")
          , "D:\\work\\git\\Europeana\\experiments\\exp-esounds-align\\src\\main\\resources\\etc\\anno\\blasian.anno.csv");
        run(getDataset("Tim Cook")
          , "D:\\work\\git\\Europeana\\experiments\\exp-esounds-align\\src\\main\\resources\\etc\\anno\\blcook.anno.csv");
        run(getDataset("Keith Summers")
          , "D:\\work\\git\\Europeana\\experiments\\exp-esounds-align\\src\\main\\resources\\etc\\anno\\blkeith.anno.csv");
        /*
        run(getDataset("MMSH")
          , "D:\\work\\github\\rd-exp\\src\\main\\resources\\etc\\anno\\mmsh.anno.csv");
        run(getDataset("CREM")
          , "D:\\work\\github\\rd-exp\\src\\main\\resources\\etc\\anno\\crem.anno.csv");
        run(getDataset("NISV")
          , "D:\\work\\github\\rd-exp\\src\\main\\resources\\etc\\anno\\nisv.anno.csv");
*/
    }

    protected static void run(DatasetConfig cfg, String target)
              throws IOException
    {
        AnnotationGenerator generator = new AnnotationGenerator(cfg);
        Collection<AnnotationResult> col = generator.generate();

        CSVPrinter printer = null;
        try {
            printer = new CSVPrinter(new PrintStream(target), CSVFormat.EXCEL);
            for ( AnnotationResult result : col ) { result.write(printer); }
            printer.flush();
        }
        finally { closeQuietly(printer); }
    }
}