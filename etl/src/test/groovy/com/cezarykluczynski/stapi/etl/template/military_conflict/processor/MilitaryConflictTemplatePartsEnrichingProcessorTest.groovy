package com.cezarykluczynski.stapi.etl.template.military_conflict.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.template.comic_series.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplateParameter
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.location.entity.Location
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class MilitaryConflictTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String DATE = '2373-2375'
	private static final Integer YEAR_FROM = 2373
	private static final Integer YEAR_TO = 2375
	private static final String LOCATION = 'LOCATION'
	private static final String COMBATANT_1 = 'COMBATANT_1'
	private static final String COMMANDER_1 = 'COMMANDER_1'
	private static final String COMBATANT_2 = 'COMBATANT_2'
	private static final String COMMANDER_2 = 'COMMANDER_2'

	private MilitaryConflictTemplatePartOfEnrichingProcessor militaryConflictTemplatePartOfEnrichingProcessorMock

	private WikitextToYearRangeProcessor wikitextToYearRangeProcessorMock

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private MilitaryConflictTemplatePartsEnrichingProcessor militaryConflictTemplatePartsEnrichingProcessor

	void setup() {
		militaryConflictTemplatePartOfEnrichingProcessorMock = Mock()
		wikitextToYearRangeProcessorMock = Mock()
		wikitextToEntitiesProcessorMock = Mock()
		militaryConflictTemplatePartsEnrichingProcessor = new MilitaryConflictTemplatePartsEnrichingProcessor(
				militaryConflictTemplatePartOfEnrichingProcessorMock, wikitextToYearRangeProcessorMock, wikitextToEntitiesProcessorMock)
	}

	void "passes MilitaryConflictTemplate to MilitaryConflictTemplatePartOfEnrichingProcessor, when part of part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: MilitaryConflictTemplateParameter.PART_OF)
		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate()

		when:
		militaryConflictTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), militaryConflictTemplate))

		then:
		1 * militaryConflictTemplatePartOfEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, ComicSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "sets year from and year to from WikitextToYearRangeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MilitaryConflictTemplateParameter.DATE, value: DATE)
		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate()
		YearRange yearRange = new YearRange(yearFrom: YEAR_FROM, yearTo: YEAR_TO)

		when:
		militaryConflictTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), militaryConflictTemplate))

		then:
		1 * wikitextToYearRangeProcessorMock.process(DATE) >> yearRange
		0 * _
		militaryConflictTemplate.yearFrom == YEAR_FROM
		militaryConflictTemplate.yearTo == YEAR_TO
	}

	void "adds all locations from WikitextToEntitiesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MilitaryConflictTemplateParameter.LOCATION, value: LOCATION)
		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate()
		Location location1 = Mock()
		Location location2 = Mock()

		when:
		militaryConflictTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), militaryConflictTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findLocations(LOCATION) >> Lists.newArrayList(location1, location2)
		0 * _
		militaryConflictTemplate.locations.contains location1
		militaryConflictTemplate.locations.contains location2
	}

	void "adds first side belligerents from WikitextToEntitiesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MilitaryConflictTemplateParameter.COMBATANT_1, value: COMBATANT_1)
		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate()
		Organization organization1 = Mock()
		Organization organization2 = Mock()

		when:
		militaryConflictTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), militaryConflictTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(COMBATANT_1) >> Lists.newArrayList(organization1, organization2)
		0 * _
		militaryConflictTemplate.firstSideBelligerents.contains organization1
		militaryConflictTemplate.firstSideBelligerents.contains organization2
	}

	void "adds first side commanders from WikitextToEntitiesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MilitaryConflictTemplateParameter.COMMANDER_1, value: COMMANDER_1)
		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate()
		Character character1 = Mock()
		Character character2 = Mock()

		when:
		militaryConflictTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), militaryConflictTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findCharacters(COMMANDER_1) >> Lists.newArrayList(character1, character2)
		0 * _
		militaryConflictTemplate.firstSideCommanders.contains character1
		militaryConflictTemplate.firstSideCommanders.contains character2
	}

	void "adds second side belligerents from WikitextToEntitiesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MilitaryConflictTemplateParameter.COMBATANT_2, value: COMBATANT_2)
		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate()
		Organization organization1 = Mock()
		Organization organization2 = Mock()

		when:
		militaryConflictTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), militaryConflictTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(COMBATANT_2) >> Lists.newArrayList(organization1, organization2)
		0 * _
		militaryConflictTemplate.secondSideBelligerents.contains organization1
		militaryConflictTemplate.secondSideBelligerents.contains organization2
	}

	void "adds second side commanders from WikitextToEntitiesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MilitaryConflictTemplateParameter.COMMANDER_2, value: COMMANDER_2)
		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate()
		Character character1 = Mock()
		Character character2 = Mock()

		when:
		militaryConflictTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), militaryConflictTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findCharacters(COMMANDER_2) >> Lists.newArrayList(character1, character2)
		0 * _
		militaryConflictTemplate.secondSideCommanders.contains character1
		militaryConflictTemplate.secondSideCommanders.contains character2
	}

}
