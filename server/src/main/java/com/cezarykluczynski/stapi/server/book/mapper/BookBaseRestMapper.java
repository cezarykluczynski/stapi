package com.cezarykluczynski.stapi.server.book.mapper;

import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.server.book.dto.BookRestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface BookBaseRestMapper {

	BookRequestDTO mapBase(BookRestBeanParams bookRestBeanParams);

	com.cezarykluczynski.stapi.client.v1.rest.model.BookBase mapBase(Book book);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.BookBase> mapBase(List<Book> bookList);

}
