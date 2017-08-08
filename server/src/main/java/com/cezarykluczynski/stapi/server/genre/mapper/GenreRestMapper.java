package com.cezarykluczynski.stapi.server.genre.mapper;

import com.cezarykluczynski.stapi.model.genre.entity.Genre;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface GenreRestMapper {

	com.cezarykluczynski.stapi.client.v1.rest.model.Genre map(Genre genre);

}

