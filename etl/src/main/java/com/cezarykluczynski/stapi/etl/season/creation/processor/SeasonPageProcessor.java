package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SeasonPageProcessor implements ItemProcessor<Page, Season> {

	private final UidGenerator uidGenerator;

	private final PageBindingService pageBindingService;

	private final SeasonSeriesProcessor seasonSeriesProcessor;

	private final SeasonNumberProcessor seasonNumberProcessor;

	private final SeasonNumberOfEpisodesProcessor seasonNumberOfEpisodesProcessor;

	public SeasonPageProcessor(UidGenerator uidGenerator, PageBindingService pageBindingService,
			SeasonSeriesProcessor seasonSeriesProcessor, SeasonNumberProcessor seasonNumberProcessor,
			SeasonNumberOfEpisodesProcessor seasonNumberOfEpisodesProcessor) {
		this.uidGenerator = uidGenerator;
		this.pageBindingService = pageBindingService;
		this.seasonSeriesProcessor = seasonSeriesProcessor;
		this.seasonNumberProcessor = seasonNumberProcessor;
		this.seasonNumberOfEpisodesProcessor = seasonNumberOfEpisodesProcessor;
	}

	@Override
	public Season process(Page item) throws Exception {
		String pageTitle = item.getTitle();
		Season season = new Season();
		season.setTitle(pageTitle);
		season.setPage(pageBindingService.fromPageToPageEntity(item));
		season.setUid(uidGenerator.generateFromPage(season.getPage(), Season.class));
		season.setSeries(seasonSeriesProcessor.process(pageTitle));
		season.setSeasonNumber(seasonNumberProcessor.process(pageTitle));
		season.setNumberOfEpisodes(seasonNumberOfEpisodesProcessor.process(item));
		return season;
	}

}
