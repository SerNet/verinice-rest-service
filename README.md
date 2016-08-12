# verinice REST service

This project which provides a
[REST](https://de.wikipedia.org/wiki/Representational_State_Transfer) service
for a verinice database. The verinice REST service is a [Spring
Boot](http://projects.spring.io/spring-boot/) application build with
[Maven](https://maven.apache.org/).


## Service

This section describes the methods of the service. The term _element_ in this
article means _CnATreeElement_.

### Load Element

#### URL

* ``/element/{uuid}``
* Example: ``/element/f35b982c-8ad4-4515-96ee-df5fdd4247b9``

#### URL Params
Required:
* ``uuid=[string]``
* Example: ``uuid=f35b982c-8ad4-4515-96ee-df5fdd4247b9``

#### Success Response
* Code: 200
* Content (If an element with the given UUID exists):
      {
        "uuid": "7b254b9a-94f5-4acf-a57b-a53f0982e3f2",
        "type": "asset",
        "title": "Asset (Kopie 4)  (Kopie 2) ",
        "sourceId": null,
        "extId": null,
        "parentId": 405,
        "scopeId": 373,
        "properties": {
          "asset_value_confidentiality": [
            "0"
          ],
          "asset_value_method_confidentiality": [
            "1"
          ],
      }

### Load elements of scope

Load all elements of one scope.

#### URL
* ``/scope/{scopeId}/elements?key={key}&value={value}&size={size}&firstResult={firstResult}``
* Example: ``/scope/23567/elements?key=asset_value_method_availability&value=1&size=10&firstResult=5``

#### Method
``GET``

#### URL Params
Required:
* ``scopeId=[integer]``
* Example: ``scopeId=23567``
Optional:
* ``key=[string]``
* Example: ``key=asset_value_method_availability``
* Hint: %25 (URL Encoding for %) is a place holder for any string (e.g. ``key=%25asset%25``)
* ``value=[string]``
* Example: ``value=1``
* Hint: %25 (URL Encoding for %) is a place holder for any string (e.g. ``key=%25asset%25``)
* ``size=[integer]``
* Example: ``value=100``
* Default: 500, adaptable through property
* ``firstResult=[integer]``
* Example: ``value=5``
* Default: 0, adaptable through property

#### Success Response:
* Code: 200
* Content:
      [{
        "uuid": "bbca9b32-2fa7-4939-87fc-a3c046bcb510",
        "type": "response_group",
        "title": "Reaktionen",
        "sourceId": null,
        "extId": null,
        "parentId": 373,
        "scopeId": 373,
        "properties": {
        "response_group_name": [
         "Reaktionen"
        ]
        }
      },
      {
        "uuid": "f59f1d6c-45ca-435e-a666-b8668969f0e0",
        "type": "asset",
        "title": "Asset (Kopie 1)  (Kopie 3)  (Kopie 2) ",
        "sourceId": null,
        "extId": null,
        "parentId": 405,
        "scopeId": 373,
        "properties": {
          "asset_value_confidentiality": [
            "0"
          ],
          "asset_value_method_confidentiality": [
            "1"
          ],
          "asset_value_method_availability": [
            "1"
          ]
        }
      }]
#### Error Response:
* Code: ``401 UNAUTHORIZED``
* Content: ``{ error : "Log in" }``

### Search elements by property
Search elements by property key and value.

#### URL
* ``/elements?key={key}&value={value}&size={size}&firstResult={firstResult}``
* Example: ``/elements?key=asset_value_method_availability&value=1&size=10&firstResult=5``

#### Method:
``GET``

#### URL Params
Optional:
* ``key=[string]``
* Example: ``key=asset_value_method_availability``
* Hint: %25 (URL Encoding for %) is a place holder for any string (e.g. ``key=%25asset%25``)


* ``value=[string]``
* Example: ``value=1``
* Hint: %25 (URL Encoding for %) is a place holder for any string (e.g. ``key=%25asset%25``)


* ``size=[integer]``
* Example: ``size=100``
* Default: 500, adaptable through property


* ``firstResult=[integer]``
* Example: ``firstResult=5``
* Default: 0, adaptable through property

#### Success Response:
* Code: ``200``
* Content: See capter _Load elements of scope_

#### Error Response:
* Code: ``401 UNAUTHORIZED``
* Content: ``{ error : "Log in" }``

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
          <host>ANY
          </host>
          <port>ANY
          </port>
          <realm>OAM 11g
          </realm>
        </basicAuthScope>
        <httpConfiguration>
          <all>
            <params>
              <property>
                <name>http.protocol.allow-circular-redirects
                </name>
                <value>%b,true
                </value>
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
