package com.cezarykluczynski.stapi.etl.template.movie.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.AbstractTemplateProcessorTest
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplateParameter
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists

class MovieTemplateStaffEnrichingProcessorTest extends AbstractTemplateProcessorTest {

	private static final String WRITERS = 'WRITERS'
	private static final String WRITER_1_NAME = 'WRITER_1_NAME'
	private static final String WRITER_2_NAME = 'WRITER_2_NAME'
	private static final String WRITER_DESCRIPTION_2 = 'WRITER_DESCRIPTION_2'
	private static final String WRITER_3_NAME = 'WRITER_3_NAME'
	private static final String WRITER_3_BIRTH_NAME = 'WRITER_3_BIRTH_NAME'
	private static final String WRITER_DESCRIPTION_3 = 'WRITER_DESCRIPTION_3'
	private static final String WRITER_4 = 'WRITER_4'
	private static final String WRITER_4_BIRTH_NAME = 'WRITER_4_BIRTH_NAME'
	private static final String WRITER_5 = 'WRITER_5'
	private static final String WRITER_5_NAME = 'WRITER_5_NAME'
	private static final String SCREENPLAY_AUTHORS = 'SCREENPLAY_AUTHORS'
	private static final String SCREENPLAY_AUTHOR = 'SCREENPLAY_AUTHOR'
	private static final String STORY_AUTHORS = 'STORY_AUTHORS'
	private static final String STORY_AUTHOR = 'STORY_AUTHOR'
	private static final String DIRECTORS = 'DIRECTORS'
	private static final String UNKNOWN = 'UNKNOWN'
	private static final String DIRECTOR = 'DIRECTOR'
	private static final String DIRECTOR_2 = 'DIRECTOR_2'
	private static final String PRODUCERS = 'PRODUCERS'
	private static final String PRODUCER = 'PRODUCER'

	private WikitextApi wikitextApiMock

	private EntityLookupByNameService entityLookupByNameServiceMock

	private MovieTemplateStaffEnrichingProcessor movieTemplateStaffEnrichingProcessor

	void setup() {
		wikitextApiMock = Mock()
		entityLookupByNameServiceMock = Mock()
		movieTemplateStaffEnrichingProcessor = new MovieTemplateStaffEnrichingProcessor(wikitextApiMock,
				entityLookupByNameServiceMock)
	}

	void "enriches MovieTemplate with staff, when links are present in all sections"() {
		given:
		Movie movieStub = new Movie()
		Template template = new Template(parts: Lists.newArrayList(
				createTemplatePart(MovieTemplateParameter.WS_WRITTEN_BY, WRITERS),
				createTemplatePart(MovieTemplateParameter.WS_SCREENPLAY_BY, SCREENPLAY_AUTHORS),
				createTemplatePart(MovieTemplateParameter.WS_STORY_BY, STORY_AUTHORS),
				createTemplatePart(MovieTemplateParameter.WS_DIRECTED_BY, DIRECTORS),
				createTemplatePart(MovieTemplateParameter.WS_PRODUCED_BY, PRODUCERS),
		))
		MovieTemplate movieTemplate = new MovieTemplate(
				movieStub: movieStub
		)
		Staff writer = new Staff(name: WRITER_1_NAME)
		Staff screenplayAuthor = new Staff(name: SCREENPLAY_AUTHOR)
		Staff storyAuthor = new Staff(name: STORY_AUTHOR)
		Staff director = new Staff(name: DIRECTOR)
		Staff producer = new Staff(name: PRODUCER)

		when:
		movieTemplateStaffEnrichingProcessor.enrich(EnrichablePair.of(template, movieTemplate))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WRITERS) >> Lists.newArrayList(
				new PageLink(title: WRITER_1_NAME))
		1 * entityLookupByNameServiceMock.findStaffByName(WRITER_1_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(writer)
		1 * wikitextApiMock.getPageLinksFromWikitext(SCREENPLAY_AUTHORS) >> Lists.newArrayList(
				new PageLink(title: SCREENPLAY_AUTHOR),
				new PageLink(title: UNKNOWN))
		1 * entityLookupByNameServiceMock.findStaffByName(SCREENPLAY_AUTHOR, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(screenplayAuthor)
		1 * entityLookupByNameServiceMock.findStaffByName(UNKNOWN, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * wikitextApiMock.getPageLinksFromWikitext(STORY_AUTHORS) >> Lists.newArrayList(
				new PageLink(title: STORY_AUTHOR))
		1 * entityLookupByNameServiceMock.findStaffByName(STORY_AUTHOR, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(storyAuthor)
		1 * wikitextApiMock.getPageLinksFromWikitext(DIRECTORS) >> Lists.newArrayList(
				new PageLink(title: DIRECTOR))
		1 * entityLookupByNameServiceMock.findStaffByName(DIRECTOR, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(director)
		1 * wikitextApiMock.getPageLinksFromWikitext(PRODUCERS) >> Lists.newArrayList(
				new PageLink(title: PRODUCER))
		1 * entityLookupByNameServiceMock.findStaffByName(PRODUCER, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(producer)
		0 * _
		movieStub.writers.size() == 1
		movieStub.writers.contains writer
		movieStub.screenplayAuthors.size() == 1
		movieStub.screenplayAuthors.contains screenplayAuthor
		movieStub.storyAuthors.size() == 1
		movieStub.storyAuthors.contains storyAuthor
		movieStub.directors.size() == 1
		movieStub.directors.contains director
		movieStub.producers.size() == 1
		movieStub.producers.contains producer
		movieStub.mainDirector == director
	}

	void "enriches MovieTemplate with staff, when links are present in some of the sections"() {
		given:
		String screenplayAuthorsWikitext = SCREENPLAY_AUTHORS + ' ' + WRITER_2_NAME + ' ' + WRITER_DESCRIPTION_3
		String storyAuthorWikitext = 'blah ' + WRITER_3_BIRTH_NAME + ' ' + WRITER_DESCRIPTION_2
		String directorsWikitext = 'blah ' + WRITER_4_BIRTH_NAME
		String producersWikitext = 'blah ' + WRITER_5
		Movie movieStub = new Movie()
		Template template = new Template(parts: Lists.newArrayList(
				createTemplatePart(MovieTemplateParameter.WS_WRITTEN_BY, WRITERS),
				createTemplatePart(MovieTemplateParameter.WS_SCREENPLAY_BY, screenplayAuthorsWikitext),
				createTemplatePart(MovieTemplateParameter.WS_STORY_BY, storyAuthorWikitext),
				createTemplatePart(MovieTemplateParameter.WS_DIRECTED_BY, directorsWikitext),
				createTemplatePart(MovieTemplateParameter.WS_PRODUCED_BY, producersWikitext),
		))
		MovieTemplate movieTemplate = new MovieTemplate(
				movieStub: movieStub
		)
		Staff writer = new Staff(name: WRITER_1_NAME)
		Staff writer2 = new Staff(name: WRITER_2_NAME)
		Staff writer3 = new Staff(name: WRITER_3_NAME, birthName: WRITER_3_BIRTH_NAME)
		Staff writer4 = new Staff(birthName: WRITER_4_BIRTH_NAME)
		Staff writer5 = new Staff(name: WRITER_5_NAME)
		Staff screenplayAuthor = new Staff(name: SCREENPLAY_AUTHOR)

		when:
		movieTemplateStaffEnrichingProcessor.enrich(EnrichablePair.of(template, movieTemplate))

		then: '5 writers are found'
		1 * wikitextApiMock.getPageLinksFromWikitext(WRITERS) >> Lists.newArrayList(
				new PageLink(title: WRITER_1_NAME),
				new PageLink(title: WRITER_2_NAME, description: WRITER_DESCRIPTION_2),
				new PageLink(title: WRITER_3_NAME, description: WRITER_DESCRIPTION_3),
				new PageLink(title: WRITER_4),
				new PageLink(title: WRITER_5))
		1 * entityLookupByNameServiceMock.findStaffByName(WRITER_1_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(writer)
		1 * entityLookupByNameServiceMock.findStaffByName(WRITER_2_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(writer2)
		1 * entityLookupByNameServiceMock.findStaffByName(WRITER_3_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(writer3)
		1 * entityLookupByNameServiceMock.findStaffByName(WRITER_4, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(writer4)
		1 * entityLookupByNameServiceMock.findStaffByName(WRITER_5, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(writer5)

		then: 'screenplay author is found'
		1 * wikitextApiMock.getPageLinksFromWikitext(screenplayAuthorsWikitext) >> Lists.newArrayList(
				new PageLink(title: SCREENPLAY_AUTHOR))
		1 * entityLookupByNameServiceMock.findStaffByName(SCREENPLAY_AUTHOR, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(screenplayAuthor)

		then: 'no other staff members are found directly'
		1 * wikitextApiMock.getPageLinksFromWikitext(storyAuthorWikitext) >> Lists.newArrayList()
		1 * wikitextApiMock.getPageLinksFromWikitext(directorsWikitext) >> Lists.newArrayList()
		1 * wikitextApiMock.getPageLinksFromWikitext(producersWikitext) >> Lists.newArrayList()
		0 * _

		then: 'staff members are added bacause raw text contained values from links or entities found among writers'
		movieStub.writers.size() == 5
		movieStub.writers.contains writer
		movieStub.writers.contains writer2
		movieStub.writers.contains writer3
		movieStub.screenplayAuthors.size() == 3
		movieStub.screenplayAuthors.contains screenplayAuthor
		movieStub.screenplayAuthors.contains writer2
		movieStub.screenplayAuthors.contains writer3
		movieStub.storyAuthors.size() == 2
		movieStub.storyAuthors.contains writer2
		movieStub.storyAuthors.contains writer3
		movieStub.directors.size() == 1
		movieStub.directors.contains writer4
		movieStub.producers.size() == 1
		movieStub.producers.contains writer5
		movieStub.mainDirector == writer4
	}

	void "tolerates null raw value"() {
		given:
		Movie movieStub = new Movie()
		Template template = new Template(parts: Lists.newArrayList(
				createTemplatePart(MovieTemplateParameter.WS_WRITTEN_BY, WRITERS),
				createTemplatePart(MovieTemplateParameter.WS_SCREENPLAY_BY, null)
		))
		MovieTemplate movieTemplate = new MovieTemplate(
				movieStub: movieStub
		)
		Staff writer = new Staff(name: WRITER_1_NAME)

		when:
		movieTemplateStaffEnrichingProcessor.enrich(EnrichablePair.of(template, movieTemplate))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WRITERS) >> Lists.newArrayList(
				new PageLink(title: WRITER_1_NAME))
		1 * entityLookupByNameServiceMock.findStaffByName(WRITER_1_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(writer)
		1 * wikitextApiMock.getPageLinksFromWikitext(null) >> Lists.newArrayList()
		0 * _
		movieStub.writers.size() == 1
		movieStub.writers.contains writer
		movieStub.screenplayAuthors.empty
		movieStub.storyAuthors.empty
		movieStub.directors.empty
		movieStub.producers.empty
	}

	void "does not set main director when there is more than one"() {
		given:
		Movie movieStub = new Movie()
		Template template = new Template(parts: Lists.newArrayList(
				createTemplatePart(MovieTemplateParameter.WS_DIRECTED_BY, DIRECTORS)
		))
		MovieTemplate movieTemplate = new MovieTemplate(
				movieStub: movieStub
		)
		Staff director = new Staff(name: DIRECTOR)
		Staff director2 = new Staff(name: DIRECTOR_2)
		when:
		movieTemplateStaffEnrichingProcessor.enrich(EnrichablePair.of(template, movieTemplate))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(DIRECTORS) >> Lists.newArrayList(
				new PageLink(title: DIRECTOR),
				new PageLink(title: DIRECTOR_2))
		1 * entityLookupByNameServiceMock.findStaffByName(DIRECTOR, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(director)
		1 * entityLookupByNameServiceMock.findStaffByName(DIRECTOR_2, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(director2)
		0 * _
		movieStub.directors.contains director
		movieStub.directors.contains director2
		movieStub.mainDirector == null
	}

}
