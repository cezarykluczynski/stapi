package com.cezarykluczynski.stapi.etl.common.processor.spacecraft_class;

import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_class.repository.SpacecraftClassRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WikitextToSpacecraftClassesProcessor implements ItemProcessor<String, List<SpacecraftClass>> {

	private final WikitextApi wikitextApi;

	private final SpacecraftClassRepository spacecraftClassRepository;

	@Inject
	public WikitextToSpacecraftClassesProcessor(WikitextApi wikitextApi, SpacecraftClassRepository spacecraftClassRepository) {
		this.wikitextApi = wikitextApi;
		this.spacecraftClassRepository = spacecraftClassRepository;
	}

	@Override
	public List<SpacecraftClass> process(String item) throws Exception {
		List<String> pageLinkTitleList = wikitextApi.getPageTitlesFromWikitext(item);

		return pageLinkTitleList.stream()
				.map(pageLinkTitle -> spacecraftClassRepository.findByPageTitleAndPageMediaWikiSource(pageLinkTitle, MediaWikiSource.MEMORY_ALPHA_EN))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

}
