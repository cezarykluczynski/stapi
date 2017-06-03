package com.cezarykluczynski.stapi.etl.common.processor.comicSeries;

import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comicSeries.repository.ComicSeriesRepository;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WikitextToComicSeriesProcessor implements ItemProcessor<String, Set<ComicSeries>> {

	private final WikitextApi wikitextApi;

	private final ComicSeriesRepository comicSeriesRepository;

	@Inject
	public WikitextToComicSeriesProcessor(WikitextApi wikitextApi, ComicSeriesRepository comicSeriesRepository) {
		this.wikitextApi = wikitextApi;
		this.comicSeriesRepository = comicSeriesRepository;
	}

	@Override
	public Set<ComicSeries> process(String item) throws Exception {
		List<String> pageLinkTitleList = wikitextApi.getPageTitlesFromWikitext(item);

		return pageLinkTitleList.stream()
				.map(pageLinkTitle -> comicSeriesRepository.findByPageTitleAndPageMediaWikiSource(pageLinkTitle, MediaWikiSource.MEMORY_ALPHA_EN))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());
	}

}
