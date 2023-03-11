package com.cezarykluczynski.stapi.server.book.mapper;

import com.cezarykluczynski.stapi.client.rest.model.BookFull;
import com.cezarykluczynski.stapi.client.rest.model.BookV2Full;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionBaseRestMapper;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseRestMapper.class, ComicCollectionBaseRestMapper.class,
		ComicSeriesBaseRestMapper.class, CompanyBaseRestMapper.class, ReferenceRestMapper.class, StaffBaseRestMapper.class,
		BookSeriesBaseRestMapper.class})
public interface BookFullRestMapper {

	@Mapping(source = "EBook", target = "eBook")
	BookFull mapFull(Book book);

	@Mapping(source = "EBook", target = "eBook")
	BookV2Full mapV2Full(Book book);

}
