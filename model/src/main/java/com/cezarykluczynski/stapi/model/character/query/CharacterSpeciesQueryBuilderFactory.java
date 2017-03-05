package com.cezarykluczynski.stapi.model.character.query;


import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CharacterSpeciesQueryBuilderFactory extends AbstractQueryBuilderFactory<CharacterSpecies> {

	@Inject
	public CharacterSpeciesQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, CharacterSpecies.class);
	}

}
