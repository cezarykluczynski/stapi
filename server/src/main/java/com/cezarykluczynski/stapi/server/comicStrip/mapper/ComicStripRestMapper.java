package com.cezarykluczynski.stapi.server.comicStrip.mapper;

import com.cezarykluczynski.stapi.model.comicStrip.dto.ComicStripRequestDTO;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderRestMapper;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesHeaderRestMapper;
import com.cezarykluczynski.stapi.server.comicStrip.dto.ComicStripRestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffHeaderRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderRestMapper.class, ComicSeriesHeaderRestMapper.class,
		RequestSortRestMapper.class, StaffHeaderRestMapper.class})
public interface ComicStripRestMapper {

	ComicStripRequestDTO map(ComicStripRestBeanParams comicStripRestBeanParams);

	@Mappings({
			@Mapping(source = "comicSeries", target = "comicSeriesHeaders"),
			@Mapping(source = "writers", target = "writerHeaders"),
			@Mapping(source = "artists", target = "artistHeaders"),
			@Mapping(source = "characters", target = "characterHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.rest.model.ComicStrip map(ComicStrip comicStrip);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.ComicStrip> map(List<ComicStrip> comicStripList);

}
