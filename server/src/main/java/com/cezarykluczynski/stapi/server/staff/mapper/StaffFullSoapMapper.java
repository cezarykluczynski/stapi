package com.cezarykluczynski.stapi.server.staff.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.StaffFull;
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullRequest;
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseSoapMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseSoapMapper.class, DateMapper.class, EnumMapper.class, EpisodeBaseSoapMapper.class,
		MovieBaseSoapMapper.class})
public interface StaffFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "birthName", ignore = true)
	@Mapping(target = "gender", ignore = true)
	@Mapping(target = "dateOfBirthFrom", ignore = true)
	@Mapping(target = "dateOfBirthTo", ignore = true)
	@Mapping(target = "dateOfDeathFrom", ignore = true)
	@Mapping(target = "dateOfDeathTo", ignore = true)
	@Mapping(target = "placeOfBirth", ignore = true)
	@Mapping(target = "placeOfDeath", ignore = true)
	@Mapping(target = "sort", ignore = true)
	@Mapping(target = "artDepartment", ignore = true)
	@Mapping(target = "artDirector", ignore = true)
	@Mapping(target = "productionDesigner", ignore = true)
	@Mapping(target = "cameraAndElectricalDepartment", ignore = true)
	@Mapping(target = "cinematographer", ignore = true)
	@Mapping(target = "castingDepartment", ignore = true)
	@Mapping(target = "costumeDepartment", ignore = true)
	@Mapping(target = "costumeDesigner", ignore = true)
	@Mapping(target = "director", ignore = true)
	@Mapping(target = "assistantOrSecondUnitDirector", ignore = true)
	@Mapping(target = "exhibitAndAttractionStaff", ignore = true)
	@Mapping(target = "filmEditor", ignore = true)
	@Mapping(target = "linguist", ignore = true)
	@Mapping(target = "locationStaff", ignore = true)
	@Mapping(target = "makeupStaff", ignore = true)
	@Mapping(target = "musicDepartment", ignore = true)
	@Mapping(target = "composer", ignore = true)
	@Mapping(target = "personalAssistant", ignore = true)
	@Mapping(target = "producer", ignore = true)
	@Mapping(target = "productionAssociate", ignore = true)
	@Mapping(target = "productionStaff", ignore = true)
	@Mapping(target = "publicationStaff", ignore = true)
	@Mapping(target = "scienceConsultant", ignore = true)
	@Mapping(target = "soundDepartment", ignore = true)
	@Mapping(target = "specialAndVisualEffectsStaff", ignore = true)
	@Mapping(target = "author", ignore = true)
	@Mapping(target = "audioAuthor", ignore = true)
	@Mapping(target = "calendarArtist", ignore = true)
	@Mapping(target = "comicArtist", ignore = true)
	@Mapping(target = "comicAuthor", ignore = true)
	@Mapping(target = "comicColorArtist", ignore = true)
	@Mapping(target = "comicInteriorArtist", ignore = true)
	@Mapping(target = "comicInkArtist", ignore = true)
	@Mapping(target = "comicPencilArtist", ignore = true)
	@Mapping(target = "comicLetterArtist", ignore = true)
	@Mapping(target = "comicStripArtist", ignore = true)
	@Mapping(target = "gameArtist", ignore = true)
	@Mapping(target = "gameAuthor", ignore = true)
	@Mapping(target = "novelArtist", ignore = true)
	@Mapping(target = "novelAuthor", ignore = true)
	@Mapping(target = "referenceArtist", ignore = true)
	@Mapping(target = "referenceAuthor", ignore = true)
	@Mapping(target = "publicationArtist", ignore = true)
	@Mapping(target = "publicationDesigner", ignore = true)
	@Mapping(target = "publicationEditor", ignore = true)
	@Mapping(target = "publicityArtist", ignore = true)
	@Mapping(target = "cbsDigitalStaff", ignore = true)
	@Mapping(target = "ilmProductionStaff", ignore = true)
	@Mapping(target = "specialFeaturesStaff", ignore = true)
	@Mapping(target = "storyEditor", ignore = true)
	@Mapping(target = "studioExecutive", ignore = true)
	@Mapping(target = "stuntDepartment", ignore = true)
	@Mapping(target = "transportationDepartment", ignore = true)
	@Mapping(target = "videoGameProductionStaff", ignore = true)
	@Mapping(target = "writer", ignore = true)
	StaffRequestDTO mapFull(StaffFullRequest staffFullRequest);

	StaffFull mapFull(Staff staffList);

}
