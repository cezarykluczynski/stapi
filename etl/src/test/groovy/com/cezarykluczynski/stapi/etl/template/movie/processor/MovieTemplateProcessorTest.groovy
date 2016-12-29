package com.cezarykluczynski.stapi.etl.template.movie.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.AbstractTemplateProcessorTest
import com.cezarykluczynski.stapi.etl.common.processor.ImageTemplateStardateYearEnrichingProcessor
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.template.common.dto.DayMonthYearCandidate
import com.cezarykluczynski.stapi.etl.template.common.dto.ImageTemplate
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthYearProcessor
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists

import java.time.LocalDate

class MovieTemplateProcessorTest extends AbstractTemplateProcessorTest {

	private static final String RELEASE_DATE_YEAR = "1998"
	private static final String RELEASE_DATE_MONTH = "April"
	private static final String RELEASE_DATE_DAY = "15"
	private static final String SCREENPLAY_AUTHORS = "SCREENPLAY_AUTHORS"
	private static final String SCREENPLAY_AUTHOR = "SCREENPLAY_AUTHOR"
	private static final String STORY_AUTHORS = "STORY_AUTHORS"
	private static final String STORY_AUTHOR = "STORY_AUTHOR"
	private static final String DIRECTORS = "DIRECTORS"
	private static final String UNKNOWN = "UNKNOWN"
	private static final String DIRECTOR = "DIRECTOR"
	private static final String PRODUCERS = "PRODUCERS"
	private static final String PRODUCER = "PRODUCER"

	private DayMonthYearProcessor dayMonthYearProcessorMock

	private ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessorMock

	private WikitextApi wikitextApiMock

	private EntityLookupByNameService entityLookupByNameServiceMock

	private MovieTemplateProcessor movieTemplateProcessor

	def setup() {
		dayMonthYearProcessorMock = Mock(DayMonthYearProcessor)
		imageTemplateStardateYearEnrichingProcessorMock = Mock(ImageTemplateStardateYearEnrichingProcessor)
		wikitextApiMock = Mock(WikitextApi)
		entityLookupByNameServiceMock = Mock(EntityLookupByNameService)
		movieTemplateProcessor = new MovieTemplateProcessor(dayMonthYearProcessorMock,
				imageTemplateStardateYearEnrichingProcessorMock, wikitextApiMock, entityLookupByNameServiceMock)
	}

	def "sets values from template parts"() {
		given:
		Template template = new Template(
				parts: Lists.newArrayList(
						createTemplatePart(MovieTemplateProcessor.N_RELEASE_YEAR , RELEASE_DATE_YEAR),
						createTemplatePart(MovieTemplateProcessor.S_RELEASE_MONTH, RELEASE_DATE_MONTH),
						createTemplatePart(MovieTemplateProcessor.N_RELEASE_DAY, RELEASE_DATE_DAY),
						createTemplatePart(MovieTemplateProcessor.WS_SCREENPLAY_BY, SCREENPLAY_AUTHORS),
						createTemplatePart(MovieTemplateProcessor.WS_STORY_BY, STORY_AUTHORS),
						createTemplatePart(MovieTemplateProcessor.WS_DIRECTED_BY, DIRECTORS),
						createTemplatePart(MovieTemplateProcessor.WS_PRODUCED_BY, PRODUCERS)
				)
		)
		LocalDate usReleaseDate = LocalDate.of(1998, 4, 15)
		Staff screenplayAuthor = new Staff(name: SCREENPLAY_AUTHOR)
		Staff storyAuthor = new Staff(name: STORY_AUTHOR)
		Staff director = new Staff(name: DIRECTOR)
		Staff producer = new Staff(name: PRODUCER)

		when:
		MovieTemplate movieTemplate = movieTemplateProcessor.process(template)

		then:
		1 * imageTemplateStardateYearEnrichingProcessorMock.enrich(_) >> { EnrichablePair<Template, ImageTemplate> enrichablePair ->
			assert enrichablePair.input == template
			assert enrichablePair.output instanceof MovieTemplate
		}
		1 * wikitextApiMock.getPageTitlesFromWikitext(SCREENPLAY_AUTHORS) >> Lists.newArrayList(SCREENPLAY_AUTHOR, UNKNOWN)
		1 * entityLookupByNameServiceMock.findStaffByName(SCREENPLAY_AUTHOR, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(screenplayAuthor)
		1 * entityLookupByNameServiceMock.findStaffByName(UNKNOWN, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * wikitextApiMock.getPageTitlesFromWikitext(STORY_AUTHORS) >> Lists.newArrayList(STORY_AUTHOR)
		1 * entityLookupByNameServiceMock.findStaffByName(STORY_AUTHOR, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(storyAuthor)
		1 * wikitextApiMock.getPageTitlesFromWikitext(DIRECTORS) >> Lists.newArrayList(DIRECTOR)
		1 * entityLookupByNameServiceMock.findStaffByName(DIRECTOR, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(director)
		1 * wikitextApiMock.getPageTitlesFromWikitext(PRODUCERS) >> Lists.newArrayList(PRODUCER)
		1 * entityLookupByNameServiceMock.findStaffByName(PRODUCER, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(producer)
		1 * dayMonthYearProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			dayMonthYearCandidate.day == RELEASE_DATE_DAY
			dayMonthYearCandidate.month == RELEASE_DATE_MONTH
			dayMonthYearCandidate.year == RELEASE_DATE_YEAR
			return usReleaseDate
		}
		0 * _
		movieTemplate.movieStub instanceof Movie
		movieTemplate.usReleaseDate == usReleaseDate
		movieTemplate.movieStub.screenplayAuthors.size() == 1
		movieTemplate.movieStub.screenplayAuthors.contains screenplayAuthor
		movieTemplate.movieStub.storyAuthors.size() == 1
		movieTemplate.movieStub.storyAuthors.contains storyAuthor
		movieTemplate.movieStub.directors.size() == 1
		movieTemplate.movieStub.directors.contains director
		movieTemplate.movieStub.producers.size() == 1
		movieTemplate.movieStub.producers.contains producer
	}

}
