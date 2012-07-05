package com.couchbase.kiva;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.Stale;
import com.couchbase.client.protocol.views.View;
import com.couchbase.client.protocol.views.ViewResponse;


public class KivaViewsQuery {

	public static void main( String[] args ) {
		
//		KivaJsonLoader couchbaseJsonLoader = new KivaJsonLoader();
//		couchbaseJsonLoader.init(args);
	//	couchbaseJsonLoader.loadData();
	

	
    try {
	      URI local = new URI("http://"+"10.2.1.12"+":8091/pools");
	      List<URI> baseURIs = new ArrayList<URI>();
	      baseURIs.add(local);

	      CouchbaseClient c = new CouchbaseClient(baseURIs, "default", "");
	      View view = c.getView("dev_loans", "loan_status");
	    	
	      Query query = new Query();
	      Stale stale=Stale.OK;
	      query.setStale(stale);
//	      query.setGroup(true);
	      Future<ViewResponse> viewResponse = c.asyncQuery(view, query);
	      
	      ViewResponse result = viewResponse.get();
	      
	      
	      c.shutdown(3, TimeUnit.SECONDS);

    	} catch (Exception e) {
      System.err.println("Error connecting to Couchbase: " + e.getMessage());
      e.printStackTrace();
      System.exit(0);
    	}
	}
}
