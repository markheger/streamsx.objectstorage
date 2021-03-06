# Launch the applications for the Data Historian Event Streams to Object Storage Demo

Either run the application [dh_generate_json](dh_generate_json/README.md) on a dedicated Streaming Analytics service instance or generate the test data on the same Streaming Analytics service, wait for completion and cancel the job before launching the  [dh_json_parquet](dh_json_parquet/README.md) application.

## 1) Write generated data to Event Streams

### Launch the Data Generator app to the Streaming Analytics service

From command line you could launch the application with [streamsx-runner](http://ibmstreams.github.io/streamsx.topology/doc/pythondoc/scripts/runner.html) to the Streaming Analytics service:

    streamsx-runner --service-name $STREAMING_ANALYTICS_SERVICE_NAME --main-composite com.ibm.streamsx.datahistorian.generate.json::Main --toolkits dh_generate_json --submission-parameters mh.topic=dh mh.topic.numPartitions=6 numMessages.per.partition=2000000

The command above launches the application that writes to *`6`* partitions, *`2000000`* messages each with the topic name *`dh`*.

## 2) "Event Streams to COS" app to the Streaming Analytics service

It is recommended to launch the application [dh_json_parquet](dh_json_parquet/README.md) to a Streaming Analytics service with "premium container" plan (16 cores and 128GB RAM)

### Prepare environment variables

`export COS_URI="s3a://<YOUR_BUCKET_NAME>"`

Replace <YOUR_BUCKET_NAME> with your target COS bucket.

Set the toolkit location of the com.ibm.streamsx.messagehub toolkit (version 1.5):

`export MH_TOOLKIT=../../../streamsx.messagehub/com.ibm.streamsx.messagehub`

Set the toolkit location of the com.ibm.streamsx.objectstorage toolkit (version 1.6).

For example:
`export COS_TOOLKIT=../../com.ibm.streamsx.objectstorage`

### Launch "Event Streams to COS" app to the Streaming Analytics service

From command line you could launch the application with [streamsx-runner](http://ibmstreams.github.io/streamsx.topology/doc/pythondoc/scripts/runner.html) to the Streaming Analytics service:

    streamsx-runner --service-name $STREAMING_ANALYTICS_SERVICE_NAME --main-composite com.ibm.streamsx.datahistorian.json.parquet::Main --toolkits dh_json_parquet $MH_TOOLKIT $COS_TOOLKIT --trace info --submission-parameters mh.consumer.group.size=6 mh.topic=dh cos.number.writers=4 cos.uri=$COS_URI

The command above launches the application read from Event Streams with the topic name *`dh`* using *`6`* consumers and writing to COS using *`4`* writers.

## 3) Validate the data integrity 

### Simulate error cases 

Restart any Processing Element (PE) of the dh_json_parquet application while running in the Streaming Analytics service.

The [Job Control Plane](https://www.ibm.com/support/knowledgecenter/en/SSCRJU_4.3.0/com.ibm.streams.dev.doc/doc/jobcontrolplane.html) will reset the region and the operators recover from their checkpoints. Consumer operators proceed processing with last saved checkpoint.

### Verify with IBM SQL Query service that all messages are written to COS exactly once 

Open the UI of the SQL query service and run the following queries to check that the expected 12.000.000 messages are processed with exactly once semantics.

Replace the bucket name "datahistorian-001" with your bucket name in the queries below.

    SELECT COUNT(id) FROM cos://us-geo/datahistorian-001/DataHistorian/*.parquet STORED AS PARQUET

Validate that the messages are distinct:

    SELECT COUNT(DISTINCT id, channel) FROM cos://us-geo/datahistorian-001/DataHistorian/*.parquet STORED AS PARQUET 

The result should look like this: 

    COUNT(DISTINCT ID, CHANNEL)
    12000000



