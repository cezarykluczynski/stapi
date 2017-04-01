package com.cezarykluczynski.stapi.server.organization.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationHeader;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationHeaderRestMapper {

	OrganizationHeader map(Organization organization);

	List<OrganizationHeader> map(List<Organization> organization);

}
