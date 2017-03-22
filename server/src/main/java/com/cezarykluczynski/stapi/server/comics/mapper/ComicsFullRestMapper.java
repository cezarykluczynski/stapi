package com.cezarykluczynski.stapi.server.comics.mapper;

import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionHeaderRestMapper;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseRestMapper.class, ComicCollectionHeaderRestMapper.class,
		ComicSeriesBaseRestMapper.class, CompanyRestMapper.class, ReferenceRestMapper.class, StaffBaseRestMapper.class})
public interface ComicsFullRestMapper {

	com.cezarykluczynski.stapi.client.v1.rest.model.ComicsFull mapFull(Comics comics);

}
