/**
 * 
 */
package eu.europeana.exp.esoundsalign;

import java.io.IOException;

import static eu.europeana.exp.esoundsalign.ExperimentUtils.*;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 21 Apr 2016
 */
public class RunGenStatistics
{
    public static final void main(String[] args) throws IOException
    {
        new StatisticsGenerator().generate(getDatasets(), System.out);
    }
}