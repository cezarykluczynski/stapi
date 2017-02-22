package com.cezarykluczynski.stapi.server.comicStrip.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripRequest;
import com.cezarykluczynski.stapi.model.comicStrip.dto.ComicStripRequestDTO;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderSoapMapper.class, ComicSeriesHeaderSoapMapper.class,
		RequestSortSoapMapper.class, StaffHeaderSoapMapper.class})
public interface ComicStripSoapMapper {

	@Mappings({
			@Mapping(source = "publishedYear.from", target = "publishedYearFrom"),
			@Mapping(source = "publishedYear.to", target = "publishedYearTo"),
			@Mapping(source = "numberOfPages.from", target = "numberOfPagesFrom"),
			@Mapping(source = "numberOfPages.to", target = "numberOfPagesTo"),
			@Mapping(source = "year.from", target = "yearFrom"),
			@Mapping(source = "year.to", target = "yearTo")
	})
	ComicStripRequestDTO map(ComicStripRequest comicStripRequest);

	@Mappings({
			@Mapping(source = "comicSeries", target = "comicSeriesHeaders"),
			@Mapping(source = "writers", target = "writerHeaders"),
			@Mapping(source = "artists", target = "artistHeaders"),
			@Mapping(source = "characters", target = "characterHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.soap.ComicStrip map(ComicStrip comicStrip);

	List<com.cezarykluczynski.stapi.client.v1.soap.ComicStrip> map(List<ComicStrip> comicStripList);

}
