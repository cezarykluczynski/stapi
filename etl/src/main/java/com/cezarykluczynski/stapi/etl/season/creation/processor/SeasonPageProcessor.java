package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeasonPageProcessor implements ItemProcessor<Page, Season> {

	private final UidGenerator uidGenerator;

	private final PageBindingService pageBindingService;

	private final SeasonSeriesProcessor seasonSeriesProcessor;

	private final SeasonNumberOfEpisodesFixedValueProvider seasonNumberOfEpisodesFixedValueProvider;

	private final SeasonNumberProcessor seasonNumberProcessor;

	private final SeasonNumberOfEpisodesProcessor seasonNumberOfEpisodesProcessor;

	@Override
	public Season process(Page item) throws Exception {
		String pageTitle = item.getTitle();
		Season season = new Season();
		if (PageTitle.ST_SEASONS.equals(pageTitle)) {
			PageHeader pageHeader;
			List<PageHeader> redirectPath = item.getRedirectPath();
			if (redirectPath != null && redirectPath.size() == 1) {
				pageHeader = item.getRedirectPath().get(0);
				pageTitle = pageHeader.getTitle();
			} else {
				log.error("Cannot get redirect path for page {}, redirect path is: {}", pageTitle, item.getRedirectPath());
				return null;
			}
			season.setPage(pageBindingService.fromPageHeaderToPageEntity(pageHeader));
		} else {
			season.setPage(pageBindingService.fromPageToPageEntity(item));
		}
		season.setTitle(pageTitle);
		season.setUid(uidGenerator.generateFromPage(season.getPage(), Season.class));
		season.setSeries(seasonSeriesProcessor.process(pageTitle));
		season.setSeasonNumber(seasonNumberProcessor.process(pageTitle));
		final FixedValueHolder<Integer> seasonNumberOfEpisodesFixedValue = seasonNumberOfEpisodesFixedValueProvider.getSearchedValue(pageTitle);
		if (seasonNumberOfEpisodesFixedValue.isFound()) {
			season.setNumberOfEpisodes(seasonNumberOfEpisodesFixedValue.getValue());
		} else {
			season.setNumberOfEpisodes(seasonNumberOfEpisodesProcessor.process(item));
		}

		season.setCompanionSeriesSeason(season.getSeries().getCompanionSeries());
		return season;
	}

}
