package com.cezarykluczynski.stapi.model.character.query;

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.entity.Character_;
import com.cezarykluczynski.stapi.model.common.query.InitialQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CharacterInitialQueryBuilderFactory implements InitialQueryBuilderFactory<CharacterRequestDTO, Character> {

	private final CharacterQueryBuilderFactory characterQueryBuilderFactory;

	public CharacterInitialQueryBuilderFactory(CharacterQueryBuilderFactory characterQueryBuilderFactory) {
		this.characterQueryBuilderFactory = characterQueryBuilderFactory;
	}

	@Override
	public QueryBuilder<Character> createInitialQueryBuilder(CharacterRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Character> characterQueryBuilder = characterQueryBuilderFactory.createQueryBuilder(pageable);

		characterQueryBuilder.equal(Character_.uid, criteria.getUid());
		characterQueryBuilder.like(Character_.name, criteria.getName());
		characterQueryBuilder.equal(Character_.gender, criteria.getGender());
		characterQueryBuilder.equal(Character_.deceased, criteria.getDeceased());
		characterQueryBuilder.equal(Character_.hologram, criteria.getHologram());
		characterQueryBuilder.equal(Character_.fictionalCharacter, criteria.getFictionalCharacter());
		characterQueryBuilder.equal(Character_.mirror, criteria.getMirror());
		characterQueryBuilder.equal(Character_.alternateReality, criteria.getAlternateReality());
		characterQueryBuilder.setSort(criteria.getSort());

		return characterQueryBuilder;
	}
}
