package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectType as RestAstronomicalObjectEnum
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectV2Type as RestAstronomicalObjectV2Enum
import com.cezarykluczynski.stapi.client.v1.rest.model.BloodType as RestBloodTypeEnum
import com.cezarykluczynski.stapi.client.v1.rest.model.Gender as RestGenderEnum
import com.cezarykluczynski.stapi.client.v1.rest.model.MaritalStatus as RestMaritalStatusEnum
import com.cezarykluczynski.stapi.client.v1.rest.model.ProductionRunUnit as RestProductionRunUnit
import com.cezarykluczynski.stapi.client.v1.rest.model.ReferenceType as RestReferenceType
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFormat as RestVideoReleaseFormat
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectTypeEnum
import com.cezarykluczynski.stapi.client.v1.soap.BloodTypeEnum as SoapBloodTypeEnum
import com.cezarykluczynski.stapi.client.v1.soap.GenderEnum as SoapGenderEnum
import com.cezarykluczynski.stapi.client.v1.soap.MaritalStatusEnum as SoapMaritalStatusEnum
import com.cezarykluczynski.stapi.client.v1.soap.ProductionRunUnitEnum
import com.cezarykluczynski.stapi.client.v1.soap.ReferenceTypeEnum as SoapReferenceType
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFormatEnum
import com.cezarykluczynski.stapi.model.astronomical_object.entity.enums.AstronomicalObjectType as AstronomicalObjectTypeEntity
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType as BloodTypeEntity
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender as GenderEntity
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus as MaritalStatusEntity
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType as ModelReferenceType
import com.cezarykluczynski.stapi.model.trading_card_set.entity.enums.ProductionRunUnit as ProductionRunUnitEntity
import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class EnumMapperTest extends Specification {

	private EnumMapper enumMapper

	void setup() {
		enumMapper = Mappers.getMapper(EnumMapper)
	}

	void "maps gender entity enum to soap enum"() {
		expect:
		enumMapper.mapGenderFromEntityEnumToSoapEnum(null) == null
		enumMapper.mapGenderFromEntityEnumToSoapEnum(GenderEntity.F) == SoapGenderEnum.F
		enumMapper.mapGenderFromEntityEnumToSoapEnum(GenderEntity.M) == SoapGenderEnum.M
	}

	void "maps gender soap dto enum to entity enum"() {
		expect:
		enumMapper.mapGenderFromSoapEnumToEntityEnum(null) == null
		enumMapper.mapGenderFromSoapEnumToEntityEnum(SoapGenderEnum.F) == GenderEntity.F
		enumMapper.mapGenderFromSoapEnumToEntityEnum(SoapGenderEnum.M) == GenderEntity.M
	}

	void "maps gender entity enum to rest enum"() {
		expect:
		enumMapper.mapGenderFromEntityEnumToRestEnum(null) == null
		enumMapper.mapGenderFromEntityEnumToRestEnum(GenderEntity.F) == RestGenderEnum.F
		enumMapper.mapGenderFromEntityEnumToRestEnum(GenderEntity.M) == RestGenderEnum.M
	}

	void "maps gender rest dto enum to entity enum"() {
		expect:
		enumMapper.mapGenderFromRestEnumToEntityEnum(null) == null
		enumMapper.mapGenderFromRestEnumToEntityEnum(RestGenderEnum.F) == GenderEntity.F
		enumMapper.mapGenderFromRestEnumToEntityEnum(RestGenderEnum.M) == GenderEntity.M
	}

	void "maps maritalStatus entity enum to soap enum"() {
		expect:
		enumMapper.mapMaritalStatusFromEntityEnumToSoapEnum(null) == null
		enumMapper.mapMaritalStatusFromEntityEnumToSoapEnum(MaritalStatusEntity.SINGLE) == SoapMaritalStatusEnum.SINGLE
		enumMapper.mapMaritalStatusFromEntityEnumToSoapEnum(MaritalStatusEntity.ENGAGED) == SoapMaritalStatusEnum.ENGAGED
		enumMapper.mapMaritalStatusFromEntityEnumToSoapEnum(MaritalStatusEntity.MARRIED) == SoapMaritalStatusEnum.MARRIED
		enumMapper.mapMaritalStatusFromEntityEnumToSoapEnum(MaritalStatusEntity.DIVORCED) == SoapMaritalStatusEnum.DIVORCED
		enumMapper.mapMaritalStatusFromEntityEnumToSoapEnum(MaritalStatusEntity.REMARRIED) == SoapMaritalStatusEnum.REMARRIED
		enumMapper.mapMaritalStatusFromEntityEnumToSoapEnum(MaritalStatusEntity.SEPARATED) == SoapMaritalStatusEnum.SEPARATED
		enumMapper.mapMaritalStatusFromEntityEnumToSoapEnum(MaritalStatusEntity.WIDOWED) == SoapMaritalStatusEnum.WIDOWED
		enumMapper.mapMaritalStatusFromEntityEnumToSoapEnum(MaritalStatusEntity.CAPTAINS_WOMAN) == SoapMaritalStatusEnum.CAPTAINS_WOMAN
	}

	void "maps maritalStatus entity enum to rest enum"() {
		expect:
		enumMapper.mapMaritalStatusFromEntityEnumToRestEnum(null) == null
		enumMapper.mapMaritalStatusFromEntityEnumToRestEnum(MaritalStatusEntity.SINGLE) == RestMaritalStatusEnum.SINGLE
		enumMapper.mapMaritalStatusFromEntityEnumToRestEnum(MaritalStatusEntity.ENGAGED) == RestMaritalStatusEnum.ENGAGED
		enumMapper.mapMaritalStatusFromEntityEnumToRestEnum(MaritalStatusEntity.MARRIED) == RestMaritalStatusEnum.MARRIED
		enumMapper.mapMaritalStatusFromEntityEnumToRestEnum(MaritalStatusEntity.DIVORCED) == RestMaritalStatusEnum.DIVORCED
		enumMapper.mapMaritalStatusFromEntityEnumToRestEnum(MaritalStatusEntity.REMARRIED) == RestMaritalStatusEnum.REMARRIED
		enumMapper.mapMaritalStatusFromEntityEnumToRestEnum(MaritalStatusEntity.SEPARATED) == RestMaritalStatusEnum.SEPARATED
		enumMapper.mapMaritalStatusFromEntityEnumToRestEnum(MaritalStatusEntity.WIDOWED) == RestMaritalStatusEnum.WIDOWED
		enumMapper.mapMaritalStatusFromEntityEnumToRestEnum(MaritalStatusEntity.CAPTAINS_WOMAN) == RestMaritalStatusEnum.CAPTAINS_WOMAN
	}

	void "maps blood type entity enum to soap enum"() {
		expect:
		enumMapper.mapBloodTypeFromEntityEnumToSoapEnum(null) == null
		enumMapper.mapBloodTypeFromEntityEnumToSoapEnum(BloodTypeEntity.B_NEGATIVE) == SoapBloodTypeEnum.B_NEGATIVE
		enumMapper.mapBloodTypeFromEntityEnumToSoapEnum(BloodTypeEntity.O_NEGATIVE) == SoapBloodTypeEnum.O_NEGATIVE
		enumMapper.mapBloodTypeFromEntityEnumToSoapEnum(BloodTypeEntity.T_NEGATIVE) == SoapBloodTypeEnum.T_NEGATIVE
	}

	void "maps blood type entity enum to rest enum"() {
		expect:
		enumMapper.mapBloodTypeFromEntityEnumToRestEnum(null) == null
		enumMapper.mapBloodTypeFromEntityEnumToRestEnum(BloodTypeEntity.B_NEGATIVE) == RestBloodTypeEnum.B_NEGATIVE
		enumMapper.mapBloodTypeFromEntityEnumToRestEnum(BloodTypeEntity.O_NEGATIVE) == RestBloodTypeEnum.O_NEGATIVE
		enumMapper.mapBloodTypeFromEntityEnumToRestEnum(BloodTypeEntity.T_NEGATIVE) == RestBloodTypeEnum.T_NEGATIVE
	}

	@SuppressWarnings('LineLength')
	void "maps astronomical object entity enum to to soap enum"() {
		expect:
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(null) == null
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.PLANET) == AstronomicalObjectTypeEnum.PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.D_CLASS_PLANET) == AstronomicalObjectTypeEnum.D_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.H_CLASS_PLANET) == AstronomicalObjectTypeEnum.H_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.GAS_GIANT_PLANET) == AstronomicalObjectTypeEnum.GAS_GIANT_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.K_CLASS_PLANET) == AstronomicalObjectTypeEnum.K_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.L_CLASS_PLANET) == AstronomicalObjectTypeEnum.L_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.M_CLASS_PLANET) == AstronomicalObjectTypeEnum.M_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.Y_CLASS_PLANET) == AstronomicalObjectTypeEnum.Y_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.ROGUE_PLANET) == AstronomicalObjectTypeEnum.ROGUE_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.ARTIFICIAL_PLANET) == AstronomicalObjectTypeEnum.ARTIFICIAL_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.ASTEROID) == AstronomicalObjectTypeEnum.ASTEROID
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.ASTEROIDAL_MOON) == AstronomicalObjectTypeEnum.ASTEROIDAL_MOON
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.ASTEROID_BELT) == AstronomicalObjectTypeEnum.ASTEROID_BELT
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.BORG_SPATIAL_DESIGNATION) == null
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.CLUSTER) == AstronomicalObjectTypeEnum.CLUSTER
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.COMET) == AstronomicalObjectTypeEnum.COMET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.CONSTELLATION) == AstronomicalObjectTypeEnum.CONSTELLATION
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.GALAXY) == AstronomicalObjectTypeEnum.GALAXY
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.MOON) == AstronomicalObjectTypeEnum.MOON
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.M_CLASS_MOON) == AstronomicalObjectTypeEnum.M_CLASS_MOON
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.NEBULA) == AstronomicalObjectTypeEnum.NEBULA
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.PLANETOID) == AstronomicalObjectTypeEnum.PLANETOID
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.D_CLASS_PLANETOID) == AstronomicalObjectTypeEnum.D_CLASS_PLANETOID
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.QUADRANT) == null
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.QUASAR) == AstronomicalObjectTypeEnum.QUASAR
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.STAR) == AstronomicalObjectTypeEnum.STAR
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.STAR_SYSTEM) == AstronomicalObjectTypeEnum.STAR_SYSTEM
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.SECTOR) == AstronomicalObjectTypeEnum.SECTOR
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectTypeEntity.REGION) == AstronomicalObjectTypeEnum.REGION
	}

	@SuppressWarnings('LineLength')
	void "maps astronomicalObject soap enum to entity enum"() {
		expect:
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(null) == null
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.PLANET) == AstronomicalObjectTypeEntity.PLANET
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.D_CLASS_PLANET) == AstronomicalObjectTypeEntity.D_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.H_CLASS_PLANET) == AstronomicalObjectTypeEntity.H_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.GAS_GIANT_PLANET) == AstronomicalObjectTypeEntity.GAS_GIANT_PLANET
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.K_CLASS_PLANET) == AstronomicalObjectTypeEntity.K_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.L_CLASS_PLANET) == AstronomicalObjectTypeEntity.L_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.M_CLASS_PLANET) == AstronomicalObjectTypeEntity.M_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.Y_CLASS_PLANET) == AstronomicalObjectTypeEntity.Y_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.ROGUE_PLANET) == AstronomicalObjectTypeEntity.ROGUE_PLANET
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.ARTIFICIAL_PLANET) == AstronomicalObjectTypeEntity.ARTIFICIAL_PLANET
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.ASTEROID) == AstronomicalObjectTypeEntity.ASTEROID
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.ASTEROIDAL_MOON) == AstronomicalObjectTypeEntity.ASTEROIDAL_MOON
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.ASTEROID_BELT) == AstronomicalObjectTypeEntity.ASTEROID_BELT
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.CLUSTER) == AstronomicalObjectTypeEntity.CLUSTER
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.COMET) == AstronomicalObjectTypeEntity.COMET
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.CONSTELLATION) == AstronomicalObjectTypeEntity.CONSTELLATION
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.GALAXY) == AstronomicalObjectTypeEntity.GALAXY
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.MOON) == AstronomicalObjectTypeEntity.MOON
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.M_CLASS_MOON) == AstronomicalObjectTypeEntity.M_CLASS_MOON
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.NEBULA) == AstronomicalObjectTypeEntity.NEBULA
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.PLANETOID) == AstronomicalObjectTypeEntity.PLANETOID
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.D_CLASS_PLANETOID) == AstronomicalObjectTypeEntity.D_CLASS_PLANETOID
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.QUASAR) == AstronomicalObjectTypeEntity.QUASAR
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.STAR) == AstronomicalObjectTypeEntity.STAR
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.STAR_SYSTEM) == AstronomicalObjectTypeEntity.STAR_SYSTEM
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.SECTOR) == AstronomicalObjectTypeEntity.SECTOR
		enumMapper.mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum.REGION) == AstronomicalObjectTypeEntity.REGION
	}

	@SuppressWarnings('LineLength')
	void "maps astronomical object entity enum to rest enum"() {
		expect:
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(null) == null
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.PLANET) == RestAstronomicalObjectEnum.PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.D_CLASS_PLANET) == RestAstronomicalObjectEnum.D_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.H_CLASS_PLANET) == RestAstronomicalObjectEnum.H_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.GAS_GIANT_PLANET) == RestAstronomicalObjectEnum.GAS_GIANT_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.K_CLASS_PLANET) == RestAstronomicalObjectEnum.K_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.L_CLASS_PLANET) == RestAstronomicalObjectEnum.L_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.M_CLASS_PLANET) == RestAstronomicalObjectEnum.M_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.Y_CLASS_PLANET) == RestAstronomicalObjectEnum.Y_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.ROGUE_PLANET) == RestAstronomicalObjectEnum.ROGUE_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.ARTIFICIAL_PLANET) == RestAstronomicalObjectEnum.ARTIFICIAL_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.ASTEROID) == RestAstronomicalObjectEnum.ASTEROID
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.ASTEROIDAL_MOON) == RestAstronomicalObjectEnum.ASTEROIDAL_MOON
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.ASTEROID_BELT) == RestAstronomicalObjectEnum.ASTEROID_BELT
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.BORG_SPATIAL_DESIGNATION) == null
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.CLUSTER) == RestAstronomicalObjectEnum.CLUSTER
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.COMET) == RestAstronomicalObjectEnum.COMET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.CONSTELLATION) == RestAstronomicalObjectEnum.CONSTELLATION
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.GALAXY) == RestAstronomicalObjectEnum.GALAXY
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.MOON) == RestAstronomicalObjectEnum.MOON
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.M_CLASS_MOON) == RestAstronomicalObjectEnum.M_CLASS_MOON
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.NEBULA) == RestAstronomicalObjectEnum.NEBULA
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.PLANETOID) == RestAstronomicalObjectEnum.PLANETOID
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.D_CLASS_PLANETOID) == RestAstronomicalObjectEnum.D_CLASS_PLANETOID
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.QUADRANT) == null
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.QUASAR) == RestAstronomicalObjectEnum.QUASAR
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.STAR) == RestAstronomicalObjectEnum.STAR
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.STAR_SYSTEM) == RestAstronomicalObjectEnum.STAR_SYSTEM
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.SECTOR) == RestAstronomicalObjectEnum.SECTOR
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestEnum(AstronomicalObjectTypeEntity.REGION) == RestAstronomicalObjectEnum.REGION
	}

	@SuppressWarnings('LineLength')
	void "maps astronomical object entity enum to rest V2 enum"() {
		expect:
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(null) == null
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.PLANET) == RestAstronomicalObjectV2Enum.PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.D_CLASS_PLANET) == RestAstronomicalObjectV2Enum.D_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.H_CLASS_PLANET) == RestAstronomicalObjectV2Enum.H_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.GAS_GIANT_PLANET) == RestAstronomicalObjectV2Enum.GAS_GIANT_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.K_CLASS_PLANET) == RestAstronomicalObjectV2Enum.K_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.L_CLASS_PLANET) == RestAstronomicalObjectV2Enum.L_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.M_CLASS_PLANET) == RestAstronomicalObjectV2Enum.M_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.Y_CLASS_PLANET) == RestAstronomicalObjectV2Enum.Y_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.ROGUE_PLANET) == RestAstronomicalObjectV2Enum.ROGUE_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.ARTIFICIAL_PLANET) == RestAstronomicalObjectV2Enum.ARTIFICIAL_PLANET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.ASTEROID) == RestAstronomicalObjectV2Enum.ASTEROID
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.ASTEROIDAL_MOON) == RestAstronomicalObjectV2Enum.ASTEROIDAL_MOON
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.ASTEROID_BELT) == RestAstronomicalObjectV2Enum.ASTEROID_BELT
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.BORG_SPATIAL_DESIGNATION) == RestAstronomicalObjectV2Enum.BORG_SPATIAL_DESIGNATION
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.CLUSTER) == RestAstronomicalObjectV2Enum.CLUSTER
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.COMET) == RestAstronomicalObjectV2Enum.COMET
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.CONSTELLATION) == RestAstronomicalObjectV2Enum.CONSTELLATION
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.GALAXY) == RestAstronomicalObjectV2Enum.GALAXY
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.MOON) == RestAstronomicalObjectV2Enum.MOON
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.M_CLASS_MOON) == RestAstronomicalObjectV2Enum.M_CLASS_MOON
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.NEBULA) == RestAstronomicalObjectV2Enum.NEBULA
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.PLANETOID) == RestAstronomicalObjectV2Enum.PLANETOID
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.D_CLASS_PLANETOID) == RestAstronomicalObjectV2Enum.D_CLASS_PLANETOID
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.QUADRANT) == RestAstronomicalObjectV2Enum.QUADRANT
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.QUASAR) == RestAstronomicalObjectV2Enum.QUASAR
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.STAR) == RestAstronomicalObjectV2Enum.STAR
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.STAR_SYSTEM) == RestAstronomicalObjectV2Enum.STAR_SYSTEM
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.SECTOR) == RestAstronomicalObjectV2Enum.SECTOR
		enumMapper.mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(AstronomicalObjectTypeEntity.REGION) == RestAstronomicalObjectV2Enum.REGION
	}

	@SuppressWarnings('LineLength')
	void "maps astronomical object rest enum to entity enum"() {
		expect:
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(null) == null
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.PLANET) == AstronomicalObjectTypeEntity.PLANET
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.D_CLASS_PLANET) == AstronomicalObjectTypeEntity.D_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.H_CLASS_PLANET) == AstronomicalObjectTypeEntity.H_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.GAS_GIANT_PLANET) == AstronomicalObjectTypeEntity.GAS_GIANT_PLANET
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.K_CLASS_PLANET) == AstronomicalObjectTypeEntity.K_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.L_CLASS_PLANET) == AstronomicalObjectTypeEntity.L_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.M_CLASS_PLANET) == AstronomicalObjectTypeEntity.M_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.Y_CLASS_PLANET) == AstronomicalObjectTypeEntity.Y_CLASS_PLANET
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.ROGUE_PLANET) == AstronomicalObjectTypeEntity.ROGUE_PLANET
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.ARTIFICIAL_PLANET) == AstronomicalObjectTypeEntity.ARTIFICIAL_PLANET
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.ASTEROID) == AstronomicalObjectTypeEntity.ASTEROID
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.ASTEROIDAL_MOON) == AstronomicalObjectTypeEntity.ASTEROIDAL_MOON
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.ASTEROID_BELT) == AstronomicalObjectTypeEntity.ASTEROID_BELT
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.CLUSTER) == AstronomicalObjectTypeEntity.CLUSTER
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.COMET) == AstronomicalObjectTypeEntity.COMET
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.CONSTELLATION) == AstronomicalObjectTypeEntity.CONSTELLATION
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.GALAXY) == AstronomicalObjectTypeEntity.GALAXY
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.MOON) == AstronomicalObjectTypeEntity.MOON
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.M_CLASS_MOON) == AstronomicalObjectTypeEntity.M_CLASS_MOON
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.NEBULA) == AstronomicalObjectTypeEntity.NEBULA
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.PLANETOID) == AstronomicalObjectTypeEntity.PLANETOID
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.D_CLASS_PLANETOID) == AstronomicalObjectTypeEntity.D_CLASS_PLANETOID
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.QUASAR) == AstronomicalObjectTypeEntity.QUASAR
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.STAR) == AstronomicalObjectTypeEntity.STAR
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.STAR_SYSTEM) == AstronomicalObjectTypeEntity.STAR_SYSTEM
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.SECTOR) == AstronomicalObjectTypeEntity.SECTOR
		enumMapper.mapAstronomicalObjectTypeFromRestEnumToEntityEnum(RestAstronomicalObjectEnum.REGION) == AstronomicalObjectTypeEntity.REGION
	}

	@SuppressWarnings('LineLength')
	void "maps astronomical object V2 rest enum to entity enum"() {
		expect:
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(null) == null
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.PLANET) == AstronomicalObjectTypeEntity.PLANET
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.D_CLASS_PLANET) == AstronomicalObjectTypeEntity.D_CLASS_PLANET
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.H_CLASS_PLANET) == AstronomicalObjectTypeEntity.H_CLASS_PLANET
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.GAS_GIANT_PLANET) == AstronomicalObjectTypeEntity.GAS_GIANT_PLANET
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.K_CLASS_PLANET) == AstronomicalObjectTypeEntity.K_CLASS_PLANET
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.L_CLASS_PLANET) == AstronomicalObjectTypeEntity.L_CLASS_PLANET
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.M_CLASS_PLANET) == AstronomicalObjectTypeEntity.M_CLASS_PLANET
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.Y_CLASS_PLANET) == AstronomicalObjectTypeEntity.Y_CLASS_PLANET
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.ROGUE_PLANET) == AstronomicalObjectTypeEntity.ROGUE_PLANET
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.ARTIFICIAL_PLANET) == AstronomicalObjectTypeEntity.ARTIFICIAL_PLANET
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.ASTEROID) == AstronomicalObjectTypeEntity.ASTEROID
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.ASTEROIDAL_MOON) == AstronomicalObjectTypeEntity.ASTEROIDAL_MOON
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.ASTEROID_BELT) == AstronomicalObjectTypeEntity.ASTEROID_BELT
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.BORG_SPATIAL_DESIGNATION) == AstronomicalObjectTypeEntity.BORG_SPATIAL_DESIGNATION
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.CLUSTER) == AstronomicalObjectTypeEntity.CLUSTER
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.COMET) == AstronomicalObjectTypeEntity.COMET
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.CONSTELLATION) == AstronomicalObjectTypeEntity.CONSTELLATION
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.GALAXY) == AstronomicalObjectTypeEntity.GALAXY
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.MOON) == AstronomicalObjectTypeEntity.MOON
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.M_CLASS_MOON) == AstronomicalObjectTypeEntity.M_CLASS_MOON
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.NEBULA) == AstronomicalObjectTypeEntity.NEBULA
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.PLANETOID) == AstronomicalObjectTypeEntity.PLANETOID
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.D_CLASS_PLANETOID) == AstronomicalObjectTypeEntity.D_CLASS_PLANETOID
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.QUADRANT) == AstronomicalObjectTypeEntity.QUADRANT
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.QUASAR) == AstronomicalObjectTypeEntity.QUASAR
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.STAR) == AstronomicalObjectTypeEntity.STAR
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.STAR_SYSTEM) == AstronomicalObjectTypeEntity.STAR_SYSTEM
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.SECTOR) == AstronomicalObjectTypeEntity.SECTOR
		enumMapper.mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(RestAstronomicalObjectV2Enum.REGION) == AstronomicalObjectTypeEntity.REGION
	}

	void "maps reference type entity enum to soap enum"() {
		expect:
		enumMapper.mapReferenceTypeFromEntityEnumToSoapEnum(null) == null
		enumMapper.mapReferenceTypeFromEntityEnumToSoapEnum(ModelReferenceType.ASIN) == SoapReferenceType.ASIN
		enumMapper.mapReferenceTypeFromEntityEnumToSoapEnum(ModelReferenceType.ISBN) == SoapReferenceType.ISBN
	}

	void "maps reference type entity enum to rest enum"() {
		expect:
		enumMapper.mapReferenceTypeFromEntityEnumToRestEnum(null) == null
		enumMapper.mapReferenceTypeFromEntityEnumToRestEnum(ModelReferenceType.ASIN) == RestReferenceType.ASIN
		enumMapper.mapReferenceTypeFromEntityEnumToRestEnum(ModelReferenceType.ISBN) == RestReferenceType.ISBN
		enumMapper.mapReferenceTypeFromEntityEnumToRestEnum(ModelReferenceType.EAN) == RestReferenceType.EAN
		enumMapper.mapReferenceTypeFromEntityEnumToRestEnum(ModelReferenceType.ISRC) == RestReferenceType.ISRC
	}

	void "maps video release format soap enum to entity enum"() {
		expect:
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(null) == null
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat.SUPER_8) == VideoReleaseFormatEnum.SUPER_8
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat.BETAMAX) == VideoReleaseFormatEnum.BETAMAX
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat.VHS) == VideoReleaseFormatEnum.VHS
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat.CED) == VideoReleaseFormatEnum.CED
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat.LD) == VideoReleaseFormatEnum.LD
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat.VHD) == VideoReleaseFormatEnum.VHD
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat.VCD) == VideoReleaseFormatEnum.VCD
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat.VIDEO_8) == VideoReleaseFormatEnum.VIDEO_8
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat.DVD) == VideoReleaseFormatEnum.DVD
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat.UMD) == VideoReleaseFormatEnum.UMD
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat.HD_DVD) == VideoReleaseFormatEnum.HD_DVD
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat.BLU_RAY) == VideoReleaseFormatEnum.BLU_RAY
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat.BLU_RAY_4K_UHD) == VideoReleaseFormatEnum.BLU_RAY_4_K_UHD
		enumMapper.mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat.DIGITAL_FORMAT) == VideoReleaseFormatEnum.DIGITAL_FORMAT
	}

	void "maps video release entity enum to rest enum"() {
		expect:
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(null) == null
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(VideoReleaseFormat.SUPER_8) == RestVideoReleaseFormat.SUPER_8
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(VideoReleaseFormat.BETAMAX) == RestVideoReleaseFormat.BETAMAX
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(VideoReleaseFormat.VHS) == RestVideoReleaseFormat.VHS
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(VideoReleaseFormat.CED) == RestVideoReleaseFormat.CED
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(VideoReleaseFormat.LD) == RestVideoReleaseFormat.LD
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(VideoReleaseFormat.VHD) == RestVideoReleaseFormat.VHD
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(VideoReleaseFormat.VCD) == RestVideoReleaseFormat.VCD
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(VideoReleaseFormat.VIDEO_8) == RestVideoReleaseFormat.VIDEO_8
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(VideoReleaseFormat.DVD) == RestVideoReleaseFormat.DVD
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(VideoReleaseFormat.UMD) == RestVideoReleaseFormat.UMD
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(VideoReleaseFormat.HD_DVD) == RestVideoReleaseFormat.HD_DVD
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(VideoReleaseFormat.BLU_RAY) == RestVideoReleaseFormat.BLU_RAY
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(VideoReleaseFormat.BLU_RAY_4K_UHD) == RestVideoReleaseFormat.BLU_RAY_4K_UHD
		enumMapper.mapVideoReleaseFormatFromEntityEnumToRestEnum(VideoReleaseFormat.DIGITAL_FORMAT) == RestVideoReleaseFormat.DIGITAL_FORMAT
	}

	void "maps production run unit entity enum to to soap enum"() {
		expect:
		enumMapper.mapProductionRunUnitFromEntityEnumToSoapEnum(null) == null
		enumMapper.mapProductionRunUnitFromEntityEnumToSoapEnum(ProductionRunUnitEntity.BOX) == ProductionRunUnitEnum.BOX
		enumMapper.mapProductionRunUnitFromEntityEnumToSoapEnum(ProductionRunUnitEntity.SET) == ProductionRunUnitEnum.SET
	}

	void "maps production run unit soap enum to entity enum"() {
		expect:
		enumMapper.mapProductionRunUnitFromSoapEnumToEntityEnum(null) == null
		enumMapper.mapProductionRunUnitFromSoapEnumToEntityEnum(ProductionRunUnitEnum.BOX) == ProductionRunUnitEntity.BOX
		enumMapper.mapProductionRunUnitFromSoapEnumToEntityEnum(ProductionRunUnitEnum.SET) == ProductionRunUnitEntity.SET
	}

	void "maps production run unit entity enum to rest enum"() {
		expect:
		enumMapper.mapProductionRunUnitFromEntityEnumToRestEnum(null) == null
		enumMapper.mapProductionRunUnitFromEntityEnumToRestEnum(ProductionRunUnitEntity.BOX) == RestProductionRunUnit.BOX
		enumMapper.mapProductionRunUnitFromEntityEnumToRestEnum(ProductionRunUnitEntity.SET) == RestProductionRunUnit.SET
	}

	void "maps production run unit rest enum to entity enum"() {
		expect:
		enumMapper.mapProductionRunUnitFromRestEnumToEntityEnum(null) == null
		enumMapper.mapProductionRunUnitFromRestEnumToEntityEnum(RestProductionRunUnit.BOX) == ProductionRunUnitEntity.BOX
		enumMapper.mapProductionRunUnitFromRestEnumToEntityEnum(RestProductionRunUnit.SET) == ProductionRunUnitEntity.SET
	}

}
