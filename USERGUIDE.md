# verinice.REST-Userguide
This document describes how to customize and run the verinice.REST.
See [README.md](README.md) for how to build the verinice.REST.  You can
also retrieve the complete JAR from the [release page](https://github.com/SerNet/verinice.REST/releases)

## Run the verinice.REST
verinice.REST comes as a self-contained jar, i. e. no further
dependencies are required except a JRE-8 or later and a running
[verinice](https://verinice.com/) installation.

You can run verinice.REST by calling

	java -jar verinice-rest-XXX.jar

Replace XXX with the proper version string.

## Configuration
The default configuration for verinice.REST is stored in
[application.properties](verinice-rest/src/main/resources/application.properties)

Every property can be overwritten by passing it as a option to the Java call,
e. g.

	java -jar verinice-rest-XXX.jar \
		--server.port=8888 \
		--spring.datasource.url=jdbc:postgresql://verinicedb.local.example:5432/customdb \
		--spring.datasource.username=verinice-rest \
		--spring.datasource.password=********
		--veriniceserver.url=https://verinice.local.example:84577 \

tells verinice.REST to listen to port *8888*. It then open a database
connection to *verinicedb.local.example:5432/customdb* authenticating as
*verinice-rest* using the given password.

*veriniceserver.url=https://verinice.local.example:84577* is the URL to your
verinice intallation and is only needed if you intend to create links.
verinice.REST uses this address to retrieve information (SNCA.xml) from
verinice to validate data. Creating links requires a verinice 1.20 or later.

**NOTE** It is highly recommended to create a technical user, e. g.
*verinice-rest*, which has to be configured, s. a. This user has to be given
reas and write access to scopes within verinice, in order to change them via
verinice.REST.

## Logging
Logging can be change and start time by setting the environment variable family
`logging.level.*`. For example you can set the logging level for every
component to `DEBUG` by running verinice.REST with the following
argument:

	java -jar verinice-rest-XXX.jar -Dlogging.level=DEBUG

Possible levels include

- `DEBUG`
- `INFO`
- `WARN`
- `ERROR`
- `FATAL`
- `TRACE`

The next sections give some examples of logging components.

### Received Requests

	-Dlogging.level.org.springframework.web.servlet=DEBUG

### verinice specific logging

	-Dlogging.level.org.verinice=TRACE

