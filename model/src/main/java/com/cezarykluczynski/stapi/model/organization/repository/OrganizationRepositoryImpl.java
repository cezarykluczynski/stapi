package com.cezarykluczynski.stapi.model.organization.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.organization.dto.OrganizationRequestDTO;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.organization.entity.Organization_;
import com.cezarykluczynski.stapi.model.organization.query.OrganizationQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class OrganizationRepositoryImpl implements OrganizationRepositoryCustom {

	private OrganizationQueryBuilderFactory organizationQueryBuilderFactory;

	@Inject
	public OrganizationRepositoryImpl(OrganizationQueryBuilderFactory organizationQueryBuilderFactory) {
		this.organizationQueryBuilderFactory = organizationQueryBuilderFactory;
	}

	@Override
	public Page<Organization> findMatching(OrganizationRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Organization> organizationQueryBuilder = organizationQueryBuilderFactory.createQueryBuilder(pageable);

		organizationQueryBuilder.equal(Organization_.uid, criteria.getUid());
		organizationQueryBuilder.like(Organization_.name, criteria.getName());
		organizationQueryBuilder.equal(Organization_.government, criteria.getGovernment());
		organizationQueryBuilder.equal(Organization_.intergovernmentalOrganization, criteria.getIntergovernmentalOrganization());
		organizationQueryBuilder.equal(Organization_.researchOrganization, criteria.getResearchOrganization());
		organizationQueryBuilder.equal(Organization_.sportOrganization, criteria.getSportOrganization());
		organizationQueryBuilder.equal(Organization_.medicalOrganization, criteria.getMedicalOrganization());
		organizationQueryBuilder.equal(Organization_.militaryOrganization, criteria.getMilitaryOrganization());
		organizationQueryBuilder.equal(Organization_.militaryUnit, criteria.getMilitaryUnit());
		organizationQueryBuilder.equal(Organization_.governmentAgency, criteria.getGovernmentAgency());
		organizationQueryBuilder.equal(Organization_.lawEnforcementAgency, criteria.getLawEnforcementAgency());
		organizationQueryBuilder.equal(Organization_.prisonOrPenalColony, criteria.getPrisonOrPenalColony());
		organizationQueryBuilder.equal(Organization_.mirror, criteria.getMirror());
		organizationQueryBuilder.equal(Organization_.alternateReality, criteria.getAlternateReality());
		organizationQueryBuilder.setSort(criteria.getSort());

		return organizationQueryBuilder.findPage();
	}

}
