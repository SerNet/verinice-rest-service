# verinice REST service

This project which provides a
[REST](https://de.wikipedia.org/wiki/Representational_State_Transfer) service
for a verinice database. The verinice REST service is a [Spring
Boot](http://projects.spring.io/spring-boot/) application build with
[Maven](https://maven.apache.org/).



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


## Build

### Oracle JDBC Driver

This project depends on Oracle's JDBC driver to be build successfully. It is not
available in Maven Central but only in Oracle's Maven repository
(https://maven.oracle.com).

In order to use it, it is necessary have an Oracle account, acknowledge the
terms of service agreement and configure the local Maven installation with
credentials for accessing the repository. The whole process is [explained in the
Oracle Help
Center](http://docs.oracle.com/middleware/1213/core/MAVEN/config_maven_repo.htm).

An [article](https://blogs.oracle.com/dev2dev/entry/how_to_get_oracle_jdbc) on
Oracle's developer blog explains how to include Oracle's JDBC drivers into a
Maven build, also explaining the details of accessing Oracle's Maven repository.

In short, add the following to your local Maven configuration:

```xml
~/.m2/security-settings.xml

<settingsSecurity>
<master>{maven-master-password}</master>
</settingsSecurity>
```

```xml
~/.m2/settings.xml

<settings>
  <servers>
    <server>
      <id>maven.oracle.com
      </id>
      <username>oracle-account-username</username>
      <password>{oracle-account-password}</password>
      <configuration>
        <basicAuthScope>
          <host>ANY</host>
          <port>ANY</port>
          <realm>OAM 11g</realm>
        </basicAuthScope>
        <httpConfiguration>
          <all>
            <params>
              <property>
                <name>http.protocol.allow-circular-redirects</name>
                <value>%b,true</value>
              </property>
            </params>
          </all>
        </httpConfiguration>
      </configuration>
    </server>
  </servers>
</settings>
```

Substitute `maven-master-password` with the encrypted version of a random
password. Encrypt the password via `mvn -emp`.

Substitute `oracle-account-username` with the user name of your Oracle account
and `oracle-account-password` with an encrypted version of your Oracle account
password. Use `mvn -ep` to encrypt it.

### Test
Integration tests are written in python using [Requests][]
and standard python [unittest](https://docs.python.org/3/library/unittest.html).

[Requesets]: http://docs.python-requests.org/en/latest/ "Requests: HTTP for Humans"
