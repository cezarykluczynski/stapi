package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.common.service.NonQualifiedCharacterFilter;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesMediaSections;
import com.cezarykluczynski.stapi.etl.util.CharacterUtil;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MoviePerformersCharactersLinkingWorker implements MovieRealPeopleLinkingWorker {

	private static final String DELETED_SCENE = "deleted scene";
	private static final String[] IGNORABLE_PAGE_PREFIXES = {"USS ", "Unnamed ", "Human ", "Klingon ", "Mercy Hospital",
			"HMS Bounty personnel", "Computer voice", "Vulcan ", "Humanoid ", "Romulan ", "US military personnel"};

	private final EntityLookupByNameService entityLookupByNameService;

	private final NonQualifiedCharacterFilter nonQualifiedCharacterFilter;

	public MoviePerformersCharactersLinkingWorker(EntityLookupByNameService entityLookupByNameService,
			NonQualifiedCharacterFilter nonQualifiedCharacterFilter) {
		this.entityLookupByNameService = entityLookupByNameService;
		this.nonQualifiedCharacterFilter = nonQualifiedCharacterFilter;
	}

	@Override
	@SuppressWarnings({"CyclomaticComplexity", "NPathComplexity", "ParameterAssignment"})
	public void link(Set<List<String>> source, Movie baseEntity) {
		Set<Performer> performerSet = Sets.newHashSet();
		Set<Character> characterSet = Sets.newHashSet();

		source.forEach(lineList -> {
			if (CollectionUtils.isEmpty(lineList) || lineList.size() == 1) {
				return;
			}

			lineList = lineList.stream()
					.filter(line -> !EpisodePerformancesMediaSections.SKIPPABLE_PAGES.contains(line.toLowerCase()))
					.collect(Collectors.toList());

			if (lineList.stream().anyMatch(this::isDeletedScene)) {
				return;
			}

			if (lineList.size() > 2) {
				log.warn("More than 2 links found, all the links were: {}", lineList);
			}

			Collection<List<String>> permutations = Collections2.permutations(lineList);

			Optional<Performer> performerOptional = Optional.empty();
			Optional<Character> characterOptional = Optional.empty();
			boolean performerNeverPresent = true;
			boolean characterNeverPresent = true;
			for (List<String> permutation : permutations) {
				String characterName = permutation.get(0);
				String performerName = permutation.get(1);
				Pair<Optional<Performer>, Optional<Character>> pair = getPerformerAndCharacter(characterName, performerName);
				if (!performerOptional.isPresent()) {
					performerOptional = pair.getLeft();
				}
				if (!characterOptional.isPresent()) {
					characterOptional = pair.getRight();
				}
				if (performerOptional.isPresent()) {
					performerNeverPresent = false;
				}
				if (characterOptional.isPresent()) {
					characterNeverPresent = false;
				}

				if (performerOptional.isPresent() && characterOptional.isPresent()) {
					performerOptional.ifPresent(performerSet::add);
					characterOptional.ifPresent(characterSet::add);
					performerOptional = Optional.empty();
					characterOptional = Optional.empty();
					if (permutation.size() == 2) {
						break;
					}
				} else if (performerOptional.isPresent()) {
					performerSet.add(performerOptional.get());
					performerOptional = Optional.empty();
				} else if (characterOptional.isPresent()) {
					characterSet.add(characterOptional.get());
					characterOptional = Optional.empty();
				}
			}
			if (performerNeverPresent && characterNeverPresent) {
				log.warn("Entities for performer and the played character were not found in: {}", lineList);
			} else if (performerNeverPresent) {
				log.warn("Entity for performer was not found in: {}", lineList);
			} else if (characterNeverPresent) {
				if (lineList.stream().noneMatch(line -> CharacterUtil.isOneOfMany(line) || hasIgnorablePagePrefix(line))) {
					log.warn("Entity for character was not found in: {}", lineList);
				}
			}

			performerOptional.ifPresent(performerSet::add);
			characterOptional.ifPresent(characterSet::add);
		});

		baseEntity.getPerformers().addAll(performerSet);
		baseEntity.getCharacters().addAll(characterSet);
	}

	private Pair<Optional<Performer>, Optional<Character>> getPerformerAndCharacter(String characterName, String performerName) {
		Optional<Performer> performerOptional = entityLookupByNameService
				.findPerformerByName(performerName, MovieRealPeopleLinkingWorker.SOURCE);

		Optional<Character> characterOptional = Optional.empty();
		if (!nonQualifiedCharacterFilter.shouldBeFilteredOut(characterName)) {
			characterOptional = entityLookupByNameService
					.findCharacterByName(characterName, MovieRealPeopleLinkingWorker.SOURCE);
		}

		return Pair.of(performerOptional, characterOptional);
	}

	private boolean hasIgnorablePagePrefix(String string) {
		return StringUtils.startsWithAny(string, IGNORABLE_PAGE_PREFIXES);
	}

	private boolean isDeletedScene(String string) {
		return string != null && string.contains(DELETED_SCENE);
	}

}
