package com.cezarykluczynski.stapi.etl.template.common.service;

import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformanceDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformancesEntitiesDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.enums.PerformanceType;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EpisodePerformancesToEntityMapper {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN;

	@Data
	private static class EpisodePerformanceEntitiesPair {

		private Performer performer;

		private Character character;

	}

	@Data
	private static class EpisodePerformanceForEntity {

		private Performer performer;

	}

	private CharacterRepository characterRepository;

	private PerformerRepository performerRepository;

	private PageApi pageApi;

	@Inject
	public EpisodePerformancesToEntityMapper(CharacterRepository characterRepository,
			PerformerRepository performerRepository, PageApi pageApi) {
		this.characterRepository = characterRepository;
		this.performerRepository = performerRepository;
		this.pageApi = pageApi;
	}

	public EpisodePerformancesEntitiesDTO mapToEntities(List<EpisodePerformanceDTO> episodePerformanceDTOList, Episode episode) {
		EpisodePerformancesEntitiesDTO episodePerformancesEntitiesDTO = new EpisodePerformancesEntitiesDTO();
		List<EpisodePerformanceEntitiesPair> episodePerformanceEntitiesPairList =
				getEpisodePerformanceEntitiesPairList(episodePerformanceDTOList);
		List<EpisodePerformanceForEntity> episodeStuntPerformanceEntityList =
				getEpisodePerformanceForEntitiesPairList(episodePerformanceDTOList, PerformanceType.STUNT);
		List<EpisodePerformanceForEntity> episodeStandInPerformanceEntityList =
				getEpisodePerformanceForEntitiesPairList(episodePerformanceDTOList, PerformanceType.STAND_IN);

		addPerformances(episodePerformanceEntitiesPairList, episodePerformancesEntitiesDTO, episode);
		addStuntPerformances(episodeStuntPerformanceEntityList, episode);
		addStandInPerformances(episodeStandInPerformanceEntityList, episode);

		return episodePerformancesEntitiesDTO;
	}

	private List<EpisodePerformanceEntitiesPair> getEpisodePerformanceEntitiesPairList(List<EpisodePerformanceDTO> episodePerformanceDTOList) {
		return episodePerformanceDTOList.stream()
				.filter(episodePerformanceDTO -> PerformanceType.PERFORMANCE.equals(episodePerformanceDTO.getPerformanceType()))
				.map(this::processPerformer)
				.collect(Collectors.toList());
	}

	private List<EpisodePerformanceForEntity> getEpisodePerformanceForEntitiesPairList(List<EpisodePerformanceDTO> episodePerformanceDTOList,
			PerformanceType performanceType) {
		return episodePerformanceDTOList.stream()
				.filter(episodePerformanceDTO -> performanceType.equals(episodePerformanceDTO.getPerformanceType()))
				.map(this::processPerformerFor)
				.collect(Collectors.toList());
	}

	private EpisodePerformanceEntitiesPair processPerformer(EpisodePerformanceDTO episodePerformanceDTO) {
		EpisodePerformanceEntitiesPair pair = new EpisodePerformanceEntitiesPair();
		pair.setCharacter(getCharacter(episodePerformanceDTO.getCharacterName()).orElse(null));
		pair.setPerformer(getPerformer(episodePerformanceDTO.getPerformerName()).orElse(null));
		return pair;
	}

	private EpisodePerformanceForEntity processPerformerFor(EpisodePerformanceDTO episodePerformanceDTO) {
		EpisodePerformanceForEntity pair = new EpisodePerformanceForEntity();
		pair.setPerformer(getPerformer(episodePerformanceDTO.getPerformerName()).orElse(null));
		return pair;
	}

	private void addPerformances(List<EpisodePerformanceEntitiesPair> episodePerformanceEntitiesPairList,
			EpisodePerformancesEntitiesDTO episodePerformancesEntitiesDTO, Episode episode) {
		episodePerformanceEntitiesPairList.forEach(pair -> {
			Character character = pair.getCharacter();
			Performer performer = pair.getPerformer();
			if (character != null && performer != null) {
				character.getPerformers().add(performer);
				performer.getCharacters().add(character);
				episode.getPerformers().add(performer);
			}

			if (character != null) {
				episodePerformancesEntitiesDTO.getCharacterSet().add(character);
			}

			if (performer != null) {
				episodePerformancesEntitiesDTO.getPerformerSet().add(performer);
			}
		});
	}

	private void addStuntPerformances(List<EpisodePerformanceForEntity> episodeStuntPerformanceEntityList, Episode episode) {
		episode.getStuntPerformers().addAll(
				episodeStuntPerformanceEntityList
				.stream()
				.map(EpisodePerformanceForEntity::getPerformer).collect(Collectors.toSet()));
	}

	private void addStandInPerformances(List<EpisodePerformanceForEntity> episodeStandInPerformanceEntityList, Episode episode) {
		episode.getStandInPerformers().addAll(episodeStandInPerformanceEntityList
				.stream()
				.map(EpisodePerformanceForEntity::getPerformer).collect(Collectors.toSet()));
	}

	private Optional<Performer> getPerformer(String performerName) {
		Optional<Performer> performerOptional;

		try {
			performerOptional = performerRepository.findByName(performerName);
		} catch (NonUniqueResultException e) {
			performerOptional = Optional.empty();
		}

		if (performerOptional.isPresent()) {
			return performerOptional;
		} else {
			Page page = pageApi.getPage(performerName, SOURCE);
			if (page != null) {
				return performerRepository.findByPagePageId(page.getPageId());
			}
		}

		return Optional.empty();
	}

	private Optional<Character> getCharacter(String characterName) {
		Optional<Character> characterOptional;

		try {
			characterOptional = characterRepository.findByName(characterName);
		} catch (NonUniqueResultException e) {
			characterOptional = Optional.empty();
		}

		if (characterOptional.isPresent()) {
			return characterOptional;
		} else {
			Page page = pageApi.getPage(characterName, SOURCE);
			if (page != null) {
				return characterRepository.findByPagePageId(page.getPageId());
			}
		}

		return Optional.empty();
	}

}
