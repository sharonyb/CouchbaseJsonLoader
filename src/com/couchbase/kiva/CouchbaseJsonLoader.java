package com.couchbase.kiva;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;



public class CouchbaseJsonLoader {
    String serverVlaue = "10.2.1.12";
    String bucketValue = "default";
    String lenderPathValue = null;
    String loansPathValue = null;
    int multiplierValue = 1;

	
	public static void main( String[] args ) {
	
		KivaJsonLoader couchbaseJsonLoader = new KivaJsonLoader();
		couchbaseJsonLoader.init(args);
		couchbaseJsonLoader.loadData();
	}
	
	public void init(String[] args)
	{
		Option help = new Option( "help", false, "print this message" );
		Option lenders = new Option( "lenders", true, "lender json file directory" );
		Option loans = new Option( "loans",  true, "loans json file directory" );
		Option bucket = new Option( "bucket",  true, "Couchbase bucket name" );
		Option server = new Option( "server",  true, "Couchbase Server IP address" );
		Option multiplier = new Option( "multiplier", true, "multiply each record by X" );
		Option debug = new Option( "debug", "true/fals" );
		Options options = new Options();

		options.addOption( help );
		options.addOption( lenders );
		options.addOption( loans );
		options.addOption( bucket );
		options.addOption( server );
		options.addOption( multiplier );
		options.addOption( debug );
		
		
    CommandLineParser parser = new GnuParser();
    try {
        // parse the command line arguments
        CommandLine line = parser.parse( options, args );
        this.serverVlaue = (line.hasOption("server")?line.getOptionValue("server"):"10.2.1.12");
        this.bucketValue = (line.hasOption("bucket")?line.getOptionValue("bucket"):"default");
        this.lenderPathValue = (line.hasOption("lenders")?line.getOptionValue("lenders"):null);
        this.loansPathValue = (line.hasOption("loans")?line.getOptionValue("loans"):null);
        this.multiplierValue = (line.hasOption("multiplier")? Integer.parseInt(line.getOptionValue("multiplier")):1);
        if (line.hasOption("help"))
        {
        	HelpFormatter formatter = new HelpFormatter();
        	formatter.printHelp( "help", options );
        }
        System.out.println("server="+line.hasOption("server")+" value="+line.getOptionValue("server"));
        System.out.println("bucket="+line.hasOption("bucket")+" value="+line.getOptionValue("bucket"));
        System.out.println("lenders="+line.hasOption("lenders")+" value="+line.getOptionValue("lenders"));
        System.out.println("loans="+line.hasOption("loans")+" value="+line.getOptionValue("loans"));
        System.out.println("multiplier="+line.hasOption("multiplier")+" value="+line.getOptionValue("multiplier"));
        if (lenderPathValue == null)
        {
            System.out.println( "-lenders is missing" );
        	System.exit(0);
        }
        
	    

    }
    catch( ParseException exp ) {
        // oops, something went wrong
        System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
    }
	}
	
}
