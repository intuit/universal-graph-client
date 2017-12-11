# Universal Graph Client
Universal Graph Client contains data persistence API and implementations specific to data persistence in graph databases. It can be called a JDBC for graph databases. The APIs are designed in such a way that they can be easily extended to work with any graph database. Thereby enabling the end developer to work with java APIs over the graph database, hiding the complexities of the underlying database query and it's complexities. Moreover, the language is very human readable kind of, which makes the API flows self explanatory.

This project consists of 3 modules:
1. Universal Graph Client SDK API
2. Universal Graph Client SDK Core Implementation
3. Graph Client DSE Provider

### Graph Databases
We live in a connected world. There are no isolated pieces of information, but rich, connected domains all around us. Only a database that embraces relationships as a core aspect of its data model is able to store, process, and query connections efficiently. While other databases compute relationships expensively at query time, a graph database stores connections as first class citizens, readily available for any “join-like” navigation operation. Accessing those already persistent connections is an efficient, constant-time operation and allows you to quickly traverse millions of connections per second per core.

There are some 30 popular variations of graph databases which are being used worldwide in real applications for everyday data modelling and insights. (Ref: https://en.wikipedia.org/wiki/Graph_database ) These graph databases come under different licenses, have compatibility with different programming languages and work with different query languages (Gremlin, GraphQL and SPARQL to name a few). But, as of today, there is no one single set of APIs and their implementation available, which could plug with any of these graph databases and work with them, irrespective of their underlying graph query language or programming language compatibility.

#### Benefits that Universal Graph Client provides
* Ease of adaptability: Universal Graph Client hides the underlying complexities to deal with the underlying graph database, exposing natural language friendly APIs, this drastically reduces the learning curve of the application developer.
* Speed & Agility: Developer can now focus on his business requirements and need not worry about the complexities of the underlying graph database
* Quick and smooth Migration: Universal Graph Client provides one API set for all varieties of graph databases so migration to another graph databases is just changing few configurations


### Universal Graph Client SDK API
Universal Graph Client provider the java APIs to work with graph database. It is the equivalent of, for example, a JDBC driver or the DataStax Cassandra Java driver for any persistence service which intends to work with an underlying graph database. It is the primary interface for Java developers to use a persistence service over graph database. 

APIs are defined to support creation, selection (including complex select queries), mutation (updation and deletion) of the graph entities. A graph Vertex is referred to as an Entity and a graph Edge is referred to as a Relationship. Properties of Vertices and Edges are referred to as Attributes. And the label of the Relationship is referred to as it's Name.

## Queries

There are three types of queries possible with Universal Graph Client:

* _Lookup queries_ are simple lookups of an entity by a key. The key can either be the database ID or a natural key specified by the domain model. Lookup queries can be used to emulate a simple key-value access pattern.
* _Graph queries_ are queries that start at a single entity (found using a lookup query) and then traverse from that entity to other entities via relationships, potentially over multiple steps. At each step, the list of entities can be filtered by a predicate. The set of attributes that is returned in the response is explicitly defined by a projection clause in the query.
* _Mutations_ are changes to entities and/or relationships, and are executed as a batch of operations that is guaranteed to be atomically applied.

All of the queries above are constructed using Universal Graph Client's query builder API, which is demonstrated in the examples below.

## Usage Examples

### Lookup Queries

Here's an example of a simple query to fetch an entity when you know the ID:

````java
Persistence persistence = ... // Injected
Entity.ID id = Entity.ID.valueOf("example.Customer/1");
Entity customerEntity = persistence.lookup()
    .entity(id)
    .includeAttribute("example.NumTransactions")
    .includeAttribute("example.FavoriteColor")
    .ready()
    .execute()
    .getEntity();
````

Here is a slightly more advanced example of looking up an entity by a natural key, `CustomerID`. This example also includes all the attributes in the `example` namespace instead of listing each one individually:

````java
Persistence persistence = ... // Injected
Entity customer123 = persistence.lookup()
    .entityBy("example.CustomerID").value("123")
    .includeAttributes("example")
    .ready()
    .execute()
    .getEntity();
````

### Graph Queries

The following is an example of graph query to find a certain subset of a vendor's customers:

````java
Persistence persistence = ... // Injected
Predicate p = persistence.predicates();

List<Entity> customers = persistence.queryGraph()
    // Pick a root entity
    .fromRootEntity(Entity.ID.valueOf("example.Vendor/1"))
    // Select all entities connected via the example.IsCustomer relationship
    .selectAdjacentEntitiesVia("example.IsCustomer")
    // Filter the selected set of customers
    .where(p.and(
        p.attribute("example.NumTransactions").greaterThan(5),
        p.attribute("example.FavoriteColor").notEqualTo("red")))
    .select()
    .includeAttribute("example.CustomerID")
    .includeAttribute("example.PostalCode")
    .ready()
    .execute()
    .getEntities();
````

### Mutations

Here is a simple example of creating an entity:

````java
Persistence persistence = ... // Injected

Entity.ID newEntityID = persistence.allocateID();

persistence.prepareBatchMutation()
    .createEntity(
        NewEntity.newInstance(newEntityID)
            .withAttribute("example.GivenName").value("Beaver")
            .withAttribute("example.FamilyName").value("Cleaver")
            .build())
    .execute();
````

A mutation is really a batch of operations, so multiple objects can be created at once:


````java
Persistence persistence = ... // Injected

Entity.ID beaverID = persistence.allocateID();
Entity.ID wallyID = persistence.allocateID();

persistence.prepareBatchMutation()
    .createEntity(
        NewEntity.newInstance(beaverID)
            .withAttribute("example.GivenName").value("Beaver")
            .withAttribute("example.FamilyName").value("Cleaver")
            .build())
    .createEntity(
        NewEntity.newInstance(wallyID)
            .withAttribute("example.GivenName").value("Wally")
            .withAttribute("example.FamilyName").value("Cleaver")
            .build())
    .createRelationship(
        NewRelationship.between(beaverID, wallyID)
            .withLabel("example.Siblings")
            .build())
    .execute();
````

And here is an example of how to make changes to existing entities using a mutation:

````java
Persistence persistence = ... // Injected

Entity.ID customer1 = Entity.ID.valueOf("example.Customer/1");
Entity.ID customer2 = Entity.ID.valueOf("example.Customer/2");

// Get the entities first so we know what they're current state is;
// remember, some other process could've changed them
List<Entity> entities = persistence.lookup()
    .entities(customer1,customer2)
    .ready()
    .execute()
    .getResult()
    .getEntities();

// Now change them in one batch operation

persistence.prepareBatchMutation()
    // Change entity customer1
    .withEntity(entities.get(0))
        .withAttribute("example.GivenName").value("Ward")
        .withAttribute("example.Premium").value(false)
        .ready()
    // Change entity customer2
    .withEntity(entities.get(1))
        .withAttribute("example.GivenName").value("June")
        .withAttribute("example.Premium").value(true)
        .ready()
    // Create a relationship between these customers
    .createRelationship(
        NewRelationship.between(entities.get(0), entities.get(1))
            .withLabel("example.Married")
            .withAttribute("example.Comment").value("They seem happy together")
            .build())
    .execute()
````

### Gizmo

For illustrative purposes, Universal Graph Client v0.3 includes an adapter for the above API with method names that closely resemble those of the Tinkerpop Blueprints/Gremlin API. This API adapter is rather fatuously named _Gizmo_.

````java
Persistence persistence = ... // Injected
Gizmo gizmo = Gizmo.newInstance(predicate);
Predicate _ = gizmo._();

List<Entity> customers =
        gizmo.g()
            // Pick a root entity
            .v("example.Vendor/1")
        // Select all entities connected via the example.IsCustomer relationship
        .out("example.IsCustomer")
        // Filter the selected set of customers
        .filter(_.and(
            _.gt("example.NumTransactions", "5"),
            _.neq("example.FavoriteColor", "red")))
        .select("example.CustomerID","example.PostalCode")
        .execute()
        .getEntities();
````

### Universal Graph Client SDK Core Implementation

Partial implementation of the Universal Graph Client API, requiring a persistence provider as specified by the Service-Provider Interface (SPI) contained in the `com.intuit.ugc.impl.core.spi` package. The provider APIs and implemented classes are further used by service specific persistence providers for specific implementation.

### Graph Client DSE Provider 
This is a specific provider for DSE Graph. Look at the `com.intuit.ugc.impl.persistence.dse.DSEGraphVisitor` class to understand how the implementation for all the different graph operations are done. Each of the API here is specific to one operation that can be done over the underlying DSE Graph database. Currently, create, select, update and delete operations are supported for both vertices and edges.
If you intend to use the Graph Client DSE Provider in your project, you can first modify it's pom.xml file to include this data stax driver dependency in the pom.xml :
````xml
<dependency>
	<groupId>com.datastax.cassandra</groupId>
	<artifactId>dse-driver</artifactId>
	<version>1.1.0</version>
</dependency>
````
Thereafter, compile the module and include it in your own project using the following pom.xml dependency :
````xml
<dependency>	
	<groupId>com.intuit.graph.client.impl</groupId>
	<artifactId>graph-client-impl-provider-dse</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>````

Now, you are all set to start calling into it's APIs to make use of this module. 
For details on how to use the Graph Client DSE Provider, look at the tests in `com.intuit.ugc.impl.persistence.dse.DSEGraphTest`. The tests here create a simple `author` Vertex and a `book` Vertex with their own specific properties and then create an `authored` relationship between these two vertices, all using the `com.intuit.ugc.impl.persistence.dse.DSEGraphVisitor` APIs. The user can have one or more equivalent of the implementation in this test class to run operations over underlying DSE persistence store.

#### Prerequisite to running the test
If you want to run the functional tests that come with Graph Client DSE Provider, you should first have your basic DSE Graph setup done.Refer:() on how to setup the DSE Graph on your local system for test purpose. Once the graph is setup, you should launch the gremlin console using the command from the DSE Installation Directory
````
bin/dse gremlin-console
````
Then from the gremlin console, you should run the following commands:
1. Connect to a test graph:
````
:remote config alias g test.g
````
2. Create the labels if not already present
````
schema.vertexLabel('Book').ifNotExists().create()
schema.vertexLabel('Author').ifNotExists().create()
````

Now, you are all set to run the tests.Once the tests are run, you can view the graph that got created in the DSE provided UI. To launch the UI and view the graph, follow these steps:
````
cd <dse-installation-folder>/bin
./start_studio_server  (This launches the UI Server)
Go to the browser and open http://localhost:9091/ (This launches the UI)
Navigate to 'Notebooks' in the UI. There will be one Notebook specific to the schema you just created (test). Inside this Notebook, you can see the graph just created.
````

#### Usage

For details on how to use the Graph Client DSE Provider, look at the tests in `com.intuit.ugc.impl.persistence.dse.DSEGraphTest`. The tests here create a simple `author` Vertex and a `book` Vertex with their own specific properties and then create an `authored` relationship between these two vertices, all using the `com.intuit.ugc.impl.persistence.dse.DSEGraphVisitor` APIs. Finally, the 'delete' tests delete the created vertices and relationship. To play around it, feel free to run individual tests (rather than the whole suite in one go) and observe the resultant graph before moving on to the next step.

#### Integration
Universal Graph Client DSE provider is readily embeddable via the following maven dependency:
```xml
<dependency>
        <groupId>com.intuit.graph.client.impl</groupId>
        <artifactId>graph-client-impl-provider-dse</artifactId>
        <version>1.0-SNAPSHOT</version>
</dependency>```

## Quick instructions to setup a graph client provider 
If you intend to use an existing graph client provider like the DSE provider, these are the two steps to set it up:
### Step1 : add the pom dependency of the corresponding provider in your project. For example, below is the pom dependency of the dse-provider:
```xml
<dependency>
      	<groupId>com.intuit.graph.client.impl</groupId>
		<artifactId>graph-client-impl-provider-dse</artifactId>
        <version>1.0-SNAPSHOT</version>
</dependency>```

### Step2 : Inject the provider in your initializer class, passing it the properties file location from where to load the database properties. Below is a sample code snippet for the same:
````java
public class DSETestModule extends AbstractModule {
	@Override
    protected void configure() {
		DSEPersistenceConfigurationProperties instance = new DSEPersistenceConfigurationProperties();
		 try {
	            instance.load("graph_client_dse_test.properties",null);
	        }
	        catch (IOException e) {
	            throw new PersistenceException(e);
	        }
	        bind(Persistence.Configuration.class).toInstance(instance);
	        bind(DSEPersistenceConfiguration.class).toInstance(instance);
	}
}
````

## Creating a graph client provider by extending the graph client api and impl
### Step 1 : Add pom dependencies
Add the pom dependency of the graph-client-api into your project
```xml
<dependency>
	<groupId>com.intuit.graph.client</groupId>
	<artifactId>universal-graph-client-api</artifactId>
	<version>0.5-SNAPSHOT</version>
</dependency>```

And add the dependency of graph-client-impl-core also in your project
```xml
<dependency>
	<groupId>com.intuit.graph.client.impl</groupId>
	<artifactId>universal-graph-client-impl-core</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>```

### Step 2 : Define the persistence properties and load them
You need to define the persistence properties using which your project will connect to the underlying graph. It can be a simple text file. (Refer to `graph-client-impl-provider-dse/src/test/resource/graph_client_dse_test.properties` for a guidance). And then, you have to define a class which loads this resource, like the one below:
````java
public class DSEPersistenceConfigurationProperties extends Properties implements DSEPersistenceConfiguration {
````
And then implement the load method to load the graph specific properties from the file or resource in which you have the graph database specific properties. Refer the following code snippet from `DSEPersistenceConfigurationProperties` :
````java
	public final void load(String resourceName, ClassLoader classLoader) throws IOException, PersistenceException {
		Preconditions.argumentNotNull(resourceName, "resourceName");
		if (isNull(classLoader)) {
			classLoader = DSEPersistenceConfigurationProperties.class.getClassLoader();
		}
		try {
			super.load(classLoader.getResource(resourceName).openStream());
			validateMissingRequiredKeys();
			LOG.info("Successfully loaded DSE provider configuration " + "from resource \"{}\"", resourceName);
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException("Could not load configuration from resource \"" + resourceName + "\"", e);
		}
	}
````
### Step 3 : Create a ConnectionManager
Refer the following code snippet from `DSEConnectionManager`.
````java
public class DSEConnectionManager {
	private DSEPersistenceConfiguration config;
	@Inject
	public DSEConnectionManager(DSEPersistenceConfiguration config) {
		this.config = config;
	}
````
### Step 4 : Implement GraphVisitor
GraphVisitor contains all the APIs for interacting with graph database. And every provider that deals with a particular kind of graph database should implement the GraphVisitor APIs. Refer to `DSEGraphVisitor`.

### Step 5 : Create a repository provider
A repository provider is injected with the right instance of GraphVisitor during startup and it then returns the same for use in the client code. Refer `DSEPersistenceRepositoryProvider`.

### Step 6 : Initialize the library
Finally, initialize the library through the client. Refer to `DSETestModule` to understand how the initialization is done.
````java
public class DSETestModule extends AbstractModule {
	@Override
    protected void configure() {
		DSEPersistenceConfigurationProperties instance = new DSEPersistenceConfigurationProperties();
		 try {
	            instance.load("graph_client_dse_test.properties",null);
	        }
	        catch (IOException e) {
	            throw new PersistenceException(e);
	        }
	        bind(Persistence.Configuration.class).toInstance(instance);
	        bind(DSEPersistenceConfiguration.class).toInstance(instance);
	}
}
````

## Features 
#### Extensibility
Universal Graph Client APIs can be implemented to develop provider for any kind of graph implementation
#### Default support for DSE Graph provider
Universal Graph Client already contains the provider for DSE Graph. This provider can be used to perform CRUD operation over a DSE Graph
#### High Performance
Universal Graph Client optimizes performance by using the efficient async APIs of underlying Data Stax Driver
#### Simplicity
Universal Graph Client is a very simple & lightweight library that can be easily plugged into any java project. 

## Integration
For integrating specific modules, refer to the corresponding README.md

## Contributions
We greatly encourage contributions! You can add new features, report and fix existing bugs, write docs and tutorials, or any of the above. Feel free to open issues and/or send pull requests.
The `master` branch of this repository contains the latest stable release of Universal Graph Client, while snapshots are published to the `develop` branch. In general, pull requests should be submitted against `develop` by forking this repo into your account, developing and testing your changes, and creating pull requests to request merges.See the [Contributing to a Project](https://guides.github.com/activities/contributing-to-open-source/)
article for more details about how to contribute in general.

Steps to contribute:

1. Fork this repository into your account on Github
2. Clone *your forked repository* (not our original one) to your hard drive with `git clone https://github.com/YOURUSERNAME/universal-graph-client.git`
3. Design and develop your changes
4. Add/update unit tests
5. Add/update integration tests
6. Add/update documentation on `gh-pages` branch
7. Create a pull request for review to request merge
8. Obtain 2 approval _squirrels_ before your changes can be merged

Thank you for your contribution!








