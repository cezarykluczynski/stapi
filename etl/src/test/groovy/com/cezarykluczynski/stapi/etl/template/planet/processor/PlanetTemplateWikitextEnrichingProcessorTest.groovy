package com.cezarykluczynski.stapi.etl.template.planet.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class PlanetTemplateWikitextEnrichingProcessorTest extends Specification {

	private static final String WIKITEXT_50 = 'abcdefghij0123456789abcdefghij0123456789abcdefghij'
	private static final String WIKITEXT_300 = '\'\'\'' + WIKITEXT_50 + WIKITEXT_50 + WIKITEXT_50 + WIKITEXT_50 + WIKITEXT_50 + WIKITEXT_50
	private static final String WIKITEXT_200 = WIKITEXT_50 + WIKITEXT_50 + WIKITEXT_50 + WIKITEXT_50
	private static final AstronomicalObjectType ASTRONOMICAL_OBJECT_TYPE_PLANET = AstronomicalObjectType.PLANET
	private static final AstronomicalObjectType ASTRONOMICAL_OBJECT_TYPE_NEBULA = AstronomicalObjectType.NEBULA

	private AstronomicalObjectWikitextProcessor astronomicalObjectWikitextProcessorMock

	private PlanetTemplateWikitextEnrichingProcessor planetTemplateWikitextEnrichingProcessor

	void setup() {
		astronomicalObjectWikitextProcessorMock = Mock()
		planetTemplateWikitextEnrichingProcessor = new PlanetTemplateWikitextEnrichingProcessor(astronomicalObjectWikitextProcessorMock)
	}

	void "when astronomical object type is neither null nor planet, nothing happens"() {
		given:
		Page page = new Page()
		PlanetTemplate planetTemplate = new PlanetTemplate(astronomicalObjectType: AstronomicalObjectType.CONSTELLATION)

		when:
		planetTemplateWikitextEnrichingProcessor.enrich(EnrichablePair.of(page, planetTemplate))

		then:
		0 * _
	}

	void "when astronomical object type is null, wikitext is parsed"() {
		given:
		Page page = new Page(wikitext: WIKITEXT_300)
		PlanetTemplate planetTemplate = new PlanetTemplate(astronomicalObjectType: null)

		when:
		planetTemplateWikitextEnrichingProcessor.enrich(EnrichablePair.of(page, planetTemplate))

		then:
		1 * astronomicalObjectWikitextProcessorMock.process(WIKITEXT_200) >> ASTRONOMICAL_OBJECT_TYPE_NEBULA
		0 * _
		planetTemplate.astronomicalObjectType == ASTRONOMICAL_OBJECT_TYPE_NEBULA
	}

	void "when astronomical object type is planet, wikitext is parsed, but when null is returned, planet type is kept"() {
		given:
		Page page = new Page(wikitext: WIKITEXT_300)
		PlanetTemplate planetTemplate = new PlanetTemplate(astronomicalObjectType: ASTRONOMICAL_OBJECT_TYPE_PLANET)

		when:
		planetTemplateWikitextEnrichingProcessor.enrich(EnrichablePair.of(page, planetTemplate))

		then:
		1 * astronomicalObjectWikitextProcessorMock.process(WIKITEXT_200) >> null
		0 * _
		planetTemplate.astronomicalObjectType == ASTRONOMICAL_OBJECT_TYPE_PLANET
	}

	void "when astronomical object type is planet, wikitext is parsed, and when returned type is not null, it is used"() {
		given:
		Page page = new Page(wikitext: WIKITEXT_300)
		PlanetTemplate planetTemplate = new PlanetTemplate(astronomicalObjectType: ASTRONOMICAL_OBJECT_TYPE_PLANET)

		when:
		planetTemplateWikitextEnrichingProcessor.enrich(EnrichablePair.of(page, planetTemplate))

		then:
		1 * astronomicalObjectWikitextProcessorMock.process(WIKITEXT_200) >> ASTRONOMICAL_OBJECT_TYPE_NEBULA
		0 * _
		planetTemplate.astronomicalObjectType == ASTRONOMICAL_OBJECT_TYPE_NEBULA
	}

}
