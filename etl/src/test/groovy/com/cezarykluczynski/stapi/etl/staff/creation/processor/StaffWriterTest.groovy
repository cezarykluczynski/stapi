package com.cezarykluczynski.stapi.etl.staff.creation.processor

import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class StaffWriterTest extends Specification {

	private static final Long PAGE_ID = 1L

	private StaffRepository staffRepositoryMock

	private StaffWriter staffWriterMock

	void setup() {
		staffRepositoryMock = Mock()
		staffWriterMock = new StaffWriter(staffRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Staff staff = new Staff(page: new Page(pageId: PAGE_ID))
		List<Staff> staffList = Lists.newArrayList(staff)

		when:
		staffWriterMock.write(new Chunk(staffList))

		then:
		1 * staffRepositoryMock.saveAll(staffList)
		0 * _
	}

}
