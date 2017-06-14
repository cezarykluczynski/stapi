package com.cezarykluczynski.stapi.server.book_collection.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBase;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseRequest;
import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface BookCollectionBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "publishedYear.from", target = "publishedYearFrom")
	@Mapping(source = "publishedYear.to", target = "publishedYearTo")
	@Mapping(source = "numberOfPages.from", target = "numberOfPagesFrom")
	@Mapping(source = "numberOfPages.to", target = "numberOfPagesTo")
	@Mapping(source = "stardate.from", target = "stardateFrom")
	@Mapping(source = "stardate.to", target = "stardateTo")
	@Mapping(source = "year.from", target = "yearFrom")
	@Mapping(source = "year.to", target = "yearTo")
	BookCollectionRequestDTO mapBase(BookCollectionBaseRequest bookCollectionBaseRequest);

	BookCollectionBase mapBase(BookCollection bookCollection);

	List<BookCollectionBase> mapBase(List<BookCollection> bookCollectionList);

}
