package com.cezarykluczynski.stapi.etl.season.creation.processor

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.util.constant.PageTitle
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import org.apache.commons.lang3.RandomUtils
import spock.lang.Specification

class SeasonPageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String UID = 'UID'
	private static final Integer SEASON_NUMBER = 3
	private static final Integer NUMBER_OF_EPISODES = 26
	private static final Boolean COMPANION_SERIES = RandomUtils.nextBoolean()

	private UidGenerator uidGeneratorMock

	private PageBindingService pageBindingServiceMock

	private SeasonSeriesProcessor seasonSeriesProcessorMock

	private  SeasonNumberOfEpisodesFixedValueProvider seasonNumberOfEpisodesFixedValueProviderMock

	private SeasonNumberProcessor seasonNumberProcessorMock

	private SeasonNumberOfEpisodesProcessor seasonNumberOfEpisodesProcessorMock

	private SeasonPageProcessor seasonPageProcessor

	void setup() {
		uidGeneratorMock = Mock()
		pageBindingServiceMock = Mock()
		seasonSeriesProcessorMock = Mock()
		seasonNumberOfEpisodesFixedValueProviderMock = Mock()
		seasonNumberProcessorMock = Mock()
		seasonNumberOfEpisodesProcessorMock = Mock()
		seasonPageProcessor = new SeasonPageProcessor(uidGeneratorMock, pageBindingServiceMock, seasonSeriesProcessorMock,
				seasonNumberOfEpisodesFixedValueProviderMock, seasonNumberProcessorMock, seasonNumberOfEpisodesProcessorMock)
	}

	void "parses page when it's not a page with multiple seasons"() {
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
		1 * seasonNumberOfEpisodesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.notFound()
		1 * seasonNumberOfEpisodesProcessorMock.process(sourcesPage) >> NUMBER_OF_EPISODES
		1 * series.companionSeries >> COMPANION_SERIES
		0 * _
		season.uid == UID
		season.title == TITLE
		season.page == modelPage
		season.series == series
		season.seasonNumber == SEASON_NUMBER
		season.numberOfEpisodes == NUMBER_OF_EPISODES
		season.companionSeriesSeason == COMPANION_SERIES
	}

	void "parses page when it's a page with multiple seasons"() {
		given:
		String stSeasonPageTitle = RandomUtil.randomItem(PageTitle.ST_SEASONS_LIST)
		PageHeader stSeasonPageHeader = new PageHeader(title: stSeasonPageTitle)
		SourcesPage sourcesPage = new SourcesPage(
				title: PageTitle.ST_SEASONS,
				redirectPath: [stSeasonPageHeader])
		ModelPage modelPage = new ModelPage()
		Series series = Mock()

		when:
		Season season = seasonPageProcessor.process(sourcesPage)

		then:
		1 * pageBindingServiceMock.fromPageHeaderToPageEntity(stSeasonPageHeader) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Season) >> UID
		1 * seasonSeriesProcessorMock.process(stSeasonPageTitle) >> series
		1 * seasonNumberProcessorMock.process(stSeasonPageTitle) >> SEASON_NUMBER
		1 * seasonNumberOfEpisodesFixedValueProviderMock.getSearchedValue(stSeasonPageTitle) >> FixedValueHolder.found(NUMBER_OF_EPISODES)
		1 * series.companionSeries >> COMPANION_SERIES
		0 * _
		season.uid == UID
		season.title == stSeasonPageTitle
		season.page == modelPage
		season.series == series
		season.seasonNumber == SEASON_NUMBER
		season.numberOfEpisodes == NUMBER_OF_EPISODES
		season.companionSeriesSeason == COMPANION_SERIES
	}

	void "return null for a page with multiple seasons but without the right redirect"() {
		given:
		SourcesPage sourcesPage = new SourcesPage(title: PageTitle.ST_SEASONS)

		when:
		Season season = seasonPageProcessor.process(sourcesPage)

		then:
		0 * _
		season == null
	}

}
