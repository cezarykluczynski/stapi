package com.cezarykluczynski.stapi.server.book.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookHeader;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookHeaderRestMapper {

	BookHeader map(Book book);

	List<BookHeader> map(List<Book> book);

}
