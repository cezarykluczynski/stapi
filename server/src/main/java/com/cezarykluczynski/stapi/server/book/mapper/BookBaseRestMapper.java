package com.cezarykluczynski.stapi.server.book.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookBase;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2Base;
import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.server.book.dto.BookRestBeanParams;
import com.cezarykluczynski.stapi.server.book.dto.BookV2RestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface BookBaseRestMapper {

	@Mapping(target = "unauthorizedPublication", ignore = true)
	BookRequestDTO mapBase(BookRestBeanParams bookRestBeanParams);

	@Mapping(source = "EBook", target = "eBook")
	BookBase mapBase(Book book);

	List<BookBase> mapBase(List<Book> bookList);

	BookRequestDTO mapV2Base(BookV2RestBeanParams bookV2RestBeanParams);

	@Mapping(source = "EBook", target = "eBook")
	BookV2Base mapV2Base(Book book);

	List<BookV2Base> mapV2Base(List<Book> bookList);

}
