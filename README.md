## CouchbaseJsonLoader

This is my sample project that loads a bunch of JSON objects (few millions) to Couchbase server 2.0, and try the view capabilities extensivly. it's WIP. <p>

I tried building it as generic tool so other types of data can be coded into it. use it at your own discresion. <p>
I used data from kiva.org, can be downloaded at: [kiva.org](builds.kiva.org). specifically the latest data file can be found here: http://s3.kiva.org/snapshots/kiva_ds_json.zip <p>
There are two types of objects it loads: <p>
1. Lenders (about 1M) <p>
2. Loans (about .4M) quite nested objects <p>
3. Header (metadata for each file) <p>

There are nice insights one can find while building views and quering, for example, how many loans were defaulted, from which country, etc <p>

Map function for lender objects - lenders by country
```JS
function (doc) {
  if (doc.type == "lender")
  emit(doc.country_code, 1);
}
```

Map function for loans objects - loan status by country
```JS
function (doc) {
  if (doc.type == "loan")
  emit([doc.status,doc.location.country_code], doc._id);
}
```
Redcue function is 
```
_count
```
Query: 
```
http://[hostname]:8092/[bucketname]/_design/[design doc name]/_view/[view name]?full_set=true&group=false&group_level=1&connection_timeout=60000&limit=10&skip=0
```

command line to run the  kiva data loader:
```
java -cp .:$LAB_ROOT/couchbase/couchbase-client/1.1-dp/couchbase-client-1.1-dp.jar:$LAB_ROOT/spy/spymemcached/2.8.1//spymemcached-2.8.1.jar:$LAB_ROOT/org/codehaus/jettison/jettison/1.1/jettison-1.1.jar:$LAB_ROOT/org/apache/httpcomponents/httpcore/4.1.1/httpcore-4.1.1.jar:$LAB_ROOT/commons-codec/commons-codec/1.5/commons-codec-1.5.jar:$LAB_ROOT/org/apache/httpcomponents/httpcore-nio/4.1.1/httpcore-nio-4.1.1.jar:$LAB_ROOT/org/jboss/netty/netty/3.2.0.Final/netty-3.2.0.Final.jar:$LAB_ROOT/com/google/code/gson/gson/2.2.1/gson-2.2.1.jar:$LAB_ROOT/commons-cli/commons-cli/1.0/commons-cli-1.0.jar CouchbaseJsonLoader -lenders /sharonyb/dev/lenders -loans /sharonyb/dev/loans/ -bucket default -multiplier 1 -server localhost
```

## Resources
   * [Couchbase 2.0 developer previews](http://www.couchbase.com/download). i used the latest build though which has all the latest performance improvment. ping me if you are interested in using it too..
   * [Couchbase 2.0 Java SDK](http://www.couchbase.com/docs/couchbase-sdk-java-1.1/index.html)
   * [Couchbase 2.0 view chapter](http://www.couchbase.com/docs/couchbase-sdk-java-1.1/index.html)
   * [Kiva - what a greate project](http://www.kiva.org/)
   * [Web Json document viewer](http://jsonviewer.stack.hu/)

