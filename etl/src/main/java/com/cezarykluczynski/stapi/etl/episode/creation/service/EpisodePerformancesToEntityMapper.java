package com.cezarykluczynski.stapi.etl.episode.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.common.service.NonQualifiedCharacterFilter;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformanceDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformancesEntitiesDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.enums.PerformanceType;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EpisodePerformancesToEntityMapper {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN;

	private final EntityLookupByNameService entityLookupByNameService;

	private final NonQualifiedCharacterFilter nonQualifiedCharacterFilter;

	public EpisodePerformancesToEntityMapper(EntityLookupByNameService entityLookupByNameService,
			NonQualifiedCharacterFilter nonQualifiedCharacterFilter) {
		this.entityLookupByNameService = entityLookupByNameService;
		this.nonQualifiedCharacterFilter = nonQualifiedCharacterFilter;
	}

	public EpisodePerformancesEntitiesDTO mapToEntities(List<EpisodePerformanceDTO> episodePerformanceDTOList, Episode episode) {
		EpisodePerformancesEntitiesDTO imageEpisodePerformancesEntitiesDTO = new EpisodePerformancesEntitiesDTO();
		List<EpisodePerformanceEntitiesPair> episodePerformanceEntitiesPairList
				= getEpisodePerformanceEntitiesPairList(episodePerformanceDTOList);
		List<EpisodePerformanceForEntity> episodeStuntPerformanceEntityList
				= getEpisodePerformanceForEntitiesPairList(episodePerformanceDTOList, PerformanceType.STUNT);
		List<EpisodePerformanceForEntity> episodeStandInPerformanceEntityList
				= getEpisodePerformanceForEntitiesPairList(episodePerformanceDTOList, PerformanceType.STAND_IN);

		addPerformances(episodePerformanceEntitiesPairList, imageEpisodePerformancesEntitiesDTO, episode);
		addStuntPerformances(episodeStuntPerformanceEntityList, episode);
		addStandInPerformances(episodeStandInPerformanceEntityList, episode);

		return imageEpisodePerformancesEntitiesDTO;
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
		String characterName = episodePerformanceDTO.getCharacterName();
		if (!nonQualifiedCharacterFilter.shouldBeFilteredOut(characterName)) {
			pair.setCharacter(getCharacter(characterName).orElse(null));
			if (pair.getCharacter() == null) {
				log.info("Character filter candidate: \"{}\"", characterName);
			}
			pair.setPerformer(getPerformer(episodePerformanceDTO.getPerformerName()).orElse(null));
		}
		return pair;
	}

	private EpisodePerformanceForEntity processPerformerFor(EpisodePerformanceDTO episodePerformanceDTO) {
		EpisodePerformanceForEntity pair = new EpisodePerformanceForEntity();
		pair.setPerformer(getPerformer(episodePerformanceDTO.getPerformerName()).orElse(null));
		return pair;
	}

	private void addPerformances(List<EpisodePerformanceEntitiesPair> episodePerformanceEntitiesPairList,
			EpisodePerformancesEntitiesDTO imageEpisodePerformancesEntitiesDTO, Episode episode) {
		episodePerformanceEntitiesPairList.forEach(pair -> {
			Character character = pair.getCharacter();
			Performer performer = pair.getPerformer();
			if (character != null && performer != null) {
				character.getPerformers().add(performer);
				performer.getCharacters().add(character);
				episode.getPerformers().add(performer);
			}

			if (character != null) {
				imageEpisodePerformancesEntitiesDTO.getCharacterSet().add(character);
				episode.getCharacters().add(character);
			}

			if (performer != null) {
				imageEpisodePerformancesEntitiesDTO.getPerformerSet().add(performer);
			}
		});
	}

	private void addStuntPerformances(List<EpisodePerformanceForEntity> episodeStuntPerformanceEntityList,
			Episode episode) {
		episode.getStuntPerformers().addAll(
				episodeStuntPerformanceEntityList
				.stream()
				.map(EpisodePerformanceForEntity::getPerformer).collect(Collectors.toSet()));
	}

	private void addStandInPerformances(List<EpisodePerformanceForEntity> episodeStandInPerformanceEntityList,
			Episode episode) {
		episode.getStandInPerformers().addAll(episodeStandInPerformanceEntityList
				.stream()
				.map(EpisodePerformanceForEntity::getPerformer).collect(Collectors.toSet()));
	}

	private Optional<Performer> getPerformer(String performerName) {
		return entityLookupByNameService.findPerformerByName(performerName, SOURCE);
	}

	private Optional<Character> getCharacter(String characterName) {
		return entityLookupByNameService.findCharacterByName(characterName, SOURCE);
	}

	@Data
	private static class EpisodePerformanceEntitiesPair {

		private Performer performer;

		private Character character;

	}

	@Data
	private static class EpisodePerformanceForEntity {

		private Performer performer;

	}

}
