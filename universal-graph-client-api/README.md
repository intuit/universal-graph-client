# Universal Graph Client SDK API

Universal Graph Client is the Data Fabric Persistence API for Java. It is the equivalent of, for example, a JDBC driver or the DataStax Cassandra Java driver for the Data Fabric persistence service. It is the primary interface for Java developers to use the Data Fabric persistence service.

The current version of the API is v0.3.

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

