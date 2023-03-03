package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.entity.Character_;
import com.cezarykluczynski.stapi.model.character.query.CharacterInitialQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CharacterRepositoryImpl extends AbstractRepositoryImpl<Character> implements CharacterRepositoryCustom {

	private final CharacterInitialQueryBuilderFactory characterInitialQueryBuilderFactory;

	public CharacterRepositoryImpl(CharacterInitialQueryBuilderFactory characterInitialQueryBuilderFactory) {
		this.characterInitialQueryBuilderFactory = characterInitialQueryBuilderFactory;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Character> findMatching(CharacterRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Character> characterQueryBuilder = createInitialCharacterQueryBuilder(criteria, pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		Page<Character> characterPage;

		if (doFetch) {
			characterQueryBuilder.fetch(Character_.performers);
			characterQueryBuilder.fetch(Character_.episodes);
			characterQueryBuilder.fetch(Character_.movies);
			characterPage = characterQueryBuilder.findPage();

			List<Character> characterList = characterPage.getContent();

			if (characterList.size() == 0) {
				return characterPage;
			}

			Character character = characterList.get(0);

			QueryBuilder<Character> characterSpeciesAndRelationsQueryBuilder = createInitialCharacterQueryBuilder(criteria, pageable);

			characterSpeciesAndRelationsQueryBuilder.fetch(Character_.characterSpecies);
			characterSpeciesAndRelationsQueryBuilder.fetch(Character_.characterRelations);

			List<Character> characterSpeciesAndRelationsList = characterSpeciesAndRelationsQueryBuilder.findAll();

			if (characterSpeciesAndRelationsList.size() == 1) {
				Character charactersSpeciesAndRelationsCharacter = characterSpeciesAndRelationsList.get(0);
				character.setCharacterSpecies(charactersSpeciesAndRelationsCharacter.getCharacterSpecies());
				character.setCharacterRelations(charactersSpeciesAndRelationsCharacter.getCharacterRelations());
			}

			QueryBuilder<Character> titlesOccupationsAndOrganizationsQueryBuilder = createInitialCharacterQueryBuilder(criteria, pageable);

			titlesOccupationsAndOrganizationsQueryBuilder.fetch(Character_.titles);
			titlesOccupationsAndOrganizationsQueryBuilder.fetch(Character_.occupations);
			titlesOccupationsAndOrganizationsQueryBuilder.fetch(Character_.organizations);

			List<Character> titlesAndOrganizationsList = titlesOccupationsAndOrganizationsQueryBuilder.findAll();

			if (characterSpeciesAndRelationsList.size() == 1) {
				Character titlesAndOrganizationsListCharacter = titlesAndOrganizationsList.get(0);
				character.setTitles(titlesAndOrganizationsListCharacter.getTitles());
				character.setOccupations(titlesAndOrganizationsListCharacter.getOccupations());
				character.setOrganizations(titlesAndOrganizationsListCharacter.getOrganizations());
			}
		} else {
			characterPage = characterQueryBuilder.findPage();
		}

		clearProxies(characterPage, !doFetch);
		return characterPage;
	}

	private QueryBuilder<Character> createInitialCharacterQueryBuilder(CharacterRequestDTO criteria, Pageable pageable) {
		return characterInitialQueryBuilderFactory.createInitialQueryBuilder(criteria, pageable);
	}

	@Override
	protected void clearProxies(Page<Character> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(character -> {
			character.setPerformers(Sets.newHashSet());
			character.setEpisodes(Sets.newHashSet());
			character.setMovies(Sets.newHashSet());
			character.setCharacterSpecies(Sets.newHashSet());
			character.setCharacterRelations(Sets.newHashSet());
			character.setTitles(Sets.newHashSet());
			character.setOccupations(Sets.newHashSet());
			character.setOrganizations(Sets.newHashSet());
		});
	}

}
