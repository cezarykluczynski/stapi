package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterSpecies as RESTCharacterSpecies
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies as ModelCharacterSpecies
import com.cezarykluczynski.stapi.model.species.entity.Species
import org.mapstruct.factory.Mappers

class CharacterSpeciesRestMapperTest extends AbstractCharacterMapperTest {

	private CharacterSpeciesRestMapper characterSpeciesRestMapper

	void setup() {
		characterSpeciesRestMapper = Mappers.getMapper(CharacterSpeciesRestMapper)
	}

	void "maps model CharacterSpecies to REST CharacterSpecies"() {
		given:
		ModelCharacterSpecies modelCharacterSpecies = new ModelCharacterSpecies(
				species: new Species(
						name: NAME,
						uid: UID,
				),
				numerator: NUMERATOR,
				denominator: DENOMINATOR)

		when:
		RESTCharacterSpecies restCharacterSpecies = characterSpeciesRestMapper.map(modelCharacterSpecies)

		then:
		restCharacterSpecies.name == NAME
		restCharacterSpecies.uid == UID
		restCharacterSpecies.numerator == NUMERATOR
		restCharacterSpecies.denominator == DENOMINATOR
	}

}
