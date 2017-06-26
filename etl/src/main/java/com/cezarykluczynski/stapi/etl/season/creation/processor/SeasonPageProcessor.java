package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SeasonPageProcessor implements ItemProcessor<Page, Season> {

	private final UidGenerator uidGenerator;

	private final PageBindingService pageBindingService;

	private final SeasonSeriesProcessor seasonSeriesProcessor;

	private final SeasonNumberProcessor seasonNumberProcessor;

	private final SeasonNumberOfEpisodesFixedValueProvider seasonNumberOfEpisodesFixedValueProvider;

	@Inject
	public SeasonPageProcessor(UidGenerator uidGenerator, PageBindingService pageBindingService, SeasonSeriesProcessor seasonSeriesProcessor,
			SeasonNumberProcessor seasonNumberProcessor, SeasonNumberOfEpisodesFixedValueProvider seasonNumberOfEpisodesFixedValueProvider) {
		this.uidGenerator = uidGenerator;
		this.pageBindingService = pageBindingService;
		this.seasonSeriesProcessor = seasonSeriesProcessor;
		this.seasonNumberProcessor = seasonNumberProcessor;
		this.seasonNumberOfEpisodesFixedValueProvider = seasonNumberOfEpisodesFixedValueProvider;
	}

	@Override
	public Season process(Page item) throws Exception {
		Season season = new Season();
		String pageTitle = item.getTitle();

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
