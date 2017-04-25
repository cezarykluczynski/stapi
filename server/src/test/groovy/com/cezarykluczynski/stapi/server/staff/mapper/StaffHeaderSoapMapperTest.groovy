package com.cezarykluczynski.stapi.server.staff.mapper

import com.cezarykluczynski.stapi.client.v1.soap.StaffHeader
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.server.common.mapper.AbstractRealWorldPersonMapperTest
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class StaffHeaderSoapMapperTest extends AbstractRealWorldPersonMapperTest {

	private StaffHeaderSoapMapper staffHeaderSoapMapper

	void setup() {
		staffHeaderSoapMapper = Mappers.getMapper(StaffHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Staff staff = new Staff(
				name: NAME,
				uid: UID)

		when:
		StaffHeader staffHeader = staffHeaderSoapMapper.map(Lists.newArrayList(staff))[0]

		then:
		staffHeader.name == NAME
		staffHeader.uid == UID
	}

}
