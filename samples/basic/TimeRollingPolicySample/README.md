# Time Rolling Policy Sample with HMAC (basic) Authentication

## Description
The sample demonstrates how to `ObjectStorageSink` operator with
HMAC (basic) authentication with time-based rolling policy,
i.e. to close output object approximately every `$timePerObject` seconds. 
Note, that in addition, the example demonstrates how to use `%TIME` 
variable in the output object name. 
    
## Utilized Toolkits
 - com.ibm.streamsx.objectstorage