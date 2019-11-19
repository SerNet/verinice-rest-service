# verinice REST service

This project which provides a
[REST](https://de.wikipedia.org/wiki/Representational_State_Transfer) service
for a verinice database. The verinice REST service is a [Spring
Boot](http://projects.spring.io/spring-boot/) application build with
[Maven](https://maven.apache.org/).

# Run

call e.g.

	mvn spring-boot:run -Drun.arguments="--server.port=8081"

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

## Testing
Integration tests are written in python using [Requests][]
and standard python [unittest][py-unittest].

Since no maven plugin has been found wich could start spring-boot and run the
test against a custom database a shell script has been written to batch several
steps. To run the integration tests execute

```sh
./integration-test
```

The script uses the following variables

- `VERINICEDB` The database name to connect to at localhost.
- `VERINICEUSER` The user which spring should use for the database. Has to exists.
- `VERINICEPASSWORD` The password for the database `$VERINICEUSER`.
- `VERINICEDUMP` The location to a database dumb which shall be used to recreate the `$VERINICEDB`.
- `SPRINGDELAY`  Time to sleep before running the tests to give spring time to boot.

i.e. to run the test against a custom database with a special user run

```sh
VERINICEDB=databasename VERINICEUSER=user VERINICEPASSWORD=password ./integration-test
```

[Requests]: http://docs.python-requests.org/en/latest/ "Requests: HTTP for Humans"
[py-unittest]: https://docs.python.org/3/library/unittest.html "unittest in python"

## Releasing
To release a new version (here 0.1 is assumed) of the project, you should

1. Initially checkout the `develop` branch.
2. Create a new branch `release/0.1`

		git checkout -b release/0.1

3. Until QA gives green light:
	1. Update project version using maven.

			mvn versions:set -DnewVersion=0.1-beta1

	2. Commit, tag and push the release branch with updated version.

			git commit -a
			git push origin release/0.1
			git tag -s 0.1-beta1
			git push origin 0.1-beta1

	3. Checkout the beta tag.

			git checkout 0.1-beta1

	4. Do a deployment build.

			mvn clean package

	5. Put deployment to QA.
	6. If QA fails, fix bugs onto branch `feature/0.1` and jump back to 3.1.
4. Update the project version.

		mvn versions:set -DnewVersion=0.1
		git commit -a
		git push origin release/0.1

5. Merge the release branch to master and tag.

		git checkout master
		git merge --no-ff release/0.1
		git tag -s 0.1

6. Checkout the tag.

		git checkout 0.1

7. Do a deployment build.

		mvn clean package

8. Release deployment.
9. Merge the release branch to develop to get bugfixes.

		git co develop
		git merge --no-ff release/0.1
		git push origin develop

## Misc
To run the create jar with customizations run

	java -jar verinice-rest-0.1.0.jar --spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/verinicedb --server.port=8888¶
