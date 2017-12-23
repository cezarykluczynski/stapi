package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.season.creation.service.SeasonPageFilter;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SeasonPageProcessor implements ItemProcessor<Page, Season> {

	private final SeasonPageFilter seasonPageFilter;

	private final UidGenerator uidGenerator;

	private final PageBindingService pageBindingService;

	private final SeasonSeriesProcessor seasonSeriesProcessor;

	private final SeasonNumberProcessor seasonNumberProcessor;

	private final SeasonNumberOfEpisodesFixedValueProvider seasonNumberOfEpisodesFixedValueProvider;

	public SeasonPageProcessor(SeasonPageFilter seasonPageFilter, UidGenerator uidGenerator, PageBindingService pageBindingService,
			SeasonSeriesProcessor seasonSeriesProcessor, SeasonNumberProcessor seasonNumberProcessor,
			SeasonNumberOfEpisodesFixedValueProvider seasonNumberOfEpisodesFixedValueProvider) {
		this.seasonPageFilter = seasonPageFilter;
		this.uidGenerator = uidGenerator;
		this.pageBindingService = pageBindingService;
		this.seasonSeriesProcessor = seasonSeriesProcessor;
		this.seasonNumberProcessor = seasonNumberProcessor;
		this.seasonNumberOfEpisodesFixedValueProvider = seasonNumberOfEpisodesFixedValueProvider;
	}

	@Override
	public Season process(Page item) throws Exception {
		if (seasonPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		String pageTitle = item.getTitle();
		Season season = new Season();
		season.setTitle(pageTitle);
		season.setPage(pageBindingService.fromPageToPageEntity(item));
		season.setUid(uidGenerator.generateFromPage(season.getPage(), Season.class));
		season.setSeries(seasonSeriesProcessor.process(pageTitle));
		season.setSeasonNumber(seasonNumberProcessor.process(pageTitle));

		FixedValueHolder<Integer> numberOfEpisodesFixedValueHolder = seasonNumberOfEpisodesFixedValueProvider.getSearchedValue(pageTitle);

		if (numberOfEpisodesFixedValueHolder.isFound()) {
			season.setNumberOfEpisodes(numberOfEpisodesFixedValueHolder.getValue());
		}

		return season;
	}

}
