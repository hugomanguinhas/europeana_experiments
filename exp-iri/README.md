# Assessment of IRIs in Europeana data

## 1. Introduction

The recent effort to migrate Europeana data to the new Virtuoso triple store has surfaced a problem of invalid [IRIs](https://www.ietf.org/rfc/rfc3987.txt) (the extension to [URIs](https://tools.ietf.org/html/rfc3986) to support characters beyond the ASCII character set) which is presently blocking many metadata records from being published in the new Europeana triple store (see our recent [blogpost](http://labs.europeana.eu/blog/europeana-sparql-endpoint)). In fact, this issue had already been identified by Ontotext upon the first export to the previous triple store, however with a lesser degree.

The reason for this to happen is because many RDF engines and processors perform validation over the IRIs in order to maintain consistency within a data store, causing statements that refer to invalid IRIs to be discarded or, in some cases, to processes being stopped as a result of an runtime error. This last behaviour was found when loading the turtle dumps into the new Virtuoso triple store which is the main reason why many metadata records were not published. This issue is presently being worked on and can be solved by making proper escaping in Turtle which preserves the invalid URIs but allows the Virtuoso parser to load the statements without generating an error, however other RDF engines will still face the same issues.

Besides creating issues to RDF engines and processors, one other side effect of invalid URIs is related to the way that references to resources are recognized in the Europeana database which is blocking some resources from being interlinked. At the moment, the only way to distinguish between a Literal and a reference, within the values of a EDM property, is by checking if it is a valid IRI. This means that resource descriptions that are identified by an invalid IRI cease to be referenceable in the data since all references are otherwise recognized as plain literals. Below is an example of a skos:Concept being used as dc:subject for which there is no explicit link between the property value and the corresponding resource.

```
<rdf:RDF>

  <ore:Proxy rdf:about="http://data.europeana.eu/proxy/provider/2048329/providedCHO_SE_SSA_0005E_A_I_a_1_27_">
    <dc:creator>Svea livgardes församling</dc:creator>
    <dc:date>1818--1827</dc:date>
    <dc:description>Över- och underbefäl.</dc:description>
    <dc:identifier>SE/SSA/0005E/A I a 1/27½</dc:identifier>
    <dc:language>sv</dc:language>
    <dc:subject>context_SE/SSA/0005E/A_I_a_1/27½</dc:subject>
    <dc:title>1818--1827</dc:title>
    <dcterms:isPartOf>providedCHO_SE-SSA_SE/SSA/0005E</dcterms:isPartOf>
    <edm:isNextInSequence rdf:resource="providedCHO_SE/SSA/0005E/A_I_a_1/17"/>
    <edm:europeanaProxy>false</edm:europeanaProxy><ore:proxyFor rdf:resource="http://data.europeana.eu/item/2048329/providedCHO_SE_SSA_0005E_A_I_a_1_27_"/>
    <ore:proxyIn rdf:resource="http://data.europeana.eu/aggregation/provider/2048329/providedCHO_SE_SSA_0005E_A_I_a_1_27_"/>
    <edm:type>TEXT</edm:type>
  </ore:Proxy>

  <skos:Concept rdf:about="context_SE/SSA/0005E/A_I_a_1/27½">
    <skos:prefLabel>Svea livgardes församlings kyrkoarkiv >> Svea livgardes församlings kyrkoarkiv >> Husförhörslängder. Huvudserie</skos:prefLabel>
  </skos:Concept>

</rdf:RDF>
```

*The purpose of this work was therefore to conduct an investigation over the full Europeana dataset in order to assess the extent of the issues related to invalid IRI and recognize the different patterns that originate them in the hope that they can be reported back to the data provider but also be corrected or minimized in the data so that software agents do not have to struggle when consuming Europeana data.*

This github project contains the source code that was developed to collect the necessary information and process it for this investigation. 

Contact: Hugo Manguinhas (hugo.manguinhas@europeana.eu)

## 2. Methodology

This investigation was run directly on the R&D MongoDB (a replica from the production MongoDB) since only a subset of data was needed and we needed the data to be as close as possible to the data stored in the database.

Early in this investigation, it was also recognized that the reasons and ways to tackle invalid IRIs could be different depending if the references were referring to a media resource (edm:WebResource) or a contextual entities (ie. edm:Agent, edm:Place, skos:Concept, edm:TimeSpan) which resulted into two separate results as shown in Section 3. This also meant that only IRIs that identify resources for which there is a resource description stored in the database were subject of investigation.

To analyse in detail the different types of IRI issues, Jena IRIFactory implementation ([org.apache.jena.iri.IRIFactory](https://svn.apache.org/repos/asf/jena/Import/Jena-CVS/Scratch/trunk/iri/doc/javadoc/com/hp/hpl/jena/iri/IRIFactory.html)) was chosen as it is currently the most complete (supports the validation against different specifications) and detailed IRI validator for Java. The default IRI validation was chose for this assessment using the weakest validation level (ignoring warnings), which validates against the following standard specifications:

* [Unicode](http://www.unicode.org/)
* [RFC 3986: Uniform Resource Identifier (URI): Generic Syntax](http://www.apps.ietf.org/rfc/rfc3986.html)
* [RFC 3987: Internationalized Resource Identifiers (IRIs)](http://www.apps.ietf.org/rfc/rfc3987.html)
* [Extensible Markup Language (XML) 1.0 (Third Edition) (section system identifier)](http://www.w3.org/TR/2004/REC-xml-20040204/#dt-sysid)
* [Resource Description Framework (RDF): Concepts and Abstract Syntax (section RDF URI References)](http://www.w3.org/TR/2004/REC-rdf-concepts-20040210/#section-Graph-URIref)
* [XML Linking Language (XLink) Version 1.0 (section Locator Attribute (href))](http://www.w3.org/TR/2001/REC-xlink-20010627/#link-locators)
* [XML Schema Part 2: Datatypes Second Edition (section anyURI)](http://www.w3.org/TR/2004/REC-xmlschema-2-20041028/#anyURI)

Besides the IRIFactory, also the Java URI ([java.net.URI](https://docs.oracle.com/javase/8/docs/api/java/net/URI.html)) built-in validation was used to compare the two implementations (see Section 3.1) and also determine the situations where a IRI would be exposed or not as a resource reference in the Europeana API.

## 3. Results

This section presents the results of this investigation. The tables in Section 3.1. And 3.2. show two additional columns indicating the actions that could be taken on how the issues could be reported back to the data provider and also if actions could be taken on Europeana side as well to correct or shadow the invalid IRIs.


### 3.1. Comparison between IRIFactory and Java URI

The table below makes a comparison between the results of using Java URI and IRIFactory for validation of IRIs using the violation categories of IRIFactory as the base for comparison. The “specs” column indicates the standard specifications that were violated (since some specifications inherit rules from others, only the topmost specification which originally introduced the rule is indicated).

| Violation | Meaning | Specs | Java URI valid? | IRIFactory valid? |
| --- | --- | --- | --- | --- |
| COMPATIBILITY_CHARACTER | Characters in the [compatibility area](https://en.wikipedia.org/wiki/Unicode_compatibility_characters) (i.e. with character code greater than \uF900 and less than \uFFFE) are not allowed in XML names. | Unicode, IRI | yes | no |
|  CONTROL_CHARACTER | Flagged when IRIs contain control characters (#00-07, #7F) as part of the path. | RDF, URI, IRI | no | no |
| DEFAULT_PORT_SHOULD_BE_OMITTED | Flagged when the default port number (e.g. 80 for HTTP) is exposed as part of the IRI | URI | yes | no |
| DOUBLE_WHITESPACE | Flagged when the path component of an IRI contains more than 1 whitespace in sequence. | URI, IRI, Schema | no | no |
| ILLEGAL_CHARACTER | Flagged when a character within an URI/IRI violates the grammar rules | URI, IRI | yes | no |
| LOWERCASE_PREFERRED | Flagged when a lower case character is preferred for a given IRI component, namely the scheme and host. | URI | yes | no |
| NON_INITIAL_DOT_SEGMENT | Flagged when the path of an IRI contains a segment "/../" not at the beginning of a relative reference, or it contains a "/./". | URI | yes | no | 
| NOT_DNS_NAME | Flagged when the host component of an IRI does not meet the syntax restrictions on DNS names | URI | no | no | 
| NOT_NFKC | Flagged when IRIs are not in the Unicode Normal Form KC (i.e. compatibility Decomposition followed by canonical composition), see section 5.3.2.2 of the RFC 3987. Examples are fullwidth characters being represented by halfwidth equivalents, superscripted characters by the plain characters, etc. | IRI | yes | no |
| NOT_XML_SCHEMA_WHITESPACE | Flagged when IRIs contain whitespace characters that are not permitted within XML Schema anyURIs, such as tab and new line characters, and consecutive space characters. | URI | no | no | 
| PERCENT_ENCODING_SHOULD_BE_UPPERCASE | Flagged when character escapes are represented in lowercase as opposed to upper case as defined in the URI spec | URI | yes | no | 
| PORT_SHOULD_NOT_BE_EMPTY | Flagged when the colon introducing a port component is empty | URI | yes | no |
| PORT_SHOULD_NOT_BE_WELL_KNOWN | Flagged when a [well known port](https://tools.ietf.org/html/rfc6335) component (ie. 0-1023) is advertised as part of the URL | URI | yes | no |
| PORT_SHOULD_NOT_START_IN_ZERO | Flagged when the port number contains leading zeros | none | yes | no | 
| PRIVATE_USE_CHARACTER | Flagged when characters from the [private area of Unicode](https://en.wikipedia.org/wiki/Private_Use_Areas) are used. | Unicode, IRI | yes | no | 
| REQUIRED_COMPONENT_MISSING | Flagged when some component of the URL syntax is missing, e.g. host, protocol | URI | yes / no (depends on the component) | no |
| SCHEME_PATTERN_MATCH_FAILED | Flagged when the pattern of the IRI does not match the one specified by the scheme | URI + IANA | yes | no | 
| UNREGISTERED_IANA_SCHEME | Flagged when the protocol scheme is not recognized by IANA (see http://www.iana.org/assignments/uri-schemes/uri-schemes.xhtml) | URI + IANA | yes | no |
| UNWISE_CHARACTER | Flagged when URIs use unwise characters (e.g. |,<,>,",`) as defined in rfc3987. The whitespace is reported as a separate issue. | URI | no | no |
| WHITESPACE | Flagged when URIs have whitespaces in the component part of the scheme | URI | no | no |

### 3.2. Evaluation of IRIs in Media Resources

The table below shows the results and analysis for references to media resources. It is also available as a [Google spreadsheet](https://docs.google.com/spreadsheets/d/1oUGWFQym8aWCBYYu6-h_rtMy6rxLP3peuHG4LeVNwto) where comments can be made to the assessment.

| Violation | Count | Notes | Reporting | Correcting |
| --- | --- | --- | --- | --- |
| [COMPATIBILITY_CHARACTER](src/test/resources/etc/data/wr/iris_wr_report.COMPATIBILITY_CHARACTER.csv) | 3117 | Typically cases using the character "´" which should be escaped. | flag as warning | Escape violating characters |
| [CONTROL_CHARACTER](src/test/resources/etc/data/wr/iris_wr_report.CONTROL_CHARACTER.csv) | 9 | Some IRIs are unresolvable, but surprisingly others are resolvable if escaped. | flag as error | Escape violating characters |
| [DEFAULT_PORT_SHOULD_BE_OMITTED](src/test/resources/etc/data/wr/iris_wr_report.DEFAULT_PORT_SHOULD_BE_OMITTED.csv) | 27423 | It is advised to omit it. | flag as warning | Remove default port |
| [DOUBLE_WHITESPACE](src/test/resources/etc/data/wr/iris_wr_report.DOUBLE_WHITESPACE.csv) | 5880 | Several cases fall under this pattern. There are IRIs for which escaping sequential white spaces into just one white space escape (typical behaviour for software agents) allow the IRI to be resolved, others where all white spaces need to be espaced, and yet others which cannot be resolvable as they are typically the result of a wrong mapping or typo. However, the last cases may be spotted with some specific rules. | flag as error | Escape violating characters whithout truncation |
| [ILLEGAL_CHARACTER](src/test/resources/etc/data/wr/iris_wr_report.ILLEGAL_CHARACTER.csv) | 1218 | Most (if not all) of the IRIs are not resolvable, and some of which result in an error when the host service tries to look-up for them. | flag as warning | Escape violating characters |
| [LOWERCASE_PREFERRED](src/test/resources/etc/data/wr/iris_wr_report.LOWERCASE_PREFERRED.csv) | 27237 | Typically cases where the protocol or host name is in upper case which is advised to be in lower case. Also a lot of cases for empty URL such as "Http://". Also found cases of URL pointing just to the host: Http://www.interscience.wiley.com, Http://www.cser.it, HTTP://WWW.MULINO.IT which presumably don't link to a web resource | flag as warning | Convert to lower case only the specific components |
| [NON_INITIAL_DOT_SEGMENT](src/test/resources/etc/data/wr/iris_wr_report.NON_INITIAL_DOT_SEGMENT.csv) | 148 | Only one of the IRIs is actually resolvable, all others seem to be the result of a wrong mapping or typo, with a significant number of duplicate values. Software agents typically process the segments into a canonical form, removing the relative references. | flag as warning (but tempted to flag as error due to the high probably of being an actual error) | Process segments into the canonical form |
| [NOT_DNS_NAME](src/test/resources/etc/data/wr/iris_wr_report.NOT_DNS_NAME.csv) | 2 | Both cases reflect a typo (extra whitespace as part of the DNS). In such cases, removing the character is sufficient to make it resolvable. | flag as error | Remove extra whitespaces in the component |
| [NOT_NFKC](src/test/resources/etc/data/wr/iris_wr_report.NOT_NFKC.csv) | 3117 | All IRIs are resolvable, however the | flag as warning | Do nothing (decomposition will likely make the IRI not resolvable) |
| [NOT_XML_SCHEMA_WHITESPACE](src/test/resources/etc/data/wr/iris_wr_report.NOT_XML_SCHEMA_WHITESPACE.csv) | 3 | If escaped, the IRIs can potentially be resolved. However this did not happen for the cases that were detected. | flag as error | Escape characters |
| [PERCENT_ENCODING_SHOULD_BE_UPPERCASE](src/test/resources/etc/data/wr/iris_wr_report.PERCENT_ENCODING_SHOULD_BE_UPPERCASE.csv) | 62274 | | flag as warning | Convert all character escapes to upper case. |
| [PORT_SHOULD_NOT_BE_EMPTY](src/test/resources/etc/data/wr/iris_wr_report.PORT_SHOULD_NOT_BE_EMPTY.csv) | 9 | The cases found are typically the result of a wrong mapping and are only found in the same dataset, and obviously, for such cases the IRIs are not resolvable. However, there could be situations where removing a possible dangling colon may be sufficient. | flag as a warning (but tempted to flag as error due to the high probably of being an actual error) | Remove port component |
| [PORT_SHOULD_NOT_BE_WELL_KNOWN](src/test/resources/etc/data/wr/iris_wr_report.PORT_SHOULD_NOT_BE_WELL_KNOWN.csv) | 42075 | flag as warning | Do nothing |
| [PORT_SHOULD_NOT_START_IN_ZERO](src/test/resources/etc/data/wr/iris_wr_report.PORT_SHOULD_NOT_START_IN_ZERO.csv) | 1 | e.g. 0080 | Do not flag | Remove leading zeros |
| [PRIVATE_USE_CHARACTER](src/test/resources/etc/data/wr/iris_wr_report.PRIVATE_USE_CHARACTER.csv) | 1 | In the only example, the image is still accessible when removing the character. | flag as warning | Remove character |
| [REQUIRED_COMPONENT_MISSING](src/test/resources/etc/data/wr/iris_wr_report.REQUIRED_COMPONENT_MISSING.csv) | 4382 | Basically the issues reflect typos in the URL. Obviously all IRIs are unresolvable. | flag as error | Some IRIs can be corrected, others removed depending on kind of issue. |
| [SCHEME_PATTERN_MATCH_FAILED](src/test/resources/etc/data/wr/iris_wr_report.SCHEME_PATTERN_MATCH_FAILED.csv) | 9352 | Only found for URN:NBN identitiers (see https://www.ietf.org/rfc/rfc3188.txt). | Requires further investigation | Pending further investigation |
| [UNREGISTERED_IANA_SCHEME](src/test/resources/etc/data/wr/iris_wr_report.UNREGISTERED_IANA_SCHEME.csv.gz) | 938619 | "The validator incorrectly identified MMS and RTMP protocols (perhaps because they are still provisional) as being unregistered. However, it do identified IID has being unregistered but additionaly only protocols related to media resources should be used. Additionally within this category, there were some issues surfaced related to duplicate links (e.g. see rtmp://www.santacecilia.it/vod_etnomusica_preview) and wrong protocols (hthttp, hhttp, ttp) which presumably come from a wrong mapping or typo. | flag as error | Some obviously wrong IRIs can be detected and corrected |
| [UNWISE_CHARACTER](src/test/resources/etc/data/wr/iris_wr_report.UNWISE_CHARACTER.csv.gz) | 1173625 | Within this category two situations happen, one where the IRI actually has such characters and is resolvable since they are typically escaped by software agents (see 0750 dataset), and another where it is typically the result of a bad mapping or typo (e.g. IRIs ending with "/>"). | flag as error | Escape violating characters |
| [WHITESPACE](src/test/resources/etc/data/wr/iris_wr_report.WHITESPACE.csv.gz) | 600698 | As opposed to UNWISE_CHARACTER, the tendency is for IRIs to be resolvable (if properly escaped), and not the result of an error. | flag as warning | Escape violating characters |

### 3.3. Evaluation of IRIs in Contextual Entities

The table below shows the results and analysis for references to contextual entities. It is also available as a [Google spreadsheet](https://docs.google.com/spreadsheets/d/1nd4c1K1vh3BgO3D8Fy5Z_5K9Ts08KXXmtBMEit2ynis) where comments can be made to the assessment.

| Violation | Count | Notes | Reporting | Correcting |
| --- | --- | --- | --- | --- |
| [COMPATIBILITY_CHARACTER](src/test/resources/etc/data/entities/iris_entities_report.COMPATIBILITY_CHARACTER.csv) | 1180 | All occurrences are for iconclass IRIs. Requires further investigation on Iconclass. | Pending further investigation |
| [NOT_NFKC](src/test/resources/etc/data/entities/iris_entities_report.NOT_NFKC.csv) | 18 | Same cases as for COMPATIBILITY_CHARACTER | flag as warning | Escape violating characters |
| [SCHEME_PATTERN_MATCH_FAILED](src/test/resources/etc/data/entities/iris_entities_report.SCHEME_PATTERN_MATCH_FAILED.csv) | 9352 | All occurrences are for NBN identifiers. Requires further investigation on NBN identifiers. | Pending further investigation |
| [UNREGISTERED_IANA_SCHEME](src/test/resources/etc/data/entities/iris_entities_report.UNREGISTERED_IANA_SCHEME.csv) | 931865 | The two patterns for GEO and OAI were incorrectly identified (would need to be added to the IRI validator). All remaining occurrences are for IID identifiers which would need to be added as well. | flag as error (when incorrect) | |
| [UNWISE_CHARACTER](src/test/resources/etc/data/entities/iris_entities_report.UNWISE_CHARACTER.csv) | 131 | Most occurrences are for Iconclass where characters were not escaped. The remainder are for DNB identifiers that have some dangling characters at the end which may be the result of a bad mapping or typo. | flag as error | Escape violating characters. The obviously wrong IRIs can also be detect and correct. |
| [WHITESPACE](src/test/resources/etc/data/entities/iris_entities_report.WHITESPACE.csv) | 75002 | Most occurrences are for Iconclass. The remainder are in fact incorrect URIs which appear to be the result of some wrong mapping or typo. | flag as warning (also include patterns to detect incorrect URIs so that they can be flagged as errors) | Escape violating characters, and consider patterns to detect and correct wrong IRIs. |

## 4. Usage

TODO