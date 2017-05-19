package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.soap.CharacterSpecies as SOAPCharacterSpecies
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies as ModelCharacterSpecies
import com.cezarykluczynski.stapi.model.species.entity.Species
import org.mapstruct.factory.Mappers

class CharacterSpeciesSoapMapperTest extends AbstractCharacterMapperTest {

	private CharacterSpeciesSoapMapper characterSpeciesSoapMapper

	void setup() {
		characterSpeciesSoapMapper = Mappers.getMapper(CharacterSpeciesSoapMapper)
	}

	void "maps model CharacterSpecies to SOAP CharacterSpecies"() {
		given:
		ModelCharacterSpecies modelCharacterSpecies = new ModelCharacterSpecies(
				species: new Species(
						name: NAME,
						uid: UID,
				),
				numerator: NUMERATOR,
				denominator: DENOMINATOR)

		when:
		SOAPCharacterSpecies soapCharacterSpecies = characterSpeciesSoapMapper.map(modelCharacterSpecies)

		then:
		soapCharacterSpecies.name == NAME
		soapCharacterSpecies.uid == UID
		soapCharacterSpecies.numerator == NUMERATOR
		soapCharacterSpecies.denominator == DENOMINATOR
	}

}
