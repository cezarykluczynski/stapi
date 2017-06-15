package com.cezarykluczynski.stapi.server.comics.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsFull;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionBaseRestMapper;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseRestMapper.class, ComicCollectionBaseRestMapper.class,
		ComicSeriesBaseRestMapper.class, CompanyBaseRestMapper.class, ReferenceRestMapper.class, StaffBaseRestMapper.class})
public interface ComicsFullRestMapper {

	ComicsFull mapFull(Comics comics);

}
