package com.cezarykluczynski.stapi.etl.staff.creation.processor

import com.cezarykluczynski.stapi.etl.common.processor.AbstractRealWorldActorTemplateProcessorTest
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.ReflectionTestUtils

class StaffActorTemplateProcessorTest extends AbstractRealWorldActorTemplateProcessorTest {

	private StaffActorTemplateProcessor staffActorTemplateProcessor

	def setup() {
		staffActorTemplateProcessor = new StaffActorTemplateProcessor()
	}

	def "converts ActorTemplate to Staff"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate(
				name: NAME,
				birthName: BIRTH_NAME,
				placeOfBirth: PLACE_OF_BIRTH,
				placeOfDeath: PLACE_OF_DEATH,
				gender: GENDER,
				lifeRange: new DateRange(
						startDate: DATE_OF_BIRTH,
						endDate: DATE_OF_DEATH
				),
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
				writer: WRITER
		)

		when:
		Staff staff = staffActorTemplateProcessor.process(actorTemplate)

		then:
		staff.name == NAME
		staff.birthName == BIRTH_NAME
		staff.placeOfBirth == PLACE_OF_BIRTH
		staff.placeOfDeath == PLACE_OF_DEATH
		staff.gender.name() == GENDER.name()
		staff.dateOfBirth == DATE_OF_BIRTH
		staff.dateOfDeath == DATE_OF_DEATH
		staff.artDepartment == ART_DEPARTMENT
		staff.artDirector == ART_DIRECTOR
		staff.productionDesigner == PRODUCTION_DESIGNER
		staff.cameraAndElectricalDepartment == CAMERA_AND_ELECTRICAL_DEPARTMENT
		staff.cinematographer == CINEMATOGRAPHER
		staff.castingDepartment == CASTING_DEPARTMENT
		staff.costumeDepartment == COSTUME_DEPARTMENT
		staff.costumeDesigner == COSTUME_DESIGNER
		staff.director == DIRECTOR
		staff.assistantAndSecondUnitDirector == ASSISTANT_AND_SECOND_UNIT_DIRECTOR
		staff.exhibitAndAttractionStaff == EXHIBIT_AND_ATTRACTION_STAFF
		staff.filmEditor == FILM_EDITOR
		staff.linguist == LINGUIST
		staff.locationStaff == LOCATION_STAFF
		staff.makeupStaff == MAKEUP_STAFF
		staff.musicDepartment == MUSIC_DEPARTMENT
		staff.composer == COMPOSER
		staff.personalAssistant == PERSONAL_ASSISTANT
		staff.producer == PRODUCER
		staff.productionAssociate == PRODUCTION_ASSOCIATE
		staff.productionStaff == PRODUCTION_STAFF
		staff.publicationStaff == PUBLICATION_STAFF
		staff.scienceConsultant == SCIENCE_CONSULTANT
		staff.soundDepartment == SOUND_DEPARTMENT
		staff.specialAndVisualEffectsStaff == SPECIAL_AND_VISUAL_EFFECTS_STAFF
		staff.author == AUTHOR
		staff.audioAuthor == AUDIO_AUTHOR
		staff.calendarArtist == CALENDAR_ARTIST
		staff.comicArtist == COMIC_ARTIST
		staff.comicAuthor == COMIC_AUTHOR
		staff.comicColorArtist == COMIC_COLOR_ARTIST
		staff.comicInteriorArtist == COMIC_INTERIOR_ARTIST
		staff.comicInkArtist == COMIC_INK_ARTIST
		staff.comicPencilArtist == COMIC_PENCIL_ARTIST
		staff.comicLetterArtist == COMIC_LETTER_ARTIST
		staff.comicStripArtist == COMIC_STRIP_ARTIST
		staff.gameArtist == GAME_ARTIST
		staff.gameAuthor == GAME_AUTHOR
		staff.novelArtist == NOVEL_ARTIST
		staff.novelAuthor == NOVEL_AUTHOR
		staff.referenceArtist == REFERENCE_ARTIST
		staff.referenceAuthor == REFERENCE_AUTHOR
		staff.publicationArtist == PUBLICATION_ARTIST
		staff.publicationDesigner == PUBLICATION_DESIGNER
		staff.publicationEditor == PUBLICATION_EDITOR
		staff.publicityArtist == PUBLICITY_ARTIST
		staff.cbsDigitalStaff == CBS_DIGITAL_STAFF
		staff.ilmProductionStaff == ILM_PRODUCTION_STAFF
		staff.specialFeaturesStaff == SPECIAL_FEATURES_STAFF
		staff.storyEditor == STORY_EDITOR
		staff.studioExecutive == STUDIO_EXECUTIVE
		staff.stuntDepartment == STUNT_DEPARTMENT
		staff.transportationDepartment == TRANSPORTATION_DEPARTMENT
		staff.videoGameProductionStaff == VIDEO_GAME_PRODUCTION_STAFF
		staff.writer == WRITER
	}

	def "ActorTemplate with only nulls and false values is converted to Staff with only nulls and false values"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()

		when:
		Staff staff = staffActorTemplateProcessor.process(actorTemplate)

		then:
		staff.name == null
		staff.birthName == null
		staff.placeOfBirth == null
		staff.placeOfDeath == null
		staff.gender == null
		staff.dateOfBirth == null
		staff.dateOfDeath == null
		ReflectionTestUtils.getNumberOfTrueBooleanFields(staff) == 0
	}

}
