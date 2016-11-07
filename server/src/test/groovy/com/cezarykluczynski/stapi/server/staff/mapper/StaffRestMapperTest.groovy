package com.cezarykluczynski.stapi.server.staff.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.Staff as RESTStaff
import com.cezarykluczynski.stapi.model.staff.entity.Staff as DBStaff
import com.cezarykluczynski.stapi.server.common.mapper.AbstractRealWorldPersonMapperTest
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class StaffRestMapperTest extends AbstractRealWorldPersonMapperTest {

	private StaffRestMapper performerRestMapper

	def setup() {
		performerRestMapper = Mappers.getMapper(StaffRestMapper)
	}

	def "maps DB entity to REST entity"() {
		given:
		DBStaff dBStaff = new DBStaff(
				name: NAME,
				birthName: BIRTH_NAME,
				gender: GENDER,
				dateOfBirth: DATE_OF_BIRTH_FROM_DB,
				dateOfDeath: DATE_OF_DEATH_FROM_DB,
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
		RESTStaff restStaff = performerRestMapper.map(Lists.newArrayList(dBStaff))[0]

		then:
		restStaff.name == NAME
		restStaff.birthName == BIRTH_NAME
		restStaff.gender == GENDER_ENUM_REST
		restStaff.dateOfBirth == DATE_OF_BIRTH_FROM_DB
		restStaff.dateOfDeath == DATE_OF_DEATH_FROM_DB
		restStaff.placeOfBirth == PLACE_OF_BIRTH
		restStaff.placeOfDeath == PLACE_OF_DEATH
		restStaff.artDepartment == ART_DEPARTMENT
		restStaff.artDirector == ART_DIRECTOR
		restStaff.productionDesigner == PRODUCTION_DESIGNER
		restStaff.cameraAndElectricalDepartment == CAMERA_AND_ELECTRICAL_DEPARTMENT
		restStaff.cinematographer == CINEMATOGRAPHER
		restStaff.castingDepartment == CASTING_DEPARTMENT
		restStaff.costumeDepartment == COSTUME_DEPARTMENT
		restStaff.costumeDesigner == COSTUME_DESIGNER
		restStaff.director == DIRECTOR
		restStaff.assistantAndSecondUnitDirector == ASSISTANT_AND_SECOND_UNIT_DIRECTOR
		restStaff.exhibitAndAttractionStaff == EXHIBIT_AND_ATTRACTION_STAFF
		restStaff.filmEditor == FILM_EDITOR
		restStaff.linguist == LINGUIST
		restStaff.locationStaff == LOCATION_STAFF
		restStaff.makeupStaff == MAKEUP_STAFF
		restStaff.musicDepartment == MUSIC_DEPARTMENT
		restStaff.composer == COMPOSER
		restStaff.personalAssistant == PERSONAL_ASSISTANT
		restStaff.producer == PRODUCER
		restStaff.productionAssociate == PRODUCTION_ASSOCIATE
		restStaff.productionStaff == PRODUCTION_STAFF
		restStaff.publicationStaff == PUBLICATION_STAFF
		restStaff.scienceConsultant == SCIENCE_CONSULTANT
		restStaff.soundDepartment == SOUND_DEPARTMENT
		restStaff.specialAndVisualEffectsStaff == SPECIAL_AND_VISUAL_EFFECTS_STAFF
		restStaff.author == AUTHOR
		restStaff.audioAuthor == AUDIO_AUTHOR
		restStaff.calendarArtist == CALENDAR_ARTIST
		restStaff.comicArtist == COMIC_ARTIST
		restStaff.comicAuthor == COMIC_AUTHOR
		restStaff.comicColorArtist == COMIC_COLOR_ARTIST
		restStaff.comicInteriorArtist == COMIC_INTERIOR_ARTIST
		restStaff.comicInkArtist == COMIC_INK_ARTIST
		restStaff.comicPencilArtist == COMIC_PENCIL_ARTIST
		restStaff.comicLetterArtist == COMIC_LETTER_ARTIST
		restStaff.comicStripArtist == COMIC_STRIP_ARTIST
		restStaff.gameArtist == GAME_ARTIST
		restStaff.gameAuthor == GAME_AUTHOR
		restStaff.novelArtist == NOVEL_ARTIST
		restStaff.novelAuthor == NOVEL_AUTHOR
		restStaff.referenceArtist == REFERENCE_ARTIST
		restStaff.referenceAuthor == REFERENCE_AUTHOR
		restStaff.publicationArtist == PUBLICATION_ARTIST
		restStaff.publicationDesigner == PUBLICATION_DESIGNER
		restStaff.publicationEditor == PUBLICATION_EDITOR
		restStaff.publicityArtist == PUBLICITY_ARTIST
		restStaff.cbsDigitalStaff == CBS_DIGITAL_STAFF
		restStaff.ilmProductionStaff == ILM_PRODUCTION_STAFF
		restStaff.specialFeaturesStaff == SPECIAL_FEATURES_STAFF
		restStaff.storyEditor == STORY_EDITOR
		restStaff.studioExecutive == STUDIO_EXECUTIVE
		restStaff.stuntDepartment == STUNT_DEPARTMENT
		restStaff.transportationDepartment == TRANSPORTATION_DEPARTMENT
		restStaff.videoGameProductionStaff == VIDEO_GAME_PRODUCTION_STAFF
		restStaff.writer == WRITER
	}

}
