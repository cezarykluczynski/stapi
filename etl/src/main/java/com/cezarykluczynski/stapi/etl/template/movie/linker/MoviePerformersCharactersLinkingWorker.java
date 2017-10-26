package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.common.service.NonQualifiedCharacterFilter;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
	public void link(Set<List<String>> source, Movie baseEntity) {
		Set<Performer> performerSet = Sets.newHashSet();
		Set<Character> characterSet = Sets.newHashSet();

		source.forEach(lineList -> {
			if (CollectionUtils.isEmpty(lineList) || lineList.size() == 1) {
				return;
			}

			String characterName = lineList.get(0);
			String performerName = lineList.get(1);

			if (StringUtils.startsWithAny(characterName, IGNORABLE_PAGE_PREFIXES)
					|| StringUtils.startsWithAny(performerName, IGNORABLE_PAGE_PREFIXES)) {
				return;
			}

			if (lineList.size() > 2) {
				if (DELETED_SCENE.equals(lineList.get(2))) {
					return;
				}

				log.warn("More than 2 links found, the remaining ignored were: {}",
						lineList.subList(2, lineList.size()));
			}

			Optional<Performer> performerOptional = entityLookupByNameService
					.findPerformerByName(performerName, MovieRealPeopleLinkingWorker.SOURCE);

			Optional<Character> characterOptional = Optional.empty();
			if (!nonQualifiedCharacterFilter.shouldBeFilteredOut(characterName)) {
				characterOptional = entityLookupByNameService
						.findCharacterByName(characterName, MovieRealPeopleLinkingWorker.SOURCE);
			}

			boolean performerIsPresent = performerOptional.isPresent();
			boolean characterIsPresent = characterOptional.isPresent();

			if (!performerIsPresent && !characterIsPresent) {
				log.warn("Entities for performer \"{}\" and the played character \"{}\" were not found", performerName,
						characterName);
			} else if (!performerIsPresent) {
				log.warn("Entity for performer \"{}\" playing character \"{}\" was not found", performerName, characterName);
			} else if (!characterIsPresent) {
				log.warn("Entity for character \"{}\" played by performer \"{}\" was not found", characterName, performerName);
			}

			performerOptional.ifPresent(performerSet::add);
			characterOptional.ifPresent(characterSet::add);
		});

		baseEntity.getPerformers().addAll(performerSet);
		baseEntity.getCharacters().addAll(characterSet);
	}
}
