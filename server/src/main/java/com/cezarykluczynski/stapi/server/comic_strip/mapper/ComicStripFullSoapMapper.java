package com.cezarykluczynski.stapi.server.comic_strip.mapper;


import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFull;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest;
import com.cezarykluczynski.stapi.model.comic_strip.dto.ComicStripRequestDTO;
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseSoapMapper.class, ComicSeriesBaseSoapMapper.class, StaffBaseSoapMapper.class})
public interface ComicStripFullSoapMapper {

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "publishedYearFrom", ignore = true)
	@Mapping(target = "publishedYearTo", ignore = true)
	@Mapping(target = "numberOfPagesFrom", ignore = true)
	@Mapping(target = "numberOfPagesTo", ignore = true)
	@Mapping(target = "yearFrom", ignore = true)
	@Mapping(target = "yearTo", ignore = true)
	@Mapping(target = "sort", ignore = true)
	ComicStripRequestDTO mapFull(ComicStripFullRequest comicStripFullRequest);

	ComicStripFull mapFull(ComicStrip comicStrip);

}
