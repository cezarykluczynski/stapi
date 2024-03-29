package com.cezarykluczynski.stapi.server.content_rating.mapper;

import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class})
public interface ContentRatingRestMapper {

	com.cezarykluczynski.stapi.client.rest.model.ContentRating map(ContentRating contentRating);

}
