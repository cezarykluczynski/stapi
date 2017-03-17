package com.cezarykluczynski.stapi.server.staff.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.StaffBase
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffFull
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class StaffRestMapperTest extends AbstractStaffMapperTest {

	private StaffRestMapper staffRestMapper

	void setup() {
		staffRestMapper = Mappers.getMapper(StaffRestMapper)
	}

	void "maps StaffRestBeanParams to StaffRequestDTO"() {
		given:
		StaffRestBeanParams staffRestBeanParams = new StaffRestBeanParams(
				guid: GUID,
				name: NAME,
				birthName: BIRTH_NAME,
				gender: GENDER,
				dateOfBirthFrom: DATE_OF_BIRTH_FROM_DB,
				dateOfBirthTo: DATE_OF_BIRTH_TO_DB,
				dateOfDeathFrom: DATE_OF_DEATH_FROM_DB,
				dateOfDeathTo: DATE_OF_DEATH_TO_DB,
				placeOfBirth: PLACE_OF_BIRTH,
				placeOfDeath: PLACE_OF_DEATH,
				artDepartment: ART_DEPARTMENT,
				artDirector: ART_DIRECTOR,
				productionDesigner: PRODUCTION_DESIGNER,
				cameraAndElectricalDepartment: CAMERA_AND_ELECTRICAL_DEPARTMENT,
				cinematographer: CINEMATOGRAPHER,
				castingDepartment: CASTING_DEPARTMENT,
				costumeDepartment: COSTUME_DEPARTMENT,
				costumeDesigner: COSTUME_DESIGNER,
				director: DIRECTOR,
				assistantAndSecondUnitDirector: ASSISTANT_AND_SECOND_UNIT_DIRECTOR,
				exhibitAndAttractionStaff: EXHIBIT_AND_ATTRACTION_STAFF,
				filmEditor: FILM_EDITOR,
				linguist: LINGUIST,
				locationStaff: LOCATION_STAFF,
				makeupStaff: MAKEUP_STAFF,
				musicDepartment: MUSIC_DEPARTMENT,
				composer: COMPOSER,
				personalAssistant: PERSONAL_ASSISTANT,
				producer: PRODUCER,
				productionAssociate: PRODUCTION_ASSOCIATE,
				productionStaff: PRODUCTION_STAFF,
				publicationStaff: PUBLICATION_STAFF,
				scienceConsultant: SCIENCE_CONSULTANT,
				soundDepartment: SOUND_DEPARTMENT,
				specialAndVisualEffectsStaff: SPECIAL_AND_VISUAL_EFFECTS_STAFF,
				author: AUTHOR,
				audioAuthor: AUDIO_AUTHOR,
				calendarArtist: CALENDAR_ARTIST,
				comicArtist: COMIC_ARTIST,
				comicAuthor: COMIC_AUTHOR,
				comicColorArtist: COMIC_COLOR_ARTIST,
				comicInteriorArtist: COMIC_INTERIOR_ARTIST,
				comicInkArtist: COMIC_INK_ARTIST,
				comicPencilArtist: COMIC_PENCIL_ARTIST,
				comicLetterArtist: COMIC_LETTER_ARTIST,
				comicStripArtist: COMIC_STRIP_ARTIST,
				gameArtist: GAME_ARTIST,
				gameAuthor: GAME_AUTHOR,
				novelArtist: NOVEL_ARTIST,
				novelAuthor: NOVEL_AUTHOR,
				referenceArtist: REFERENCE_ARTIST,
				referenceAuthor: REFERENCE_AUTHOR,
				publicationArtist: PUBLICATION_ARTIST,
				publicationDesigner: PUBLICATION_DESIGNER,
				publicationEditor: PUBLICATION_EDITOR,
				publicityArtist: PUBLICITY_ARTIST,
				cbsDigitalStaff: CBS_DIGITAL_STAFF,
				ilmProductionStaff: ILM_PRODUCTION_STAFF,
				specialFeaturesStaff: SPECIAL_FEATURES_STAFF,
				storyEditor: STORY_EDITOR,
				studioExecutive: STUDIO_EXECUTIVE,
				stuntDepartment: STUNT_DEPARTMENT,
				transportationDepartment: TRANSPORTATION_DEPARTMENT,
				videoGameProductionStaff: VIDEO_GAME_PRODUCTION_STAFF,
				writer: WRITER)

		when:
		StaffRequestDTO staffRequestDTO = staffRestMapper.mapBase staffRestBeanParams

		then:
		staffRequestDTO.guid == GUID
		staffRequestDTO.name == NAME
		staffRequestDTO.birthName == BIRTH_NAME
		staffRequestDTO.gender == GENDER
		staffRequestDTO.dateOfBirthFrom == DATE_OF_BIRTH_FROM_DB
		staffRequestDTO.dateOfBirthTo == DATE_OF_BIRTH_TO_DB
		staffRequestDTO.dateOfDeathFrom == DATE_OF_DEATH_FROM_DB
		staffRequestDTO.dateOfDeathTo == DATE_OF_DEATH_TO_DB
		staffRequestDTO.placeOfBirth == PLACE_OF_BIRTH
		staffRequestDTO.placeOfDeath == PLACE_OF_DEATH
		staffRequestDTO.artDepartment == ART_DEPARTMENT
		staffRequestDTO.artDirector == ART_DIRECTOR
		staffRequestDTO.productionDesigner == PRODUCTION_DESIGNER
		staffRequestDTO.cameraAndElectricalDepartment == CAMERA_AND_ELECTRICAL_DEPARTMENT
		staffRequestDTO.cinematographer == CINEMATOGRAPHER
		staffRequestDTO.castingDepartment == CASTING_DEPARTMENT
		staffRequestDTO.costumeDepartment == COSTUME_DEPARTMENT
		staffRequestDTO.costumeDesigner == COSTUME_DESIGNER
		staffRequestDTO.director == DIRECTOR
		staffRequestDTO.assistantAndSecondUnitDirector == ASSISTANT_AND_SECOND_UNIT_DIRECTOR
		staffRequestDTO.exhibitAndAttractionStaff == EXHIBIT_AND_ATTRACTION_STAFF
		staffRequestDTO.filmEditor == FILM_EDITOR
		staffRequestDTO.linguist == LINGUIST
		staffRequestDTO.locationStaff == LOCATION_STAFF
		staffRequestDTO.makeupStaff == MAKEUP_STAFF
		staffRequestDTO.musicDepartment == MUSIC_DEPARTMENT
		staffRequestDTO.composer == COMPOSER
		staffRequestDTO.personalAssistant == PERSONAL_ASSISTANT
		staffRequestDTO.producer == PRODUCER
		staffRequestDTO.productionAssociate == PRODUCTION_ASSOCIATE
		staffRequestDTO.productionStaff == PRODUCTION_STAFF
		staffRequestDTO.publicationStaff == PUBLICATION_STAFF
		staffRequestDTO.scienceConsultant == SCIENCE_CONSULTANT
		staffRequestDTO.soundDepartment == SOUND_DEPARTMENT
		staffRequestDTO.specialAndVisualEffectsStaff == SPECIAL_AND_VISUAL_EFFECTS_STAFF
		staffRequestDTO.author == AUTHOR
		staffRequestDTO.audioAuthor == AUDIO_AUTHOR
		staffRequestDTO.calendarArtist == CALENDAR_ARTIST
		staffRequestDTO.comicArtist == COMIC_ARTIST
		staffRequestDTO.comicAuthor == COMIC_AUTHOR
		staffRequestDTO.comicColorArtist == COMIC_COLOR_ARTIST
		staffRequestDTO.comicInteriorArtist == COMIC_INTERIOR_ARTIST
		staffRequestDTO.comicInkArtist == COMIC_INK_ARTIST
		staffRequestDTO.comicPencilArtist == COMIC_PENCIL_ARTIST
		staffRequestDTO.comicLetterArtist == COMIC_LETTER_ARTIST
		staffRequestDTO.comicStripArtist == COMIC_STRIP_ARTIST
		staffRequestDTO.gameArtist == GAME_ARTIST
		staffRequestDTO.gameAuthor == GAME_AUTHOR
		staffRequestDTO.novelArtist == NOVEL_ARTIST
		staffRequestDTO.novelAuthor == NOVEL_AUTHOR
		staffRequestDTO.referenceArtist == REFERENCE_ARTIST
		staffRequestDTO.referenceAuthor == REFERENCE_AUTHOR
		staffRequestDTO.publicationArtist == PUBLICATION_ARTIST
		staffRequestDTO.publicationDesigner == PUBLICATION_DESIGNER
		staffRequestDTO.publicationEditor == PUBLICATION_EDITOR
		staffRequestDTO.publicityArtist == PUBLICITY_ARTIST
		staffRequestDTO.cbsDigitalStaff == CBS_DIGITAL_STAFF
		staffRequestDTO.ilmProductionStaff == ILM_PRODUCTION_STAFF
		staffRequestDTO.specialFeaturesStaff == SPECIAL_FEATURES_STAFF
		staffRequestDTO.storyEditor == STORY_EDITOR
		staffRequestDTO.studioExecutive == STUDIO_EXECUTIVE
		staffRequestDTO.stuntDepartment == STUNT_DEPARTMENT
		staffRequestDTO.transportationDepartment == TRANSPORTATION_DEPARTMENT
		staffRequestDTO.videoGameProductionStaff == VIDEO_GAME_PRODUCTION_STAFF
		staffRequestDTO.writer == WRITER
	}

	void "maps DB entity to base REST entity"() {
		given:
		Staff staff = createStaff()

		when:
		StaffBase staffBase = staffRestMapper.mapBase(Lists.newArrayList(staff))[0]

		then:
		staffBase.name == NAME
		staffBase.guid == GUID
		staffBase.birthName == BIRTH_NAME
		staffBase.gender == GENDER_ENUM_REST
		staffBase.dateOfBirth == DATE_OF_BIRTH_FROM_DB
		staffBase.dateOfDeath == DATE_OF_DEATH_FROM_DB
		staffBase.placeOfBirth == PLACE_OF_BIRTH
		staffBase.placeOfDeath == PLACE_OF_DEATH
		staffBase.artDepartment == ART_DEPARTMENT
		staffBase.artDirector == ART_DIRECTOR
		staffBase.productionDesigner == PRODUCTION_DESIGNER
		staffBase.cameraAndElectricalDepartment == CAMERA_AND_ELECTRICAL_DEPARTMENT
		staffBase.cinematographer == CINEMATOGRAPHER
		staffBase.castingDepartment == CASTING_DEPARTMENT
		staffBase.costumeDepartment == COSTUME_DEPARTMENT
		staffBase.costumeDesigner == COSTUME_DESIGNER
		staffBase.director == DIRECTOR
		staffBase.assistantAndSecondUnitDirector == ASSISTANT_AND_SECOND_UNIT_DIRECTOR
		staffBase.exhibitAndAttractionStaff == EXHIBIT_AND_ATTRACTION_STAFF
		staffBase.filmEditor == FILM_EDITOR
		staffBase.linguist == LINGUIST
		staffBase.locationStaff == LOCATION_STAFF
		staffBase.makeupStaff == MAKEUP_STAFF
		staffBase.musicDepartment == MUSIC_DEPARTMENT
		staffBase.composer == COMPOSER
		staffBase.personalAssistant == PERSONAL_ASSISTANT
		staffBase.producer == PRODUCER
		staffBase.productionAssociate == PRODUCTION_ASSOCIATE
		staffBase.productionStaff == PRODUCTION_STAFF
		staffBase.publicationStaff == PUBLICATION_STAFF
		staffBase.scienceConsultant == SCIENCE_CONSULTANT
		staffBase.soundDepartment == SOUND_DEPARTMENT
		staffBase.specialAndVisualEffectsStaff == SPECIAL_AND_VISUAL_EFFECTS_STAFF
		staffBase.author == AUTHOR
		staffBase.audioAuthor == AUDIO_AUTHOR
		staffBase.calendarArtist == CALENDAR_ARTIST
		staffBase.comicArtist == COMIC_ARTIST
		staffBase.comicAuthor == COMIC_AUTHOR
		staffBase.comicColorArtist == COMIC_COLOR_ARTIST
		staffBase.comicInteriorArtist == COMIC_INTERIOR_ARTIST
		staffBase.comicInkArtist == COMIC_INK_ARTIST
		staffBase.comicPencilArtist == COMIC_PENCIL_ARTIST
		staffBase.comicLetterArtist == COMIC_LETTER_ARTIST
		staffBase.comicStripArtist == COMIC_STRIP_ARTIST
		staffBase.gameArtist == GAME_ARTIST
		staffBase.gameAuthor == GAME_AUTHOR
		staffBase.novelArtist == NOVEL_ARTIST
		staffBase.novelAuthor == NOVEL_AUTHOR
		staffBase.referenceArtist == REFERENCE_ARTIST
		staffBase.referenceAuthor == REFERENCE_AUTHOR
		staffBase.publicationArtist == PUBLICATION_ARTIST
		staffBase.publicationDesigner == PUBLICATION_DESIGNER
		staffBase.publicationEditor == PUBLICATION_EDITOR
		staffBase.publicityArtist == PUBLICITY_ARTIST
		staffBase.cbsDigitalStaff == CBS_DIGITAL_STAFF
		staffBase.ilmProductionStaff == ILM_PRODUCTION_STAFF
		staffBase.specialFeaturesStaff == SPECIAL_FEATURES_STAFF
		staffBase.storyEditor == STORY_EDITOR
		staffBase.studioExecutive == STUDIO_EXECUTIVE
		staffBase.stuntDepartment == STUNT_DEPARTMENT
		staffBase.transportationDepartment == TRANSPORTATION_DEPARTMENT
		staffBase.videoGameProductionStaff == VIDEO_GAME_PRODUCTION_STAFF
		staffBase.writer == WRITER
	}

	void "maps DB entity to full REST entity"() {
		given:
		Staff staff = createStaff()

		when:
		StaffFull staffFull = staffRestMapper.mapFull(staff)

		then:
		staffFull.name == NAME
		staffFull.guid == GUID
		staffFull.birthName == BIRTH_NAME
		staffFull.gender == GENDER_ENUM_REST
		staffFull.dateOfBirth == DATE_OF_BIRTH_FROM_DB
		staffFull.dateOfDeath == DATE_OF_DEATH_FROM_DB
		staffFull.placeOfBirth == PLACE_OF_BIRTH
		staffFull.placeOfDeath == PLACE_OF_DEATH
		staffFull.artDepartment == ART_DEPARTMENT
		staffFull.artDirector == ART_DIRECTOR
		staffFull.productionDesigner == PRODUCTION_DESIGNER
		staffFull.cameraAndElectricalDepartment == CAMERA_AND_ELECTRICAL_DEPARTMENT
		staffFull.cinematographer == CINEMATOGRAPHER
		staffFull.castingDepartment == CASTING_DEPARTMENT
		staffFull.costumeDepartment == COSTUME_DEPARTMENT
		staffFull.costumeDesigner == COSTUME_DESIGNER
		staffFull.director == DIRECTOR
		staffFull.assistantAndSecondUnitDirector == ASSISTANT_AND_SECOND_UNIT_DIRECTOR
		staffFull.exhibitAndAttractionStaff == EXHIBIT_AND_ATTRACTION_STAFF
		staffFull.filmEditor == FILM_EDITOR
		staffFull.linguist == LINGUIST
		staffFull.locationStaff == LOCATION_STAFF
		staffFull.makeupStaff == MAKEUP_STAFF
		staffFull.musicDepartment == MUSIC_DEPARTMENT
		staffFull.composer == COMPOSER
		staffFull.personalAssistant == PERSONAL_ASSISTANT
		staffFull.producer == PRODUCER
		staffFull.productionAssociate == PRODUCTION_ASSOCIATE
		staffFull.productionStaff == PRODUCTION_STAFF
		staffFull.publicationStaff == PUBLICATION_STAFF
		staffFull.scienceConsultant == SCIENCE_CONSULTANT
		staffFull.soundDepartment == SOUND_DEPARTMENT
		staffFull.specialAndVisualEffectsStaff == SPECIAL_AND_VISUAL_EFFECTS_STAFF
		staffFull.author == AUTHOR
		staffFull.audioAuthor == AUDIO_AUTHOR
		staffFull.calendarArtist == CALENDAR_ARTIST
		staffFull.comicArtist == COMIC_ARTIST
		staffFull.comicAuthor == COMIC_AUTHOR
		staffFull.comicColorArtist == COMIC_COLOR_ARTIST
		staffFull.comicInteriorArtist == COMIC_INTERIOR_ARTIST
		staffFull.comicInkArtist == COMIC_INK_ARTIST
		staffFull.comicPencilArtist == COMIC_PENCIL_ARTIST
		staffFull.comicLetterArtist == COMIC_LETTER_ARTIST
		staffFull.comicStripArtist == COMIC_STRIP_ARTIST
		staffFull.gameArtist == GAME_ARTIST
		staffFull.gameAuthor == GAME_AUTHOR
		staffFull.novelArtist == NOVEL_ARTIST
		staffFull.novelAuthor == NOVEL_AUTHOR
		staffFull.referenceArtist == REFERENCE_ARTIST
		staffFull.referenceAuthor == REFERENCE_AUTHOR
		staffFull.publicationArtist == PUBLICATION_ARTIST
		staffFull.publicationDesigner == PUBLICATION_DESIGNER
		staffFull.publicationEditor == PUBLICATION_EDITOR
		staffFull.publicityArtist == PUBLICITY_ARTIST
		staffFull.cbsDigitalStaff == CBS_DIGITAL_STAFF
		staffFull.ilmProductionStaff == ILM_PRODUCTION_STAFF
		staffFull.specialFeaturesStaff == SPECIAL_FEATURES_STAFF
		staffFull.storyEditor == STORY_EDITOR
		staffFull.studioExecutive == STUDIO_EXECUTIVE
		staffFull.stuntDepartment == STUNT_DEPARTMENT
		staffFull.transportationDepartment == TRANSPORTATION_DEPARTMENT
		staffFull.videoGameProductionStaff == VIDEO_GAME_PRODUCTION_STAFF
		staffFull.writer == WRITER
		staffFull.writtenEpisodeHeaders.size() == staff.writtenEpisodes.size()
		staffFull.teleplayAuthoredEpisodeHeaders.size() == staff.teleplayAuthoredEpisodes.size()
		staffFull.storyAuthoredEpisodeHeaders.size() == staff.storyAuthoredEpisodes.size()
		staffFull.directedEpisodeHeaders.size() == staff.directedEpisodes.size()
		staffFull.episodeHeaders.size() == staff.episodes.size()
		staffFull.writtenMovieHeaders.size() == staff.writtenMovies.size()
		staffFull.screenplayAuthoredMovieHeaders.size() == staff.screenplayAuthoredMovies.size()
		staffFull.storyAuthoredMovieHeaders.size() == staff.storyAuthoredMovies.size()
		staffFull.directedMovieHeaders.size() == staff.directedMovies.size()
		staffFull.producedMovieHeaders.size() == staff.producedMovies.size()
		staffFull.movieHeaders.size() == staff.movies.size()
	}

}
