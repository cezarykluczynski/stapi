package com.cezarykluczynski.stapi.etl.template.species.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplate
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class SpeciesTemplateTypeWikitextEnrichingProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock

	private SpeciesTemplateTypeWikitextEnrichingProcessor speciesTemplateTypeWikitextEnrichingProcessor

	void setup() {
		wikitextApiMock = Mock()
		speciesTemplateTypeWikitextEnrichingProcessor = new SpeciesTemplateTypeWikitextEnrichingProcessor(wikitextApiMock)
	}

	@SuppressWarnings('LineLength')
	@Unroll('#title sets #flagName to #flag')
	void "sets flag from species type link"() {
		given:
		Template.Part templatePart = new Template.Part(value: WIKITEXT)
		wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(title)

		expect:
		speciesTemplateTypeWikitextEnrichingProcessor.enrich(EnrichablePair.of(templatePart, speciesTemplate))
		flag == speciesTemplate[flagName]
		numberOfTrueBooleans == ReflectionTestUtils.getNumberOfTrueBooleanFields(speciesTemplate)

		where:
		speciesTemplate       | title                                                                | flagName               | flag  | numberOfTrueBooleans
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.HUMANOID               | 'humanoidSpecies'      | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.SHAPESHIFTER           | 'shapeshiftingSpecies' | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.SHAPESHIFTING_SPECIES  | 'shapeshiftingSpecies' | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.SHAPE_CHANGER          | 'shapeshiftingSpecies' | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.REPTILE                | 'reptilianSpecies'     | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.REPTILIAN              | 'reptilianSpecies'     | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.REPTILOID              | 'reptilianSpecies'     | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.SAURIAN                | 'reptilianSpecies'     | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.ORNITHOID              | 'avianSpecies'         | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.AVIAN                  | 'avianSpecies'         | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.BIRD                   | 'avianSpecies'         | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.NON_CORPOREAL_SPECIES  | 'nonCorporealSpecies'  | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.NON_CORPOREAL          | 'nonCorporealSpecies'  | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.NON_CORPOREAL_LIFEFORM | 'nonCorporealSpecies'  | true  | 1
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.UNKNOWN                | 'humanoidSpecies'      | false | 0
		new SpeciesTemplate() | SpeciesTemplateTypeWikitextEnrichingProcessor.NON_HUMANOID           | 'humanoidSpecies'      | false | 0
	}

	@Unroll('empty wikitext \'#wikitext\' is tolerated')
	void "empty wikitext is tolerated"() {
		given:
		SpeciesTemplate speciesTemplate = Mock()
		Template.Part templatePart = new Template.Part(value: wikitext)
		0 * _

		expect:
		speciesTemplateTypeWikitextEnrichingProcessor.enrich(EnrichablePair.of(templatePart, speciesTemplate))

		where:
		wikitext << [null, '', ' ']
	}

}
