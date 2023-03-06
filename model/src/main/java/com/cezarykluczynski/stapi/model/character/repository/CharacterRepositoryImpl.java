package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.entity.Character_;
import com.cezarykluczynski.stapi.model.character.query.CharacterQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class CharacterRepositoryImpl extends AbstractRepositoryImpl<Character> implements CharacterRepositoryCustom {

	private final CharacterQueryBuilderFactory characterQueryBuilderFactory;

	public CharacterRepositoryImpl(CharacterQueryBuilderFactory characterQueryBuilderFactory) {
		this.characterQueryBuilderFactory = characterQueryBuilderFactory;
	}

	@Override
	public Page<Character> findMatching(CharacterRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Character> characterQueryBuilder = characterQueryBuilderFactory.createQueryBuilder(pageable);

		String uid = criteria.getUid();
		boolean doFetch = uid != null;
		characterQueryBuilder.equal(Character_.uid, uid);
		characterQueryBuilder.like(Character_.name, criteria.getName());
		characterQueryBuilder.equal(Character_.gender, criteria.getGender());
		characterQueryBuilder.equal(Character_.deceased, criteria.getDeceased());
		characterQueryBuilder.equal(Character_.hologram, criteria.getHologram());
		characterQueryBuilder.equal(Character_.fictionalCharacter, criteria.getFictionalCharacter());
		characterQueryBuilder.equal(Character_.mirror, criteria.getMirror());
		characterQueryBuilder.equal(Character_.alternateReality, criteria.getAlternateReality());
		characterQueryBuilder.setSort(criteria.getSort());
		characterQueryBuilder.fetch(Character_.performers, doFetch);
		characterQueryBuilder.divideQueries();
		characterQueryBuilder.fetch(Character_.movies, doFetch);
		characterQueryBuilder.divideQueries();
		characterQueryBuilder.fetch(Character_.episodes, doFetch);
		characterQueryBuilder.divideQueries();
		characterQueryBuilder.fetch(Character_.characterSpecies, doFetch);
		characterQueryBuilder.fetch(Character_.characterRelations, doFetch);
		characterQueryBuilder.divideQueries();
		characterQueryBuilder.fetch(Character_.titles, doFetch);
		characterQueryBuilder.fetch(Character_.occupations, doFetch);
		characterQueryBuilder.fetch(Character_.organizations, doFetch);

		Page<Character> characterPage = characterQueryBuilder.findPage();
		clearProxies(characterPage, !doFetch);
		return characterPage;
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
