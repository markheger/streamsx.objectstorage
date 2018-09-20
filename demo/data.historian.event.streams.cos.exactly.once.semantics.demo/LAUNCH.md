# Launch the applications for the Data Historian Event Streams to Object Storage Demo

## Write generated data to Event Streams

### Launch the Data Generator app to the Streaming Analytics service

From command line you could launch the application with [streamsx-runner](http://ibmstreams.github.io/streamsx.topology/doc/pythondoc/scripts/runner.html) to the Streaming Analytics service:

`streamsx-runner --service-name $STREAMING_ANALYTICS_SERVICE_NAME --main-composite com.ibm.streamsx.datahistorian.generate.json::Main --toolkits dh_generate_json --submission-parameters mh.topic=dh6 mh.topic.numPartitions=6 numMessages.per.partition=4000000`

The command above launches the application to write *`4000000`* messages in *`6`* partitions with the topic name *`dh6`*.

## "Event Streams to COS" app to the Streaming Analytics service

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

`streamsx-runner --service-name $STREAMING_ANALYTICS_SERVICE_NAME --main-composite com.ibm.streamsx.datahistorian.json.parquet::Main --toolkits dh_json_parquet $MH_TOOLKIT $COS_TOOLKIT --trace info --submission-parameters mh.consumer.group.size=6 mh.topic=dh6 cos.number.writers=4 cos.uri=$COS_URI`

The command above launches the application read from Event Streams with the topic name *`dh6`* using *`6`* consumers and writing to COS using *`4`* writers.