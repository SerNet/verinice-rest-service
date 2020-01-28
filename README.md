# verinice REST service

This project which provides a
[REST](https://de.wikipedia.org/wiki/Representational_State_Transfer) service
for a verinice database. The verinice REST service is a [Spring
Boot](http://projects.spring.io/spring-boot/) application build with
[Maven](https://maven.apache.org/).

## Run

call e.g.

	mvn spring-boot:run -Dlogging.level.org.springframework=TRACE -Drun.arguments="--server.port=8081"

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
To build the application run

	mvn install

If want to start SpringBoot you can then run

	mvn -pl verinice-rest spring-boot:run

assuming you have proper database setup up, see [application.properties](verinice-rest/src/main/resources/application.properties).

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

## Documentation
The Rest-API is documented using the spring-swagger-framework. To view the
documentation run the application and visit
[localhost:8081/swagger-ui.html](localhost:8081/swagger-ui.html).

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

## Logging
Logging can be change and start time by setting the environment variable family
`logging.level.*`

### Received Requests

	-Dlogging.level.org.springframework.web.servlet=DEBUG

### verinice specific logging

	-Dlogging.level.org.verinice=TRACE

## Misc
To run the created jar with customizations run, e. g.

	java -jar verinice-rest-0.1.0.jar --spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/verinicedb --server.port=8888
