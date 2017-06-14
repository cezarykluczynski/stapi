package com.cezarykluczynski.stapi.server.comic_strip.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripHeader;
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComicStripHeaderSoapMapper {

	ComicStripHeader map(ComicStrip comicStrip);

	List<ComicStripHeader> map(List<ComicStrip> comicStrip);

}
