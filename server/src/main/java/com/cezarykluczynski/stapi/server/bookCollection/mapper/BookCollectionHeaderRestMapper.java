package com.cezarykluczynski.stapi.server.bookCollection.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionHeader;
import com.cezarykluczynski.stapi.model.bookCollection.entity.BookCollection;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookCollectionHeaderRestMapper {

	BookCollectionHeader map(BookCollection bookCollection);

	List<BookCollectionHeader> map(List<BookCollection> bookCollection);

}
