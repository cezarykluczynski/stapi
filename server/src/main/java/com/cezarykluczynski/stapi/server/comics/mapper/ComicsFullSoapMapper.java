package com.cezarykluczynski.stapi.server.comics.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ComicsFull;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullRequest;
import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceSoapMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseSoapMapper.class, ComicCollectionBaseSoapMapper.class,
		ComicSeriesBaseSoapMapper.class, CompanyBaseSoapMapper.class, ReferenceSoapMapper.class, StaffBaseSoapMapper.class})
public interface ComicsFullSoapMapper {

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
	@Mapping(target = "adaptation", ignore = true)
	@Mapping(target = "sort", ignore = true)
	ComicsRequestDTO mapFull(ComicsFullRequest comicsFullRequest);

	ComicsFull mapFull(Comics comics);

}
