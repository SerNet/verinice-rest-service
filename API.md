## Service

This section describes the methods of the service. The term _element_ in this
article means _CnATreeElement_.

### Load element by UUID

#### URL

* ``/element/{uuid:[a-f0-9]{8}(-[a-f0-9]{4}){3}-[a-f0-9]{12}}``
* Example: ``/element/f35b982c-8ad4-4515-96ee-df5fdd4247b9``
* Notes: The regular expression matches a string of
	* a block of 8 HEX chars followed by
	* 3 blocks of 4 HEX chars followed by
	* a block of 12 HEX chars.

	This is the normalized UUID representation.

#### URL Params
Required:
* ``uuid=[string]``
* Example: ``uuid=f35b982c-8ad4-4515-96ee-df5fdd4247b9``

#### Success Response
* Code: 200
* Content (If an element with the given UUID exists):
```json
{
  "uuid": "f35b982c-8ad4-4515-96ee-df5fdd4247b9",
  "dbid": 123,
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
    ]
  }
}
```

### Load element by database ID

#### URL

* ``/element/{dbid:\d+}``
* Example: ``/element/700``

#### URL Params
Required:
* ``uuid=[long]``
* Example: ``dbid=700``

#### Success Response
* Code: 200
* Content (If an element with the given UUID exists):
```json
{
  "uuid": "f35b982c-8ad4-4515-96ee-df5fdd4247b9",
  "dbid": 123,
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
    ]
  }
}
```

### Load element by source-id and ext-id

#### URL

* ``/elements/source-id/{sourceId}/ext-id/{extId}`
* Example: ``/elements/source-id/SerNet-verinice.PRO-4/ext-id/entity-34523``

#### URL Params
Required:
* source-id/[string]
* Example: source-id/SerNet-verinice.PRO-4


* ext-id/[string]
* Example: ext-id/entity-34523

#### Success Response
* Code: 200
* Content (If an element with the given source-id and ext-id exists):
```json
{
  "uuid": "f35b982c-8ad4-4515-96ee-df5fdd4247b9",
  "dbid": 123,
  "type": "asset",
  "title": "Kundenverwaltungssoftware",
  "srcId": "SerNet-verinice.PRO-4",
  "extId": "entity-34523",
  "parentId": "35675",
  "scopeId": "23567",
  "properties": {
    "asset_value_method_availability": ["1"],
    "asset_name": ["Kundenverwaltungssoftware"],
  }
}
```

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
```json
[{
  "uuid": "bbca9b32-2fa7-4939-87fc-a3c046bcb510",
  "dbid": 123,
  "type": "response_group",
  "title": "Reaktionen",
  "sourceId": null,
  "extId": null,
  "parentId": 373,
  "scopeId": 373,
  "properties": {
  "response_group_name": [
   "Reaktionen"
  ]}
},
{
  "uuid": "f59f1d6c-45ca-435e-a666-b8668969f0e0",
  "dbid": 123,
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
    ]}
}]
```
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
