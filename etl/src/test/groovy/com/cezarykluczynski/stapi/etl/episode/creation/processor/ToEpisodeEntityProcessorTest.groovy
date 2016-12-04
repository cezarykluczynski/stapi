package com.cezarykluczynski.stapi.etl.episode.creation.processor

import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import spock.lang.Specification

class ToEpisodeEntityProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String GUID = 'GUID'
	private static final Long SERIES_ID = 1L
	private final Page PAGE = Mock(Page)
	private final Series SERIES_DETACHED = Mock(Series)
	private final Series SERIES_NEW = Mock(Series)

	private GuidGenerator guidGeneratorMock

	private SeriesRepository seriesRepositoryMock

	private ToEpisodeEntityProcessor toEpisodeEntityProcessor

	def setup() {
		guidGeneratorMock = Mock(GuidGenerator)
		seriesRepositoryMock = Mock(SeriesRepository)
		toEpisodeEntityProcessor = new ToEpisodeEntityProcessor(guidGeneratorMock, seriesRepositoryMock)
	}

	def "converts EpisodeTemplate to Episode"() {
		given:
		Episode episodeStub = new Episode()
		EpisodeTemplate episodeTemplate = new EpisodeTemplate(
				episodeStub: episodeStub,
				series: SERIES_DETACHED,
				page: PAGE,
				title: TITLE
		)

		when:
		Episode episode = toEpisodeEntityProcessor.process(episodeTemplate)

		then:
		episode == episodeStub
		1 * SERIES_DETACHED.getId() >> SERIES_ID
		1 * seriesRepositoryMock.findOne(SERIES_ID) >> SERIES_NEW
		1 * guidGeneratorMock.generateFromPage(PAGE, Episode) >> GUID
		episode.title == TITLE
		episode.page == PAGE
		episode.series == SERIES_NEW
		episode.guid == GUID
	}

}
