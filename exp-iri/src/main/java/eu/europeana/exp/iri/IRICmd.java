/**
 * 
 */
package eu.europeana.exp.iri;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.apache.jena.iri.ViolationCodes;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import eu.europeana.ld.harvester.HarvesterCallback;
import eu.europeana.ld.harvester.LDHarvester;
import eu.europeana.ld.mongo.MongoEDMHarvester;
import eu.europeana.ld.mongo.cmd.MongoHarvestCmd;
import eu.europeana.ld.tools.ToolkitCmd;

/**
 * @author Hugo Manguinhas <hugo.manguinhas@europeana.eu>
 * @since 17 Oct 2016
 */
public class IRICmd extends MongoHarvestCmd
{
    public static void main(String[] args) { new IRICmd().process(args); }

    public IRICmd() { super("/etc/cmd/iri.cfg"); }

    protected Options buildOptions()
    {
        Options options = new Options();
        addDefaultOptions(options, _props);
        addMongoOptions(options, _props);
        addOutputOptions(options, _props);

        OptionGroup group = new OptionGroup()
            .addOption(OptionBuilder
                .withDescription(getProperty("info.option.all"))
                .create("all"))
            .addOption(OptionBuilder
                .withDescription(getProperty("info.option.entities"))
                .create("entities"))
            .addOption(OptionBuilder
                .withDescription(getProperty("info.option.media"))
                .create("media"));
        group.setRequired(true);
        options.addOptionGroup(group);

        return options;
    }

    @Override
    protected void process(CommandLine line) throws Throwable
    {
        checkLogging(line);

        MongoClient   c   = getMongoClient(line, _props);
        try {
            File          out = getOutputFile(line);
            MongoDatabase db  = getMongoDatabase(c, line, _props);
            new RunMongoIRICheck(db).run(out);
        }
        finally { c.close(); }
    }

    protected void checkLogging(CommandLine line)
    {
        if ( !line.hasOption("silent") ) { return; }
        Logger.getLogger(LDHarvester.class.getName()).setLevel(Level.OFF);
    }

    protected void handleCmd(RunMongoIRICheck check
                           , CommandLine line) throws Throwable
    {
        if ( line.hasOption("all") ) { return; }

        if ( line.hasOption("uris") ) {
        }

        if ( line.hasOption("file") ) {
        }

    }
}