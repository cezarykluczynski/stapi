package com.cezarykluczynski.stapi.etl.template.species.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplate
import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplateParameter
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class SpeciesTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String PLANET = 'PLANET'
	private static final String QUADRANT = 'QUADRANT'
	private static final String WIKITEXT = 'WIKITEXT'
	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private WikitextApi wikitextApiMock

	private EntityLookupByNameService entityLookupByNameServiceMock

	private SpeciesTemplateTypeWikitextEnrichingProcessor speciesTemplateTypeWikitextEnrichingProcessorMock

	private SpeciesTemplatePartsEnrichingProcessor speciesTemplatePartsEnrichingProcessor

	void setup() {
		wikitextApiMock = Mock()
		entityLookupByNameServiceMock = Mock()
		speciesTemplateTypeWikitextEnrichingProcessorMock = Mock()
		speciesTemplatePartsEnrichingProcessor = new SpeciesTemplatePartsEnrichingProcessor(wikitextApiMock, entityLookupByNameServiceMock,
				speciesTemplateTypeWikitextEnrichingProcessorMock)
	}

	void "sets name"() {
		given:
		Template.Part templatePart = new Template.Part(key: SpeciesTemplateParameter.NAME, value: NAME)
		SpeciesTemplate speciesTemplate = new SpeciesTemplate()

		when:
		speciesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), speciesTemplate))

		then:
		0 * _
		speciesTemplate.name == NAME
	}

	void "sets homeworld from wikitext, then from EntityLookupByNameService"() {
		given:
		Template.Part templatePart = new Template.Part(key: SpeciesTemplateParameter.PLANET, value: WIKITEXT)
		SpeciesTemplate speciesTemplate = new SpeciesTemplate()
		AstronomicalObject homeworld = Mock()

		when:
		speciesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), speciesTemplate))

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PLANET)
		1 * entityLookupByNameServiceMock.findAstronomicalObjectByName(PLANET, SOURCE) >> Optional.of(homeworld)
		0 * _
		speciesTemplate.homeworld == homeworld
		speciesTemplate.quadrant == null
	}

	void "sets quadrant from wikitext, then from EntityLookupByNameService"() {
		given:
		Template.Part templatePart = new Template.Part(key: SpeciesTemplateParameter.QUADRANT, value: WIKITEXT)
		SpeciesTemplate speciesTemplate = new SpeciesTemplate()
		AstronomicalObject quadrant = Mock()

		when:
		speciesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), speciesTemplate))

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(QUADRANT)
		1 * entityLookupByNameServiceMock.findAstronomicalObjectByName(QUADRANT, SOURCE) >> Optional.of(quadrant)
		0 * _
		speciesTemplate.quadrant == quadrant
		speciesTemplate.homeworld == null
	}

	void "when type part is found, it is passed to SpeciesTemplateTypeWikitextEnrichingProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: SpeciesTemplateParameter.TYPE, value: '')
		SpeciesTemplate speciesTemplate = new SpeciesTemplate()

		when:
		speciesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), speciesTemplate))

		then:
		1 * speciesTemplateTypeWikitextEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Template.Part, SpeciesTemplate> enrichablePair ->
			assert enrichablePair.input == templatePart
			assert enrichablePair.output == speciesTemplate
		}
		0 * _
	}

	void "sets extincted species flag to true when population value is 'extinct'"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: SpeciesTemplateParameter.POPULATION,
				value: SpeciesTemplatePartsEnrichingProcessor.EXTINCT)
		SpeciesTemplate speciesTemplate = new SpeciesTemplate()

		when:
		speciesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), speciesTemplate))

		then:
		0 * _
		speciesTemplate.extinctSpecies
	}

	void "logs population template part with value other than 'extinct'"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: SpeciesTemplateParameter.POPULATION,
				value: '')
		SpeciesTemplate speciesTemplate = Mock()

		when:
		speciesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), speciesTemplate))

		then:
		1 * speciesTemplate.name
		0 * _
	}

}
