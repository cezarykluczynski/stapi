package com.cezarykluczynski.stapi.etl.template.video_game.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatePartToLocalDateProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToStardateRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplateParameter
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.AbstractVideoGameTest
import com.google.common.collect.Lists

class VideoGameTemplateContentsEnrichingProcessorTest extends AbstractVideoGameTest {

	private static final String OLD_TITLE = 'OLD_TITLE'
	private static final String NEW_TITLE = 'NEW_TITLE'
	private static final String NEW_TITLE_WITHOUT_LINKS = 'NEW_TITLE_WITHOUT_LINKS'
	private static final String YEARS = '1995-1997'
	private static final String STARDATES = '1995-1997'

	private DatePartToLocalDateProcessor datePartToLocalDateProcessorMock

	private WikitextToYearRangeProcessor wikitextToYearRangeProcessorMock

	private WikitextToStardateRangeProcessor wikitextToStardateRangeProcessorMock

	private WikitextApi wikitextApiMock

	private VideoGameTemplateContentsEnrichingProcessor videoGameTemplateContentsEnrichingProcessor

	void setup() {
		datePartToLocalDateProcessorMock = Mock()
		wikitextToYearRangeProcessorMock = Mock()
		wikitextToStardateRangeProcessorMock = Mock()
		wikitextApiMock = Mock()
		videoGameTemplateContentsEnrichingProcessor = new VideoGameTemplateContentsEnrichingProcessor(datePartToLocalDateProcessorMock,
				wikitextToYearRangeProcessorMock, wikitextToStardateRangeProcessorMock, wikitextApiMock)
	}

	void "sets title from template, when it is different from title already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: VideoGameTemplateParameter.TITLE, value: NEW_TITLE)
		Template template = new Template(parts: Lists.newArrayList(templatePart))
		VideoGameTemplate videoGameTemplate = new VideoGameTemplate(title: OLD_TITLE)
		EnrichablePair<Template, VideoGameTemplate> enrichablePair = EnrichablePair.of(template, videoGameTemplate)

		when:
		videoGameTemplateContentsEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * wikitextApiMock.getWikitextWithoutLinks(NEW_TITLE) >> NEW_TITLE_WITHOUT_LINKS
		0 * _
		videoGameTemplate.title == NEW_TITLE_WITHOUT_LINKS
	}

	void "does not sets title from template, when it is equal to title already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: VideoGameTemplateParameter.TITLE, value: OLD_TITLE)
		Template template = new Template(parts: Lists.newArrayList(templatePart))
		VideoGameTemplate videoGameTemplate = Mock()
		EnrichablePair<Template, VideoGameTemplate> enrichablePair = EnrichablePair.of(template, videoGameTemplate)

		when:
		videoGameTemplateContentsEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * videoGameTemplate.title >> OLD_TITLE
		0 * _
	}

	void "sets release date from DatePartToLocalDateProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: VideoGameTemplateParameter.RELEASED)
		Template template = new Template(parts: Lists.newArrayList(templatePart))
		VideoGameTemplate videoGameTemplate = new VideoGameTemplate()
		EnrichablePair<Template, VideoGameTemplate> enrichablePair = EnrichablePair.of(template, videoGameTemplate)

		when:
		videoGameTemplateContentsEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * datePartToLocalDateProcessorMock.process(templatePart) >> RELEASE_DATE
		videoGameTemplate.releaseDate == RELEASE_DATE
		0 * _
	}

	void "sets year from and year to from WikitextToYearRangeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: VideoGameTemplateParameter.YEAR, value: YEARS)
		Template template = new Template(parts: Lists.newArrayList(templatePart))
		VideoGameTemplate videoGameTemplate = new VideoGameTemplate()
		YearRange yearRange = new YearRange(yearFrom: YEAR_FROM, yearTo: YEAR_TO)

		when:
		videoGameTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(template, videoGameTemplate))

		then:
		1 * wikitextToYearRangeProcessorMock.process(YEARS) >> yearRange
		0 * _
		videoGameTemplate.yearFrom == YEAR_FROM
		videoGameTemplate.yearTo == YEAR_TO
	}

	void "sets stardate from and stardate to from WikitextToStardateRangeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: VideoGameTemplateParameter.STARDATE, value: STARDATES)
		Template template = new Template(parts: Lists.newArrayList(templatePart))
		VideoGameTemplate videoGameTemplate = new VideoGameTemplate()
		StardateRange stardateRange = StardateRange.of(STARDATE_FROM, STARDATE_TO)

		when:
		videoGameTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(template, videoGameTemplate))

		then:
		1 * wikitextToStardateRangeProcessorMock.process(STARDATES) >> stardateRange
		0 * _
		videoGameTemplate.stardateFrom == STARDATE_FROM
		videoGameTemplate.stardateTo == STARDATE_TO
	}

	void "sets system requirements when they are found"() {
		given:
		Template.Part templatePart = new Template.Part(key: VideoGameTemplateParameter.REQUIREMENTS, value: SYSTEM_REQUIREMENTS)
		Template template = new Template(parts: Lists.newArrayList(templatePart))
		VideoGameTemplate videoGameTemplate = new VideoGameTemplate()

		when:
		videoGameTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(template, videoGameTemplate))

		then:
		0 * _
		videoGameTemplate.systemRequirements == SYSTEM_REQUIREMENTS
	}

}
