package com.cezarykluczynski.stapi.server.comic_strip.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBase;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest;
import com.cezarykluczynski.stapi.model.comic_strip.dto.ComicStripRequestDTO;
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface ComicStripBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "publishedYear.from", target = "publishedYearFrom")
	@Mapping(source = "publishedYear.to", target = "publishedYearTo")
	@Mapping(source = "numberOfPages.from", target = "numberOfPagesFrom")
	@Mapping(source = "numberOfPages.to", target = "numberOfPagesTo")
	@Mapping(source = "year.from", target = "yearFrom")
	@Mapping(source = "year.to", target = "yearTo")
	ComicStripRequestDTO mapBase(ComicStripBaseRequest comicStripBaseRequest);

	ComicStripBase mapBase(ComicStrip comicStrip);

	List<ComicStripBase> mapBase(List<ComicStrip> comicStripList);

}
