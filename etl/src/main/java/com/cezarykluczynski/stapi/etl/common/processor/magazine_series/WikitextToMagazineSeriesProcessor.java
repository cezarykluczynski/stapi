package com.cezarykluczynski.stapi.etl.common.processor.magazine_series;

import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.model.magazine_series.repository.MagazineSeriesRepository;
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
public class WikitextToMagazineSeriesProcessor implements ItemProcessor<String, Set<MagazineSeries>> {

	private final WikitextApi wikitextApi;

	private final MagazineSeriesRepository magazineSeriesRepository;

	@Inject
	public WikitextToMagazineSeriesProcessor(WikitextApi wikitextApi, MagazineSeriesRepository magazineSeriesRepository) {
		this.wikitextApi = wikitextApi;
		this.magazineSeriesRepository = magazineSeriesRepository;
	}

	@Override
	public Set<MagazineSeries> process(String item) throws Exception {
		List<String> pageLinkTitleList = wikitextApi.getPageTitlesFromWikitext(item);

		return pageLinkTitleList.stream()
				.map(pageLinkTitle -> magazineSeriesRepository.findByPageTitleAndPageMediaWikiSource(pageLinkTitle, MediaWikiSource.MEMORY_ALPHA_EN))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());
	}

}
