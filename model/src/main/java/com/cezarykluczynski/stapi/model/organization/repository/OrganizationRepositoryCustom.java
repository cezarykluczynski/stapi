package com.cezarykluczynski.stapi.model.organization.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.organization.dto.OrganizationRequestDTO;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;

public interface OrganizationRepositoryCustom extends CriteriaMatcher<OrganizationRequestDTO, Organization> {
}
