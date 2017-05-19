package com.cezarykluczynski.stapi.server.bookCollection.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionBase;
import com.cezarykluczynski.stapi.model.bookCollection.dto.BookCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.bookCollection.entity.BookCollection;
import com.cezarykluczynski.stapi.server.bookCollection.dto.BookCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface BookCollectionBaseRestMapper {

	BookCollectionRequestDTO mapBase(BookCollectionRestBeanParams bookCollectionRestBeanParams);

	BookCollectionBase mapBase(BookCollection bookCollection);

	List<BookCollectionBase> mapBase(List<BookCollection> bookCollectionList);

}
