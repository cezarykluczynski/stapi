package com.cezarykluczynski.stapi.etl.common.processor.title;

import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WikitextToTitlesProcessor implements ItemProcessor<String, List<Title>> {

	private final WikitextApi wikitextApi;

	private final TitleRepository titleRepository;

	@Inject
	public WikitextToTitlesProcessor(WikitextApi wikitextApi, TitleRepository titleRepository) {
		this.wikitextApi = wikitextApi;
		this.titleRepository = titleRepository;
	}

	@Override
	public List<Title> process(String item) throws Exception {
		List<String> pageLinkTitleList = wikitextApi.getPageTitlesFromWikitext(item);

		return pageLinkTitleList.stream()
				.map(pageLinkTitle -> titleRepository.findByPageTitleAndPageMediaWikiSource(pageLinkTitle, MediaWikiSource.MEMORY_ALPHA_EN))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

}
