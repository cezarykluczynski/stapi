package com.cezarykluczynski.stapi.etl.episode.creation.processor

import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.util.AbstractEpisodeTest

class ToEpisodeEntityProcessorTest extends AbstractEpisodeTest {

	private static final Long SERIES_ID = 1L

	private final Page page = Mock()
	private final Series seriesDetached = Mock()
	private final Series seriesNew = Mock()
	private final Season season = Mock()

	private UidGenerator uidGeneratorMock

	private SeriesRepository seriesRepositoryMock

	private ToEpisodeEntityProcessor toEpisodeEntityProcessor

	void setup() {
		uidGeneratorMock = Mock()
		seriesRepositoryMock = Mock()
		toEpisodeEntityProcessor = new ToEpisodeEntityProcessor(uidGeneratorMock, seriesRepositoryMock)
	}

	void "converts EpisodeTemplate to Episode"() {
		given:
		Episode episodeStub = new Episode()
		EpisodeTemplate episodeTemplate = new EpisodeTemplate(
				episodeStub: episodeStub,
				series: seriesDetached,
				season: season,
				page: page,
				title: TITLE,
				titleGerman: TITLE_GERMAN,
				titleItalian: TITLE_ITALIAN,
				titleJapanese: TITLE_JAPANESE,
				seasonNumber: SEASON_NUMBER,
				episodeNumber: EPISODE_NUMBER,
				productionSerialNumber: PRODUCTION_SERIAL_NUMBER,
				featureLength: FEATURE_LENGTH,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				usAirDate: US_AIR_DATE,
				finalScriptDate: FINAL_SCRIPT_DATE
		)

		when:
		Episode episode = toEpisodeEntityProcessor.process(episodeTemplate)

		then:
		episode == episodeStub
		1 * seriesDetached.id >> SERIES_ID
		1 * seriesRepositoryMock.findOne(SERIES_ID) >> seriesNew
		1 * uidGeneratorMock.generateFromPage(page, Episode) >> UID
		episode.title == TITLE
		episode.titleGerman == TITLE_GERMAN
		episode.titleItalian == TITLE_ITALIAN
		episode.titleJapanese == TITLE_JAPANESE
		episode.page == page
		episode.series == seriesNew
		episode.season == season
		episode.uid == UID
		episode.seasonNumber == SEASON_NUMBER
		episode.episodeNumber == EPISODE_NUMBER
		episode.productionSerialNumber == PRODUCTION_SERIAL_NUMBER
		episode.featureLength == FEATURE_LENGTH
		episode.stardateFrom == STARDATE_FROM
		episode.stardateTo == STARDATE_TO
		episode.yearFrom == YEAR_FROM
		episode.yearTo == YEAR_TO
		episode.usAirDate == US_AIR_DATE
		episode.finalScriptDate == FINAL_SCRIPT_DATE
	}

}
