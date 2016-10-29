package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.soap.GenderEnum
import com.cezarykluczynski.stapi.model.common.entity.Gender as GenderEntity
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class EnumMapperTest extends Specification {

	private EnumMapper enumMapper

	def setup() {
		enumMapper = Mappers.getMapper(EnumMapper.class)
	}

	def "maps entity to dto"() {
		expect:
		enumMapper.mapFromEntityToSoapEnum(null) == null
		enumMapper.mapFromEntityToSoapEnum(GenderEntity.F) == GenderEnum.F
		enumMapper.mapFromEntityToSoapEnum(GenderEntity.M) == GenderEnum.M
	}

}
