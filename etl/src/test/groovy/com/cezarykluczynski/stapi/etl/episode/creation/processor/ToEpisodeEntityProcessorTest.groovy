package com.cezarykluczynski.stapi.etl.episode.creation.processor

import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.util.tool.LogicUtil
import spock.lang.Specification

import java.time.LocalDate

class ToEpisodeEntityProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String GUID = 'GUID'
	private static final Long SERIES_ID = 1L
	private static final Integer SEASON_NUMBER = 2
	private static final Integer EPISODE_NUMBER = 3
	private static final String PRODUCTION_SERIAL_NUMBER = 'PRODUCTION_SERIAL_NUMBER'
	private static final Boolean FEATURE_LENGTH = LogicUtil.nextBoolean()
	private static final Float STARDATE = (Float) 123.4
	private static final Integer YEAR = 2368
	private static final LocalDate US_AIR_DATE = LocalDate.of(1995, 4, 8)
	private static final LocalDate FINAL_SCRIPT_DATE = LocalDate.of(1995, 2, 3)

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
				title: TITLE,
				seasonNumber: SEASON_NUMBER,
				episodeNumber: EPISODE_NUMBER,
				productionSerialNumber: PRODUCTION_SERIAL_NUMBER,
				featureLength: FEATURE_LENGTH,
				stardate: STARDATE,
				year: YEAR,
				usAirDate: US_AIR_DATE,
				finalScriptDate: FINAL_SCRIPT_DATE
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
		episode.seasonNumber == SEASON_NUMBER
		episode.episodeNumber == EPISODE_NUMBER
		episode.productionSerialNumber == PRODUCTION_SERIAL_NUMBER
		episode.featureLength == FEATURE_LENGTH
		episode.stardate == STARDATE
		episode.year == YEAR
		episode.usAirDate == US_AIR_DATE
		episode.finalScriptDate == FINAL_SCRIPT_DATE
	}

}
