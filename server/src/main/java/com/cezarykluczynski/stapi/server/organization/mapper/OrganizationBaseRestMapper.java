package com.cezarykluczynski.stapi.server.organization.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationBase;
import com.cezarykluczynski.stapi.model.organization.dto.OrganizationRequestDTO;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.organization.dto.OrganizationRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface OrganizationBaseRestMapper {

	OrganizationRequestDTO mapBase(OrganizationRestBeanParams organizationRestBeanParams);

	OrganizationBase mapBase(Organization organization);

	List<OrganizationBase> mapBase(List<Organization> organizationList);

}
