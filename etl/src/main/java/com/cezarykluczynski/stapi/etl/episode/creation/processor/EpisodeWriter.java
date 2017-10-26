package com.cezarykluczynski.stapi.etl.episode.creation.processor;

import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.episode.repository.EpisodeRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EpisodeWriter implements ItemWriter<Episode> {

	private final EpisodeRepository episodeRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public EpisodeWriter(EpisodeRepository episodeRepository, DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.episodeRepository = episodeRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Episode> items) throws Exception {
		episodeRepository.save(process(items));
	}

	private List<Episode> process(List<? extends Episode> episodeList) {
		List<Episode> episodeListWithoutExtends = fromExtendsListToEpisodeList(episodeList);
		return filterDuplicates(episodeListWithoutExtends);
	}

	private List<Episode> fromExtendsListToEpisodeList(List<? extends Episode> episodeList) {
		return episodeList
				.stream()
				.map(pageAware -> (Episode) pageAware)
				.collect(Collectors.toList());
	}

	private List<Episode> filterDuplicates(List<Episode> episodeList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(episodeList.stream()
				.map(episode -> (PageAware) episode)
				.collect(Collectors.toList()), Episode.class).stream()
				.map(pageAware -> (Episode) pageAware)
				.collect(Collectors.toList());
	}

}
