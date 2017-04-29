package com.cezarykluczynski.stapi.server.book.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.BookBase;
import com.cezarykluczynski.stapi.client.v1.soap.BookBaseRequest;
import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface BookBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "publishedYear.from", target = "publishedYearFrom")
	@Mapping(source = "publishedYear.to", target = "publishedYearTo")
	@Mapping(source = "numberOfPages.from", target = "numberOfPagesFrom")
	@Mapping(source = "numberOfPages.to", target = "numberOfPagesTo")
	@Mapping(source = "stardate.from", target = "stardateFrom")
	@Mapping(source = "stardate.to", target = "stardateTo")
	@Mapping(source = "year.from", target = "yearFrom")
	@Mapping(source = "year.to", target = "yearTo")
	@Mapping(source = "audiobookPublishedYear.from", target = "audiobookPublishedYearFrom")
	@Mapping(source = "audiobookPublishedYear.to", target = "audiobookPublishedYearTo")
	@Mapping(source = "audiobookRunTime.from", target = "audiobookRunTimeFrom")
	@Mapping(source = "audiobookRunTime.to", target = "audiobookRunTimeTo")
	BookRequestDTO mapBase(BookBaseRequest bookBaseRequest);

	BookBase mapBase(Book book);

	List<BookBase> mapBase(List<Book> bookList);

}
