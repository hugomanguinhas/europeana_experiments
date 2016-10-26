/**
 * 
 */
package eu.europeana.exp.esoundsalign;

import java.io.IOException;

import org.apache.commons.csv.CSVPrinter;
import org.apache.jena.rdf.model.Literal;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 22 Apr 2016
 */
public class AnnotationResult
{
    private String  _eid;
    private String  _pid;
    private String  _skosProp;
    private Literal _label;
    private String  _mimo;

    public AnnotationResult(String eid, String pid, Literal label
                          , String prop, String mimo)
    {
        _eid   = eid;
        _pid   = pid;
        _label = label;
        _skosProp  = prop;
        _mimo  = mimo;
    }

    public String  getEID()      { return _eid;   }
    public String  getPID()      { return _pid;   }
    public String  getSKOSProperty() { return _skosProp;  }
    public Literal getLabel()    { return _label; }
    public String  getMIMO()     { return _mimo;  }

    public void write(CSVPrinter p) throws IOException
    {
        p.printRecord(_eid, _mimo);
    }
}