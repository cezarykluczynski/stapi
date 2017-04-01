package com.cezarykluczynski.stapi.model.organization.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long>, PageAwareRepository<Organization>, OrganizationRepositoryCustom {
}
