package com.cezarykluczynski.stapi.server.spacecraft_class.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationBase;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassFull;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassV2Full;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassV3Full;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.content_rating.mapper.ContentRatingRestMapper;
import com.cezarykluczynski.stapi.server.genre.mapper.GenreRestMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseRestMapper;
import com.cezarykluczynski.stapi.server.platform.mapper.PlatformRestMapper;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft_type.mapper.SpacecraftTypeRestMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Comparator;
import java.util.Set;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseRestMapper.class, DateMapper.class, EnumMapper.class, PlatformRestMapper.class,
		GenreRestMapper.class, ContentRatingRestMapper.class, ReferenceRestMapper.class, SpacecraftBaseRestMapper.class,
		SpacecraftTypeRestMapper.class})
public interface SpacecraftClassFullRestMapper {

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
				.map(organization -> Mappers.getMapper(OrganizationBaseRestMapper.class).mapBase(organization))
				.orElse(null);
	}

	SpacecraftClassV2Full mapV2Full(SpacecraftClass spacecraftClass);

	SpacecraftClassV3Full mapV3Full(SpacecraftClass spacecraftClass);

}
