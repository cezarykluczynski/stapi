package com.cezarykluczynski.stapi.model.occupation.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation_
import com.cezarykluczynski.stapi.model.occupation.query.OccupationQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractOccupationTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class OccupationRepositoryImplTest extends AbstractOccupationTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private OccupationQueryBuilderFactory occupationQueryBuilderFactoryMock

	private OccupationRepositoryImpl occupationRepositoryImpl

	private QueryBuilder<Occupation> occupationQueryBuilder

	private Pageable pageable

	private OccupationRequestDTO occupationRequestDTO

	private Occupation occupation

	private Page page

	void setup() {
		occupationQueryBuilderFactoryMock = Mock()
		occupationRepositoryImpl = new OccupationRepositoryImpl(occupationQueryBuilderFactoryMock)
		occupationQueryBuilder = Mock()
		pageable = Mock()
		occupationRequestDTO = Mock()
		page = Mock()
		occupation = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = occupationRepositoryImpl.findMatching(occupationRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * occupationQueryBuilderFactoryMock.createQueryBuilder(pageable) >> occupationQueryBuilder

		then: 'uid criteria is set'
		1 * occupationRequestDTO.uid >> UID
		1 * occupationQueryBuilder.equal(Occupation_.uid, UID)

		then: 'string criteria are set'
		1 * occupationRequestDTO.name >> NAME
		1 * occupationQueryBuilder.like(Occupation_.name, NAME)

		then: 'boolean criteria are set'
		1 * occupationRequestDTO.legalOccupation >> LEGAL_OCCUPATION
		1 * occupationQueryBuilder.equal(Occupation_.legalOccupation, LEGAL_OCCUPATION)
		1 * occupationRequestDTO.medicalOccupation >> MEDICAL_OCCUPATION
		1 * occupationQueryBuilder.equal(Occupation_.medicalOccupation, MEDICAL_OCCUPATION)
		1 * occupationRequestDTO.scientificOccupation >> SCIENTIFIC_OCCUPATION
		1 * occupationQueryBuilder.equal(Occupation_.scientificOccupation, SCIENTIFIC_OCCUPATION)

		then: 'sort is set'
		1 * occupationRequestDTO.sort >> SORT
		1 * occupationQueryBuilder.setSort(SORT)

		then: 'fetch is performed with true flag'
		1 * occupationQueryBuilder.fetch(Occupation_.characters, true)

		then: 'page is retrieved'
		1 * occupationQueryBuilder.findPage() >> page
		0 * page.content

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = occupationRepositoryImpl.findMatching(occupationRequestDTO, pageable)

		then:
		1 * occupationQueryBuilderFactoryMock.createQueryBuilder(pageable) >> occupationQueryBuilder

		then: 'uid criteria is set to null'
		1 * occupationRequestDTO.uid >> null

		then: 'fetch is performed with false flag'
		1 * occupationQueryBuilder.fetch(Occupation_.characters, false)

		then: 'page is searched for and returned'
		1 * occupationQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(occupation)
		1 * occupation.setCharacters(Sets.newHashSet())
		pageOutput == page
	}

}
