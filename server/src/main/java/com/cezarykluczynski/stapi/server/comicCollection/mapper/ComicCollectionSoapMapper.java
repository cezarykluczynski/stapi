package com.cezarykluczynski.stapi.server.comicCollection.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionRequest;
import com.cezarykluczynski.stapi.model.comicCollection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceSoapMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderSoapMapper.class, ComicsHeaderSoapMapper.class,
		ComicSeriesHeaderSoapMapper.class, CompanyHeaderSoapMapper.class, RequestSortSoapMapper.class, ReferenceSoapMapper.class,
		StaffHeaderSoapMapper.class})
public interface ComicCollectionSoapMapper {

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
	ComicCollectionRequestDTO map(ComicCollectionRequest comicCollectionRequest);

	@Mappings({
			@Mapping(source = "comicSeries", target = "comicSeriesHeaders"),
			@Mapping(source = "writers", target = "writerHeaders"),
			@Mapping(source = "editors", target = "editorHeaders"),
			@Mapping(source = "artists", target = "artistHeaders"),
			@Mapping(source = "staff", target = "staffHeaders"),
			@Mapping(source = "publishers", target = "publisherHeaders"),
			@Mapping(source = "characters", target = "characterHeaders"),
			@Mapping(source = "comics", target = "comicsHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.soap.ComicCollection map(ComicCollection comicCollection);

	List<com.cezarykluczynski.stapi.client.v1.soap.ComicCollection> map(List<ComicCollection> comicCollectionList);

}
