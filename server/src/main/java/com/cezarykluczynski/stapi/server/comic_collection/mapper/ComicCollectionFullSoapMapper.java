package com.cezarykluczynski.stapi.server.comic_collection.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFull;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullRequest;
import com.cezarykluczynski.stapi.model.comic_collection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceSoapMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseSoapMapper.class, ComicsBaseSoapMapper.class,
		ComicSeriesBaseSoapMapper.class, CompanyBaseSoapMapper.class, RequestSortSoapMapper.class, ReferenceSoapMapper.class,
		StaffBaseSoapMapper.class})
public interface ComicCollectionFullSoapMapper {

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "publishedYearFrom", ignore = true)
	@Mapping(target = "publishedYearTo", ignore = true)
	@Mapping(target = "numberOfPagesFrom", ignore = true)
	@Mapping(target = "numberOfPagesTo", ignore = true)
	@Mapping(target = "stardateFrom", ignore = true)
	@Mapping(target = "stardateTo", ignore = true)
	@Mapping(target = "yearFrom", ignore = true)
	@Mapping(target = "yearTo", ignore = true)
	@Mapping(target = "photonovel", ignore = true)
	@Mapping(target = "sort", ignore = true)
	ComicCollectionRequestDTO mapFull(ComicCollectionFullRequest comicCollectionFullRequest);

	ComicCollectionFull mapFull(ComicCollection comicCollection);

}
