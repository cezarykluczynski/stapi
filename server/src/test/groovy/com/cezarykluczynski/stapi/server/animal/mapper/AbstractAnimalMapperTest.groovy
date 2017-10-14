package com.cezarykluczynski.stapi.server.animal.mapper

import com.cezarykluczynski.stapi.model.animal.entity.Animal
import com.cezarykluczynski.stapi.util.AbstractAnimalTest

abstract class AbstractAnimalMapperTest extends AbstractAnimalTest {

	protected static Animal createAnimal() {
		new Animal(
				uid: UID,
				name: NAME,
				earthAnimal: EARTH_ANIMAL,
				earthInsect: EARTH_INSECT,
				avian: AVIAN,
				canine: CANINE,
				feline: FELINE)
	}

}
