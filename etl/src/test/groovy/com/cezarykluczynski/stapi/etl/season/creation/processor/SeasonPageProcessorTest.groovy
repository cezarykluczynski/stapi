package com.cezarykluczynski.stapi.etl.season.creation.processor

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import spock.lang.Specification

class SeasonPageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String UID = 'UID'
	private static final Integer SEASON_NUMBER = 3
	private static final Integer NUMBER_OF_EPISODES = 26

	private UidGenerator uidGeneratorMock

	private PageBindingService pageBindingServiceMock

	private SeasonSeriesProcessor seasonSeriesProcessorMock

	private SeasonNumberProcessor seasonNumberProcessorMock

	private SeasonNumberOfEpisodesProcessor seasonNumberOfEpisodesProcessorMock

	private SeasonPageProcessor seasonPageProcessor

	void setup() {
		uidGeneratorMock = Mock()
		pageBindingServiceMock = Mock()
		seasonSeriesProcessorMock = Mock()
		seasonNumberProcessorMock = Mock()
		seasonNumberOfEpisodesProcessorMock = Mock()
		seasonPageProcessor = new SeasonPageProcessor(uidGeneratorMock, pageBindingServiceMock, seasonSeriesProcessorMock,
				seasonNumberProcessorMock, seasonNumberOfEpisodesProcessorMock)
	}

	void "parses page"() {
		given:
		SourcesPage sourcesPage = new SourcesPage(title: TITLE)
		ModelPage modelPage = new ModelPage()
		Series series = Mock()

		when:
		Season season = seasonPageProcessor.process(sourcesPage)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(sourcesPage) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Season) >> UID
		1 * seasonSeriesProcessorMock.process(TITLE) >> series
		1 * seasonNumberProcessorMock.process(TITLE) >> SEASON_NUMBER
		1 * seasonNumberOfEpisodesProcessorMock.process(sourcesPage) >> NUMBER_OF_EPISODES
		0 * _
		season.uid == UID
		season.title == TITLE
		season.page == modelPage
		season.series == series
		season.seasonNumber == SEASON_NUMBER
		season.numberOfEpisodes == NUMBER_OF_EPISODES
	}

}
