# Graph Client DSE Provider

This is a specific provider for DSE Graph. Look at the `com.intuit.ugc.impl.persistence.dse.DSEGraphVisitor` class to understand how the implementation for all the different graph operations are done. Each of the API here is specific to one operation that can be done over the underlying DSE Graph database. Currently, create, select, update and delete operations are supported for both vertices and edges.

### Initializing Graph Client DSE Provider
To see how to inject your specific dependencies and initialize the Graph Client DSE Provider, check the details in the class `com.intuit.ugc.impl.persistence.UGCDSETestBase`. The connection specific properties for connecting with DSE persistence store can be found at `/src/test/resource/graph_client_dse_test.properties`. Similarly, the user can have their own properties file placed in their project as applicable. Reference `com.intuit.ugc.impl.persistence.dse.DSETestModule` to see how this file is read and the configs loaded. The user should have an equivalent of this class in their project.

### Using Graph Client DSE Provider 
For details on how to use the Graph Client DSE Provider, look at the tests in `com.intuit.ugc.impl.persistence.dse.DSEGraphTest`. The tests here create a simple `author` Vertex and a `book` Vertex with their own specific properties and then create an `authored` relationship between these two vertices, all using the `com.intuit.ugc.impl.persistence.dse.DSEGraphVisitor` APIs. The user can have one or more equivalent of the implementation in this test class to run operations over underlying DSE persistence store.

### Integration
Universal Graph Client DSE provider is readily embeddable via the following maven dependency:
```xml
<dependency>	
	<groupId>com.intuit.graph.client.impl</groupId>
	<artifactId>graph-client-impl-provider-dse</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>```

However, to get working with this provider, you need to modify it's pom.xml file, and include the following data stax dependency in the pom.xml file and then compile the module once.
```xml
<dependency>
	<groupId>com.datastax.cassandra</groupId>
	<artifactId>dse-driver</artifactId>
	<version>1.1.0</version>
</dependency>
````