package com.cezarykluczynski.stapi.server.spacecraft_class.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationHeader;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBase;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseRequest;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationHeaderSoapMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortSoapMapper.class})
public interface SpacecraftClassBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(target = "mirror", ignore = true)
	SpacecraftClassRequestDTO mapBase(SpacecraftClassBaseRequest spacecraftClassBaseRequest);

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
				.map(organization -> Mappers.getMapper(OrganizationHeaderSoapMapper.class).map(organization))
				.orElse(null);
	}

}
