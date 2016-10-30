package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.soap.GenderEnum
import com.cezarykluczynski.stapi.model.common.entity.Gender as GenderEntity
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class EnumMapperTest extends Specification {

	private EnumMapper enumMapper

	def setup() {
		enumMapper = Mappers.getMapper(EnumMapper)
	}

	def "maps entity enum to dto enum"() {
		expect:
		enumMapper.mapFromEntityEnumToSoapEnum(null) == null
		enumMapper.mapFromEntityEnumToSoapEnum(GenderEntity.F) == GenderEnum.F
		enumMapper.mapFromEntityEnumToSoapEnum(GenderEntity.M) == GenderEnum.M
	}

	def "maps dto enum to entity enum"() {
		expect:
		enumMapper.mapFromSoapEnumToEntityEnum(null) == null
		enumMapper.mapFromSoapEnumToEntityEnum(GenderEnum.F) == GenderEntity.F
		enumMapper.mapFromSoapEnumToEntityEnum(GenderEnum.M) == GenderEntity.M
	}

}
