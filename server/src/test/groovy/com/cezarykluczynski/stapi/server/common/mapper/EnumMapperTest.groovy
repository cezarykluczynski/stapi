package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.Gender as RestGenderEnum
import com.cezarykluczynski.stapi.client.v1.soap.GenderEnum as SoapGenderEnum
import com.cezarykluczynski.stapi.model.common.entity.Gender as GenderEntity
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class EnumMapperTest extends Specification {

	private EnumMapper enumMapper

	def setup() {
		enumMapper = Mappers.getMapper(EnumMapper)
	}

	def "maps entity enum to soap dto enum"() {
		expect:
		enumMapper.mapFromEntityEnumToSoapEnum(null) == null
		enumMapper.mapFromEntityEnumToSoapEnum(GenderEntity.F) == SoapGenderEnum.F
		enumMapper.mapFromEntityEnumToSoapEnum(GenderEntity.M) == SoapGenderEnum.M
	}

	def "maps soap dto enum to entity enum"() {
		expect:
		enumMapper.mapFromSoapEnumToEntityEnum(null) == null
		enumMapper.mapFromSoapEnumToEntityEnum(SoapGenderEnum.F) == GenderEntity.F
		enumMapper.mapFromSoapEnumToEntityEnum(SoapGenderEnum.M) == GenderEntity.M
	}

	def "maps entity enum to rest dto enum"() {
		expect:
		enumMapper.mapFromEntityEnumToRestEnum(null) == null
		enumMapper.mapFromEntityEnumToRestEnum(GenderEntity.F) == RestGenderEnum.F
		enumMapper.mapFromEntityEnumToRestEnum(GenderEntity.M) == RestGenderEnum.M
	}

	def "maps rest dto enum to entity enum"() {
		expect:
		enumMapper.mapFromRestEnumToEntityEnum(null) == null
		enumMapper.mapFromRestEnumToEntityEnum(RestGenderEnum.F) == GenderEntity.F
		enumMapper.mapFromRestEnumToEntityEnum(RestGenderEnum.M) == GenderEntity.M
	}

}
