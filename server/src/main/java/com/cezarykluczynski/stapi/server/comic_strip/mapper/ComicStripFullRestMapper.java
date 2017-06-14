package com.cezarykluczynski.stapi.server.comic_strip.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripFull;
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseRestMapper.class, ComicSeriesBaseRestMapper.class, StaffBaseRestMapper.class})
public interface ComicStripFullRestMapper {

	ComicStripFull mapFull(ComicStrip comicStrip);

}
