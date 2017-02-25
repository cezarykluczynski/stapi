package com.cezarykluczynski.stapi.server.comicCollection.mapper;

import com.cezarykluczynski.stapi.model.comicCollection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderRestMapper;
import com.cezarykluczynski.stapi.server.comicCollection.dto.ComicCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesHeaderRestMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsHeaderRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyHeaderRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffHeaderRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderRestMapper.class, ComicsHeaderRestMapper.class,
		ComicSeriesHeaderRestMapper.class, CompanyHeaderRestMapper.class, ReferenceRestMapper.class, RequestSortRestMapper.class,
		StaffHeaderRestMapper.class})
public interface ComicCollectionRestMapper {

	ComicCollectionRequestDTO map(ComicCollectionRestBeanParams comicCollectionRestBeanParams);

	@Mappings({
			@Mapping(source = "comicSeries", target = "comicSeriesHeaders"),
			@Mapping(source = "writers", target = "writerHeaders"),
			@Mapping(source = "editors", target = "editorHeaders"),
			@Mapping(source = "artists", target = "artistHeaders"),
			@Mapping(source = "staff", target = "staffHeaders"),
			@Mapping(source = "publishers", target = "publisherHeaders"),
			@Mapping(source = "characters", target = "characterHeaders"),
			@Mapping(source = "comics", target = "comicsHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollection map(ComicCollection comicCollection);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollection> map(List<ComicCollection> comicCollectionList);

}
