package com.cezarykluczynski.stapi.server.staff.mapper

import com.cezarykluczynski.stapi.client.v1.soap.Staff as SOAPStaff
import com.cezarykluczynski.stapi.model.staff.entity.Staff as DBStaff
import com.cezarykluczynski.stapi.server.common.mapper.AbstractRealWorldPersonMapperTest
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class StaffSoapMapperTest extends AbstractRealWorldPersonMapperTest {

	private StaffSoapMapper staffSoapMapper

	def setup() {
		staffSoapMapper = Mappers.getMapper(StaffSoapMapper)
	}

	def "maps DB entity to SOAP entity"() {
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
		SOAPStaff soapStaff = staffSoapMapper.map(Lists.newArrayList(dBStaff))[0]

		then:
		soapStaff.name == NAME
		soapStaff.birthName == BIRTH_NAME
		soapStaff.gender == GENDER_ENUM_SOAP
		soapStaff.dateOfBirth == DATE_OF_BIRTH_FROM_SOAP
		soapStaff.dateOfDeath == DATE_OF_DEATH_FROM_SOAP
		soapStaff.placeOfBirth == PLACE_OF_BIRTH
		soapStaff.placeOfDeath == PLACE_OF_DEATH
		soapStaff.artDepartment == ART_DEPARTMENT
		soapStaff.artDirector == ART_DIRECTOR
		soapStaff.productionDesigner == PRODUCTION_DESIGNER
		soapStaff.cameraAndElectricalDepartment == CAMERA_AND_ELECTRICAL_DEPARTMENT
		soapStaff.cinematographer == CINEMATOGRAPHER
		soapStaff.castingDepartment == CASTING_DEPARTMENT
		soapStaff.costumeDepartment == COSTUME_DEPARTMENT
		soapStaff.costumeDesigner == COSTUME_DESIGNER
		soapStaff.director == DIRECTOR
		soapStaff.assistantAndSecondUnitDirector == ASSISTANT_AND_SECOND_UNIT_DIRECTOR
		soapStaff.exhibitAndAttractionStaff == EXHIBIT_AND_ATTRACTION_STAFF
		soapStaff.filmEditor == FILM_EDITOR
		soapStaff.linguist == LINGUIST
		soapStaff.locationStaff == LOCATION_STAFF
		soapStaff.makeupStaff == MAKEUP_STAFF
		soapStaff.musicDepartment == MUSIC_DEPARTMENT
		soapStaff.composer == COMPOSER
		soapStaff.personalAssistant == PERSONAL_ASSISTANT
		soapStaff.producer == PRODUCER
		soapStaff.productionAssociate == PRODUCTION_ASSOCIATE
		soapStaff.productionStaff == PRODUCTION_STAFF
		soapStaff.publicationStaff == PUBLICATION_STAFF
		soapStaff.scienceConsultant == SCIENCE_CONSULTANT
		soapStaff.soundDepartment == SOUND_DEPARTMENT
		soapStaff.specialAndVisualEffectsStaff == SPECIAL_AND_VISUAL_EFFECTS_STAFF
		soapStaff.author == AUTHOR
		soapStaff.audioAuthor == AUDIO_AUTHOR
		soapStaff.calendarArtist == CALENDAR_ARTIST
		soapStaff.comicArtist == COMIC_ARTIST
		soapStaff.comicAuthor == COMIC_AUTHOR
		soapStaff.comicColorArtist == COMIC_COLOR_ARTIST
		soapStaff.comicInteriorArtist == COMIC_INTERIOR_ARTIST
		soapStaff.comicInkArtist == COMIC_INK_ARTIST
		soapStaff.comicPencilArtist == COMIC_PENCIL_ARTIST
		soapStaff.comicLetterArtist == COMIC_LETTER_ARTIST
		soapStaff.comicStripArtist == COMIC_STRIP_ARTIST
		soapStaff.gameArtist == GAME_ARTIST
		soapStaff.gameAuthor == GAME_AUTHOR
		soapStaff.novelArtist == NOVEL_ARTIST
		soapStaff.novelAuthor == NOVEL_AUTHOR
		soapStaff.referenceArtist == REFERENCE_ARTIST
		soapStaff.referenceAuthor == REFERENCE_AUTHOR
		soapStaff.publicationArtist == PUBLICATION_ARTIST
		soapStaff.publicationDesigner == PUBLICATION_DESIGNER
		soapStaff.publicationEditor == PUBLICATION_EDITOR
		soapStaff.publicityArtist == PUBLICITY_ARTIST
		soapStaff.cbsDigitalStaff == CBS_DIGITAL_STAFF
		soapStaff.ilmProductionStaff == ILM_PRODUCTION_STAFF
		soapStaff.specialFeaturesStaff == SPECIAL_FEATURES_STAFF
		soapStaff.storyEditor == STORY_EDITOR
		soapStaff.studioExecutive == STUDIO_EXECUTIVE
		soapStaff.stuntDepartment == STUNT_DEPARTMENT
		soapStaff.transportationDepartment == TRANSPORTATION_DEPARTMENT
		soapStaff.videoGameProductionStaff == VIDEO_GAME_PRODUCTION_STAFF
		soapStaff.writer == WRITER
	}

}
