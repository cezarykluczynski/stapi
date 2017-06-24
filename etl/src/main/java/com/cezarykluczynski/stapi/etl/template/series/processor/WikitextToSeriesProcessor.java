package com.cezarykluczynski.stapi.etl.template.series.processor;

import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Service
public class WikitextToSeriesProcessor implements ItemProcessor<String, Series> {

	private final WikitextApi wikitextApi;

	private final SeriesRepository seriesRepository;

	@Inject
	public WikitextToSeriesProcessor(WikitextApi wikitextApi, SeriesRepository seriesRepository) {
		this.wikitextApi = wikitextApi;
		this.seriesRepository = seriesRepository;
	}

	@Override
	public Series process(String item) throws Exception {
		List<String> pageLinkTitleList = wikitextApi.getPageTitlesFromWikitext(item);

		return pageLinkTitleList.stream()
				.map(pageLinkTitle -> seriesRepository
						.findByPageTitleAndPageMediaWikiSource(pageLinkTitle, MediaWikiSource.MEMORY_ALPHA_EN)
						.orElseGet(() -> seriesRepository.findByAbbreviation(pageLinkTitle).orElse(null)))
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(null);
	}

}
