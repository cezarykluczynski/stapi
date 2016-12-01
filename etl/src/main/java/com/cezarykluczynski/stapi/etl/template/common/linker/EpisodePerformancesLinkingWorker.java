package com.cezarykluczynski.stapi.etl.template.common.linker;

import com.cezarykluczynski.stapi.etl.common.processor.LinkingWorker;
import com.cezarykluczynski.stapi.etl.template.common.dto.EpisodePerformanceDTO;
import com.cezarykluczynski.stapi.etl.template.common.service.EpisodePerformancesExtractor;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryNames;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class EpisodePerformancesLinkingWorker implements LinkingWorker<Page> {

	private CharacterRepository characterRepository;

	private PerformerRepository performerRepository;

	private EpisodePerformancesExtractor episodePerformancesExtractor;

	@Inject
	public EpisodePerformancesLinkingWorker(CharacterRepository characterRepository,
			PerformerRepository performerRepository, EpisodePerformancesExtractor episodePerformancesExtractor) {
		this.characterRepository = characterRepository;
		this.performerRepository = performerRepository;
		this.episodePerformancesExtractor = episodePerformancesExtractor;
	}

	@Override
	public void link(Page source) {
		if (!isEpisodePage(source)) {
			return;
		}

		List<EpisodePerformanceDTO> episodePerformances = episodePerformancesExtractor.getEpisodePerformances(source);


}

	private boolean isEpisodePage(Page source) {
		List<CategoryHeader> categoryHeaderList = source.getCategories();
		boolean hasEpisodeCategory = categoryHeaderList
				.stream()
				.map(CategoryHeader::getTitle)
				.anyMatch(this::isEpisodeCategory);

		boolean hasProductionListCategory = categoryHeaderList
				.stream()
				.map(CategoryHeader::getTitle)
				.anyMatch(this::isProductionList);

		return hasEpisodeCategory && !hasProductionListCategory;
	}

	private boolean isProductionList(String categoryName) {
		return CategoryName.PRODUCTION_LISTS.equals(categoryName);
	}

	private boolean isEpisodeCategory(String categoryName) {
		return CategoryNames.EPISODES.contains(categoryName);
	}
}
