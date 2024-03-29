package com.cezarykluczynski.stapi.etl.trading_card.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.trading_card.creation.processor.TradingCardSetReader;
import com.cezarykluczynski.stapi.etl.util.SortingUtil;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.etl.util.constant.WordPressPageId;
import com.cezarykluczynski.stapi.etl.wordpress.api.WordPressApi;
import com.cezarykluczynski.stapi.etl.wordpress.api.enums.WordPressSource;
import com.cezarykluczynski.stapi.etl.wordpress.dto.Page;
import com.google.common.collect.Lists;
import jakarta.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@Configuration
public class TradingCardCreationConfiguration {

	@Inject
	private WordPressApi wordPressApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDataSourceInitializer")
	public TradingCardSetReader tradingCardReader() {
		List<Page> tradingCardList = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_TRADING_CARDS)) {
			tradingCardList.addAll(wordPressApi.getAllPagesUnderPage(WordPressPageId.MAIN_CARD_INDEX, WordPressSource.STAR_TREK_CARDS));
		}

		return new TradingCardSetReader(SortingUtil.sortedUniqueWordpressPages(tradingCardList));
	}

}
