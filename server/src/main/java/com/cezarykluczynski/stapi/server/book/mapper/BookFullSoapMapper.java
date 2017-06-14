package com.cezarykluczynski.stapi.server.book.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.BookFull;
import com.cezarykluczynski.stapi.client.v1.soap.BookFullRequest;
import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO;
import com.cezarykluczynski.stapi.model.book.entity.Book;
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
public interface BookFullSoapMapper {

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "publishedYearFrom", ignore = true)
	@Mapping(target = "publishedYearTo", ignore = true)
	@Mapping(target = "numberOfPagesFrom", ignore = true)
	@Mapping(target = "numberOfPagesTo", ignore = true)
	@Mapping(target = "stardateFrom", ignore = true)
	@Mapping(target = "stardateTo", ignore = true)
	@Mapping(target = "yearFrom", ignore = true)
	@Mapping(target = "yearTo", ignore = true)
	@Mapping(target = "novel", ignore = true)
	@Mapping(target = "referenceBook", ignore = true)
	@Mapping(target = "biographyBook", ignore = true)
	@Mapping(target = "rolePlayingBook", ignore = true)
	@Mapping(target = "EBook", ignore = true)
	@Mapping(target = "anthology", ignore = true)
	@Mapping(target = "novelization", ignore = true)
	@Mapping(target = "audiobook", ignore = true)
	@Mapping(target = "audiobookAbridged", ignore = true)
	@Mapping(target = "audiobookPublishedYearFrom", ignore = true)
	@Mapping(target = "audiobookPublishedYearTo", ignore = true)
	@Mapping(target = "audiobookRunTimeFrom", ignore = true)
	@Mapping(target = "audiobookRunTimeTo", ignore = true)
	@Mapping(target = "sort", ignore = true)
	BookRequestDTO mapFull(BookFullRequest bookFullRequest);

	BookFull mapFull(Book book);

}
