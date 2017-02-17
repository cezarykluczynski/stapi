package com.cezarykluczynski.stapi.server.comics.mapper;

import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderRestMapper;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesHeaderRestMapper;
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyHeaderRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffHeaderRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderRestMapper.class, ComicSeriesHeaderRestMapper.class,
		CompanyHeaderRestMapper.class, ReferenceRestMapper.class, RequestSortRestMapper.class, StaffHeaderRestMapper.class})
public interface ComicsRestMapper {

	ComicsRequestDTO map(ComicsRestBeanParams comicsRestBeanParams);

	@Mappings({
			@Mapping(source = "comicSeries", target = "comicSeriesHeaders"),
			@Mapping(source = "writers", target = "writerHeaders"),
			@Mapping(source = "editors", target = "editorHeaders"),
			@Mapping(source = "artists", target = "artistHeaders"),
			@Mapping(source = "staff", target = "staffHeaders"),
			@Mapping(source = "publishers", target = "publisherHeaders"),
			@Mapping(source = "characters", target = "characterHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.rest.model.Comics map(Comics comics);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.Comics> map(List<Comics> comicsList);

}
