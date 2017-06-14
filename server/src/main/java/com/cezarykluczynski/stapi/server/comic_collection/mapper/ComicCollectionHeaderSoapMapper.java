package com.cezarykluczynski.stapi.server.comic_collection.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionHeader;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComicCollectionHeaderSoapMapper {

	ComicCollectionHeader map(ComicCollection comicCollection);

	List<ComicCollectionHeader> map(List<ComicCollection> comicCollection);

}
