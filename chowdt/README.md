# Linking Europeana Collection to Wikidata

## Introduction

As a result of projects such as Europeana Sounds and Europeana Art 280, as well
as other initiatives such as the Wikidata project [Sum of All Paintings]
(https://www.wikidata.org/wiki/Wikidata:WikiProject_sum_of_all_paintings), data 
in Wikidata is referring more and more to objects in the Europeana collection.

This github project contains the source code that was developed to extract
data from Wikidata and generate annotations that will link Europeana objects to
Wikidata entries.

Contact: Hugo Manguinhas (hugo.manguinhas@europeana.eu)

## More on the projects

In [Europeana Sounds](http://www.europeanasounds.eu/), audio recordings are 
uploaded to Wikimedia Commons and where suitable the sounds will be referenced 
from Wikidata and used in Wikipedia articles.

In [Europeana 280](http://pro.europeana.eu/pressrelease/europeana-280-art-from-the-28-countries-of-europe), new Wikidata entities and existing entities representing artworks 
will be created and/or updated based on Europeana metadata. Corresponding 
Wikipedia articles will also be updated. The process has begun at small 
scale already, https://www.wikidata.org/wiki/User:Multichill/Europeana_280_list

## Methodology

The following major steps were performed for this experiment:

**1. Harvest wikidata links to CHOs from SPARQL endpoint**

The wikidata links to CHO resources were harvested from the Wikidata main
SPARQL endpoint available at ```http://query.wikidata.org/sparql```. This was
done by querying for all resources being linked through the 
[Europeana ID](http://www.wikidata.org/entity/P727) property using this SPARQL 
query 
```SELECT ?wdt ?cho WHERE { ?wdt wdt:P727 ?cho }```. All the links obtained
from Wikidata were stored in this 
[file](../src/test/resources/etc/chowdt/links.csv).

**2. Filter links referring to invalid resources**

After harvesting the links from Wikidata, we identified that there were links
referring to resources that had been deleted for which it would not make sense
to create an annotation. Given this, an intermediate step was introduced to
filter out all links which were referring to invalid resources. A resource was
found as invalid if a HTTP 404 was returned when dereferencing the URI. All 
invalid links identified in this step were stored in this 
[file](../src/test/resources/etc/chowdt/links_invalid.csv).

**3. Identifying duplicate links (or resources)**

We also found, after harvesting, that for some links either the CHO or the 
Wikidata resource was being referred twice, which can indicate that 
two of the resource (within the same dataset, i.e. Europeana collection or 
Wikidata) are potential duplicates (see Section on Duplicates).
 
 **4. Create annotations using the Annotation API**

Finally, an Annotation was created for all links that were found valid by 
calling the Annotation API and using the creation method. The semantic tagging
scenario was chosen for representing the annotation since the object linking
is not yet available. However, this should be changed once this method becomes
available. Below is an example of the generated annotation:
```

```

## Wikidata Links

This sections gives an overview of the sort of links that were obtained from
Wikidata. It shows only the ones for the Europeana 280 Project.


| Europeana Object | Wikidata Entry | Annotation |
| :--- | :--- | :--- |
[Death Playing Chess](http://data.europeana.eu/item/2063602/SWE_280_001) | [Death playing chess](http://www.wikidata.org/entity/Q21257918) | [184](http://test-annotations.europeana.eu/annotation/webanno/184?wskey=apidemo) |
[Rudolph II as Vertumnus](http://data.europeana.eu/item/2063602/SWE_280_002) | [Vertumnus](http://www.wikidata.org/entity/Q16333797) | [185](http://test-annotations.europeana.eu/annotation/webanno/185?wskey=apidemo) |
[The Kitchen Maid](http://data.europeana.eu/item/2063602/SWE_280_003) | [The Kitchen Maid](http://www.wikidata.org/entity/Q18089111) | [186](http://test-annotations.europeana.eu/annotation/webanno/186?wskey=apidemo) |
[Double Portrait](http://data.europeana.eu/item/2063602/SWE_280_004) | [Double Portrait](http://www.wikidata.org/entity/Q21743231) | [187](http://test-annotations.europeana.eu/annotation/webanno/187?wskey=apidemo) |
[Portrait of a Violinist](http://data.europeana.eu/item/2063602/SWE_280_005) | [Portrait of a violinist](http://www.wikidata.org/entity/Q21260816) | [188](http://test-annotations.europeana.eu/annotation/webanno/188?wskey=apidemo) |
[Adoration of the Magi](http://data.europeana.eu/item/2063602/SWE_280_006) | [Adoration of the Magi](http://www.wikidata.org/entity/Q21743263) | [189](http://test-annotations.europeana.eu/annotation/webanno/189?wskey=apidemo) |
[Passionate Lovers](http://data.europeana.eu/item/2063602/SWE_280_007) | [Passionate lovers](http://www.wikidata.org/entity/Q21260375) | [190](http://test-annotations.europeana.eu/annotation/webanno/190?wskey=apidemo) |
[Boys Drawing](http://data.europeana.eu/item/2063602/SWE_280_008) | [Boys drawing](http://www.wikidata.org/entity/Q21258327) | [191](http://test-annotations.europeana.eu/annotation/webanno/191?wskey=apidemo) |
[The Artist Venny Soldan-Brofeldt](http://data.europeana.eu/item/2063602/SWE_280_009) | [Venny Soldan-Brofeldt, Artist](http://www.wikidata.org/entity/Q21259109) | [192](http://test-annotations.europeana.eu/annotation/webanno/192?wskey=apidemo) |
[The Town](http://data.europeana.eu/item/2063602/SWE_280_010) | [The Town](http://www.wikidata.org/entity/Q18600103) | [193](http://test-annotations.europeana.eu/annotation/webanno/193?wskey=apidemo) |
[Karin at the Shore](http://data.europeana.eu/item/2063602/SWE_280_011) | [Karin by the shore](http://www.wikidata.org/entity/Q20064873) | [194](http://test-annotations.europeana.eu/annotation/webanno/194?wskey=apidemo) |
[Tuvstarr Still Sitting There and Staring Wonderingly into the Water](http://data.europeana.eu/item/2063602/SWE_280_012) | [Tuvstarr is still sitting there wistfully looking into the water](http://www.wikidata.org/entity/Q19887156) | [195](http://test-annotations.europeana.eu/annotation/webanno/195?wskey=apidemo) |
[The Dying Dandy](http://data.europeana.eu/item/2063602/SWE_280_013) | [The dying dandy](http://www.wikidata.org/entity/Q10468599) | [196](http://test-annotations.europeana.eu/annotation/webanno/196?wskey=apidemo) |
[The Horse Breaker](http://data.europeana.eu/item/2063602/SWE_280_014) | [The Horse Breaker](http://www.wikidata.org/entity/Q21746928) | [197](http://test-annotations.europeana.eu/annotation/webanno/197?wskey=apidemo) |
[The Mother of God of Trakai](http://data.europeana.eu/item/2063603/LIT_280_001) | [?](http://www.wikidata.org/entity/Q21755439) | [198](http://test-annotations.europeana.eu/annotation/webanno/198?wskey=apidemo) |
[Persian Envoys before the King of Ethiopia](http://data.europeana.eu/item/2063603/LIT_280_002) | [Persian Envoys before the King of Ethiopia](http://www.wikidata.org/entity/Q22121282) | [199](http://test-annotations.europeana.eu/annotation/webanno/199?wskey=apidemo) |
[Settlers](http://data.europeana.eu/item/2063603/LIT_280_003) | [The New Settlers](http://www.wikidata.org/entity/Q21758870) | [200](http://test-annotations.europeana.eu/annotation/webanno/200?wskey=apidemo) |
[Lithuanian Girl with Palm Sunday Fronds](http://data.europeana.eu/item/2063603/LIT_280_004) | [Lithuanian girl with Palm Sunday Fronds](http://www.wikidata.org/entity/Q21771319) | [201](http://test-annotations.europeana.eu/annotation/webanno/201?wskey=apidemo) |
[All Through the Night](http://data.europeana.eu/item/2063603/LIT_280_005) | [All Through the Night](http://www.wikidata.org/entity/Q21771321) | [202](http://test-annotations.europeana.eu/annotation/webanno/202?wskey=apidemo) |
[The White Apple Tree](http://data.europeana.eu/item/2063603/LIT_280_006) | [The White Apple Tree](http://www.wikidata.org/entity/Q21771325) | [203](http://test-annotations.europeana.eu/annotation/webanno/203?wskey=apidemo) |
[Sonata No. 6 (Sonata of the Stars). Allegro. Andante](http://data.europeana.eu/item/2063603/LIT_280_007) | [Sonata of the Stars](http://www.wikidata.org/entity/Q21771385) | [204](http://test-annotations.europeana.eu/annotation/webanno/204?wskey=apidemo) |
[Mural paintings at the Chapel of Saint Casimir of Vilnius Cathedral](http://data.europeana.eu/item/2063603/LIT_280_008) | [Mural paintings at the Chapel of Saint Casimir](http://www.wikidata.org/entity/Q22121207) | [205](http://test-annotations.europeana.eu/annotation/webanno/205?wskey=apidemo) |
[Scenery of Lithuanian Village](http://data.europeana.eu/item/2063603/LIT_280_009) | [Scenery of a Lithuanian Village](http://www.wikidata.org/entity/Q21771817) | [206](http://test-annotations.europeana.eu/annotation/webanno/206?wskey=apidemo) |
[Woman with the Artist](http://data.europeana.eu/item/2063603/LIT_280_010) | [Woman with the Artist](http://www.wikidata.org/entity/Q21771910) | [207](http://test-annotations.europeana.eu/annotation/webanno/207?wskey=apidemo) |
[Scythian Messengers Meet the Persian King Darius I](http://data.europeana.eu/item/2063603/LIT_280_011) | [Scythian Messengers Meet the Persian King Darius I](http://www.wikidata.org/entity/Q22121520) | [208](http://test-annotations.europeana.eu/annotation/webanno/208?wskey=apidemo) |
[View from the artist's room](http://data.europeana.eu/item/2063604/DEN_280_001) | [View from the Artist's Window](http://www.wikidata.org/entity/Q18359104) | [209](http://test-annotations.europeana.eu/annotation/webanno/209?wskey=apidemo) |
[Portrait Group with the Artist’s Father Amilcare Anguissola, Brother Astrubale and Sister Minerva](http://data.europeana.eu/item/2063604/DEN_280_002) | [Portrait Group with the Artist’s Father, Brother and Sister](http://www.wikidata.org/entity/Q21204021) | [210](http://test-annotations.europeana.eu/annotation/webanno/210?wskey=apidemo) |
[Tupi woman](http://data.europeana.eu/item/2063604/DEN_280_003) | [Tupi woman](http://www.wikidata.org/entity/Q21748988) | [211](http://test-annotations.europeana.eu/annotation/webanno/211?wskey=apidemo) |
[The mysterious wedding in Pistoia](http://data.europeana.eu/item/2063604/DEN_280_004) | [The Mystical Wedding between the Bishop and the Abbess of Pistoia](http://www.wikidata.org/entity/Q21747000) | [212](http://test-annotations.europeana.eu/annotation/webanno/212?wskey=apidemo) |
[Nightmare](http://data.europeana.eu/item/2063604/DEN_280_005) | [Nightmare](http://www.wikidata.org/entity/Q21747078) | [213](http://test-annotations.europeana.eu/annotation/webanno/213?wskey=apidemo) |
[Sct. Hans Evening at Vejle Fiord](http://data.europeana.eu/item/2063604/DEN_280_006) | [Midsummer Night near Vejle Fjord](http://www.wikidata.org/entity/Q21747106) | [214](http://test-annotations.europeana.eu/annotation/webanno/214?wskey=apidemo) |
[A nude woman doing her hair before a mirror](http://data.europeana.eu/item/2063604/DEN_280_007) | [A nude woman doing her hair before a mirror](http://www.wikidata.org/entity/Q6644913) | [215](http://test-annotations.europeana.eu/annotation/webanno/215?wskey=apidemo) |
[Dolmen at Raklev, Røsnæs](http://data.europeana.eu/item/2063604/DEN_280_008) | [A burial mound from ancient times by Raklev on Refsnæs](http://www.wikidata.org/entity/Q21747156) | [216](http://test-annotations.europeana.eu/annotation/webanno/216?wskey=apidemo) |
[A christening](http://data.europeana.eu/item/2063604/DEN_280_009) | [A Baptism](http://www.wikidata.org/entity/Q21747201) | [217](http://test-annotations.europeana.eu/annotation/webanno/217?wskey=apidemo) |
[Self-portrait with soft hat](http://data.europeana.eu/item/2063604/DEN_280_010) | [Self-portrait with soft hat](http://www.wikidata.org/entity/Q21747212) | [218](http://test-annotations.europeana.eu/annotation/webanno/218?wskey=apidemo) |
[Ten Women from Stralsund (in contemporary attire)](http://data.europeana.eu/item/2063604/DEN_280_011) | [Ten Women of Stralsund](http://www.wikidata.org/entity/Q21204267) | [219](http://test-annotations.europeana.eu/annotation/webanno/219?wskey=apidemo) |
[A Young Artist (Ditlev Blunck) Examining a Sketch in a Mirror](http://data.europeana.eu/item/2063604/DEN_280_012) | [A Young Artist (Ditlev Blunck) Examining a Sketch in a Mirror](http://www.wikidata.org/entity/Q20278308) | [220](http://test-annotations.europeana.eu/annotation/webanno/220?wskey=apidemo) |
[The Sculptor Jens Adolf Jerichau, the Artist's Husband](http://data.europeana.eu/item/2063604/DEN_280_013) | [The Sculptor Jens Adolf Jerichau, the Artist's Husband](http://www.wikidata.org/entity/Q20440955) | [221](http://test-annotations.europeana.eu/annotation/webanno/221?wskey=apidemo) |
[The Artist's Mother Ane Hedvig Brøndum in the Blue Room](http://data.europeana.eu/item/2063604/DEN_280_014) | [The Artist's Mother Ane Hedvig Brøndum in the Blue Room](http://www.wikidata.org/entity/Q20438955) | [222](http://test-annotations.europeana.eu/annotation/webanno/222?wskey=apidemo) |
[Amalienborg Square, Copenhagen](http://data.europeana.eu/item/2063604/DEN_280_015) | [Amalienborg Square, Copenhagen](http://www.wikidata.org/entity/Q20354158) | [223](http://test-annotations.europeana.eu/annotation/webanno/223?wskey=apidemo) |
[At the French Windows. The Artist's Wife](http://data.europeana.eu/item/2063604/DEN_280_016) | [At the French Windows. The Artist's Wife](http://www.wikidata.org/entity/Q20537703) | [224](http://test-annotations.europeana.eu/annotation/webanno/224?wskey=apidemo) |
[Faun and Nymph](http://data.europeana.eu/item/2063604/DEN_280_017) | [Faun and Nymph](http://www.wikidata.org/entity/Q20540698) | [225](http://test-annotations.europeana.eu/annotation/webanno/225?wskey=apidemo) |
[Worn Out](http://data.europeana.eu/item/2063604/DEN_280_018) | [Worn Out](http://www.wikidata.org/entity/Q21096635) | [226](http://test-annotations.europeana.eu/annotation/webanno/226?wskey=apidemo) |
[Evening Game in Svanninge Hills](http://data.europeana.eu/item/2063604/DEN_280_019) | [Evening Play in Svanninge Hills](http://www.wikidata.org/entity/Q21748949) | [227](http://test-annotations.europeana.eu/annotation/webanno/227?wskey=apidemo) |
[Summer evening on Skagen's Southern Beach](http://data.europeana.eu/item/2063604/DEN_280_020) | [Summer Evening on Skagen's Southern Beach](http://www.wikidata.org/entity/Q18085208) | [228](http://test-annotations.europeana.eu/annotation/webanno/228?wskey=apidemo) |
[Jason with the Golden Fleece](http://data.europeana.eu/item/2063604/DEN_280_021) | [Jason with the Golden Fleece](http://www.wikidata.org/entity/Q4563532) | [229](http://test-annotations.europeana.eu/annotation/webanno/229?wskey=apidemo) |
[Victory](http://data.europeana.eu/item/2063604/DEN_280_022) | [Victory](http://www.wikidata.org/entity/Q22928187) | [230](http://test-annotations.europeana.eu/annotation/webanno/230?wskey=apidemo) |
[Young Gypsy Woman](http://data.europeana.eu/item/2063605/LAT_280_001) | [Young Gypsy Woman](http://www.wikidata.org/entity/Q21162216) | [231](http://test-annotations.europeana.eu/annotation/webanno/231?wskey=apidemo) |
[The Gauja Valley](http://data.europeana.eu/item/2063605/LAT_280_002) | [The Gauja Valley](http://www.wikidata.org/entity/Q22055510) | [232](http://test-annotations.europeana.eu/annotation/webanno/232?wskey=apidemo) |
[Princess with a Monkey](http://data.europeana.eu/item/2063605/LAT_280_003) | [Princess with a Monkey](http://www.wikidata.org/entity/Q22043968) | [233](http://test-annotations.europeana.eu/annotation/webanno/233?wskey=apidemo) |
[Winter](http://data.europeana.eu/item/2063605/LAT_280_004) | [Winter](http://www.wikidata.org/entity/Q22056352) | [234](http://test-annotations.europeana.eu/annotation/webanno/234?wskey=apidemo) |
[Bathing Boys](http://data.europeana.eu/item/2063605/LAT_280_005) | [Bathing Boys](http://www.wikidata.org/entity/Q22056331) | [235](http://test-annotations.europeana.eu/annotation/webanno/235?wskey=apidemo) |
[Refugees](http://data.europeana.eu/item/2063605/LAT_280_006) | [Refugees](http://www.wikidata.org/entity/Q22056364) | [236](http://test-annotations.europeana.eu/annotation/webanno/236?wskey=apidemo) |
[Man Entering a Room](http://data.europeana.eu/item/2063605/LAT_280_007) | [Man Entering a Room](http://www.wikidata.org/entity/Q22043770) | [237](http://test-annotations.europeana.eu/annotation/webanno/237?wskey=apidemo) |
[The White and the Black](http://data.europeana.eu/item/2063605/LAT_280_008) | [The White and the Black](http://www.wikidata.org/entity/Q22045458) | [238](http://test-annotations.europeana.eu/annotation/webanno/238?wskey=apidemo) |
[Madonna with Machine Gun](http://data.europeana.eu/item/2063605/LAT_280_009) | [Madonna with Machine Gun](http://www.wikidata.org/entity/Q22041005) | [239](http://test-annotations.europeana.eu/annotation/webanno/239?wskey=apidemo) |
[Girl in a Folk Costume](http://data.europeana.eu/item/2063605/LAT_280_010) | [Girl in a Folk Costume](http://www.wikidata.org/entity/Q22055751) | [240](http://test-annotations.europeana.eu/annotation/webanno/240?wskey=apidemo) |
[Aparição do Anjo a S. Roque](http://data.europeana.eu/item/2063606/POR_280_001) | [The Appearance of the Angel to St. Roch](http://www.wikidata.org/entity/Q21227244) | [241](http://test-annotations.europeana.eu/annotation/webanno/241?wskey=apidemo) |
[Painéis de S. Vicente de Fora](http://data.europeana.eu/item/2063606/POR_280_002) | [Saint Vincent Panels](http://www.wikidata.org/entity/Q3774964) | [242](http://test-annotations.europeana.eu/annotation/webanno/242?wskey=apidemo) |
[The Greyhounds](http://data.europeana.eu/item/2063606/POR_280_003) | [Os galgos](http://www.wikidata.org/entity/Q22231643) | [243](http://test-annotations.europeana.eu/annotation/webanno/243?wskey=apidemo) |
[Apocalipsis of Lorvao](http://data.europeana.eu/item/2063606/POR_280_004) | [Apocalipsis of Lorvao](http://www.wikidata.org/entity/Q16492936) | [244](http://test-annotations.europeana.eu/annotation/webanno/244?wskey=apidemo) |
[Concerto de Amadores](http://data.europeana.eu/item/2063606/POR_280_005) | [An amateur concert](http://www.wikidata.org/entity/Q10387302) | [245](http://test-annotations.europeana.eu/annotation/webanno/245?wskey=apidemo) |
[Natureza morta com doces e barros](http://data.europeana.eu/item/2063606/POR_280_006) | [Still Life with Sweets](http://www.wikidata.org/entity/Q10335781) | [246](http://test-annotations.europeana.eu/annotation/webanno/246?wskey=apidemo) |
[Cancioneiro da Ajuda](http://data.europeana.eu/item/2063606/POR_280_007) | [Cancioneiro da Ajuda](http://www.wikidata.org/entity/Q2535912) | [247](http://test-annotations.europeana.eu/annotation/webanno/247?wskey=apidemo) |
[The Last Super](http://data.europeana.eu/item/2063606/POR_280_008) | [Last supper](http://www.wikidata.org/entity/Q21226401) | [248](http://test-annotations.europeana.eu/annotation/webanno/248?wskey=apidemo) |
[Sopa de Arroios](http://data.europeana.eu/item/2063606/POR_280_009) | [Soup Kitchen in Arroios](http://www.wikidata.org/entity/Q22233706) | [249](http://test-annotations.europeana.eu/annotation/webanno/249?wskey=apidemo) |
[A Library on Fire](http://data.europeana.eu/item/2063606/POR_280_010) | [A library on fire](http://www.wikidata.org/entity/Q22234726) | [250](http://test-annotations.europeana.eu/annotation/webanno/250?wskey=apidemo) |
[Hell](http://data.europeana.eu/item/2063607/EST_280_001) | [Hell](http://www.wikidata.org/entity/Q21257263) | [251](http://test-annotations.europeana.eu/annotation/webanno/251?wskey=apidemo) |
[Danse Macabre](http://data.europeana.eu/item/2063607/EST_280_002) | [The Danse Macabre](http://www.wikidata.org/entity/Q21806426) | [252](http://test-annotations.europeana.eu/annotation/webanno/252?wskey=apidemo) |
[Singing Tree](http://data.europeana.eu/item/2063607/EST_280_003) | [Singing tree](http://www.wikidata.org/entity/Q21294968) | [253](http://test-annotations.europeana.eu/annotation/webanno/253?wskey=apidemo) |
[Grain for the State](http://data.europeana.eu/item/2063607/EST_280_004) | [Grain for the State](http://www.wikidata.org/entity/Q21152970) | [254](http://test-annotations.europeana.eu/annotation/webanno/254?wskey=apidemo) |
[Seaside Landscape](http://data.europeana.eu/item/2063607/EST_280_005) | [Coastal landscape (Fishermen going home)](http://www.wikidata.org/entity/Q21152784) | [255](http://test-annotations.europeana.eu/annotation/webanno/255?wskey=apidemo) |
[Landscape with a Red Cloud](http://data.europeana.eu/item/2063607/EST_280_006) | [Landscape with a red cloud](http://www.wikidata.org/entity/Q21152819) | [256](http://test-annotations.europeana.eu/annotation/webanno/256?wskey=apidemo) |
[The Maiden of the Grave. Triptych](http://data.europeana.eu/item/2063607/EST_280_007) | [The Maiden of the Grave. Triptych](http://www.wikidata.org/entity/Q21750239) | [257](http://test-annotations.europeana.eu/annotation/webanno/257?wskey=apidemo) |
[An Italian Woman with Children by a Stream](http://data.europeana.eu/item/2063607/EST_280_008) | [An Italian Woman with Children by a Stream](http://www.wikidata.org/entity/Q21152345) | [258](http://test-annotations.europeana.eu/annotation/webanno/258?wskey=apidemo) |
[Paraphrase](http://data.europeana.eu/item/2063607/EST_280_009) | [Paraphrase E](http://www.wikidata.org/entity/Q21152853) | [259](http://test-annotations.europeana.eu/annotation/webanno/259?wskey=apidemo) |
[Farmhouse with a Stove](http://data.europeana.eu/item/2063607/EST_280_010) | [Farmhouse with a Stove](http://www.wikidata.org/entity/Q21257452) | [260](http://test-annotations.europeana.eu/annotation/webanno/260?wskey=apidemo) |
[Young Gardeners](http://data.europeana.eu/item/2063607/EST_280_011) | [Young Gardeners](http://www.wikidata.org/entity/Q21750214) | [261](http://test-annotations.europeana.eu/annotation/webanno/261?wskey=apidemo) |
[Eye in the Egg](http://data.europeana.eu/item/2063607/EST_280_012) | [Eye in the Egg](http://www.wikidata.org/entity/Q21750230) | [262](http://test-annotations.europeana.eu/annotation/webanno/262?wskey=apidemo) |
[Stymphalian Birds](http://data.europeana.eu/item/2063608/CYP_280_001) | [Stymphalian Birds](http://www.wikidata.org/entity/Q22661775) | [263](http://test-annotations.europeana.eu/annotation/webanno/263?wskey=apidemo) |
[Disputation at Peristerona](http://data.europeana.eu/item/2063608/CYP_280_002) | [Disputation at Peristerona](http://www.wikidata.org/entity/Q22661785) | [264](http://test-annotations.europeana.eu/annotation/webanno/264?wskey=apidemo) |
[The Good Fruit of the Earth](http://data.europeana.eu/item/2063608/CYP_280_003) | [The Good Fruit of the Earth](http://www.wikidata.org/entity/Q22661793) | [265](http://test-annotations.europeana.eu/annotation/webanno/265?wskey=apidemo) |
[Return to the Village](http://data.europeana.eu/item/2063608/CYP_280_004) | [Return to the Village](http://www.wikidata.org/entity/Q22661796) | [266](http://test-annotations.europeana.eu/annotation/webanno/266?wskey=apidemo) |
[Composition](http://data.europeana.eu/item/2063608/CYP_280_005) | [Composition](http://www.wikidata.org/entity/Q22661800) | [267](http://test-annotations.europeana.eu/annotation/webanno/267?wskey=apidemo) |
[Composition, Throne II](http://data.europeana.eu/item/2063608/CYP_280_006) | [Composition, Throne II](http://www.wikidata.org/entity/Q22661805) | [268](http://test-annotations.europeana.eu/annotation/webanno/268?wskey=apidemo) |
[The Planters](http://data.europeana.eu/item/2063608/CYP_280_007) | [The Planters](http://www.wikidata.org/entity/Q22661815) | [269](http://test-annotations.europeana.eu/annotation/webanno/269?wskey=apidemo) |
[Madonna and Child](http://data.europeana.eu/item/2063608/CYP_280_008) | [Madonna and Child](http://www.wikidata.org/entity/Q22661822) | [270](http://test-annotations.europeana.eu/annotation/webanno/270?wskey=apidemo) |
[Pagan Spring](http://data.europeana.eu/item/2063608/CYP_280_009) | [Pagan Spring](http://www.wikidata.org/entity/Q22661824) | [271](http://test-annotations.europeana.eu/annotation/webanno/271?wskey=apidemo) |
[Return from the Fields](http://data.europeana.eu/item/2063608/CYP_280_010) | [Return from the Fields](http://www.wikidata.org/entity/Q22661827) | [272](http://test-annotations.europeana.eu/annotation/webanno/272?wskey=apidemo) |
[Techo de los policromos de Altamira](http://data.europeana.eu/item/2063609/ES_280_001) | [Altamira](http://www.wikidata.org/entity/Q133575) | [273](http://test-annotations.europeana.eu/annotation/webanno/273?wskey=apidemo) |
[Vista y plano de Toledo](http://data.europeana.eu/item/2063609/ES_280_002) | [View and Plan of Toledo](http://www.wikidata.org/entity/Q6163991) | [274](http://test-annotations.europeana.eu/annotation/webanno/274?wskey=apidemo) |
[La Piedad](http://data.europeana.eu/item/2063609/ES_280_003) | [The Pietá](http://www.wikidata.org/entity/Q20017050) | [275](http://test-annotations.europeana.eu/annotation/webanno/275?wskey=apidemo) |
[Libro de horas de Leonor de la Vega](http://data.europeana.eu/item/2063609/ES_280_004) | [Book of Hours of Leonor de la Vega](http://www.wikidata.org/entity/Q21155429) | [276](http://test-annotations.europeana.eu/annotation/webanno/276?wskey=apidemo) |
[De Aetatibus Mundi Imagines](http://data.europeana.eu/item/2063609/ES_280_005) | [De aetatibus mundi imagines](http://www.wikidata.org/entity/Q21155503) | [277](http://test-annotations.europeana.eu/annotation/webanno/277?wskey=apidemo) |
[Códice de trajes](http://data.europeana.eu/item/2063609/ES_280_006) | [Codex of costumes](http://www.wikidata.org/entity/Q21261988) | [278](http://test-annotations.europeana.eu/annotation/webanno/278?wskey=apidemo) |
[España artística y monumental](http://data.europeana.eu/item/2063609/ES_280_007) | [España ­Artística y Monumental](http://www.wikidata.org/entity/Q21856719) | [279](http://test-annotations.europeana.eu/annotation/webanno/279?wskey=apidemo) |
[Las Meninas](http://data.europeana.eu/item/2063609/ES_280_008) | [Las Meninas](http://www.wikidata.org/entity/Q208758) | [280](http://test-annotations.europeana.eu/annotation/webanno/280?wskey=apidemo) |
[El 3 de mayo en Madrid](http://data.europeana.eu/item/2063609/ES_280_009) | [The Third of May 1808](http://www.wikidata.org/entity/Q1091086) | [281](http://test-annotations.europeana.eu/annotation/webanno/281?wskey=apidemo) |
[ES_280_010](http://data.europeana.eu/item/2063609/ES_280_010) | [The Great Masturbator](http://www.wikidata.org/entity/Q167536) | [282](http://test-annotations.europeana.eu/annotation/webanno/282?wskey=apidemo) |
[Inmaculada Concepción](http://data.europeana.eu/item/2063609/ES_280_011) | [Immaculate Conception](http://www.wikidata.org/entity/Q22120723) | [283](http://test-annotations.europeana.eu/annotation/webanno/283?wskey=apidemo) |
[Paseo a orillas del mar](http://data.europeana.eu/item/2063609/ES_280_012) | [Walk on the Beach](http://www.wikidata.org/entity/Q6065609) | [284](http://test-annotations.europeana.eu/annotation/webanno/284?wskey=apidemo) |
[[In Apocalipsis]](http://data.europeana.eu/item/2063609/ES_280_013) | [Commentary on the Apocalypse](http://www.wikidata.org/entity/Q813323) | [285](http://test-annotations.europeana.eu/annotation/webanno/285?wskey=apidemo) |
[Álbum de salón](http://data.europeana.eu/item/2063609/ES_280_014) | [Álbum salón](http://www.wikidata.org/entity/Q22121059) | [286](http://test-annotations.europeana.eu/annotation/webanno/286?wskey=apidemo) |
[Baleares, la isla de las maravillas ](http://data.europeana.eu/item/2063609/ES_280_015) | [Baleares, la isla de las maravillas](http://www.wikidata.org/entity/Q22121100) | [287](http://test-annotations.europeana.eu/annotation/webanno/287?wskey=apidemo) |
[Cartilla Escolar Antifascista y Cartilla Aritmética Antifascista](http://data.europeana.eu/item/2063609/ES_280_016) | [Cartilla Escolar Antifascista](http://www.wikidata.org/entity/Q23022017) | [288](http://test-annotations.europeana.eu/annotation/webanno/288?wskey=apidemo) |
[Self-portrait](http://data.europeana.eu/item/2063611/CRO_280_001) | [Self-portrait](http://www.wikidata.org/entity/Q22948543) | [289](http://test-annotations.europeana.eu/annotation/webanno/289?wskey=apidemo) |
[Pafama](http://data.europeana.eu/item/2063611/CRO_280_002) | [Pafama](http://www.wikidata.org/entity/Q22953889) | [290](http://test-annotations.europeana.eu/annotation/webanno/290?wskey=apidemo) |
[Na bregovima - prašuma](http://data.europeana.eu/item/2063611/CRO_280_003) | [On the hills - rainforest](http://www.wikidata.org/entity/Q22954041) | [291](http://test-annotations.europeana.eu/annotation/webanno/291?wskey=apidemo) |
[In Honour of El Lissitzki](http://data.europeana.eu/item/2063611/CRO_280_004) | [In Honour of El Lissitzki](http://www.wikidata.org/entity/Q22976570) | [292](http://test-annotations.europeana.eu/annotation/webanno/292?wskey=apidemo) |
[White background](http://data.europeana.eu/item/2063611/CRO_280_005) | [White Background](http://www.wikidata.org/entity/Q22976609) | [293](http://test-annotations.europeana.eu/annotation/webanno/293?wskey=apidemo) |
[Portret Madame Récamier](http://data.europeana.eu/item/2063611/CRO_280_006) | [Madame Récamier](http://www.wikidata.org/entity/Q22670982) | [294](http://test-annotations.europeana.eu/annotation/webanno/294?wskey=apidemo) |
[Crvena petlja](http://data.europeana.eu/item/2063611/CRO_280_007) | [Red Loop](http://www.wikidata.org/entity/Q22976619) | [295](http://test-annotations.europeana.eu/annotation/webanno/295?wskey=apidemo) |
[Abraham žrtvuje Izaka](http://data.europeana.eu/item/2063611/CRO_280_008) | [Abraham's sacrifice of Isaac](http://www.wikidata.org/entity/Q22671007) | [296](http://test-annotations.europeana.eu/annotation/webanno/296?wskey=apidemo) |
[Meander 2](http://data.europeana.eu/item/2063611/CRO_280_009) | [Meander 2](http://www.wikidata.org/entity/Q22670970) | [297](http://test-annotations.europeana.eu/annotation/webanno/297?wskey=apidemo) |
[Art (according to Marlboro)](http://data.europeana.eu/item/2063611/CRO_280_010) | [Art (according to Marlboro)](http://www.wikidata.org/entity/Q22976628) | [298](http://test-annotations.europeana.eu/annotation/webanno/298?wskey=apidemo) |
[Madonna](http://data.europeana.eu/item/2063612/NO_280_001) | [Madonna](http://www.wikidata.org/entity/Q1989780) | [299](http://test-annotations.europeana.eu/annotation/webanno/299?wskey=apidemo) |
[The Scream](http://data.europeana.eu/item/2063612/NO_280_002) | [The Scream](http://www.wikidata.org/entity/Q18891156) | [300](http://test-annotations.europeana.eu/annotation/webanno/300?wskey=apidemo) |
[Winter Night in the Mountains](http://data.europeana.eu/item/2063612/NO_280_003) | [Winter Night in the Mountains](http://www.wikidata.org/entity/Q19689132) | [301](http://test-annotations.europeana.eu/annotation/webanno/301?wskey=apidemo) |
[Christening in Tanum Church](http://data.europeana.eu/item/2063612/NO_280_004) | [Christening in Tanum Church](http://www.wikidata.org/entity/Q21177097) | [302](http://test-annotations.europeana.eu/annotation/webanno/302?wskey=apidemo) |
[Winter at the Sognefjord](http://data.europeana.eu/item/2063612/NO_280_005) | [Winter at the Sognefjord](http://www.wikidata.org/entity/Q21980572) | [303](http://test-annotations.europeana.eu/annotation/webanno/303?wskey=apidemo) |
[Armour](http://data.europeana.eu/item/2063612/NO_280_006) | [Armour](http://www.wikidata.org/entity/Q22443226) | [304](http://test-annotations.europeana.eu/annotation/webanno/304?wskey=apidemo) |
[Stetind in Fog](http://data.europeana.eu/item/2063612/NO_280_007) | [Stetind in Fog](http://www.wikidata.org/entity/Q22443298) | [305](http://test-annotations.europeana.eu/annotation/webanno/305?wskey=apidemo) |
[Summer night. Inger on the beach](http://data.europeana.eu/item/2063612/NO_280_008) | [Inger on the Beach](http://www.wikidata.org/entity/Q18748731) | [306](http://test-annotations.europeana.eu/annotation/webanno/306?wskey=apidemo) |
[Old Pine Trees](http://data.europeana.eu/item/2063612/NO_280_009) | [Old Pine Trees](http://www.wikidata.org/entity/Q21204853) | [307](http://test-annotations.europeana.eu/annotation/webanno/307?wskey=apidemo) |
[Finnmark, Winter](http://data.europeana.eu/item/2063612/NO_280_010) | [Finnmark, Winter](http://www.wikidata.org/entity/Q22945340) | [308](http://test-annotations.europeana.eu/annotation/webanno/308?wskey=apidemo) |
[The Queue Continues](http://data.europeana.eu/item/2063613/POL_280_001) | [The Queuing Continues](http://www.wikidata.org/entity/Q22671354) | [309](http://test-annotations.europeana.eu/annotation/webanno/309?wskey=apidemo) |
[Battle of Grunwald](http://data.europeana.eu/item/2063613/POL_280_002) | [Battle of Grunwald](http://www.wikidata.org/entity/Q2636368) | [310](http://test-annotations.europeana.eu/annotation/webanno/310?wskey=apidemo) |
[Polish Hamlet - Portrait of Aleksander Wielopolski, Jacek Malczewski](http://data.europeana.eu/item/2063613/POL_280_003) | [Polish Hamlet. Portrait of Aleksander Wielopolski](http://www.wikidata.org/entity/Q9285794) | [311](http://test-annotations.europeana.eu/annotation/webanno/311?wskey=apidemo) |
[Battle of Orsha on 8 September 1514](http://data.europeana.eu/item/2063613/POL_280_004) | [Battle of Orsha](http://www.wikidata.org/entity/Q9173390) | [312](http://test-annotations.europeana.eu/annotation/webanno/312?wskey=apidemo) |
[God the Father](http://data.europeana.eu/item/2063613/POL_280_005) | [God the father - Arise](http://www.wikidata.org/entity/Q22674448) | [313](http://test-annotations.europeana.eu/annotation/webanno/313?wskey=apidemo) |
[Gaberbocchus some of the old favourites](http://data.europeana.eu/item/2063613/POL_280_006) | [Gaberbocchus some of the old favourites](http://www.wikidata.org/entity/Q22939736) | [314](http://test-annotations.europeana.eu/annotation/webanno/314?wskey=apidemo) |
[Staging Point](http://data.europeana.eu/item/2063613/POL_280_007) | [Staging Point](http://www.wikidata.org/entity/Q22945245) | [315](http://test-annotations.europeana.eu/annotation/webanno/315?wskey=apidemo) |
[The catalogue of Gniezno’s Archbishops ](http://data.europeana.eu/item/2063613/POL_280_008) | [Catalogue of the Archbishops of Gniezno](http://www.wikidata.org/entity/Q22939575) | [316](http://test-annotations.europeana.eu/annotation/webanno/316?wskey=apidemo) |
[Four Gospels](http://data.europeana.eu/item/2063614/BU_280_01) | [Four Gospels](http://www.wikidata.org/entity/Q22947038) | [317](http://test-annotations.europeana.eu/annotation/webanno/317?wskey=apidemo) |
[Miscellany ](http://data.europeana.eu/item/2063614/BU_280_02) | [Miscellany](http://www.wikidata.org/entity/Q22947273) | [318](http://test-annotations.europeana.eu/annotation/webanno/318?wskey=apidemo) |
[Alexandrija, Sofia Illustrated Alexandria, Romance of Alexander and Version of the History of the Trojan War](http://data.europeana.eu/item/2063614/BU_280_03) | [Alexandrija, Sofia Illustrated Alexandria, Romance of Alexander and Version of the History of the Trojan War](http://www.wikidata.org/entity/Q22947617) | [319](http://test-annotations.europeana.eu/annotation/webanno/319?wskey=apidemo) |
[Landscape ](http://data.europeana.eu/item/2063614/BU_280_04) | [Landscape (Painting by Zlatyu Boyadziev)](http://www.wikidata.org/entity/Q22953628) | [320](http://test-annotations.europeana.eu/annotation/webanno/320?wskey=apidemo) |
[Portrait of Mrs. Stefka Gueorgieva Otmarova](http://data.europeana.eu/item/2063614/BU_280_05) | [Portrait of Mrs. Stefka Gueorgieva Otmarova](http://www.wikidata.org/entity/Q22953670) | [321](http://test-annotations.europeana.eu/annotation/webanno/321?wskey=apidemo) |
[Psalter](http://data.europeana.eu/item/2063614/BU_280_06) | [Psalter](http://www.wikidata.org/entity/Q22953730) | [322](http://test-annotations.europeana.eu/annotation/webanno/322?wskey=apidemo) |
[Erma River by the Town of Tran](http://data.europeana.eu/item/2063614/BU_280_07) | [Erma River by the Town of Tran](http://www.wikidata.org/entity/Q21152290) | [323](http://test-annotations.europeana.eu/annotation/webanno/323?wskey=apidemo) |
[Portrait of Eliezer Alshekh](http://data.europeana.eu/item/2063614/BU_280_08) | [Portrait of Eliezer Alshekh](http://www.wikidata.org/entity/Q22953788) | [324](http://test-annotations.europeana.eu/annotation/webanno/324?wskey=apidemo) |
[Lucifer](http://data.europeana.eu/item/2063614/BU_280_09) | [Lucifer](http://www.wikidata.org/entity/Q22953800) | [325](http://test-annotations.europeana.eu/annotation/webanno/325?wskey=apidemo) |
[Cityscape from Tzarigrad](http://data.europeana.eu/item/2063614/BU_280_10) | [Landscape from Tsarigrad](http://www.wikidata.org/entity/Q21231512) | [326](http://test-annotations.europeana.eu/annotation/webanno/326?wskey=apidemo) |
[Programirana grafika - program vertikalne smeri](http://data.europeana.eu/item/2063615/SLO_280_001) | [Programmed Print - the Vertical Line Program](http://www.wikidata.org/entity/Q22678603) | [327](http://test-annotations.europeana.eu/annotation/webanno/327?wskey=apidemo) |
[Družinski portret](http://data.europeana.eu/item/2063615/SLO_280_003) | [Family portrait](http://www.wikidata.org/entity/Q22678531) | [328](http://test-annotations.europeana.eu/annotation/webanno/328?wskey=apidemo) |
[Kvartopirci II](http://data.europeana.eu/item/2063615/SLO_280_004) | [The Card Players II](http://www.wikidata.org/entity/Q22678143) | [329](http://test-annotations.europeana.eu/annotation/webanno/329?wskey=apidemo) |
[Stoječa Marija ](http://data.europeana.eu/item/2063615/SLO_280_005) | [Standing Madonna with child](http://www.wikidata.org/entity/Q21157624) | [330](http://test-annotations.europeana.eu/annotation/webanno/330?wskey=apidemo) |
[Zmagoslavje Flore](http://data.europeana.eu/item/2063615/SLO_280_006) | [The Triumph of Flora](http://www.wikidata.org/entity/Q21262115) | [331](http://test-annotations.europeana.eu/annotation/webanno/331?wskey=apidemo) |
[Portret očeta ](http://data.europeana.eu/item/2063615/SLO_280_007) | [Portrait of the artist's father](http://www.wikidata.org/entity/Q22678110) | [332](http://test-annotations.europeana.eu/annotation/webanno/332?wskey=apidemo) |
[Pompejansko omizje](http://data.europeana.eu/item/2063615/SLO_280_008) | [Pompeiian table](http://www.wikidata.org/entity/Q21204744) | [333](http://test-annotations.europeana.eu/annotation/webanno/333?wskey=apidemo) |
[Triglav iz Bohinja](http://data.europeana.eu/item/2063615/SLO_280_009) | [View of Mt Triglav from Bohinj](http://www.wikidata.org/entity/Q22921088) | [334](http://test-annotations.europeana.eu/annotation/webanno/334?wskey=apidemo) |
[Idealna krajina z mladeničem, ki ubija kačo](http://data.europeana.eu/item/2063615/SLO_280_010) | [Ideal landscape with a young man killing a snake](http://www.wikidata.org/entity/Q22922226) | [335](http://test-annotations.europeana.eu/annotation/webanno/335?wskey=apidemo) |
[Beheading of St John the Baptist](http://data.europeana.eu/item/2063617/MAL_280_001) | [The Beheading of Saint John the Baptist](http://www.wikidata.org/entity/Q2727560) | [336](http://test-annotations.europeana.eu/annotation/webanno/336?wskey=apidemo) |
[St Jerome ](http://data.europeana.eu/item/2063617/MAL_280_002) | [Saint Jerome](http://www.wikidata.org/entity/Q22671332) | [337](http://test-annotations.europeana.eu/annotation/webanno/337?wskey=apidemo) |
[Judith and Holphernes](http://data.europeana.eu/item/2063617/MAL_280_003) | [Judith and Holofernes](http://www.wikidata.org/entity/Q22920647) | [338](http://test-annotations.europeana.eu/annotation/webanno/338?wskey=apidemo) |
[Charity of St Thomas of Villanova](http://data.europeana.eu/item/2063617/MAL_280_004) | [Charity of St Thomas of Villanova](http://www.wikidata.org/entity/Q23022589) | [339](http://test-annotations.europeana.eu/annotation/webanno/339?wskey=apidemo) |
[Front Elevation for a Monument to the Unknown Soldier](http://data.europeana.eu/item/2063617/MAL_280_005) | [Front Elevation for a Monument to the Unknown Soldier](http://www.wikidata.org/entity/Q23034829) | [340](http://test-annotations.europeana.eu/annotation/webanno/340?wskey=apidemo) |
[Risen Christ](http://data.europeana.eu/item/2063617/MAL_280_006) | [Risen Christ](http://www.wikidata.org/entity/Q22945654) | [341](http://test-annotations.europeana.eu/annotation/webanno/341?wskey=apidemo) |
[Abstract painting](http://data.europeana.eu/item/2063617/MAL_280_007) | [Abstract painting](http://www.wikidata.org/entity/Q22945895) | [342](http://test-annotations.europeana.eu/annotation/webanno/342?wskey=apidemo) |
[Death of Dragut](http://data.europeana.eu/item/2063617/MAL_280_008) | [Death of Dragut](http://www.wikidata.org/entity/Q21153247) | [343](http://test-annotations.europeana.eu/annotation/webanno/343?wskey=apidemo) |
[St John the Baptist Wearing the Red Tabard of the Order of St John](http://data.europeana.eu/item/2063617/MAL_280_009) | [St John the Baptist Wearing the Red Tabard of the Order of St John](http://www.wikidata.org/entity/Q22945980) | [344](http://test-annotations.europeana.eu/annotation/webanno/344?wskey=apidemo) |
[St George on Horseback](http://data.europeana.eu/item/2063617/MAL_280_010) | [Saint George on Horseback](http://www.wikidata.org/entity/Q22920999) | [345](http://test-annotations.europeana.eu/annotation/webanno/345?wskey=apidemo) |
[Kreeta Haapasalo Playing the Kantele in a Peasant Cottage](http://data.europeana.eu/item/2063618/FI_280_001) | [Kreeta Haapasalo Playing the Kantele in a Peasant Cottage](http://www.wikidata.org/entity/Q11872962) | [346](http://test-annotations.europeana.eu/annotation/webanno/346?wskey=apidemo) |
[Road in Häme (A Hot Summer Day)](http://data.europeana.eu/item/2063618/FI_280_002) | [Road in Häme](http://www.wikidata.org/entity/Q11880391) | [347](http://test-annotations.europeana.eu/annotation/webanno/347?wskey=apidemo) |
[The Luxembourg Gardens, Paris](http://data.europeana.eu/item/2063618/FI_280_003) | [The Luxembourg Gardens, Paris](http://www.wikidata.org/entity/Q18346884) | [348](http://test-annotations.europeana.eu/annotation/webanno/348?wskey=apidemo) |
[Out into the World](http://data.europeana.eu/item/2063618/FI_280_004) | [Out into the World](http://www.wikidata.org/entity/Q11880306) | [349](http://test-annotations.europeana.eu/annotation/webanno/349?wskey=apidemo) |
[The Wounded Angel](http://data.europeana.eu/item/2063618/FI_280_005) | [The Wounded Angel](http://www.wikidata.org/entity/Q471289) | [350](http://test-annotations.europeana.eu/annotation/webanno/350?wskey=apidemo) |
[Seated Lady (Artist’s Sister Ina Rosenberg)](http://data.europeana.eu/item/2063618/FI_280_006) | [Seated Lady](http://www.wikidata.org/entity/Q20800123) | [351](http://test-annotations.europeana.eu/annotation/webanno/351?wskey=apidemo) |
[The Bells](http://data.europeana.eu/item/2063618/FI_280_007) | [The Bells](http://www.wikidata.org/entity/Q20792756) | [352](http://test-annotations.europeana.eu/annotation/webanno/352?wskey=apidemo) |
[Hearing the Homework](http://data.europeana.eu/item/2063618/FI_280_008) | [Hearing the Homework](http://www.wikidata.org/entity/Q20799751) | [353](http://test-annotations.europeana.eu/annotation/webanno/353?wskey=apidemo) |
[The Grey Dance](http://data.europeana.eu/item/2063618/FI_280_009) | [The Grey Dance](http://www.wikidata.org/entity/Q20796266) | [354](http://test-annotations.europeana.eu/annotation/webanno/354?wskey=apidemo) |
[Self-Portrait](http://data.europeana.eu/item/2063618/FI_280_010) | [Self-Portrait](http://www.wikidata.org/entity/Q20773696) | [355](http://test-annotations.europeana.eu/annotation/webanno/355?wskey=apidemo) |
[The reliquary of St. Maurus](http://data.europeana.eu/item/2063619/CZR_280_001) | [Reliquary of St. Maurus](http://www.wikidata.org/entity/Q1636942) | [356](http://test-annotations.europeana.eu/annotation/webanno/356?wskey=apidemo) |
[Painting of St Jude Thaddaeus](http://data.europeana.eu/item/2063619/CZR_280_002) | [St Jude Thaddaeus](http://www.wikidata.org/entity/Q22976727) | [357](http://test-annotations.europeana.eu/annotation/webanno/357?wskey=apidemo) |
[Liber viaticus Johannis Noviforensis](http://data.europeana.eu/item/2063619/CZR_280_003) | [Liber viaticus of Johannes Noviforensis](http://www.wikidata.org/entity/Q20755471) | [358](http://test-annotations.europeana.eu/annotation/webanno/358?wskey=apidemo) |
[Great Prospect of Prague from Petřín Hill](http://data.europeana.eu/item/2063619/CZR_280_004) | [Great View of Prague](http://www.wikidata.org/entity/Q22976858) | [359](http://test-annotations.europeana.eu/annotation/webanno/359?wskey=apidemo) |
[Altar Wings of Roudníky](http://data.europeana.eu/item/2063619/CZR_280_005) | [Altar Wings of Roudníky](http://www.wikidata.org/entity/Q22671034) | [360](http://test-annotations.europeana.eu/annotation/webanno/360?wskey=apidemo) |
[St Sebastian, 1912](http://data.europeana.eu/item/2063619/CZR_280_006) | [St. Sebastian](http://www.wikidata.org/entity/Q22976966) | [361](http://test-annotations.europeana.eu/annotation/webanno/361?wskey=apidemo) |
[Portrait of the Gem-Cutter Dionysius Misseroni and His Family](http://data.europeana.eu/item/2063619/CZR_280_007) | [Portrait of the Gem-Cutter Dionysius Miseroni and His Family](http://www.wikidata.org/entity/Q22976977) | [362](http://test-annotations.europeana.eu/annotation/webanno/362?wskey=apidemo) |
[Třeboň Altarpiece Resurrection ](http://data.europeana.eu/item/2063619/CZR_280_008) | [Třeboň Altarpiece](http://www.wikidata.org/entity/Q2428392) | [363](http://test-annotations.europeana.eu/annotation/webanno/363?wskey=apidemo) |
[Sleepwalker](http://data.europeana.eu/item/2063619/CZR_280_009) | [Sleepwalker](http://www.wikidata.org/entity/Q22976979) | [364](http://test-annotations.europeana.eu/annotation/webanno/364?wskey=apidemo) |
[Ride of the Kings](http://data.europeana.eu/item/2063619/CZR_280_010) | [Ride of the Kings](http://www.wikidata.org/entity/Q22976991) | [365](http://test-annotations.europeana.eu/annotation/webanno/365?wskey=apidemo) |
[Η Σταύρωση](http://data.europeana.eu/item/2063620/GRE_280_01) | [The crucifiction](http://www.wikidata.org/entity/Q21232677) | [366](http://test-annotations.europeana.eu/annotation/webanno/366?wskey=apidemo) |
[Η ταφή του Χριστού](http://data.europeana.eu/item/2063620/GRE_280_02) | [The Entombment of Christ](http://www.wikidata.org/entity/Q6121364) | [367](http://test-annotations.europeana.eu/annotation/webanno/367?wskey=apidemo) |
[Η υποδοχή του Λόρδου Βύρωνα στο Μεσολόγγι](http://data.europeana.eu/item/2063620/GRE_280_03) | [The Reception of Lord Byron at Missolonghi](http://www.wikidata.org/entity/Q12877595) | [368](http://test-annotations.europeana.eu/annotation/webanno/368?wskey=apidemo) |
[Καΐκι στις Σπέτσες](http://data.europeana.eu/item/2063620/GRE_280_04) | [Caique at Spetses](http://www.wikidata.org/entity/Q22671102) | [369](http://test-annotations.europeana.eu/annotation/webanno/369?wskey=apidemo) |
[Το ψαριανό μοιρολόι](http://data.europeana.eu/item/2063620/GRE_280_05) | [The Dirge in Psara](http://www.wikidata.org/entity/Q22671113) | [370](http://test-annotations.europeana.eu/annotation/webanno/370?wskey=apidemo) |
[Ιδού ο Νυμφίος έρχεται](http://data.europeana.eu/item/2063620/GRE_280_06) | [Behold the Bridegroom Arriving](http://www.wikidata.org/entity/Q22671120) | [371](http://test-annotations.europeana.eu/annotation/webanno/371?wskey=apidemo) |
[Το ψάθινο καπέλλο](http://data.europeana.eu/item/2063620/GRE_280_07) | [The Straw Hat](http://www.wikidata.org/entity/Q22671139) | [372](http://test-annotations.europeana.eu/annotation/webanno/372?wskey=apidemo) |
[Αποθέωση του Αθανασίου Διάκου](http://data.europeana.eu/item/2063620/GRE_280_08) | [The Apotheosis of Athanasios Diakos](http://www.wikidata.org/entity/Q22671144) | [373](http://test-annotations.europeana.eu/annotation/webanno/373?wskey=apidemo) |
[Καφενείον το Νέον (Νύχτα)](http://data.europeana.eu/item/2063620/GRE_280_09) | ["Neon" Cafe (Night)](http://www.wikidata.org/entity/Q22671279) | [374](http://test-annotations.europeana.eu/annotation/webanno/374?wskey=apidemo) |
[Επιτύμβιο](http://data.europeana.eu/item/2063620/GRE_280_10) | [Funeral Composition](http://www.wikidata.org/entity/Q22671271) | [375](http://test-annotations.europeana.eu/annotation/webanno/375?wskey=apidemo) |
[Gabrielle d'Estrées et une de ses soeurs](http://data.europeana.eu/item/2063621/FRA_280_001) | [Gabrielle d'Estrées et une de ses sœurs](http://www.wikidata.org/entity/Q542066) | [376](http://test-annotations.europeana.eu/annotation/webanno/376?wskey=apidemo) |
[La pêche à la ligne](http://data.europeana.eu/item/2063621/FRA_280_002) | [Fishing](http://www.wikidata.org/entity/Q23011437) | [377](http://test-annotations.europeana.eu/annotation/webanno/377?wskey=apidemo) |
[Femmes de Tahiti ](http://data.europeana.eu/item/2063621/FRA_280_003) | [Tahitian Women on the Beach](http://www.wikidata.org/entity/Q930535) | [378](http://test-annotations.europeana.eu/annotation/webanno/378?wskey=apidemo) |
[Berthe Morisot au bouquet de violettes](http://data.europeana.eu/item/2063621/FRA_280_004) | [Berthe Morisot with a Bouquet of Violets](http://www.wikidata.org/entity/Q2899286) | [379](http://test-annotations.europeana.eu/annotation/webanno/379?wskey=apidemo) |
[La fuite en Egypte](http://data.europeana.eu/item/2063621/FRA_280_005) | [The Flight into Egypt](http://www.wikidata.org/entity/Q970808) | [380](http://test-annotations.europeana.eu/annotation/webanno/380?wskey=apidemo) |
[Louis XIV, roi de France, portrait en pied en costume royal](http://data.europeana.eu/item/2063621/FRA_280_006) | [Portrait of Louis XIV](http://www.wikidata.org/entity/Q3399440) | [381](http://test-annotations.europeana.eu/annotation/webanno/381?wskey=apidemo) |
[Le jardin de l'artiste à Giverny](http://data.europeana.eu/item/2063621/FRA_280_007) | [Le Jardin de l'artiste à Giverny](http://www.wikidata.org/entity/Q3794124) | [382](http://test-annotations.europeana.eu/annotation/webanno/382?wskey=apidemo) |
[Embarquement pour Cythère](http://data.europeana.eu/item/2063621/FRA_280_008) | [The Embarkation for Cythera](http://www.wikidata.org/entity/Q531329) | [383](http://test-annotations.europeana.eu/annotation/webanno/383?wskey=apidemo) |
[la Liberté guidant le peuple ](http://data.europeana.eu/item/2063621/FRA_280_009) | [Liberty Leading the People](http://www.wikidata.org/entity/Q29530) | [384](http://test-annotations.europeana.eu/annotation/webanno/384?wskey=apidemo) |
[Rythmes](http://data.europeana.eu/item/2063621/FRA_280_010) | [Rhythms](http://www.wikidata.org/entity/Q19861769) | [385](http://test-annotations.europeana.eu/annotation/webanno/385?wskey=apidemo) |
[Halbzeit](http://data.europeana.eu/item/2063622/LUX_280_001) | [Halbzeit](http://www.wikidata.org/entity/Q23011512) | [386](http://test-annotations.europeana.eu/annotation/webanno/386?wskey=apidemo) |
[[schrëftbild 1998] ](http://data.europeana.eu/item/2063622/LUX_280_002) | [Calligraphical image](http://www.wikidata.org/entity/Q23012030) | [387](http://test-annotations.europeana.eu/annotation/webanno/387?wskey=apidemo) |
[Le noyau rouge](http://data.europeana.eu/item/2063622/LUX_280_003) | [Le noyau rouge](http://www.wikidata.org/entity/Q23011529) | [388](http://test-annotations.europeana.eu/annotation/webanno/388?wskey=apidemo) |
[Composition E 10/58](http://data.europeana.eu/item/2063622/LUX_280_004) | [Composition E 10/58](http://www.wikidata.org/entity/Q23011544) | [389](http://test-annotations.europeana.eu/annotation/webanno/389?wskey=apidemo) |
[Tête de Clown](http://data.europeana.eu/item/2063622/LUX_280_005) | [Head of a clown](http://www.wikidata.org/entity/Q22284213) | [390](http://test-annotations.europeana.eu/annotation/webanno/390?wskey=apidemo) |
[Le barrage](http://data.europeana.eu/item/2063622/LUX_280_006) | [The Dam](http://www.wikidata.org/entity/Q22671308) | [391](http://test-annotations.europeana.eu/annotation/webanno/391?wskey=apidemo) |
[Vue de la ville de Luxembourg depuis le Fetschenhof](http://data.europeana.eu/item/2063622/LUX_280_007) | [View of the city of Luxemburg from the Fetschenhof](http://www.wikidata.org/entity/Q22284390) | [392](http://test-annotations.europeana.eu/annotation/webanno/392?wskey=apidemo) |
[Codex Mariendalensis](http://data.europeana.eu/item/2063622/LUX_280_009) | [Codex Mariendalensis](http://www.wikidata.org/entity/Q632114) | [393](http://test-annotations.europeana.eu/annotation/webanno/393?wskey=apidemo) |
[Self-Portrait with Fur-Trimmed Robe ](http://data.europeana.eu/item/2063623/GER_280_001) | [Self-Portrait](http://www.wikidata.org/entity/Q2546309) | [394](http://test-annotations.europeana.eu/annotation/webanno/394?wskey=apidemo) |
[Kamel (in rhythm. Baumlandschaft)](http://data.europeana.eu/item/2063623/GER_280_002) | [Camel (in Rhythmic Landscape with Trees)](http://www.wikidata.org/entity/Q21153070) | [395](http://test-annotations.europeana.eu/annotation/webanno/395?wskey=apidemo) |
[The Fountain of Youth ](http://data.europeana.eu/item/2063623/GER_280_003) | [The fountain of youth](http://www.wikidata.org/entity/Q20828882) | [396](http://test-annotations.europeana.eu/annotation/webanno/396?wskey=apidemo) |
[Dada-Review](http://data.europeana.eu/item/2063623/GER_280_004) | [Dada-Review](http://www.wikidata.org/entity/Q22570016) | [397](http://test-annotations.europeana.eu/annotation/webanno/397?wskey=apidemo) |
[Kaffeegesellschaft an der Alster mit Blick über Lombardsbrücke auf die Stadt (Uhrenbild - Bilderuhr)](http://data.europeana.eu/item/2063623/GER_280_005) | [Picture clock with Alster panorama](http://www.wikidata.org/entity/Q22570045) | [398](http://test-annotations.europeana.eu/annotation/webanno/398?wskey=apidemo) |
[Konzentrische Gruppe](http://data.europeana.eu/item/2063623/GER_280_006) | [Concentric Group](http://www.wikidata.org/entity/Q22570081) | [399](http://test-annotations.europeana.eu/annotation/webanno/399?wskey=apidemo) |
[The Sistine Madonna](http://data.europeana.eu/item/2063623/GER_280_007) | [Sistine Madonna](http://www.wikidata.org/entity/Q328079) | [400](http://test-annotations.europeana.eu/annotation/webanno/400?wskey=apidemo) |
[Hortus semper vivens exhibens icones plantarum selectiorum](http://data.europeana.eu/item/2063623/GER_280_008) | [Hortus semper virens](http://www.wikidata.org/entity/Q22570086) | [401](http://test-annotations.europeana.eu/annotation/webanno/401?wskey=apidemo) |
[GER_280_009](http://data.europeana.eu/item/2063623/GER_280_009) | [Blue horse](http://www.wikidata.org/entity/Q22570092) | [402](http://test-annotations.europeana.eu/annotation/webanno/402?wskey=apidemo) |
[Selbstbildnis am 6. Hochzeitstag](http://data.europeana.eu/item/2063623/GER_280_010) | [Selfportrait at 6th wedding anniversary](http://www.wikidata.org/entity/Q22570122) | [403](http://test-annotations.europeana.eu/annotation/webanno/403?wskey=apidemo) |
[Merzbild Einunddreissig](http://data.europeana.eu/item/2063623/GER_280_011) | [Merzpicture Thirty-One](http://www.wikidata.org/entity/Q22928341) | [404](http://test-annotations.europeana.eu/annotation/webanno/404?wskey=apidemo) |
[The opening of St Mathew's Gospel in the Lindisfarne Gospels](http://data.europeana.eu/item/2063624/UK_280_001) | [Lindisfarne Gospels](http://www.wikidata.org/entity/Q80935) | [405](http://test-annotations.europeana.eu/annotation/webanno/405?wskey=apidemo) |
[A Rake’s Progress (plate 3)](http://data.europeana.eu/item/2063624/UK_280_002) | [The Tavern Scene](http://www.wikidata.org/entity/Q22920028) | [406](http://test-annotations.europeana.eu/annotation/webanno/406?wskey=apidemo) |
[A Philosopher Giving that Lecture on the Orrery, in which a Lamp is put in the Place of the Sun](http://data.europeana.eu/item/2063624/UK_280_003) | [A Philosopher Lecturing on the Orrery](http://www.wikidata.org/entity/Q1651874) | [407](http://test-annotations.europeana.eu/annotation/webanno/407?wskey=apidemo) |
[Europe: a Prophecy (frontispiece)](http://data.europeana.eu/item/2063624/UK_280_004) | [The Ancient of Days](http://www.wikidata.org/entity/Q2324840) | [408](http://test-annotations.europeana.eu/annotation/webanno/408?wskey=apidemo) |
[The Destruction of Sodom and Gomorrah](http://data.europeana.eu/item/2063624/UK_280_005) | [The Destruction of Sodom And Gomorrah](http://www.wikidata.org/entity/Q20354732) | [409](http://test-annotations.europeana.eu/annotation/webanno/409?wskey=apidemo) |
[The Last of England](http://data.europeana.eu/item/2063624/UK_280_006) | [The Last of England](http://www.wikidata.org/entity/Q958245) | [410](http://test-annotations.europeana.eu/annotation/webanno/410?wskey=apidemo) |
[Coming from the Mill](http://data.europeana.eu/item/2063624/UK_280_007) | [Coming from the Mill](http://www.wikidata.org/entity/Q22920057) | [411](http://test-annotations.europeana.eu/annotation/webanno/411?wskey=apidemo) |
[The Fighting Temeraire tugged to her last berth to be broken up](http://data.europeana.eu/item/2063624/UK_280_008) | [The Fighting Temeraire](http://www.wikidata.org/entity/Q257580) | [412](http://test-annotations.europeana.eu/annotation/webanno/412?wskey=apidemo) |
[The Hay Wain](http://data.europeana.eu/item/2063624/UK_280_009) | [The Hay Wain](http://www.wikidata.org/entity/Q2366825) | [413](http://test-annotations.europeana.eu/annotation/webanno/413?wskey=apidemo) |
[Wooded Landscape with a Herdsman Seated](http://data.europeana.eu/item/2063624/UK_280_010) | [Wooded Landscape with a Herdsman Seated](http://www.wikidata.org/entity/Q22920078) | [414](http://test-annotations.europeana.eu/annotation/webanno/414?wskey=apidemo) |
[Studio with Gloves](http://data.europeana.eu/item/2063624/UK_280_011) | [Studio with gloves](http://www.wikidata.org/entity/Q23050520) | [415](http://test-annotations.europeana.eu/annotation/webanno/415?wskey=apidemo) |
[The gathering ](http://data.europeana.eu/item/2063624/UK_280_012) | [The gathering](http://www.wikidata.org/entity/Q23050450) | [416](http://test-annotations.europeana.eu/annotation/webanno/416?wskey=apidemo) |
[Vase of Flowers](http://data.europeana.eu/item/2063624/UK_280_013) | [Vase of flowers](http://www.wikidata.org/entity/Q22977148) | [417](http://test-annotations.europeana.eu/annotation/webanno/417?wskey=apidemo) |
[An Overshot Mill in Wales (Aberdulais)](http://data.europeana.eu/item/2063624/UK_280_014) | [An Overshot Mill in Wales (Aberdulais)](http://www.wikidata.org/entity/Q22977094) | [418](http://test-annotations.europeana.eu/annotation/webanno/418?wskey=apidemo) |
[Sir John Williams, Bart, GCVO, MD](http://data.europeana.eu/item/2063624/UK_280_015) | [portrait of Sir John Williams](http://www.wikidata.org/entity/Q22977140) | [419](http://test-annotations.europeana.eu/annotation/webanno/419?wskey=apidemo) |
[Miss Catherine Jones of Colomendy, near Mold](http://data.europeana.eu/item/2063624/UK_280_016) | [Miss Catherine Jones of Colomendy, near Mold](http://www.wikidata.org/entity/Q22977135) | [420](http://test-annotations.europeana.eu/annotation/webanno/420?wskey=apidemo) |
[Dolbadarn castle](http://data.europeana.eu/item/2063624/UK_280_017) | [Dolbadarn Castle](http://www.wikidata.org/entity/Q22977128) | [421](http://test-annotations.europeana.eu/annotation/webanno/421?wskey=apidemo) |
[Bellringer of Caernarvon in costume of trade](http://data.europeana.eu/item/2063624/UK_280_018) | [Bellringer of Caernarvon in costume of trade](http://www.wikidata.org/entity/Q23007300) | [422](http://test-annotations.europeana.eu/annotation/webanno/422?wskey=apidemo) |
[Dr Richard Price, DD, FRS](http://data.europeana.eu/item/2063624/UK_280_019) | [Portrait of Dr Richard Price](http://www.wikidata.org/entity/Q22977105) | [423](http://test-annotations.europeana.eu/annotation/webanno/423?wskey=apidemo) |
[Shane Williams](http://data.europeana.eu/item/2063624/UK_280_020) | [Shane Williams](http://www.wikidata.org/entity/Q23007311) | [424](http://test-annotations.europeana.eu/annotation/webanno/424?wskey=apidemo) |
[Oblique](http://data.europeana.eu/item/2063625/HU_280_001) | [Oblique](http://www.wikidata.org/entity/Q23012051) | [425](http://test-annotations.europeana.eu/annotation/webanno/425?wskey=apidemo) |
[Twofold Movement](http://data.europeana.eu/item/2063625/HU_280_002) | [Twofold movement](http://www.wikidata.org/entity/Q23012068) | [426](http://test-annotations.europeana.eu/annotation/webanno/426?wskey=apidemo) |
[The Visitation](http://data.europeana.eu/item/2063625/HU_280_003) | [The Visitation](http://www.wikidata.org/entity/Q21806457) | [427](http://test-annotations.europeana.eu/annotation/webanno/427?wskey=apidemo) |
[The New Eve](http://data.europeana.eu/item/2063625/HU_280_004) | [The New Eve](http://www.wikidata.org/entity/Q21808511) | [428](http://test-annotations.europeana.eu/annotation/webanno/428?wskey=apidemo) |
[The New Adam](http://data.europeana.eu/item/2063625/HU_280_005) | [The New Adam](http://www.wikidata.org/entity/Q21808509) | [429](http://test-annotations.europeana.eu/annotation/webanno/429?wskey=apidemo) |
[Pilgrimage to the Cedars in Lebanon](http://data.europeana.eu/item/2063625/HU_280_006) | [Pilgrimage to the Cedars in Lebanon](http://www.wikidata.org/entity/Q21807895) | [430](http://test-annotations.europeana.eu/annotation/webanno/430?wskey=apidemo) |
[Balloon](http://data.europeana.eu/item/2063625/HU_280_007) | [The Balloon](http://www.wikidata.org/entity/Q21806470) | [431](http://test-annotations.europeana.eu/annotation/webanno/431?wskey=apidemo) |
[Japanese Woman](http://data.europeana.eu/item/2063625/HU_280_008) | [Japanese Woman](http://www.wikidata.org/entity/Q21806462) | [432](http://test-annotations.europeana.eu/annotation/webanno/432?wskey=apidemo) |
[Il bacio](http://data.europeana.eu/item/2063626/IT_280_001) | [The Kiss](http://www.wikidata.org/entity/Q1195035) | [433](http://test-annotations.europeana.eu/annotation/webanno/433?wskey=apidemo) |
[Predica di San Marco in una piazza di Alessandria d'Egitto](http://data.europeana.eu/item/2063626/IT_280_002) | [The Preaching of Saint Mark in Alexandria](http://www.wikidata.org/entity/Q3910087) | [434](http://test-annotations.europeana.eu/annotation/webanno/434?wskey=apidemo) |
[Cristo morto nel sepolcro e tre dolenti](http://data.europeana.eu/item/2063626/IT_280_003) | [Lamentation of Christ](http://www.wikidata.org/entity/Q546297) | [435](http://test-annotations.europeana.eu/annotation/webanno/435?wskey=apidemo) |
[Sposalizio della Vergine](http://data.europeana.eu/item/2063626/IT_280_004) | [The Marriage of the Virgin](http://www.wikidata.org/entity/Q2030685) | [436](http://test-annotations.europeana.eu/annotation/webanno/436?wskey=apidemo) |
[Cena in Emmaus - Supper at Emmaus](http://data.europeana.eu/item/2063626/IT_280_005) | [Supper at Emmaus](http://www.wikidata.org/entity/Q2097931) | [437](http://test-annotations.europeana.eu/annotation/webanno/437?wskey=apidemo) |
[Sidereus nuncius magna longeque admirabiblia spectacula pandens, suspiciendaque proponens vnicuique praesertim vero philosophis](http://data.europeana.eu/item/2063626/IT_280_006) | [Sidereus Nuncius](http://www.wikidata.org/entity/Q1457297) | [438](http://test-annotations.europeana.eu/annotation/webanno/438?wskey=apidemo) |
[Ritratto di Goffredo Mameli ritratto dal vivo](http://data.europeana.eu/item/2063626/IT_280_008) | [Goffredo Mameli life drawing](http://www.wikidata.org/entity/Q23011493) | [439](http://test-annotations.europeana.eu/annotation/webanno/439?wskey=apidemo) |
[Pornokrates](http://data.europeana.eu/item/2063627/BEL_280_001) | [?](http://www.wikidata.org/entity/Q22283342) | [440](http://test-annotations.europeana.eu/annotation/webanno/440?wskey=apidemo) |
[Les Grecs et les Troyens se disputant le corps de Patrocle](http://data.europeana.eu/item/2063627/BEL_280_002) | [The Greeks and the Trojans Fighting over the Body of Patroclus](http://www.wikidata.org/entity/Q21152204) | [441](http://test-annotations.europeana.eu/annotation/webanno/441?wskey=apidemo) |
[Diptyque (Acrasia dans les bois – Britomart dans les bois) ](http://data.europeana.eu/item/2063627/BEL_280_003) | [The Isolation](http://www.wikidata.org/entity/Q22443066) | [442](http://test-annotations.europeana.eu/annotation/webanno/442?wskey=apidemo) |
[Evangéliaire d'Averbode](http://data.europeana.eu/item/2063627/BEL_280_004) | [Evangeliary of Averbode](http://www.wikidata.org/entity/Q22443081) | [443](http://test-annotations.europeana.eu/annotation/webanno/443?wskey=apidemo) |
[Chronicles of Hainaut (miniature of Rogier Van der Weyden)](http://data.europeana.eu/item/2063627/BEL_280_005) | [Jean Wauquelin presenting his 'Chroniques de Hainaut' to Philip the Good](http://www.wikidata.org/entity/Q17015401) | [444](http://test-annotations.europeana.eu/annotation/webanno/444?wskey=apidemo) |
[Luxuria](http://data.europeana.eu/item/2063627/BEL_280_006) | [Luxuria](http://www.wikidata.org/entity/Q22814441) | [445](http://test-annotations.europeana.eu/annotation/webanno/445?wskey=apidemo) |
[Iustitia](http://data.europeana.eu/item/2063627/BEL_280_007) | [Lusticia](http://www.wikidata.org/entity/Q22814403) | [446](http://test-annotations.europeana.eu/annotation/webanno/446?wskey=apidemo) |
[Sainte Cécile de Rome](http://data.europeana.eu/item/2063627/BEL_280_008) | [Saint Cecilia of Rome](http://www.wikidata.org/entity/Q21152254) | [447](http://test-annotations.europeana.eu/annotation/webanno/447?wskey=apidemo) |
[The Unicorn](http://data.europeana.eu/item/2063627/BEL_280_009) | [?](http://www.wikidata.org/entity/Q22442940) | [448](http://test-annotations.europeana.eu/annotation/webanno/448?wskey=apidemo) |
[La nuque](http://data.europeana.eu/item/2063627/BEL_280_010) | [Figure Seen from Behind](http://www.wikidata.org/entity/Q21152148) | [449](http://test-annotations.europeana.eu/annotation/webanno/449?wskey=apidemo) |
[Project for Sky-Writing (planche n°1)](http://data.europeana.eu/item/2063627/BEL_280_011) | [Project for Sky-Writing (planche n°1)](http://www.wikidata.org/entity/Q22442953) | [450](http://test-annotations.europeana.eu/annotation/webanno/450?wskey=apidemo) |
[Ironing](http://data.europeana.eu/item/2063627/BEL_280_012) | [The Ironer](http://www.wikidata.org/entity/Q20992173) | [451](http://test-annotations.europeana.eu/annotation/webanno/451?wskey=apidemo) |
[The Virgin and Child with Canon Joris Van der Paele](http://data.europeana.eu/item/2063627/BEL_280_013) | [Virgin and Child with Canon van der Paele](http://www.wikidata.org/entity/Q2480921) | [452](http://test-annotations.europeana.eu/annotation/webanno/452?wskey=apidemo) |
[Raising of the Cross](http://data.europeana.eu/item/2063627/BEL_280_014) | [The Elevation of the Cross](http://www.wikidata.org/entity/Q2653851) | [453](http://test-annotations.europeana.eu/annotation/webanno/453?wskey=apidemo) |
[Christ Carrying the Cross](http://data.europeana.eu/item/2063627/BEL_280_015) | [Christ Carrying the Cross](http://www.wikidata.org/entity/Q680128) | [454](http://test-annotations.europeana.eu/annotation/webanno/454?wskey=apidemo) |
[The Ghent Altarpiece](http://data.europeana.eu/item/2063627/BEL_280_016) | [Ghent Altarpiece](http://www.wikidata.org/entity/Q734834) | [455](http://test-annotations.europeana.eu/annotation/webanno/455?wskey=apidemo) |
[The Ha'Penny Bridge Dublin](http://data.europeana.eu/item/2063628/IRE_280_001) | [The Ha'Penny Bridge, Dublin](http://www.wikidata.org/entity/Q21727671) | [456](http://test-annotations.europeana.eu/annotation/webanno/456?wskey=apidemo) |
[New Sexual Lifestyles](http://data.europeana.eu/item/2063628/IRE_280_002) | [New Sexual Lifestyles](http://www.wikidata.org/entity/Q21727723) | [457](http://test-annotations.europeana.eu/annotation/webanno/457?wskey=apidemo) |
[Saddle](http://data.europeana.eu/item/2063628/IRE_280_003) | [Saddle](http://www.wikidata.org/entity/Q21743157) | [458](http://test-annotations.europeana.eu/annotation/webanno/458?wskey=apidemo) |
[Four Element Composition](http://data.europeana.eu/item/2063628/IRE_280_004) | [Four Element Composition](http://www.wikidata.org/entity/Q21743163) | [459](http://test-annotations.europeana.eu/annotation/webanno/459?wskey=apidemo) |
["We Are Not Afraid" campaign, NYC subways](http://data.europeana.eu/item/2063628/IRE_280_005) | [''We Are Not Afraid'' campaign, NYC subways](http://www.wikidata.org/entity/Q21743177) | [460](http://test-annotations.europeana.eu/annotation/webanno/460?wskey=apidemo) |
[Berry Dress](http://data.europeana.eu/item/2063628/IRE_280_006) | [Berry Dress](http://www.wikidata.org/entity/Q21743180) | [461](http://test-annotations.europeana.eu/annotation/webanno/461?wskey=apidemo) |
[Meditation Painting 28](http://data.europeana.eu/item/2063628/IRE_280_007) | [Meditation Painting 28](http://www.wikidata.org/entity/Q21743184) | [462](http://test-annotations.europeana.eu/annotation/webanno/462?wskey=apidemo) |
[Dust Storm  (Manter, Kansas)](http://data.europeana.eu/item/2063628/IRE_280_008) | [Dust Storm (Manter, Kansas)](http://www.wikidata.org/entity/Q21743189) | [463](http://test-annotations.europeana.eu/annotation/webanno/463?wskey=apidemo) |
[East Coast Light](http://data.europeana.eu/item/2063628/IRE_280_009) | [East Coast  Light I](http://www.wikidata.org/entity/Q21727692) | [464](http://test-annotations.europeana.eu/annotation/webanno/464?wskey=apidemo) |
[Medusa](http://data.europeana.eu/item/2063628/IRE_280_010) | [Medusa](http://www.wikidata.org/entity/Q21743202) | [465](http://test-annotations.europeana.eu/annotation/webanno/465?wskey=apidemo) |
[The Tower of Babel](http://data.europeana.eu/item/2063629/AUS_280_001) | [The Tower of Babel (Bruegel)](http://www.wikidata.org/entity/Q15293656) | [466](http://test-annotations.europeana.eu/annotation/webanno/466?wskey=apidemo) |
[Cellini Salt Cellar](http://data.europeana.eu/item/2063629/AUS_280_002) | [Cellini Salt Cellar](http://www.wikidata.org/entity/Q697208) | [467](http://test-annotations.europeana.eu/annotation/webanno/467?wskey=apidemo) |
[Wing of a European Roller](http://data.europeana.eu/item/2063629/AUS_280_003) | [Wing of a European Roller](http://www.wikidata.org/entity/Q21151884) | [468](http://test-annotations.europeana.eu/annotation/webanno/468?wskey=apidemo) |
[Male Back With a Flag](http://data.europeana.eu/item/2063629/AUS_280_004) | [Male Back With a Flag](http://www.wikidata.org/entity/Q21151813) | [469](http://test-annotations.europeana.eu/annotation/webanno/469?wskey=apidemo) |
[The Kiss](http://data.europeana.eu/item/2063629/AUS_280_005) | [The Kiss](http://www.wikidata.org/entity/Q698487) | [470](http://test-annotations.europeana.eu/annotation/webanno/470?wskey=apidemo) |
[Der Schaafkopf](http://data.europeana.eu/item/2063629/AUS_280_006) | [Der Schaafkopf](http://www.wikidata.org/entity/Q22920411) | [471](http://test-annotations.europeana.eu/annotation/webanno/471?wskey=apidemo) |
[Umar defeats a dragon](http://data.europeana.eu/item/2063629/AUS_280_007) | [Umar Defeats a Dragon](http://www.wikidata.org/entity/Q22946905) | [472](http://test-annotations.europeana.eu/annotation/webanno/472?wskey=apidemo) |
[Metropolis](http://data.europeana.eu/item/2063629/AUS_280_008) | [Metropolis film poster](http://www.wikidata.org/entity/Q21151973) | [473](http://test-annotations.europeana.eu/annotation/webanno/473?wskey=apidemo) |
[Nature studies](http://data.europeana.eu/item/2063629/AUS_280_009) | [Nature studies](http://www.wikidata.org/entity/Q21749047) | [474](http://test-annotations.europeana.eu/annotation/webanno/474?wskey=apidemo) |
[Schönberg family](http://data.europeana.eu/item/2063629/AUS_280_011) | [Schönberg family](http://www.wikidata.org/entity/Q20980856) | [475](http://test-annotations.europeana.eu/annotation/webanno/475?wskey=apidemo) |
[Aktion mit eigenem Körper](http://data.europeana.eu/item/2063629/AUS_280_012) | [Action with their own bodies](http://www.wikidata.org/entity/Q22826410) | [476](http://test-annotations.europeana.eu/annotation/webanno/476?wskey=apidemo) |


## List of references to potentially duplicate items

The list below shows the Wikidata resources which refer to more than one 
Europeana object. As this typically indicates that the two Europeana objects
are equivalent, a annotation was also generated linking them together.

| Wikidata Entry | Europeana Object 1 | Europeana Object 2 |
| :--- | :--- | :--- |
[H.H. Simon en Judaskerk](http://www.wikidata.org/entity/Q11721791) | [Rijksmonument](http://data.europeana.eu/item/2020718/DR_31756) | [RijksmonumentH.H. Simon en Judaskerk](http://data.europeana.eu/item/2020718/DR_508960) |
[The jolly drinker](http://www.wikidata.org/entity/Q17324463) | [The Merry Drinker (Jolly Toper)](http://data.europeana.eu/item/90402/SK_A_1685) | [The Jolly Toper](http://data.europeana.eu/item/92034/GVNRC_FHM01_OS_60_55) |
[Portrait of Joan Meatsuyker (1606-78), Governor general (1653-78)](http://www.wikidata.org/entity/Q17334843) | [Portrait of Joan Maetsuyker, Governor-General of the Dutch East Indies](http://data.europeana.eu/item/90402/SK_A_3765) | [Portrait of Joan Maetsuyker, Governor-General of the Dutch East Indies](http://data.europeana.eu/item/90402/SK_A_4535) |
[Fort Crèvecoeur](http://www.wikidata.org/entity/Q17371198) | [RijksmonumentFort Crevecoer](http://data.europeana.eu/item/2020718/DR_14969) | [RijksmonumentFort Crevecoer](http://data.europeana.eu/item/2020718/DR_14970) |
[Witgepleisterde villa](http://www.wikidata.org/entity/Q17454904) | [Rijksmonument](http://data.europeana.eu/item/2020718/DR_46519) | [Rijksmonument](http://data.europeana.eu/item/2020718/DR_8367) |
[Inundatiekanaal](http://www.wikidata.org/entity/Q17463195) | [RijksmonumentInundatiekanaal](http://data.europeana.eu/item/2020718/DR_511649) | [RijksmonumentInundatiekanaal](http://data.europeana.eu/item/2020718/DR_511650) |
[The Kitchen Maid](http://www.wikidata.org/entity/Q18089111) | ["Kökspigan"](http://data.europeana.eu/item/2048005/Athena_Plus_ProvidedCHO_Nationalmuseum__Sweden_17587) | [The Kitchen Maid](http://data.europeana.eu/item/2063602/SWE_280_003) |
[The Town](http://www.wikidata.org/entity/Q18600103) | [Staden](http://data.europeana.eu/item/2048005/Athena_Plus_ProvidedCHO_Nationalmuseum__Sweden_21568) | [The Town](http://data.europeana.eu/item/2063602/SWE_280_010) |
[At the French Windows. The Artist's Wife](http://www.wikidata.org/entity/Q20537703) | [At the French Windows. The Artist's Wife](http://data.europeana.eu/item/2020903/KMS3716) | [At the French Windows. The Artist's Wife](http://data.europeana.eu/item/2063604/DEN_280_016) |
[Gronsveltkameren](http://www.wikidata.org/entity/Q2113364) | [Rijksmonument](http://data.europeana.eu/item/2020718/DR_36376) | [Rijksmonument](http://data.europeana.eu/item/2020718/DR_36378) |
[Sint-Petrustsjerke](http://www.wikidata.org/entity/Q2150500) | [Rijksmonument](http://data.europeana.eu/item/2020718/DR_31865) | [Rijksmonument](http://data.europeana.eu/item/2020718/DR_31866) |
[Stadbroekermolen](http://www.wikidata.org/entity/Q2360972) | [Rijksmonument](http://data.europeana.eu/item/2020718/DR_33713) | [RijksmonumentStadbroekermolen](http://data.europeana.eu/item/2020718/DR_521650) |
[De Edensermolen](http://www.wikidata.org/entity/Q2466818) | [RijksmonumentIenzer Mole](http://data.europeana.eu/item/2020718/DR_21571) | [Rijksmonument](http://data.europeana.eu/item/2020718/DR_21575) |
[Teetlum, Tzum](http://www.wikidata.org/entity/Q2780699) | [RijksmonumentTeatlum](http://data.europeana.eu/item/2020718/DR_15875) | [Rijksmonument](http://data.europeana.eu/item/2020718/DR_15877) |
[St. Martin's Cathedral](http://www.wikidata.org/entity/Q936545) | [Rijksmonument](http://data.europeana.eu/item/2020718/DR_35973) | [Rijksmonument](http://data.europeana.eu/item/2020718/DR_36077) |


## Resources

The following resources were generated:
* [links.csv]
(../src/test/resources/etc/chowdt/links.csv): All links that were harvested.
* [links_invalid.csv]
(../src/test/resources/etc/chowdt/links_invalid.csv): All 
links that were no longer resolving and therefore considered as invalid.
* [links_dup.csv]
(../src/test/resources/etc/chowdt/links_dup.csv): All duplicate links.
* [links_anno.csv]
(../src/test/resources/etc/chowdt/links_anno.csv): All 
links for which an annotation was created.
* [links_sample.csv]
(../src/test/resources/etc/chowdt/links_sample.csv): A sample of the annotations 
that were created in particular for the Europeana 280 Project.

## Usage

