package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplateParameter
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

class IndividualTemplateDateOfDeathEnrichingProcessorTest extends Specification {

	private static final String KIA_TITLE = 'Casualties'
	private static final String KIA_DESCRIPTION = 'kia'
	private static final String KIA = "[[${KIA_TITLE}|${KIA_DESCRIPTION}]] in some massacre"
	private static final String YEAR_STRING = '1960'
	private static final Integer YEAR_INTEGER = 1960
	private static final String DEAD_SYNONYM = IndividualTemplateDateOfDeathEnrichingProcessor.DEAD_SYNONYMS[0]
	private static final String NOT_DEAD_SYNONYM = IndividualTemplateDateOfDeathEnrichingProcessor.NOT_DEAD_SYNONYMS[0]
	private static final String NEITHER_WORD = 'Neither'

	private IndividualDateStatusValueToYearProcessor individualDateStatusValueToYearProcessorMock

	private WikitextApi wikitextApiMock

	private IndividualTemplateDateOfDeathEnrichingProcessor individualTemplateDateOfDeathEnrichingProcessor

	void setup() {
		individualDateStatusValueToYearProcessorMock = Mock()
		wikitextApiMock = Mock()
		individualTemplateDateOfDeathEnrichingProcessor = new IndividualTemplateDateOfDeathEnrichingProcessor(wikitextApiMock,
				individualDateStatusValueToYearProcessorMock)
	}

	void "does nothing to individual template when there is no parts"() {
		given:
		Template.Part templatePart = Mock(Template.Part)
		Template template = Mock()
		CharacterTemplate characterTemplate = Mock()

		when:
		individualTemplateDateOfDeathEnrichingProcessor.enrich(EnrichablePair.of(template, characterTemplate))

		then:
		1 * template.parts >> Lists.newArrayList(templatePart)
		1 * templatePart.key >> ''
		0 * _
	}

	void "does nothing to individual template when there is only status part"() {
		given:
		Template.Part templatePart = Mock(Template.Part)
		Template template = Mock()
		CharacterTemplate characterTemplate = Mock()

		when:
		individualTemplateDateOfDeathEnrichingProcessor.enrich(EnrichablePair.of(template, characterTemplate))

		then:
		1 * template.parts >> Lists.newArrayList(templatePart)
		1 * templatePart.key >> IndividualTemplateParameter.STATUS
		0 * _
	}

	void "does nothing to individual template when there is only date status part"() {
		given:
		Template.Part templatePart = Mock(Template.Part)
		Template template = Mock()
		CharacterTemplate characterTemplate = Mock()

		when:
		individualTemplateDateOfDeathEnrichingProcessor.enrich(EnrichablePair.of(template, characterTemplate))

		then:
		1 * template.parts >> Lists.newArrayList(templatePart)
		1 * templatePart.key >> IndividualTemplateParameter.DATE_STATUS
		0 * _
	}

	void "does nothing to individual template when status part value is empty"() {
		given:
		Template.Part dateTemplatePart = Mock(Template.Part)
		Template.Part dateStatusTemplatePart = Mock(Template.Part)
		Template template = Mock()
		CharacterTemplate characterTemplate = Mock()

		when:
		individualTemplateDateOfDeathEnrichingProcessor.enrich(EnrichablePair.of(template, characterTemplate))

		then:
		1 * template.parts >> Lists.newArrayList(dateTemplatePart, dateStatusTemplatePart)
		1 * dateTemplatePart.key >> IndividualTemplateParameter.STATUS
		1 * dateTemplatePart.value >> StringUtils.EMPTY
		1 * dateStatusTemplatePart.key >> IndividualTemplateParameter.DATE_STATUS
		0 * _
	}

	void "sets deceased flag to true when status contains 'kia'"() {
		given:
		Template.Part dateTemplatePart = Mock(Template.Part)
		Template.Part dateStatusTemplatePart = Mock(Template.Part)
		Template template = Mock()
		CharacterTemplate characterTemplate = Mock()

		when:
		individualTemplateDateOfDeathEnrichingProcessor.enrich(EnrichablePair.of(template, characterTemplate))

		then:
		1 * template.parts >> Lists.newArrayList(dateTemplatePart, dateStatusTemplatePart)
		1 * dateTemplatePart.key >> IndividualTemplateParameter.STATUS
		2 * dateTemplatePart.value >> KIA
		1 * dateStatusTemplatePart.key >> IndividualTemplateParameter.DATE_STATUS
		1 * dateStatusTemplatePart.value >> YEAR_STRING
		1 * wikitextApiMock.getPageLinksFromWikitext(KIA) >> Lists.newArrayList(
				new PageLink(
						title: KIA_TITLE,
						description: KIA_DESCRIPTION
				)
		)
		1 * characterTemplate.setDeceased(true)
		1 * individualDateStatusValueToYearProcessorMock.process(YEAR_STRING) >> YEAR_INTEGER
		1 * characterTemplate.setYearOfDeath(YEAR_INTEGER)
		0 * _
	}

	void "sets deceased flag to true when status contains dead word"() {
		given:
		Template.Part dateTemplatePart = Mock(Template.Part)
		Template.Part dateStatusTemplatePart = Mock(Template.Part)
		Template template = Mock()
		CharacterTemplate characterTemplate = Mock()

		when:
		individualTemplateDateOfDeathEnrichingProcessor.enrich(EnrichablePair.of(template, characterTemplate))

		then:
		1 * template.parts >> Lists.newArrayList(dateTemplatePart, dateStatusTemplatePart)
		1 * dateTemplatePart.key >> IndividualTemplateParameter.STATUS
		2 * dateTemplatePart.value >> DEAD_SYNONYM
		1 * dateStatusTemplatePart.key >> IndividualTemplateParameter.DATE_STATUS
		1 * dateStatusTemplatePart.value >> YEAR_STRING
		1 * wikitextApiMock.getPageLinksFromWikitext(DEAD_SYNONYM) >> Lists.newArrayList()
		1 * characterTemplate.setDeceased(true)
		1 * individualDateStatusValueToYearProcessorMock.process(YEAR_STRING) >> YEAR_INTEGER
		1 * characterTemplate.setYearOfDeath(YEAR_INTEGER)
		0 * _
	}

	void "does not set deceased flag to true when status contains not dead word"() {
		given:
		Template.Part dateTemplatePart = Mock(Template.Part)
		Template.Part dateStatusTemplatePart = Mock(Template.Part)
		Template template = Mock()
		CharacterTemplate characterTemplate = Mock()

		when:
		individualTemplateDateOfDeathEnrichingProcessor.enrich(EnrichablePair.of(template, characterTemplate))

		then:
		1 * template.parts >> Lists.newArrayList(dateTemplatePart, dateStatusTemplatePart)
		1 * dateTemplatePart.key >> IndividualTemplateParameter.STATUS
		2 * dateTemplatePart.value >> NOT_DEAD_SYNONYM
		1 * dateStatusTemplatePart.key >> IndividualTemplateParameter.DATE_STATUS
		1 * wikitextApiMock.getPageLinksFromWikitext(NOT_DEAD_SYNONYM) >> Lists.newArrayList()
		0 * _
	}

	void "does not set deceased flag and logs when status contains neither dead nor not dead word"() {
		given:
		Template.Part dateTemplatePart = Mock(Template.Part)
		Template.Part dateStatusTemplatePart = Mock(Template.Part)
		Template template = Mock()
		CharacterTemplate characterTemplate = Mock()

		when:
		individualTemplateDateOfDeathEnrichingProcessor.enrich(EnrichablePair.of(template, characterTemplate))

		then:
		1 * template.parts >> Lists.newArrayList(dateTemplatePart, dateStatusTemplatePart)
		1 * dateTemplatePart.key >> IndividualTemplateParameter.STATUS
		2 * dateTemplatePart.value >> NEITHER_WORD
		1 * dateStatusTemplatePart.key >> IndividualTemplateParameter.DATE_STATUS
		1 * wikitextApiMock.getPageLinksFromWikitext(NEITHER_WORD) >> Lists.newArrayList()
		1 * characterTemplate.name
		0 * _
	}

	void "does not set deceased flag and logs when status contains both dead word and not dead word"() {
		given:
		Template.Part dateTemplatePart = Mock(Template.Part)
		Template.Part dateStatusTemplatePart = Mock(Template.Part)
		Template template = Mock()
		CharacterTemplate characterTemplate = Mock()

		when:
		individualTemplateDateOfDeathEnrichingProcessor.enrich(EnrichablePair.of(template, characterTemplate))

		then:
		1 * template.parts >> Lists.newArrayList(dateTemplatePart, dateStatusTemplatePart)
		1 * dateTemplatePart.key >> IndividualTemplateParameter.STATUS
		2 * dateTemplatePart.value >> DEAD_SYNONYM + ' ' + NOT_DEAD_SYNONYM
		1 * dateStatusTemplatePart.key >> IndividualTemplateParameter.DATE_STATUS
		1 * wikitextApiMock.getPageLinksFromWikitext(DEAD_SYNONYM + ' ' + NOT_DEAD_SYNONYM) >> Lists.newArrayList()
		1 * characterTemplate.name
		0 * _
	}

}
