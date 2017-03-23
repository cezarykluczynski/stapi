package com.cezarykluczynski.stapi.server.comicCollection.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionBase;
import com.cezarykluczynski.stapi.model.comicCollection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.server.comicCollection.dto.ComicCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface ComicCollectionBaseRestMapper {

	ComicCollectionRequestDTO mapBase(ComicCollectionRestBeanParams comicCollectionRestBeanParams);

	ComicCollectionBase mapBase(ComicCollection comicCollection);

	List<ComicCollectionBase> mapBase(List<ComicCollection> comicCollectionList);

}
