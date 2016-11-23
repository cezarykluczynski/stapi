package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.Gender as RestGenderEnum
import com.cezarykluczynski.stapi.client.v1.soap.GenderEnum as SoapGenderEnum
import com.cezarykluczynski.stapi.model.common.entity.Gender as GenderEntity
import com.cezarykluczynski.stapi.model.common.entity.MaritalStatus as MaritalStatusEntity
import com.cezarykluczynski.stapi.client.v1.soap.MaritalStatusEnum as SoapMaritalStatusEnum
import com.cezarykluczynski.stapi.client.v1.soap.BloodTypeEnum as SoapBloodTypeEnum
import com.cezarykluczynski.stapi.model.common.entity.BloodType as BloodTypeEntity
import com.cezarykluczynski.stapi.client.v1.rest.model.MaritalStatus as RestMaritalStatusEnum
import com.cezarykluczynski.stapi.client.v1.rest.model.BloodType as RestBloodTypeEnum
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class EnumMapperTest extends Specification {

	private EnumMapper enumMapper

	def setup() {
		enumMapper = Mappers.getMapper(EnumMapper)
	}

	def "maps gender entity enum to soap enum"() {
		expect:
		enumMapper.mapGenderFromEntityEnumToSoapEnum(null) == null
		enumMapper.mapGenderFromEntityEnumToSoapEnum(GenderEntity.F) == SoapGenderEnum.F
		enumMapper.mapGenderFromEntityEnumToSoapEnum(GenderEntity.M) == SoapGenderEnum.M
	}

	def "maps gender soap dto enum to entity enum"() {
		expect:
		enumMapper.mapGenderFromSoapEnumToEntityEnum(null) == null
		enumMapper.mapGenderFromSoapEnumToEntityEnum(SoapGenderEnum.F) == GenderEntity.F
		enumMapper.mapGenderFromSoapEnumToEntityEnum(SoapGenderEnum.M) == GenderEntity.M
	}

	def "maps gender entity enum to rest enum"() {
		expect:
		enumMapper.mapGenderFromEntityEnumToRestEnum(null) == null
		enumMapper.mapGenderFromEntityEnumToRestEnum(GenderEntity.F) == RestGenderEnum.F
		enumMapper.mapGenderFromEntityEnumToRestEnum(GenderEntity.M) == RestGenderEnum.M
	}

	def "maps gender rest dto enum to entity enum"() {
		expect:
		enumMapper.mapGenderFromRestEnumToEntityEnum(null) == null
		enumMapper.mapGenderFromRestEnumToEntityEnum(RestGenderEnum.F) == GenderEntity.F
		enumMapper.mapGenderFromRestEnumToEntityEnum(RestGenderEnum.M) == GenderEntity.M
	}

	def "maps maritalStatus entity enum to soap enum"() {
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

	def "maps maritalStatus soap enum to entity enum"() {
		expect:
		enumMapper.mapMaritalStatusFromSoapEnumToEntityEnum(null) == null
		enumMapper.mapMaritalStatusFromSoapEnumToEntityEnum(SoapMaritalStatusEnum.SINGLE) == MaritalStatusEntity.SINGLE
		enumMapper.mapMaritalStatusFromSoapEnumToEntityEnum(SoapMaritalStatusEnum.ENGAGED) == MaritalStatusEntity.ENGAGED
		enumMapper.mapMaritalStatusFromSoapEnumToEntityEnum(SoapMaritalStatusEnum.MARRIED) == MaritalStatusEntity.MARRIED
		enumMapper.mapMaritalStatusFromSoapEnumToEntityEnum(SoapMaritalStatusEnum.DIVORCED) == MaritalStatusEntity.DIVORCED
		enumMapper.mapMaritalStatusFromSoapEnumToEntityEnum(SoapMaritalStatusEnum.REMARRIED) == MaritalStatusEntity.REMARRIED
		enumMapper.mapMaritalStatusFromSoapEnumToEntityEnum(SoapMaritalStatusEnum.SEPARATED) == MaritalStatusEntity.SEPARATED
		enumMapper.mapMaritalStatusFromSoapEnumToEntityEnum(SoapMaritalStatusEnum.WIDOWED) == MaritalStatusEntity.WIDOWED
		enumMapper.mapMaritalStatusFromSoapEnumToEntityEnum(SoapMaritalStatusEnum.CAPTAINS_WOMAN) == MaritalStatusEntity.CAPTAINS_WOMAN
	}

	def "maps maritalStatus entity enum to rest enum"() {
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

	def "maps maritalStatus rest enum to entity enum"() {
		expect:
		enumMapper.mapMaritalStatusFromRestEnumToEntityEnum(null) == null
		enumMapper.mapMaritalStatusFromRestEnumToEntityEnum(RestMaritalStatusEnum.SINGLE) == MaritalStatusEntity.SINGLE
		enumMapper.mapMaritalStatusFromRestEnumToEntityEnum(RestMaritalStatusEnum.ENGAGED) == MaritalStatusEntity.ENGAGED
		enumMapper.mapMaritalStatusFromRestEnumToEntityEnum(RestMaritalStatusEnum.MARRIED) == MaritalStatusEntity.MARRIED
		enumMapper.mapMaritalStatusFromRestEnumToEntityEnum(RestMaritalStatusEnum.DIVORCED) == MaritalStatusEntity.DIVORCED
		enumMapper.mapMaritalStatusFromRestEnumToEntityEnum(RestMaritalStatusEnum.REMARRIED) == MaritalStatusEntity.REMARRIED
		enumMapper.mapMaritalStatusFromRestEnumToEntityEnum(RestMaritalStatusEnum.SEPARATED) == MaritalStatusEntity.SEPARATED
		enumMapper.mapMaritalStatusFromRestEnumToEntityEnum(RestMaritalStatusEnum.WIDOWED) == MaritalStatusEntity.WIDOWED
		enumMapper.mapMaritalStatusFromRestEnumToEntityEnum(RestMaritalStatusEnum.CAPTAINS_WOMAN) == MaritalStatusEntity.CAPTAINS_WOMAN
	}

	def "maps blood type entity enum to soap enum"() {
		expect:
		enumMapper.mapBloodTypeFromEntityEnumToSoapEnum(null) == null
		enumMapper.mapBloodTypeFromEntityEnumToSoapEnum(BloodTypeEntity.B_NEGATIVE) == SoapBloodTypeEnum.B_NEGATIVE
		enumMapper.mapBloodTypeFromEntityEnumToSoapEnum(BloodTypeEntity.O_NEGATIVE) == SoapBloodTypeEnum.O_NEGATIVE
		enumMapper.mapBloodTypeFromEntityEnumToSoapEnum(BloodTypeEntity.T_NEGATIVE) == SoapBloodTypeEnum.T_NEGATIVE
	}

	def "maps gender soap enum to entity enum"() {
		expect:
		enumMapper.mapBloodTypeFromSoapEnumToEntityEnum(null) == null
		enumMapper.mapBloodTypeFromSoapEnumToEntityEnum(SoapBloodTypeEnum.B_NEGATIVE) == BloodTypeEntity.B_NEGATIVE
		enumMapper.mapBloodTypeFromSoapEnumToEntityEnum(SoapBloodTypeEnum.O_NEGATIVE) == BloodTypeEntity.O_NEGATIVE
		enumMapper.mapBloodTypeFromSoapEnumToEntityEnum(SoapBloodTypeEnum.T_NEGATIVE) == BloodTypeEntity.T_NEGATIVE
	}

	def "maps blood type entity enum to rest enum"() {
		expect:
		enumMapper.mapBloodTypeFromEntityEnumToRestEnum(null) == null
		enumMapper.mapBloodTypeFromEntityEnumToRestEnum(BloodTypeEntity.B_NEGATIVE) == RestBloodTypeEnum.B_NEGATIVE
		enumMapper.mapBloodTypeFromEntityEnumToRestEnum(BloodTypeEntity.O_NEGATIVE) == RestBloodTypeEnum.O_NEGATIVE
		enumMapper.mapBloodTypeFromEntityEnumToRestEnum(BloodTypeEntity.T_NEGATIVE) == RestBloodTypeEnum.T_NEGATIVE
	}

	def "maps gender rest enum to entity enum"() {
		expect:
		enumMapper.mapBloodTypeFromRestEnumToEntityEnum(null) == null
		enumMapper.mapBloodTypeFromRestEnumToEntityEnum(RestBloodTypeEnum.B_NEGATIVE) == BloodTypeEntity.B_NEGATIVE
		enumMapper.mapBloodTypeFromRestEnumToEntityEnum(RestBloodTypeEnum.O_NEGATIVE) == BloodTypeEntity.O_NEGATIVE
		enumMapper.mapBloodTypeFromRestEnumToEntityEnum(RestBloodTypeEnum.T_NEGATIVE) == BloodTypeEntity.T_NEGATIVE
	}

}
