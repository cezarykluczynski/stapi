package com.cezarykluczynski.stapi.server.spacecraft_class.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFull;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullRequest;
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.content_rating.mapper.ContentRatingSoapMapper;
import com.cezarykluczynski.stapi.server.genre.mapper.GenreSoapMapper;
import com.cezarykluczynski.stapi.server.platform.mapper.PlatformSoapMapper;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceSoapMapper;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseSoapMapper;
import com.cezarykluczynski.stapi.server.spacecraft_type.mapper.SpacecraftTypeSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseSoapMapper.class, DateMapper.class, EnumMapper.class, PlatformSoapMapper.class,
		GenreSoapMapper.class, ContentRatingSoapMapper.class, ReferenceSoapMapper.class, SpacecraftBaseSoapMapper.class,
		SpacecraftTypeSoapMapper.class})
public interface SpacecraftClassFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "warpCapable", ignore = true)
	@Mapping(target = "alternateReality", ignore = true)
	@Mapping(target = "sort", ignore = true)
	SpacecraftClassRequestDTO mapFull(SpacecraftClassFullRequest spacecraftClassFullRequest);

	SpacecraftClassFull mapFull(SpacecraftClass spacecraftClass);

}
