package com.cezarykluczynski.stapi.server.content_rating.mapper;

import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class})
public interface ContentRatingSoapMapper {

	@Mapping(target = "uid", ignore = true)
	com.cezarykluczynski.stapi.client.v1.soap.ContentRating map(ContentRating contentRating);

}
