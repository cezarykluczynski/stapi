package com.cezarykluczynski.stapi.etl.common.processor.season;

import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WikitextToSeasonProcessor implements ItemProcessor<String, Set<Season>> {

	private final WikitextApi wikitextApi;

	private final SeasonRepository seasonRepository;

	@Inject
	public WikitextToSeasonProcessor(WikitextApi wikitextApi, SeasonRepository seasonRepository) {
		this.wikitextApi = wikitextApi;
		this.seasonRepository = seasonRepository;
	}

	@Override
	public Set<Season> process(String item) throws Exception {
		List<String> pageLinkTitleList = wikitextApi.getPageTitlesFromWikitext(item);

		return pageLinkTitleList.stream()
				.map(pageLinkTitle -> seasonRepository.findByPageTitleAndPageMediaWikiSource(pageLinkTitle, MediaWikiSource.MEMORY_ALPHA_EN))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());
	}

}
