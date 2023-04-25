package com.cezarykluczynski.stapi.etl.template.common.linker;

import com.cezarykluczynski.stapi.etl.common.processor.LinkingWorker;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.episode.creation.service.EpisodePerformancesToEntityMapper;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformanceDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformancesEntitiesDTO;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EpisodePerformancesLinkingWorker implements LinkingWorker<Page, Episode> {

	private final CharacterRepository characterRepository;

	private final PerformerRepository performerRepository;

	private final EpisodePerformancesExtractingProcessor episodePerformancesExtractingProcessor;

	private final EpisodePerformancesToEntityMapper episodePerformancesToEntityMapper;

	@Override
	public void link(Page source, Episode baseEntity) {
		List<EpisodePerformanceDTO> episodePerformances = episodePerformancesExtractingProcessor.process(source);
		EpisodePerformancesEntitiesDTO imageEpisodePerformancesEntitiesDTO = episodePerformancesToEntityMapper
				.mapToEntities(episodePerformances, baseEntity);

		characterRepository.saveAll(imageEpisodePerformancesEntitiesDTO.getCharacterSet());
		performerRepository.saveAll(imageEpisodePerformancesEntitiesDTO.getPerformerSet());
	}

}
