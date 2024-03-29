package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.StaffApi
import com.cezarykluczynski.stapi.client.rest.model.Gender
import com.cezarykluczynski.stapi.client.rest.model.StaffV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.StaffV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.StaffV2SearchCriteria
import com.cezarykluczynski.stapi.util.AbstractRealWorldPersonTest

class StaffTest extends AbstractRealWorldPersonTest {

	private StaffApi staffApiMock

	private Staff staff

	void setup() {
		staffApiMock = Mock()
		staff = new Staff(staffApiMock)
	}

	void "gets single entity (V2)"() {
		given:
		StaffV2FullResponse staffV2FullResponse = Mock()

		when:
		StaffV2FullResponse staffV2FullResponseOutput = staff.getV2(UID)

		then:
		1 * staffApiMock.v2GetStaff(UID) >> staffV2FullResponse
		0 * _
		staffV2FullResponse == staffV2FullResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		StaffV2BaseResponse staffV2BaseResponse = Mock()
		StaffV2SearchCriteria staffV2SearchCriteria = new StaffV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				birthName: BIRTH_NAME,
				gender: Gender.valueOf(GENDER),
				dateOfBirthFrom: DATE_OF_BIRTH_FROM,
				dateOfBirthTo: DATE_OF_BIRTH_TO,
				dateOfDeathFrom: DATE_OF_DEATH_FROM,
				dateOfDeathTo: DATE_OF_DEATH_TO,
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
				assistantOrSecondUnitDirector: ASSISTANT_AND_SECOND_UNIT_DIRECTOR,
				exhibitAndAttractionStaff: EXHIBIT_AND_ATTRACTION_STAFF,
				filmEditor: FILM_EDITOR,
				filmationProductionStaff: FILMATION_PRODUCTION_STAFF,
				linguist: LINGUIST,
				locationStaff: LOCATION_STAFF,
				makeupStaff: MAKEUP_STAFF,
				merchandiseStaff: MERCHANDISE_STAFF,
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
				comicCoverArtist: COMIC_COVER_ARTIST,
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
		staffV2SearchCriteria.sort = SORT

		when:
		StaffV2BaseResponse staffBaseResponseOutput = staff.searchV2(staffV2SearchCriteria)

		then:
		1 * staffApiMock.v2SearchStaff(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, BIRTH_NAME, GENDER, DATE_OF_BIRTH_FROM,
				DATE_OF_BIRTH_TO, PLACE_OF_BIRTH, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO, PLACE_OF_DEATH, ART_DEPARTMENT, ART_DIRECTOR,
				PRODUCTION_DESIGNER, CAMERA_AND_ELECTRICAL_DEPARTMENT, CINEMATOGRAPHER, CASTING_DEPARTMENT, COSTUME_DEPARTMENT, COSTUME_DESIGNER,
				DIRECTOR, ASSISTANT_AND_SECOND_UNIT_DIRECTOR, EXHIBIT_AND_ATTRACTION_STAFF, FILM_EDITOR, FILMATION_PRODUCTION_STAFF, LINGUIST,
				LOCATION_STAFF, MAKEUP_STAFF, MERCHANDISE_STAFF, MUSIC_DEPARTMENT, COMPOSER, PERSONAL_ASSISTANT, PRODUCER, PRODUCTION_ASSOCIATE,
				PRODUCTION_STAFF, PUBLICATION_STAFF, SCIENCE_CONSULTANT, SOUND_DEPARTMENT, SPECIAL_AND_VISUAL_EFFECTS_STAFF, AUTHOR, AUDIO_AUTHOR,
				CALENDAR_ARTIST, COMIC_ARTIST, COMIC_AUTHOR, COMIC_COLOR_ARTIST, COMIC_COVER_ARTIST, COMIC_INTERIOR_ARTIST, COMIC_INK_ARTIST,
				COMIC_PENCIL_ARTIST, COMIC_LETTER_ARTIST, COMIC_STRIP_ARTIST, GAME_ARTIST, GAME_AUTHOR, NOVEL_ARTIST, NOVEL_AUTHOR, REFERENCE_ARTIST,
				REFERENCE_AUTHOR, PUBLICATION_ARTIST, PUBLICATION_DESIGNER, PUBLICATION_EDITOR, PUBLICITY_ARTIST, CBS_DIGITAL_STAFF,
				ILM_PRODUCTION_STAFF, SPECIAL_FEATURES_STAFF, STORY_EDITOR, STUDIO_EXECUTIVE, STUNT_DEPARTMENT, TRANSPORTATION_DEPARTMENT,
				VIDEO_GAME_PRODUCTION_STAFF, WRITER) >> staffV2BaseResponse
		0 * _
		staffV2BaseResponse == staffBaseResponseOutput
	}

}
