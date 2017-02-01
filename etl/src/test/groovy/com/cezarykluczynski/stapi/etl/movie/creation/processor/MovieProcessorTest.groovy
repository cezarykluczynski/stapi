package com.cezarykluczynski.stapi.etl.movie.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate
import com.cezarykluczynski.stapi.etl.template.movie.processor.ToMovieTemplateProcessor
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class MovieProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private ToMovieTemplateProcessor toMovieTemplateProcessorMock

	private ToMovieEntityProcessor toMovieEntityProcessorMock

	private MovieProcessor episodeProcessor

	void setup() {
		pageHeaderProcessorMock = Mock(PageHeaderProcessor)
		toMovieTemplateProcessorMock = Mock(ToMovieTemplateProcessor)
		toMovieEntityProcessorMock = Mock(ToMovieEntityProcessor)
		episodeProcessor = new MovieProcessor(pageHeaderProcessorMock, toMovieTemplateProcessorMock,
				toMovieEntityProcessorMock)
	}

	void "converts PageHeader to Movie"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		MovieTemplate episodeTemplate = new MovieTemplate()
		Movie movie = new Movie()

		when:
		Movie movieOutput = episodeProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page
		1 * toMovieTemplateProcessorMock.process(page) >> episodeTemplate
		1 * toMovieEntityProcessorMock.process(episodeTemplate) >> movie

		then: 'last processor output is returned'
		movieOutput == movie
	}

}
