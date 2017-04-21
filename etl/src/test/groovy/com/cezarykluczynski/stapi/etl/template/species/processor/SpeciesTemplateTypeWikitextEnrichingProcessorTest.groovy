package com.cezarykluczynski.stapi.etl.template.species.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification

class SpeciesTemplateTypeWikitextEnrichingProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock

	private SpeciesTemplateTypeWikitextEnrichingProcessor speciesTemplateTypeWikitextEnrichingProcessor

	void setup() {
		wikitextApiMock = Mock()
		speciesTemplateTypeWikitextEnrichingProcessor = new SpeciesTemplateTypeWikitextEnrichingProcessor(wikitextApiMock)
	}

	@SuppressWarnings('LineLength')
	void "sets flag from species type link"() {
		given:
		Template.Part templatePart = new Template.Part(value: WIKITEXT)
		wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(title)

		expect:
		speciesTemplateTypeWikitextEnrichingProcessor.enrich(EnrichablePair.of(templatePart, speciesTemplate))
		flag == speciesTemplate[flagName]
		numberOfTrueBooleans == ReflectionTestUtils.getNumberOfTrueBooleanFields(speciesTemplate)

		where:
		speciesTemplate       | title                                                               | flagName               | flag  | numberOfTrueBooleans
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.HUMANOID              | 'humanoidSpecies'      | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.SHAPESHIFTER          | 'shapeshiftingSpecies' | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.SAURIAN               | 'reptilianSpecies'     | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.NON_CORPOREAL_SPECIES | 'nonCorporealSpecies'  | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.REPTILE               | 'reptilianSpecies'     | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.UNKNOWN               | 'humanoidSpecies'      | false | 0
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.NON_HUMANOID          | 'humanoidSpecies'      | false | 0
	}

}
