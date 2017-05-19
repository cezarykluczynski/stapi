package com.cezarykluczynski.stapi.server.organization.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBase;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseRequest;
import com.cezarykluczynski.stapi.model.organization.dto.OrganizationRequestDTO;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface OrganizationBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	OrganizationRequestDTO mapBase(OrganizationBaseRequest organizationBaseRequest);

	OrganizationBase mapBase(Organization organization);

	List<OrganizationBase> mapBase(List<Organization> organizationList);

}
