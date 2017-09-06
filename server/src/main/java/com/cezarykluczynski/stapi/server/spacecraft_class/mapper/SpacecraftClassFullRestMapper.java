package com.cezarykluczynski.stapi.server.spacecraft_class.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassFull;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.content_rating.mapper.ContentRatingRestMapper;
import com.cezarykluczynski.stapi.server.genre.mapper.GenreRestMapper;
import com.cezarykluczynski.stapi.server.platform.mapper.PlatformRestMapper;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft_type.mapper.SpacecraftTypeRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseRestMapper.class, DateMapper.class, EnumMapper.class, PlatformRestMapper.class,
		GenreRestMapper.class, ContentRatingRestMapper.class, ReferenceRestMapper.class, SpacecraftBaseRestMapper.class,
		SpacecraftTypeRestMapper.class})
public interface SpacecraftClassFullRestMapper {

	SpacecraftClassFull mapFull(SpacecraftClass spacecraftClass);

}
