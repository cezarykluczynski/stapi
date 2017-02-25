package com.cezarykluczynski.stapi.server.comics.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ComicsRequest;
import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceSoapMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderSoapMapper.class, ComicCollectionHeaderSoapMapper.class,
		ComicSeriesHeaderSoapMapper.class, CompanyHeaderSoapMapper.class, RequestSortSoapMapper.class, ReferenceSoapMapper.class,
		StaffHeaderSoapMapper.class})
public interface ComicsSoapMapper {

	@Mappings({
			@Mapping(source = "publishedYear.from", target = "publishedYearFrom"),
			@Mapping(source = "publishedYear.to", target = "publishedYearTo"),
			@Mapping(source = "numberOfPages.from", target = "numberOfPagesFrom"),
			@Mapping(source = "numberOfPages.to", target = "numberOfPagesTo"),
			@Mapping(source = "stardate.from", target = "stardateFrom"),
			@Mapping(source = "stardate.to", target = "stardateTo"),
			@Mapping(source = "year.from", target = "yearFrom"),
			@Mapping(source = "year.to", target = "yearTo")
	})
	ComicsRequestDTO map(ComicsRequest comicsRequest);

	@Mappings({
			@Mapping(source = "comicSeries", target = "comicSeriesHeaders"),
			@Mapping(source = "writers", target = "writerHeaders"),
			@Mapping(source = "editors", target = "editorHeaders"),
			@Mapping(source = "artists", target = "artistHeaders"),
			@Mapping(source = "staff", target = "staffHeaders"),
			@Mapping(source = "publishers", target = "publisherHeaders"),
			@Mapping(source = "characters", target = "characterHeaders"),
			@Mapping(source = "comicCollections", target = "comicCollectionHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.soap.Comics map(Comics comics);

	List<com.cezarykluczynski.stapi.client.v1.soap.Comics> map(List<Comics> comicsList);

}
