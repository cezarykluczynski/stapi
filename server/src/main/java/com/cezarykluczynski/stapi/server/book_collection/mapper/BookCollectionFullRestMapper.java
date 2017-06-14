package com.cezarykluczynski.stapi.server.book_collection.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionFull;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseRestMapper.class, ComicsBaseRestMapper.class, ComicSeriesBaseRestMapper.class,
		CompanyBaseRestMapper.class, ReferenceRestMapper.class, StaffBaseRestMapper.class})
public interface BookCollectionFullRestMapper {

	BookCollectionFull mapFull(BookCollection bookCollection);

}
