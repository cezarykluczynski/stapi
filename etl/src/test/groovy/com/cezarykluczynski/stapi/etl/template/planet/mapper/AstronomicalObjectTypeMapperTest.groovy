package com.cezarykluczynski.stapi.etl.template.planet.mapper

import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType as EtlAstronomicalObjectType
import com.cezarykluczynski.stapi.model.astronomical_object.entity.enums.AstronomicalObjectType as ModelAstronomicalObjectType
import spock.lang.Specification

class AstronomicalObjectTypeMapperTest extends Specification {

	private AstronomicalObjectTypeMapper astronomicalObjectTypeMapper

	void setup() {
		astronomicalObjectTypeMapper = new AstronomicalObjectTypeMapper()
	}

	void "maps ETL AstronomicalObjectType to model AstronomicalObjectTypeMapper"() {
		expect:
		astronomicalObjectTypeMapper.fromEtlToModel(null) == null
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.D_CLASS_PLANET) == ModelAstronomicalObjectType.D_CLASS_PLANET
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.H_CLASS_PLANET) == ModelAstronomicalObjectType.H_CLASS_PLANET
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.GAS_GIANT_PLANET) == ModelAstronomicalObjectType.GAS_GIANT_PLANET
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.K_CLASS_PLANET) == ModelAstronomicalObjectType.K_CLASS_PLANET
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.L_CLASS_PLANET) == ModelAstronomicalObjectType.L_CLASS_PLANET
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.M_CLASS_PLANET) == ModelAstronomicalObjectType.M_CLASS_PLANET
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.Y_CLASS_PLANET) == ModelAstronomicalObjectType.Y_CLASS_PLANET
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.ROGUE_PLANET) == ModelAstronomicalObjectType.ROGUE_PLANET
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.ARTIFICIAL_PLANET) == ModelAstronomicalObjectType.ARTIFICIAL_PLANET
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.ASTEROID) == ModelAstronomicalObjectType.ASTEROID
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.ASTEROID_BELT) == ModelAstronomicalObjectType.ASTEROID_BELT
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.CLUSTER) == ModelAstronomicalObjectType.CLUSTER
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.COMET) == ModelAstronomicalObjectType.COMET
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.CONSTELLATION) == ModelAstronomicalObjectType.CONSTELLATION
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.GALAXY) == ModelAstronomicalObjectType.GALAXY
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.MOON) == ModelAstronomicalObjectType.MOON
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.NEBULA) == ModelAstronomicalObjectType.NEBULA
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.PLANETOID) == ModelAstronomicalObjectType.PLANETOID
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.QUASAR) == ModelAstronomicalObjectType.QUASAR
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.STAR) == ModelAstronomicalObjectType.STAR
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.STAR_SYSTEM) == ModelAstronomicalObjectType.STAR_SYSTEM
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.SECTOR) == ModelAstronomicalObjectType.SECTOR
		astronomicalObjectTypeMapper.fromEtlToModel(EtlAstronomicalObjectType.REGION) == ModelAstronomicalObjectType.REGION
	}

	void "both enums has equal number of values"() {
		expect:
		EtlAstronomicalObjectType.values().size() == ModelAstronomicalObjectType.values().size()
	}

}
