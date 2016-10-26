<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:cld="http://purl.org/cld/terms/"
  xmlns:crm="http://www.cidoc-crm.org/rdfs/cidoc_crm_v5.0.2_english_label.rdfs#"
  xmlns:dc="http://purl.org/dc/elements/1.1/"
  xmlns:dcterms="http://purl.org/dc/terms/"
  xmlns:ebucore="http://www.ebu.ch/metadata/ontologies/ebucore/ebucore#"
  xmlns:edm="http://www.europeana.eu/schemas/edm/"
  xmlns:foaf="http://xmlns.com/foaf/0.1/"
  xmlns:mo="http://purl.org/ontology/mo/"
  xmlns:ore="http://www.openarchives.org/ore/terms/"
  xmlns:owl="http://www.w3.org/2002/07/owl#"
  xmlns:rdaGr2="http://rdvocab.info/ElementsGr2/"
  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
  xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
  xmlns:schema="http://schema.org/"
  xmlns:skos="http://www.w3.org/2004/02/skos/core#"
  xmlns:wgs84="http://www.w3.org/2003/01/geo/wgs84_pos#"
  xmlns:xalan="http://xml.apache.org/xalan" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:param name="baseURI">http://mint-projects.image.ntua.gr/data/</xsl:param>
  <xsl:param name="collection">sounds</xsl:param>
  <xsl:template match="/">
    <xsl:apply-templates select="/item"/>
  </xsl:template>
  <xsl:template match="/item">
    <!-- rdf:RDF, id: 0 -->
    <rdf:RDF>
      <!-- Check for mandatory elements on edm:ProvidedCHO -->
      <xsl:if test="dc_identifier">
        <!-- edm:ProvidedCHO, id: 1 -->
        <edm:ProvidedCHO>
          <xsl:attribute name="rdf:about">
            <xsl:value-of select="$baseURI"/>
            <xsl:value-of select="$collection"/>
            <xsl:text>/</xsl:text>
            <xsl:for-each select="dc_identifier">
              <xsl:if test="position() = 1">
                <xsl:value-of select="."/>
              </xsl:if>
            </xsl:for-each>
          </xsl:attribute>
          <!-- dc:contributor, id: 574 -->
          <xsl:for-each select="dc_contributor__recordist_">
            <dc:contributor>
              <xsl:value-of select="."/>
            </dc:contributor>
          </xsl:for-each>
          <!-- dc:contributor, id: 571 -->
          <xsl:for-each select="dc_interviewee">
            <dc:contributor>
              <xsl:value-of select="."/>
            </dc:contributor>
          </xsl:for-each>
          <!-- dc:contributor, id: 568 -->
          <xsl:for-each select="dc_interviewer">
            <dc:contributor>
              <xsl:value-of select="."/>
            </dc:contributor>
          </xsl:for-each>
          <!-- dc:contributor, id: 565 -->
          <xsl:for-each select="dc_performer">
            <dc:contributor>
              <xsl:value-of select="."/>
            </dc:contributor>
          </xsl:for-each>
          <!-- dc:contributor, id: 3 -->
          <xsl:for-each select="dc_contributor__speaker_">
            <dc:contributor>
              <xsl:value-of select="."/>
            </dc:contributor>
          </xsl:for-each>
          <!-- dc:date, id: 12 -->
          <xsl:for-each select="dc_date">
            <dc:date>
              <xsl:value-of select="."/>
            </dc:date>
          </xsl:for-each>
          <!-- dc:description, id: 15 -->
          <xsl:for-each select="dc_description">
            <dc:description>
              <xsl:value-of select="."/>
            </dc:description>
          </xsl:for-each>
          <!-- dc:format, id: 18 -->
          <dc:format>
            <xsl:text/>
          </dc:format>
          <!-- dc:identifier, id: 21 -->
          <xsl:for-each select="dc_identifier">
            <dc:identifier>
              <xsl:value-of select="."/>
            </dc:identifier>
          </xsl:for-each>
          <!-- dc:language, id: 23 -->
          <dc:language>
            <xsl:text>eng</xsl:text>
          </dc:language>
          <!-- dc:subject, id: 583 -->
          <xsl:for-each select="dc_subject1">
            <dc:subject>
              <xsl:value-of select="."/>
            </dc:subject>
          </xsl:for-each>
          <!-- dc:subject, id: 580 -->
          <xsl:for-each select="dc_subject2">
            <dc:subject>
              <xsl:value-of select="."/>
            </dc:subject>
          </xsl:for-each>
          <!-- dc:subject, id: 577 -->
          <xsl:for-each select="dc_subject3">
            <dc:subject>
              <xsl:value-of select="."/>
            </dc:subject>
          </xsl:for-each>
          <!-- dc:subject, id: 37 -->
          <xsl:for-each select="dc_subject4">
            <dc:subject>
              <xsl:value-of select="."/>
            </dc:subject>
          </xsl:for-each>
          <!-- dc:title, id: 40 -->
          <xsl:for-each select="dc_title">
            <dc:title>
              <xsl:value-of select="."/>
            </dc:title>
          </xsl:for-each>
          <!-- dcterms:extent, id: 56 -->
          <xsl:for-each select="dc_extent">
            <dcterms:extent>
              <xsl:value-of select="."/>
            </dcterms:extent>
          </xsl:for-each>
          <!-- dcterms:spatial, id: 106 -->
          <xsl:for-each select="dc_spatial">
            <dcterms:spatial>
              <xsl:value-of select="."/>
            </dcterms:spatial>
          </xsl:for-each>
          <!-- edm:type, id: 141 -->
          <edm:type>
            <xsl:text>SOUND</xsl:text>
          </edm:type>
          <!-- ebucore:hasGenre, id: 559 -->
          <ebucore:hasGenre>
            <xsl:attribute name="rdf:resource">
              <xsl:text>http://data.europeana.eu/concept/soundgenres/Music/Traditional_and_folk_music</xsl:text>
            </xsl:attribute>
          </ebucore:hasGenre>
        </edm:ProvidedCHO>
      </xsl:if>
      <!-- Check for mandatory elements on ore:Aggregation -->
      <xsl:if test="dc_identifier">
        <!-- ore:Aggregation, id: 468 -->
        <ore:Aggregation>
          <xsl:attribute name="rdf:about">
            <xsl:value-of select="$baseURI"/>
            <xsl:value-of select="$collection"/>
            <xsl:text>/Aggregation_</xsl:text>
            <xsl:for-each select="dc_identifier">
              <xsl:if test="position() = 1">
                <xsl:value-of select="."/>
              </xsl:if>
            </xsl:for-each>
          </xsl:attribute>
          <!-- Check for mandatory elements on edm:aggregatedCHO -->
          <xsl:if test="dc_identifier">
            <!-- edm:aggregatedCHO, id: 470 -->
            <edm:aggregatedCHO>
              <xsl:attribute name="rdf:resource">
                <xsl:value-of select="$baseURI"/>
                <xsl:value-of select="$collection"/>
                <xsl:text>/</xsl:text>
                <xsl:for-each select="dc_identifier">
                  <xsl:if test="position() = 1">
                    <xsl:value-of select="."/>
                  </xsl:if>
                </xsl:for-each>
              </xsl:attribute>
            </edm:aggregatedCHO>
          </xsl:if>
          <!-- edm:dataProvider, id: 472 -->
          <edm:dataProvider>
            <xsl:text>The British Library</xsl:text>
          </edm:dataProvider>
          <!-- Check for mandatory elements on edm:isShownAt -->
          <xsl:if test="dc_identifier">
            <!-- edm:isShownAt, id: 477 -->
            <edm:isShownAt>
              <xsl:attribute name="rdf:resource">
                <xsl:text>http://sounds.bl.uk/World-and-traditional-music/Keith-Summers-Collection/025M-</xsl:text>
                <xsl:for-each select="dc_identifier">
                  <xsl:if test="position() = 1">
                    <xsl:value-of select="."/>
                  </xsl:if>
                </xsl:for-each>
                <xsl:text>V0</xsl:text>
              </xsl:attribute>
              <xsl:text/>
            </edm:isShownAt>
          </xsl:if>
          <!-- Check for mandatory elements on edm:object -->
          <xsl:if test="dc_identifier">
            <!-- edm:object, id: 481 -->
            <edm:object>
              <xsl:attribute name="rdf:resource">
                <xsl:text>http://sounds.bl.uk/waveforms/Keith-Summers-Collection/025A-</xsl:text>
                <xsl:for-each select="dc_identifier">
                  <xsl:if test="position() = 1">
                    <xsl:value-of select="."/>
                  </xsl:if>
                </xsl:for-each>
                <xsl:text>A0.png</xsl:text>
              </xsl:attribute>
            </edm:object>
          </xsl:if>
          <!-- edm:provider, id: 483 -->
          <edm:provider>
            <xsl:text>Europeana Sounds</xsl:text>
          </edm:provider>
          <!-- dc:rights, id: 486 -->
          <dc:rights>
            <xsl:text>Please view the British Library's Legal and Ethical Usage page: http://sounds.bl.uk/Information/Legal-And-Ethical-Usage </xsl:text>
          </dc:rights>
          <!-- edm:rights, id: 489 -->
          <edm:rights>
            <xsl:attribute name="rdf:resource">
              <xsl:text>http://www.europeana.eu/rights/rr-f/</xsl:text>
            </xsl:attribute>
            <xsl:text/>
          </edm:rights>
        </ore:Aggregation>
      </xsl:if>
    </rdf:RDF>
  </xsl:template>
</xsl:stylesheet>
