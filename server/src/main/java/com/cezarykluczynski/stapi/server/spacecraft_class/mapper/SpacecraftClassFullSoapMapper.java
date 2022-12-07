package com.cezarykluczynski.stapi.server.spacecraft_class.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBase;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFull;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullRequest;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.content_rating.mapper.ContentRatingSoapMapper;
import com.cezarykluczynski.stapi.server.genre.mapper.GenreSoapMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.platform.mapper.PlatformSoapMapper;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceSoapMapper;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseSoapMapper;
import com.cezarykluczynski.stapi.server.spacecraft_type.mapper.SpacecraftTypeSoapMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Comparator;
import java.util.Set;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseSoapMapper.class, DateMapper.class, EnumMapper.class, PlatformSoapMapper.class,
		GenreSoapMapper.class, ContentRatingSoapMapper.class, ReferenceSoapMapper.class, SpacecraftBaseSoapMapper.class,
		SpacecraftTypeSoapMapper.class})
public interface SpacecraftClassFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "warpCapable", ignore = true)
	@Mapping(target = "mirror", ignore = true)
	@Mapping(target = "alternateReality", ignore = true)
	@Mapping(target = "sort", ignore = true)
	SpacecraftClassRequestDTO mapFull(SpacecraftClassFullRequest spacecraftClassFullRequest);

	@Mapping(target = "owner", ignore = true)
	@Mapping(target = "operator", ignore = true)
	@Mapping(target = "affiliation", ignore = true)
	SpacecraftClassFull mapFull(SpacecraftClass spacecraftClass);

	@AfterMapping
	default void mapBase(SpacecraftClass spacecraftClass, @MappingTarget SpacecraftClassFull spacecraftClassFull) {
		if (!spacecraftClass.getOwners().isEmpty()) {
			spacecraftClassFull.setOwner(getFirst(spacecraftClass.getOwners()));
		}
		if (!spacecraftClass.getOperators().isEmpty()) {
			spacecraftClassFull.setOperator(getFirst(spacecraftClass.getOperators()));
		}
		if (!spacecraftClass.getAffiliations().isEmpty()) {
			spacecraftClassFull.setAffiliation(getFirst(spacecraftClass.getAffiliations()));
		}
	}

	default OrganizationBase getFirst(Set<Organization> organizations) {
		return organizations.stream()
				.min(Comparator.comparing(Organization::getName))
				.map(organization -> Mappers.getMapper(OrganizationBaseSoapMapper.class).mapBase(organization))
				.orElse(null);
	}


}
