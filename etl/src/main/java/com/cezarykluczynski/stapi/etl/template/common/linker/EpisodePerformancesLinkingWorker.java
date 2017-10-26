package com.cezarykluczynski.stapi.etl.template.common.linker;

import com.cezarykluczynski.stapi.etl.common.processor.LinkingWorker;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.episode.creation.service.EpisodePerformancesToEntityMapper;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformanceDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformancesEntitiesDTO;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EpisodePerformancesLinkingWorker implements LinkingWorker<Page, Episode> {

	private final CharacterRepository characterRepository;

	private final PerformerRepository performerRepository;

	private final EpisodePerformancesExtractingProcessor episodePerformancesExtractingProcessor;

	private final EpisodePerformancesToEntityMapper episodePerformancesToEntityMapper;

	public EpisodePerformancesLinkingWorker(CharacterRepository characterRepository,
			PerformerRepository performerRepository, EpisodePerformancesExtractingProcessor episodePerformancesExtractingProcessor,
			EpisodePerformancesToEntityMapper episodePerformancesToEntityMapper) {
		this.characterRepository = characterRepository;
		this.performerRepository = performerRepository;
		this.episodePerformancesExtractingProcessor = episodePerformancesExtractingProcessor;
		this.episodePerformancesToEntityMapper = episodePerformancesToEntityMapper;
	}

	@Override
	public void link(Page source, Episode baseEntity) {
		List<EpisodePerformanceDTO> episodePerformances = episodePerformancesExtractingProcessor.process(source);
		EpisodePerformancesEntitiesDTO imageEpisodePerformancesEntitiesDTO = episodePerformancesToEntityMapper
				.mapToEntities(episodePerformances, baseEntity);

		characterRepository.save(imageEpisodePerformancesEntitiesDTO.getCharacterSet());
		performerRepository.save(imageEpisodePerformancesEntitiesDTO.getPerformerSet());
	}

}
