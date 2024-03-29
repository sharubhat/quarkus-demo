# quarkus-demo

This is a simple application to demo Quarkus Reactive using Kotlin co-routines and Panache (Panache is a Quarkus-specific library that simplifies the development of Hibernate-based persistence layer) for MongoDB. 

In addition to everything awesome that comes bundled with Quarkus, following have been integrated and tested.
1. Health checks
2. Logging
3. Micrometer
4. Swagger
5. Liquibase for MongoDB
6. Linters

There are 4 branches currently in this project. `trunk` is actively maintained, rest are examples only and may have bugs or incomplete code. 
P.S: The branches other than trunk demo using postgres while trunk will be switching to mongodb as the experimentation continues.

| Branch                                                                                                          |                                          Description                                           |
|-----------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------:|
| [trunk](https://github.com/sharubhat/quarkus-demo/tree/trunk)                                                   |                 active branch - non-blocking db calls using active record pattern              |
| [non-reactive-hibernate](https://github.com/sharubhat/quarkus-demo/tree/non-reactive-hibernate)                 |                                    shows blocking db calls                                     |
| [reactive-active-record-pattern](https://github.com/sharubhat/quarkus-demo/tree/reactive-active-record-pattern) |            non-blocking db calls using active record pattern with hibernate panache            |
| [reactive-repository-pattern](https://github.com/sharubhat/quarkus-demo/tree/reactive-repository-pattern)       |             non-blocking db calls using repository pattern with hibernate panache              |



This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./gradlew quarkusDev
```
By default Quarkus devservices is enabled which means not specifying `quarkus.mongodb.connection-string` in application.properties automatically spins up a docker container. This requires docker to be running locally.

Liquibase is used for database change management. It is set to run a migration at startup. If you are running native image, ensure you either set the above property to point to a running mongodb cluster or run mongodb container locally.

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using:
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/quarkus-demo-1.0.0-SNAPSHOT-runner`. 
If you are running locally, make sure to run a mongodb container. 

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

A log line similar to below will indicate that the application has started successfully.
```shell script
2023-09-29T15:58:59 INFO  [io.quarkus] (main) quarkus-demo 1.0.0-SNAPSHOT native (powered by Quarkus 3.0.4.Final) started in 0.112s. Listening on: http://0.0.0.0:9090
```

## Performance
There is a copy of [hyperfoil](https://hyperfoil.io/) version 0.25.2 at root level of the project. There is also a benchmark script that can be run to generate load and also produce flame graph based on application profiling.
P.S. Application needs to be built as uber jar every time a change is made, before running the benchmark script.
The flame graph generated is stored in script folder as a html page.
Ref: https://www.youtube.com/watch?v=Cw4nN5L-2vU
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
./benchmark.sh 
```
Following is the color coding for flame graph for quick reference.
![Mixed-Mode Flame Graphs.png](..%2F..%2F..%2FLibrary%2FContainers%2Fcom.apple.Notes%2FData%2Ftmp%2FTemporaryItems%2FNSIRD_Notes_l1cmoz%2FHardLinkURLTemp%2F668C8226-3F84-4A5F-ABE7-2CA189007319%2F1697299284%2FMixed-Mode%20Flame%20Graphs.png)
## Related Guides

- Hibernate ORM ([guide](https://quarkus.io/guides/hibernate-orm)): Define your persistent model with Hibernate ORM and Jakarta Persistence
- JDBC Driver - H2 ([guide](https://quarkus.io/guides/datasource)): Connect to the H2 database via JDBC
- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin
- Liquibase ([guide](https://quarkus.io/guides/liquibase)): Handle your database schema migrations with Liquibase
- JDBC Driver - MySQL ([guide](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)



### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
