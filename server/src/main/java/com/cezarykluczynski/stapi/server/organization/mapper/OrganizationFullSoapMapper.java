package com.cezarykluczynski.stapi.server.organization.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFull;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullRequest;
import com.cezarykluczynski.stapi.model.organization.dto.OrganizationRequestDTO;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseSoapMapper.class, RequestSortSoapMapper.class})
public interface OrganizationFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "government", ignore = true)
	@Mapping(target = "intergovernmentalOrganization", ignore = true)
	@Mapping(target = "researchOrganization", ignore = true)
	@Mapping(target = "sportOrganization", ignore = true)
	@Mapping(target = "medicalOrganization", ignore = true)
	@Mapping(target = "militaryOrganization", ignore = true)
	@Mapping(target = "militaryUnit", ignore = true)
	@Mapping(target = "governmentAgency", ignore = true)
	@Mapping(target = "lawEnforcementAgency", ignore = true)
	@Mapping(target = "prisonOrPenalColony", ignore = true)
	@Mapping(target = "mirror", ignore = true)
	@Mapping(target = "alternateReality", ignore = true)
	@Mapping(target = "sort", ignore = true)
	OrganizationRequestDTO mapFull(OrganizationFullRequest organizationFullRequest);

	OrganizationFull mapFull(Organization organization);

}
