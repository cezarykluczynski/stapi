package com.cezarykluczynski.stapi.etl.template.common.linker;

import com.cezarykluczynski.stapi.etl.common.processor.LinkingWorker;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformanceDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformancesEntitiesDTO;
import com.cezarykluczynski.stapi.etl.template.common.service.EpisodePerformancesExtractor;
import com.cezarykluczynski.stapi.etl.template.common.service.EpisodePerformancesToEntityMapper;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class EpisodePerformancesLinkingWorker implements LinkingWorker<Page, Episode> {

	private CharacterRepository characterRepository;

	private PerformerRepository performerRepository;

	private EpisodePerformancesExtractor episodePerformancesExtractor;

	private EpisodePerformancesToEntityMapper episodePerformancesToEntityMapper;

	@Inject
	public EpisodePerformancesLinkingWorker(CharacterRepository characterRepository,
			PerformerRepository performerRepository, EpisodePerformancesExtractor episodePerformancesExtractor,
			EpisodePerformancesToEntityMapper episodePerformancesToEntityMapper) {
		this.characterRepository = characterRepository;
		this.performerRepository = performerRepository;
		this.episodePerformancesExtractor = episodePerformancesExtractor;
		this.episodePerformancesToEntityMapper = episodePerformancesToEntityMapper;
	}

	@Override
	public void link(Page source, Episode episode) {
		List<EpisodePerformanceDTO> episodePerformances = episodePerformancesExtractor.getEpisodePerformances(source);
		EpisodePerformancesEntitiesDTO episodePerformancesEntitiesDTO = episodePerformancesToEntityMapper
				.mapToEntities(episodePerformances, episode);

		characterRepository.save(episodePerformancesEntitiesDTO.getCharacterSet());
		performerRepository.save(episodePerformancesEntitiesDTO.getPerformerSet());
	}

}
