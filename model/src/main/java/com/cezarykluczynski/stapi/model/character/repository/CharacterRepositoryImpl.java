package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.entity.Character_;
import com.cezarykluczynski.stapi.model.character.query.CharacterQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.movie.entity.Movie_;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class CharacterRepositoryImpl extends AbstractRepositoryImpl<Character> implements CharacterRepositoryCustom {

	private CharacterQueryBuilderFactory characterQueryBuilderFactory;

	@Inject
	public CharacterRepositoryImpl(CharacterQueryBuilderFactory characterQueryBuilderFactory) {
		this.characterQueryBuilderFactory = characterQueryBuilderFactory;
	}

	@Override
	public Page<Character> findMatching(CharacterRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Character> characterQueryBuilder = characterQueryBuilderFactory.createQueryBuilder(pageable);
		String guid = criteria.getGuid();
		boolean doFetch = guid != null;

		characterQueryBuilder.equal(Character_.guid, guid);
		characterQueryBuilder.like(Character_.name, criteria.getName());
		characterQueryBuilder.equal(Character_.gender, criteria.getGender());
		characterQueryBuilder.equal(Character_.deceased, criteria.getDeceased());
		characterQueryBuilder.equal(Character_.mirror, criteria.getMirror());
		characterQueryBuilder.equal(Character_.alternateReality, criteria.getAlternateReality());
		characterQueryBuilder.setSort(criteria.getSort());
		characterQueryBuilder.fetch(Character_.performers, doFetch);
		characterQueryBuilder.fetch(Character_.episodes, doFetch);
		characterQueryBuilder.fetch(Character_.movies, doFetch);
		characterQueryBuilder.fetch(Character_.movies, Movie_.mainDirector, doFetch);
		characterQueryBuilder.fetch(Character_.characterSpecies, doFetch);

		Page<Character> performerPage = characterQueryBuilder.findPage();
		clearProxies(performerPage, !doFetch);
		return performerPage;
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
		});
	}

}
