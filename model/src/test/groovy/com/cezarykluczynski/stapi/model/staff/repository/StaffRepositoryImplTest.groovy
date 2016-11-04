package com.cezarykluczynski.stapi.model.staff.repository

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.model.staff.query.StaffQueryBuiler
import com.cezarykluczynski.stapi.util.AbstractStaffTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class StaffRepositoryImplTest extends AbstractStaffTest {

	private StaffQueryBuiler staffQueryBuilerMock

	private StaffRepositoryImpl staffRepositoryImpl

	private QueryBuilder<Staff> staffQueryBuilder

	private Pageable pageable

	private StaffRequestDTO staffRequestDTO

	private Page page

	def setup() {
		staffQueryBuilerMock = Mock(StaffQueryBuiler)
		staffRepositoryImpl = new StaffRepositoryImpl(staffQueryBuilerMock)
		staffQueryBuilder = Mock(QueryBuilder)
		pageable = Mock(Pageable)
		staffRequestDTO = Mock(StaffRequestDTO)
		page = Mock(Page)
	}

	def "query is built and performed"() {
		when:
		Page pageOutput = staffRepositoryImpl.findMatching(staffRequestDTO, pageable)

		then:
		1 * staffQueryBuilerMock.createQueryBuilder(pageable) >> staffQueryBuilder

		then: 'string criteria are set'
		1 * staffRequestDTO.getName() >> NAME
		1 * staffQueryBuilder.like("name", NAME)
		1 * staffRequestDTO.getBirthName() >> BIRTH_NAME
		1 * staffQueryBuilder.like("birthName", BIRTH_NAME)
		1 * staffRequestDTO.getPlaceOfBirth() >> PLACE_OF_BIRTH
		1 * staffQueryBuilder.like("placeOfBirth", PLACE_OF_BIRTH)
		1 * staffRequestDTO.getPlaceOfDeath() >> PLACE_OF_DEATH
		1 * staffQueryBuilder.like("placeOfDeath", PLACE_OF_DEATH)

		then: 'date criteria are set'
		1 * staffRequestDTO.getDateOfBirthFrom() >> DATE_OF_BIRTH_FROM
		1 * staffRequestDTO.getDateOfBirthTo() >> DATE_OF_BIRTH_TO
		1 * staffQueryBuilder.between("dateOfBirth", DATE_OF_BIRTH_FROM, DATE_OF_BIRTH_TO)
		1 * staffRequestDTO.getDateOfDeathFrom() >> DATE_OF_DEATH_FROM
		1 * staffRequestDTO.getDateOfDeathTo() >> DATE_OF_DEATH_TO
		1 * staffQueryBuilder.between("dateOfDeath", DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO)

		then: 'boolean criteria are set'
		true // TODO

		then: 'page is searched for and returned'
		1 * staffQueryBuilder.findPage() >> page
		pageOutput == page
	}

}
