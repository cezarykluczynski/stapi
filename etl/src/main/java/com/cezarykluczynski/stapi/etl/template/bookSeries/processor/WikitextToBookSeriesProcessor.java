package com.cezarykluczynski.stapi.etl.template.bookSeries.processor;

import com.cezarykluczynski.stapi.model.bookSeries.entity.BookSeries;
import com.cezarykluczynski.stapi.model.bookSeries.repository.BookSeriesRepository;
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
public class WikitextToBookSeriesProcessor implements ItemProcessor<String, Set<BookSeries>> {

	private WikitextApi wikitextApi;

	private BookSeriesRepository bookSeriesRepository;

	@Inject
	public WikitextToBookSeriesProcessor(WikitextApi wikitextApi, BookSeriesRepository bookSeriesRepository) {
		this.wikitextApi = wikitextApi;
		this.bookSeriesRepository = bookSeriesRepository;
	}

	@Override
	public Set<BookSeries> process(String item) throws Exception {
		List<String> pageLinkTitleList = wikitextApi.getPageTitlesFromWikitext(item);

		return pageLinkTitleList.stream()
				.map(pageLinkTitle -> bookSeriesRepository.findByPageTitleAndPageMediaWikiSource(pageLinkTitle, MediaWikiSource.MEMORY_ALPHA_EN))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());
	}

}
