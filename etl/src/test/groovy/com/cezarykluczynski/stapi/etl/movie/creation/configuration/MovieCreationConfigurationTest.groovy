package com.cezarykluczynski.stapi.etl.movie.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.episode.creation.service.ModuleEpisodeDataProvider
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieReader
import com.cezarykluczynski.stapi.etl.movie.creation.service.MovieExistingEntitiesHelper
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource

class MovieCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_STAR_TREK_GENERATIONS = 'TITLE_STAR_TREK_GENERATIONS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private ModuleEpisodeDataProvider moduleEpisodeDataProviderMock

	private MovieExistingEntitiesHelper movieExistingEntitiesHelperMock

	private MovieCreationConfiguration movieCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		moduleEpisodeDataProviderMock = Mock()
		movieExistingEntitiesHelperMock = Mock()
		movieCreationConfiguration = new MovieCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock,
				moduleEpisodeDataProvider: moduleEpisodeDataProviderMock,
				movieExistingEntitiesHelper: movieExistingEntitiesHelperMock)
	}

	void "MovieReader is created with all pages when step is not completed"() {
		when:
		MovieReader movieReader = movieCreationConfiguration.movieReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(movieReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MOVIES) >> false
		1 * categoryApiMock.getPages(CategoryTitle.STAR_TREK_FILMS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_STAR_TREK_GENERATIONS)
		1 * moduleEpisodeDataProviderMock.initialize()
		1 * movieExistingEntitiesHelperMock.initialize()
		0 * _
		categoryHeaderTitleList.contains TITLE_STAR_TREK_GENERATIONS
	}

	void "MovieReader is created with no pages when step is completed"() {
		when:
		MovieReader movieReader = movieCreationConfiguration.movieReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(movieReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MOVIES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
