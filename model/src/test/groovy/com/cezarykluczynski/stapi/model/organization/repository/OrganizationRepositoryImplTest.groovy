package com.cezarykluczynski.stapi.model.organization.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.organization.dto.OrganizationRequestDTO
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.organization.entity.Organization_
import com.cezarykluczynski.stapi.model.organization.query.OrganizationQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractOrganizationTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class OrganizationRepositoryImplTest extends AbstractOrganizationTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private OrganizationQueryBuilderFactory organizationQueryBuilderFactoryMock

	private OrganizationRepositoryImpl organizationRepositoryImpl

	private QueryBuilder<Organization> organizationQueryBuilder

	private Pageable pageable

	private OrganizationRequestDTO organizationRequestDTO

	private Organization organization

	private Page page

	void setup() {
		organizationQueryBuilderFactoryMock = Mock()
		organizationRepositoryImpl = new OrganizationRepositoryImpl(organizationQueryBuilderFactoryMock)
		organizationQueryBuilder = Mock()
		pageable = Mock()
		organizationRequestDTO = Mock()
		organization = Mock()
		page = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = organizationRepositoryImpl.findMatching(organizationRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * organizationQueryBuilderFactoryMock.createQueryBuilder(pageable) >> organizationQueryBuilder

		then: 'uid criteria is set'
		1 * organizationRequestDTO.uid >> UID
		1 * organizationQueryBuilder.equal(Organization_.uid, UID)

		then: 'string criteria are set'
		1 * organizationRequestDTO.name >> NAME
		1 * organizationQueryBuilder.like(Organization_.name, NAME)

		then: 'boolean criteria are set'
		1 * organizationRequestDTO.government >> GOVERNMENT
		1 * organizationQueryBuilder.equal(Organization_.government, GOVERNMENT)
		1 * organizationRequestDTO.intergovernmentalOrganization >> INTERGOVERNMENTAL_ORGANIZATION
		1 * organizationQueryBuilder.equal(Organization_.intergovernmentalOrganization, INTERGOVERNMENTAL_ORGANIZATION)
		1 * organizationRequestDTO.researchOrganization >> RESEARCH_ORGANIZATION
		1 * organizationQueryBuilder.equal(Organization_.researchOrganization, RESEARCH_ORGANIZATION)
		1 * organizationRequestDTO.sportOrganization >> SPORT_ORGANIZATION
		1 * organizationQueryBuilder.equal(Organization_.sportOrganization, SPORT_ORGANIZATION)
		1 * organizationRequestDTO.medicalOrganization >> MEDICAL_ORGANIZATION
		1 * organizationQueryBuilder.equal(Organization_.medicalOrganization, MEDICAL_ORGANIZATION)
		1 * organizationRequestDTO.militaryOrganization >> MILITARY_ORGANIZATION
		1 * organizationQueryBuilder.equal(Organization_.militaryOrganization, MILITARY_ORGANIZATION)
		1 * organizationRequestDTO.militaryUnit >> MILITARY_UNIT
		1 * organizationQueryBuilder.equal(Organization_.militaryUnit, MILITARY_UNIT)
		1 * organizationRequestDTO.governmentAgency >> GOVERNMENT_AGENCY
		1 * organizationQueryBuilder.equal(Organization_.governmentAgency, GOVERNMENT_AGENCY)
		1 * organizationRequestDTO.lawEnforcementAgency >> LAW_ENFORCEMENT_AGENCY
		1 * organizationQueryBuilder.equal(Organization_.lawEnforcementAgency, LAW_ENFORCEMENT_AGENCY)
		1 * organizationRequestDTO.prisonOrPenalColony >> PRISON_OR_PENAL_COLONY
		1 * organizationQueryBuilder.equal(Organization_.prisonOrPenalColony, PRISON_OR_PENAL_COLONY)
		1 * organizationRequestDTO.mirror >> MIRROR
		1 * organizationQueryBuilder.equal(Organization_.mirror, MIRROR)
		1 * organizationRequestDTO.alternateReality >> ALTERNATE_REALITY
		1 * organizationQueryBuilder.equal(Organization_.alternateReality, ALTERNATE_REALITY)

		then: 'sort is set'
		1 * organizationRequestDTO.sort >> SORT
		1 * organizationQueryBuilder.setSort(SORT)

		then: 'fetch is performed with true flag'
		1 * organizationQueryBuilder.fetch(Organization_.characters, true)

		then: 'page is retrieved'
		1 * organizationQueryBuilder.findPage() >> page
		0 * page.content

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = organizationRepositoryImpl.findMatching(organizationRequestDTO, pageable)

		then:
		1 * organizationQueryBuilderFactoryMock.createQueryBuilder(pageable) >> organizationQueryBuilder

		then: 'uid criteria is set to null'
		1 * organizationRequestDTO.uid >> null

		then: 'fetch is performed with false flag'
		1 * organizationQueryBuilder.fetch(Organization_.characters, false)

		then: 'page is searched for and returned'
		1 * organizationQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(organization)
		1 * organization.setCharacters(Sets.newHashSet())
		pageOutput == page
	}

}
