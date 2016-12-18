package com.cezarykluczynski.stapi.server.staff.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.StaffHeader
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.server.common.mapper.AbstractRealWorldPersonMapperTest
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class StaffHeaderRestMapperTest extends AbstractRealWorldPersonMapperTest {

	private StaffHeaderRestMapper staffHeaderRestMapper

	def setup() {
		staffHeaderRestMapper = Mappers.getMapper(StaffHeaderRestMapper)
	}

	def "maps DB entity to REST header"() {
		given:
		Staff staff = new Staff(
				name: NAME,
				guid: GUID)

		when:
		StaffHeader staffHeader = staffHeaderRestMapper.map(Lists.newArrayList(staff))[0]

		then:
		staffHeader.name == NAME
		staffHeader.guid == GUID
	}

}
