package com.cezarykluczynski.stapi.model.organization.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.organization.dto.OrganizationRequestDTO;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.organization.entity.Organization_;
import com.cezarykluczynski.stapi.model.organization.query.OrganizationQueryBuilderFactory;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationRepositoryImpl extends AbstractRepositoryImpl<Organization> implements OrganizationRepositoryCustom {

	private final OrganizationQueryBuilderFactory organizationQueryBuilderFactory;

	public OrganizationRepositoryImpl(OrganizationQueryBuilderFactory organizationQueryBuilderFactory) {
		this.organizationQueryBuilderFactory = organizationQueryBuilderFactory;
	}

	@Override
	public Page<Organization> findMatching(OrganizationRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Organization> organizationQueryBuilder = organizationQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		organizationQueryBuilder.equal(Organization_.uid, uid);
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
		organizationQueryBuilder.fetch(Organization_.characters, doFetch);

		Page<Organization> organizationPage = organizationQueryBuilder.findPage();
		clearProxies(organizationPage, !doFetch);
		return organizationPage;
	}

	@Override
	protected void clearProxies(Page<Organization> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(title -> {
			title.setCharacters(Sets.newHashSet());
		});
	}

}
