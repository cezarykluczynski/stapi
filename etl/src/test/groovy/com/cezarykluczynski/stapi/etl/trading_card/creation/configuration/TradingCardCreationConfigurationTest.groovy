package com.cezarykluczynski.stapi.etl.trading_card.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.trading_card.creation.processor.TradingCardSetReader
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.util.constant.WordPressPageId
import com.cezarykluczynski.stapi.sources.wordpress.api.WordPressApi
import com.cezarykluczynski.stapi.sources.wordpress.api.enums.WordPressSource

class TradingCardCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_TRADING_CARD_SET = 'TITLE_TRADING_CARD_SET'

	private WordPressApi wordPressApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private TradingCardCreationConfiguration tradingCardCreationConfiguration

	void setup() {
		wordPressApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		tradingCardCreationConfiguration = new TradingCardCreationConfiguration(
				wordPressApi: wordPressApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "TradingCardSetReader is created with all pages when step is not completed"() {
		when:
		TradingCardSetReader tradingCardSetReader = tradingCardCreationConfiguration.tradingCardReader()
		List<String> pageTitleList = pageReaderToList(tradingCardSetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_TRADING_CARDS) >> false
		1 * wordPressApiMock.getAllPagesUnderPage(WordPressPageId.MAIN_CARD_INDEX, WordPressSource.STAR_TREK_CARDS) >>
				createListWithPageRenderedTitle(TITLE_TRADING_CARD_SET)
		0 * _
		pageTitleList.contains TITLE_TRADING_CARD_SET
	}

	void "TradingCardSetReader is created with no pages when step is completed"() {
		when:
		TradingCardSetReader tradingCardSetReader = tradingCardCreationConfiguration.tradingCardReader()
		List<String> pageTitleList = pageReaderToList(tradingCardSetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_TRADING_CARDS) >> true
		0 * _
		pageTitleList.empty
	}

}
