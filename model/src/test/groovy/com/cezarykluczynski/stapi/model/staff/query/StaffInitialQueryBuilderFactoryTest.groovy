package com.cezarykluczynski.stapi.model.staff.query

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.model.staff.entity.Staff_
import com.cezarykluczynski.stapi.util.AbstractRealWorldPersonTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class StaffInitialQueryBuilderFactoryTest extends AbstractRealWorldPersonTest {

	private static final Gender GENDER = Gender.F
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private StaffQueryBuilderFactory staffQueryBuilderMock

	private StaffInitialQueryBuilderFactory staffInitialQueryBuilderFactory

	private QueryBuilder<Staff> staffQueryBuilder

	private Pageable pageable

	private StaffRequestDTO staffRequestDTO

	private Staff staff

	private Page page

	void setup() {
		staffQueryBuilderMock = Mock()
		staffInitialQueryBuilderFactory = new StaffInitialQueryBuilderFactory(staffQueryBuilderMock)
		staffQueryBuilder = Mock()
		pageable = Mock()
		staffRequestDTO = Mock()
		staff = Mock()
		page = Mock()
	}

	void "initial query builder is built, then returned"() {
		when:
		QueryBuilder<Staff> staffQueryBuilderOutput = staffInitialQueryBuilderFactory.createInitialQueryBuilder(staffRequestDTO, pageable)

		then:
		1 * staffQueryBuilderMock.createQueryBuilder(pageable) >> staffQueryBuilder

		then: 'uid criteria is set'
		1 * staffRequestDTO.uid >> UID
		1 * staffQueryBuilder.equal(Staff_.uid, UID)

		then: 'string criteria are set'
		1 * staffRequestDTO.name >> NAME
		1 * staffQueryBuilder.like(Staff_.name, NAME)
		1 * staffRequestDTO.birthName >> BIRTH_NAME
		1 * staffQueryBuilder.like(Staff_.birthName, BIRTH_NAME)
		1 * staffRequestDTO.placeOfBirth >> PLACE_OF_BIRTH
		1 * staffQueryBuilder.like(Staff_.placeOfBirth, PLACE_OF_BIRTH)
		1 * staffRequestDTO.placeOfDeath >> PLACE_OF_DEATH
		1 * staffQueryBuilder.like(Staff_.placeOfDeath, PLACE_OF_DEATH)

		then: 'date criteria are set'
		1 * staffRequestDTO.dateOfBirthFrom >> DATE_OF_BIRTH_FROM
		1 * staffRequestDTO.dateOfBirthTo >> DATE_OF_BIRTH_TO
		1 * staffQueryBuilder.between(Staff_.dateOfBirth, DATE_OF_BIRTH_FROM, DATE_OF_BIRTH_TO)
		1 * staffRequestDTO.dateOfDeathFrom >> DATE_OF_DEATH_FROM
		1 * staffRequestDTO.dateOfDeathTo >> DATE_OF_DEATH_TO
		1 * staffQueryBuilder.between(Staff_.dateOfDeath, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO)

		then: 'enum criteria is set'
		1 * staffRequestDTO.gender >> GENDER
		1 * staffQueryBuilder.equal(Staff_.gender, GENDER)

		then: 'boolean criteria are set'
		1 * staffRequestDTO.artDepartment >> ART_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.artDepartment, ART_DEPARTMENT)
		1 * staffRequestDTO.artDirector >> ART_DIRECTOR
		1 * staffQueryBuilder.equal(Staff_.artDirector, ART_DIRECTOR)
		1 * staffRequestDTO.productionDesigner >> PRODUCTION_DESIGNER
		1 * staffQueryBuilder.equal(Staff_.productionDesigner, PRODUCTION_DESIGNER)
		1 * staffRequestDTO.cameraAndElectricalDepartment >> CAMERA_AND_ELECTRICAL_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.cameraAndElectricalDepartment, CAMERA_AND_ELECTRICAL_DEPARTMENT)
		1 * staffRequestDTO.cinematographer >> CINEMATOGRAPHER
		1 * staffQueryBuilder.equal(Staff_.cinematographer, CINEMATOGRAPHER)
		1 * staffRequestDTO.castingDepartment >> CASTING_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.castingDepartment, CASTING_DEPARTMENT)
		1 * staffRequestDTO.costumeDepartment >> COSTUME_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.costumeDepartment, COSTUME_DEPARTMENT)
		1 * staffRequestDTO.costumeDesigner >> COSTUME_DESIGNER
		1 * staffQueryBuilder.equal(Staff_.costumeDesigner, COSTUME_DESIGNER)
		1 * staffRequestDTO.director >> DIRECTOR
		1 * staffQueryBuilder.equal(Staff_.director, DIRECTOR)
		1 * staffRequestDTO.assistantOrSecondUnitDirector >> ASSISTANT_AND_SECOND_UNIT_DIRECTOR
		1 * staffQueryBuilder.equal(Staff_.assistantOrSecondUnitDirector, ASSISTANT_AND_SECOND_UNIT_DIRECTOR)
		1 * staffRequestDTO.exhibitAndAttractionStaff >> EXHIBIT_AND_ATTRACTION_STAFF
		1 * staffQueryBuilder.equal(Staff_.exhibitAndAttractionStaff, EXHIBIT_AND_ATTRACTION_STAFF)
		1 * staffRequestDTO.filmEditor >> FILM_EDITOR
		1 * staffQueryBuilder.equal(Staff_.filmEditor, FILM_EDITOR)
		1 * staffRequestDTO.linguist >> LINGUIST
		1 * staffQueryBuilder.equal(Staff_.linguist, LINGUIST)
		1 * staffRequestDTO.locationStaff >> LOCATION_STAFF
		1 * staffQueryBuilder.equal(Staff_.locationStaff, LOCATION_STAFF)
		1 * staffRequestDTO.makeupStaff >> MAKEUP_STAFF
		1 * staffQueryBuilder.equal(Staff_.makeupStaff, MAKEUP_STAFF)
		1 * staffRequestDTO.musicDepartment >> MUSIC_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.musicDepartment, MUSIC_DEPARTMENT)
		1 * staffRequestDTO.composer >> COMPOSER
		1 * staffQueryBuilder.equal(Staff_.composer, COMPOSER)
		1 * staffRequestDTO.personalAssistant >> PERSONAL_ASSISTANT
		1 * staffQueryBuilder.equal(Staff_.personalAssistant, PERSONAL_ASSISTANT)
		1 * staffRequestDTO.producer >> PRODUCER
		1 * staffQueryBuilder.equal(Staff_.producer, PRODUCER)
		1 * staffRequestDTO.productionAssociate >> PRODUCTION_ASSOCIATE
		1 * staffQueryBuilder.equal(Staff_.productionAssociate, PRODUCTION_ASSOCIATE)
		1 * staffRequestDTO.productionStaff >> PRODUCTION_STAFF
		1 * staffQueryBuilder.equal(Staff_.productionStaff, PRODUCTION_STAFF)
		1 * staffRequestDTO.publicationStaff >> PUBLICATION_STAFF
		1 * staffQueryBuilder.equal(Staff_.publicationStaff, PUBLICATION_STAFF)
		1 * staffRequestDTO.scienceConsultant >> SCIENCE_CONSULTANT
		1 * staffQueryBuilder.equal(Staff_.scienceConsultant, SCIENCE_CONSULTANT)
		1 * staffRequestDTO.soundDepartment >> SOUND_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.soundDepartment, SOUND_DEPARTMENT)
		1 * staffRequestDTO.specialAndVisualEffectsStaff >> SPECIAL_AND_VISUAL_EFFECTS_STAFF
		1 * staffQueryBuilder.equal(Staff_.specialAndVisualEffectsStaff, SPECIAL_AND_VISUAL_EFFECTS_STAFF)
		1 * staffRequestDTO.author >> AUTHOR
		1 * staffQueryBuilder.equal(Staff_.author, AUTHOR)
		1 * staffRequestDTO.audioAuthor >> AUDIO_AUTHOR
		1 * staffQueryBuilder.equal(Staff_.audioAuthor, AUDIO_AUTHOR)
		1 * staffRequestDTO.calendarArtist >> CALENDAR_ARTIST
		1 * staffQueryBuilder.equal(Staff_.calendarArtist, CALENDAR_ARTIST)
		1 * staffRequestDTO.comicArtist >> COMIC_ARTIST
		1 * staffQueryBuilder.equal(Staff_.comicArtist, COMIC_ARTIST)
		1 * staffRequestDTO.comicAuthor >> COMIC_AUTHOR
		1 * staffQueryBuilder.equal(Staff_.comicAuthor, COMIC_AUTHOR)
		1 * staffRequestDTO.comicColorArtist >> COMIC_COLOR_ARTIST
		1 * staffQueryBuilder.equal(Staff_.comicColorArtist, COMIC_COLOR_ARTIST)
		1 * staffRequestDTO.comicInteriorArtist >> COMIC_INTERIOR_ARTIST
		1 * staffQueryBuilder.equal(Staff_.comicInteriorArtist, COMIC_INTERIOR_ARTIST)
		1 * staffRequestDTO.comicInkArtist >> COMIC_INK_ARTIST
		1 * staffQueryBuilder.equal(Staff_.comicInkArtist, COMIC_INK_ARTIST)
		1 * staffRequestDTO.comicPencilArtist >> COMIC_PENCIL_ARTIST
		1 * staffQueryBuilder.equal(Staff_.comicPencilArtist, COMIC_PENCIL_ARTIST)
		1 * staffRequestDTO.comicLetterArtist >> COMIC_LETTER_ARTIST
		1 * staffQueryBuilder.equal(Staff_.comicLetterArtist, COMIC_LETTER_ARTIST)
		1 * staffRequestDTO.comicStripArtist >> COMIC_STRIP_ARTIST
		1 * staffQueryBuilder.equal(Staff_.comicStripArtist, COMIC_STRIP_ARTIST)
		1 * staffRequestDTO.gameArtist >> GAME_ARTIST
		1 * staffQueryBuilder.equal(Staff_.gameArtist, GAME_ARTIST)
		1 * staffRequestDTO.gameAuthor >> GAME_AUTHOR
		1 * staffQueryBuilder.equal(Staff_.gameAuthor, GAME_AUTHOR)
		1 * staffRequestDTO.novelArtist >> NOVEL_ARTIST
		1 * staffQueryBuilder.equal(Staff_.novelArtist, NOVEL_ARTIST)
		1 * staffRequestDTO.novelAuthor >> NOVEL_AUTHOR
		1 * staffQueryBuilder.equal(Staff_.novelAuthor, NOVEL_AUTHOR)
		1 * staffRequestDTO.referenceArtist >> REFERENCE_ARTIST
		1 * staffQueryBuilder.equal(Staff_.referenceArtist, REFERENCE_ARTIST)
		1 * staffRequestDTO.referenceAuthor >> REFERENCE_AUTHOR
		1 * staffQueryBuilder.equal(Staff_.referenceAuthor, REFERENCE_AUTHOR)
		1 * staffRequestDTO.publicationArtist >> PUBLICATION_ARTIST
		1 * staffQueryBuilder.equal(Staff_.publicationArtist, PUBLICATION_ARTIST)
		1 * staffRequestDTO.publicationDesigner >> PUBLICATION_DESIGNER
		1 * staffQueryBuilder.equal(Staff_.publicationDesigner, PUBLICATION_DESIGNER)
		1 * staffRequestDTO.publicationEditor >> PUBLICATION_EDITOR
		1 * staffQueryBuilder.equal(Staff_.publicationEditor, PUBLICATION_EDITOR)
		1 * staffRequestDTO.publicityArtist >> PUBLICITY_ARTIST
		1 * staffQueryBuilder.equal(Staff_.publicityArtist, PUBLICITY_ARTIST)
		1 * staffRequestDTO.cbsDigitalStaff >> CBS_DIGITAL_STAFF
		1 * staffQueryBuilder.equal(Staff_.cbsDigitalStaff, CBS_DIGITAL_STAFF)
		1 * staffRequestDTO.ilmProductionStaff >> ILM_PRODUCTION_STAFF
		1 * staffQueryBuilder.equal(Staff_.ilmProductionStaff, ILM_PRODUCTION_STAFF)
		1 * staffRequestDTO.specialFeaturesStaff >> SPECIAL_FEATURES_STAFF
		1 * staffQueryBuilder.equal(Staff_.specialFeaturesStaff, SPECIAL_FEATURES_STAFF)
		1 * staffRequestDTO.storyEditor >> STORY_EDITOR
		1 * staffQueryBuilder.equal(Staff_.storyEditor, STORY_EDITOR)
		1 * staffRequestDTO.studioExecutive >> STUDIO_EXECUTIVE
		1 * staffQueryBuilder.equal(Staff_.studioExecutive, STUDIO_EXECUTIVE)
		1 * staffRequestDTO.stuntDepartment >> STUNT_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.stuntDepartment, STUNT_DEPARTMENT)
		1 * staffRequestDTO.transportationDepartment >> TRANSPORTATION_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.transportationDepartment, TRANSPORTATION_DEPARTMENT)
		1 * staffRequestDTO.videoGameProductionStaff >> VIDEO_GAME_PRODUCTION_STAFF
		1 * staffQueryBuilder.equal(Staff_.videoGameProductionStaff, VIDEO_GAME_PRODUCTION_STAFF)
		1 * staffRequestDTO.writer >> WRITER
		1 * staffQueryBuilder.equal(Staff_.writer, WRITER)

		then: 'sort is set'
		1 * staffRequestDTO.sort >> SORT
		1 * staffQueryBuilder.setSort(SORT)

		then: 'query builder is returned'
		staffQueryBuilderOutput == staffQueryBuilder

		then: 'no other interactions are expected'
		0 * _
	}

}
