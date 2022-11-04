package com.cezarykluczynski.stapi.etl.episode.creation.service

import com.cezarykluczynski.stapi.etl.episode.creation.dto.ModuleEpisodeData
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.MonthNameToMonthProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.constant.PageTitle
import spock.lang.Specification

import java.time.Month

class ModuleEpisodeDataProviderTest extends Specification {

	private static final String VALID_EPISODE = 'Court Martial'
	private static final String VALID_EPISODE_WITH_BRACKETS = 'Court Martial (episode)'
	private static final String MISSING_EPISODE = 'MISSING_EPISODE'
	private static final String MODULE_EPISODE_DATA_A_CONTENTS = 'return {\n    ["Court Martial (episode)"] = "TOS",\n}'
	private static final String MODULE_EPISODE_DATA_D_CONTENTS = 'return {\n    ["Court Martial"] = "2",\nti'
	private static final String MODULE_EPISODE_DATA_M_CONTENTS = 'return {\n    ["Court Martial"] = "February"}'
	private static final String MODULE_EPISODE_DATA_N_CONTENTS = 'return {\n    ["Court Martial"] = "1x14",}'
	private static final String MODULE_EPISODE_DATA_P_CONTENTS = 'return {\n    ["Court Martial"] = "6149-15"\n}'
	private static final String MODULE_EPISODE_DATA_Y_CONTENTS = 'return {\n    ["Court Martial"] = "1967",,'

	PageApi pageApiMock
	MonthNameToMonthProcessor monthNameToMonthProcessorMock
	ModuleEpisodeDataProvider moduleEpisodeDataProvider

	void setup() {
		pageApiMock = Mock()
		monthNameToMonthProcessorMock = Mock()
		moduleEpisodeDataProvider = new ModuleEpisodeDataProvider(pageApiMock, monthNameToMonthProcessorMock)
	}

	void "has to be initialized first"() {
		when:
		moduleEpisodeDataProvider.provideDataFor(VALID_EPISODE)

		then:
		thrown(RuntimeException)
	}

	@SuppressWarnings('LineLength')
	void "episode details can be obtained"() {
		when:
		moduleEpisodeDataProvider.initialize()

		then:
		1 * pageApiMock.getPage(PageTitle.MODULE_EPISODE_DATA_A, MediaWikiSource.MEMORY_ALPHA_EN) >> new Page(wikitext: MODULE_EPISODE_DATA_A_CONTENTS)
		1 * pageApiMock.getPage(PageTitle.MODULE_EPISODE_DATA_D, MediaWikiSource.MEMORY_ALPHA_EN) >> new Page(wikitext: MODULE_EPISODE_DATA_D_CONTENTS)
		1 * pageApiMock.getPage(PageTitle.MODULE_EPISODE_DATA_M, MediaWikiSource.MEMORY_ALPHA_EN) >> new Page(wikitext: MODULE_EPISODE_DATA_M_CONTENTS)
		1 * pageApiMock.getPage(PageTitle.MODULE_EPISODE_DATA_N, MediaWikiSource.MEMORY_ALPHA_EN) >> new Page(wikitext: MODULE_EPISODE_DATA_N_CONTENTS)
		1 * pageApiMock.getPage(PageTitle.MODULE_EPISODE_DATA_P, MediaWikiSource.MEMORY_ALPHA_EN) >> new Page(wikitext: MODULE_EPISODE_DATA_P_CONTENTS)
		1 * pageApiMock.getPage(PageTitle.MODULE_EPISODE_DATA_Y, MediaWikiSource.MEMORY_ALPHA_EN) >> new Page(wikitext: MODULE_EPISODE_DATA_Y_CONTENTS)

		when:
		ModuleEpisodeData moduleEpisodeData = moduleEpisodeDataProvider.provideDataFor(VALID_EPISODE)

		then:
		1 * monthNameToMonthProcessorMock.process('February') >> Month.FEBRUARY
		0 * _
		moduleEpisodeData.pageTitle == VALID_EPISODE
		moduleEpisodeData.series == 'TOS'
		moduleEpisodeData.seasonNumber == 1
		moduleEpisodeData.episodeNumber == 14
		moduleEpisodeData.releaseDay == 2
		moduleEpisodeData.releaseMonth == 2
		moduleEpisodeData.releaseYear == 1967
		moduleEpisodeData.productionNumber == '6149-15'

		when:
		moduleEpisodeData = moduleEpisodeDataProvider.provideDataFor(VALID_EPISODE_WITH_BRACKETS)

		then:
		1 * monthNameToMonthProcessorMock.process('February') >> Month.FEBRUARY
		0 * _
		moduleEpisodeData.pageTitle == VALID_EPISODE
		moduleEpisodeData.series == 'TOS'
		moduleEpisodeData.seasonNumber == 1
		moduleEpisodeData.episodeNumber == 14
		moduleEpisodeData.releaseDay == 2
		moduleEpisodeData.releaseMonth == 2
		moduleEpisodeData.releaseYear == 1967
		moduleEpisodeData.productionNumber == '6149-15'

		when:
		moduleEpisodeData = moduleEpisodeDataProvider.provideDataFor(MISSING_EPISODE)

		then:
		moduleEpisodeData == null
	}

}
