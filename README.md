# verinice REST service

This project which provides a
[REST](https://de.wikipedia.org/wiki/Representational_State_Transfer) service
for a verinice database. The verinice REST service is a [Spring
Boot](http://projects.spring.io/spring-boot/) application build with
[Maven](https://maven.apache.org/).


## Service

This section describes the methods of the service. The term _object_ in this
article means _CnATreeElement_.
* Load objects by scope id.
  * Parameter: one scope id
  * Return value: A list of objects or _null_
* Load object by UUID id.
  * Parameter: one UUID
  * Return value: One object or _null_
* Load objects by property key value pair.
  * Parameter: A property key and a property value
  * Return value: A list of objects or _null_


## Modules

The project consist of several modules to seperate concerns. This section
describes the modules of the project.

### verinice-interface

Module _verinice-interface_ contains classes and interfaces which are shared by all other
modules.

### verinice-persistence

Module _verinice-persistence_ contains objects to access data from the database and the
Java Persistence API (JPA) configuration.

### verinice-service

Module _verinice-service_ contains a Java API of the service.

### verinice-rest

Module _verinice-rest_ contains the REST controller for the service.

## BUILD

To run the build with maven you need to add the ojdbc7.jar (12.1.0.2) to your private repository
Download the file, e.g. from [here](http://www.oracle.com/technetwork/database/features/jdbc/default-2280470.html).

After downloading you need to add the file to your maven repository:
`mvn install:install-file -Dfile=ojdbc7.jar  -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.2 -Dpackaging=jar`

After that the build should run successfully.
