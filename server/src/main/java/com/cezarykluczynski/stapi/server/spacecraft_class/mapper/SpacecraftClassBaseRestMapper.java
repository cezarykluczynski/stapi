package com.cezarykluczynski.stapi.server.spacecraft_class.mapper;

import com.cezarykluczynski.stapi.client.rest.model.OrganizationHeader;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassBase;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2Base;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationHeaderRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassRestBeanParams;
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassV2RestBeanParams;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesHeaderRestMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Mapper(config = MapstructConfiguration.class, uses = {OrganizationHeaderRestMapper.class, RequestSortRestMapper.class,
		SpeciesHeaderRestMapper.class})
public interface SpacecraftClassBaseRestMapper {

	@Mapping(target = "mirror", ignore = true)
	SpacecraftClassRequestDTO mapBase(SpacecraftClassRestBeanParams spacecraftClassRestBeanParams);

	@Mapping(target = "owner", ignore = true)
	@Mapping(target = "operator", ignore = true)
	@Mapping(target = "affiliation", ignore = true)
	SpacecraftClassBase mapBase(SpacecraftClass spacecraftClass);

	List<SpacecraftClassBase> mapBase(List<SpacecraftClass> spacecraftClassList);

	@AfterMapping
	default void mapBase(SpacecraftClass spacecraftClass, @MappingTarget SpacecraftClassBase spacecraftClassBase) {
		if (!spacecraftClass.getOwners().isEmpty()) {
			spacecraftClassBase.setOwner(getFirst(spacecraftClass.getOwners()));
		}
		if (!spacecraftClass.getOperators().isEmpty()) {
			spacecraftClassBase.setOperator(getFirst(spacecraftClass.getOperators()));
		}
		if (!spacecraftClass.getAffiliations().isEmpty()) {
			spacecraftClassBase.setAffiliation(getFirst(spacecraftClass.getAffiliations()));
		}
	}

	default OrganizationHeader getFirst(Set<Organization> organizations) {
		return organizations.stream()
				.min(Comparator.comparing(Organization::getName))
				.map(organization -> Mappers.getMapper(OrganizationHeaderRestMapper.class).map(organization))
				.orElse(null);
	}

	SpacecraftClassRequestDTO mapV2Base(SpacecraftClassV2RestBeanParams spacecraftClassV2RestBeanParams);

	SpacecraftClassV2Base mapV2Base(SpacecraftClass spacecraftClass);

	List<SpacecraftClassV2Base> mapV2Base(List<SpacecraftClass> spacecraftClassList);

}
