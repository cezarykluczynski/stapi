package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import org.apache.commons.lang3.math.Fraction;

public interface CharacterSpeciesRepositoryCustom {

	CharacterSpecies findOrCreate(Species species, Fraction fraction);

}
