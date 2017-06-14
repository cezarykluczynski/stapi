package com.cezarykluczynski.stapi.server.comic_collection.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBase;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseRequest;
import com.cezarykluczynski.stapi.model.comic_collection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface ComicCollectionBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "publishedYear.from", target = "publishedYearFrom")
	@Mapping(source = "publishedYear.to", target = "publishedYearTo")
	@Mapping(source = "numberOfPages.from", target = "numberOfPagesFrom")
	@Mapping(source = "numberOfPages.to", target = "numberOfPagesTo")
	@Mapping(source = "stardate.from", target = "stardateFrom")
	@Mapping(source = "stardate.to", target = "stardateTo")
	@Mapping(source = "year.from", target = "yearFrom")
	@Mapping(source = "year.to", target = "yearTo")
	ComicCollectionRequestDTO mapBase(ComicCollectionBaseRequest comicCollectionBaseRequest);

	ComicCollectionBase mapBase(ComicCollection comicCollection);

	List<ComicCollectionBase> mapBase(List<ComicCollection> comicCollectionList);

}
