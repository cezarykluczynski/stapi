package com.cezarykluczynski.stapi.model.staff.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.model.staff.entity.Staff_
import com.cezarykluczynski.stapi.model.staff.query.StaffQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractRealWorldPersonTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class StaffRepositoryImplTest extends AbstractRealWorldPersonTest {

	private static final Gender GENDER = Gender.F
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private StaffQueryBuilderFactory staffQueryBuilerMock

	private StaffRepositoryImpl staffRepositoryImpl

	private QueryBuilder<Staff> staffQueryBuilder

	private Pageable pageable

	private StaffRequestDTO staffRequestDTO

	private Page page

	def setup() {
		staffQueryBuilerMock = Mock(StaffQueryBuilderFactory)
		staffRepositoryImpl = new StaffRepositoryImpl(staffQueryBuilerMock)
		staffQueryBuilder = Mock(QueryBuilder)
		pageable = Mock(Pageable)
		staffRequestDTO = Mock(StaffRequestDTO)
		page = Mock(Page)
	}

	def "query is built and performed"() {
		when:
		Page pageOutput = staffRepositoryImpl.findMatching(staffRequestDTO, pageable)

		then:
		1 * staffQueryBuilerMock.createQueryBuilder(pageable) >> staffQueryBuilder

		then: 'guid criteria is set'
		1 * staffRequestDTO.getGuid() >> GUID
		1 * staffQueryBuilder.equal(Staff_.guid, GUID)

		then: 'string criteria are set'
		1 * staffRequestDTO.getName() >> NAME
		1 * staffQueryBuilder.like(Staff_.name, NAME)
		1 * staffRequestDTO.getBirthName() >> BIRTH_NAME
		1 * staffQueryBuilder.like(Staff_.birthName, BIRTH_NAME)
		1 * staffRequestDTO.getPlaceOfBirth() >> PLACE_OF_BIRTH
		1 * staffQueryBuilder.like(Staff_.placeOfBirth, PLACE_OF_BIRTH)
		1 * staffRequestDTO.getPlaceOfDeath() >> PLACE_OF_DEATH
		1 * staffQueryBuilder.like(Staff_.placeOfDeath, PLACE_OF_DEATH)

		then: 'date criteria are set'
		1 * staffRequestDTO.getDateOfBirthFrom() >> DATE_OF_BIRTH_FROM
		1 * staffRequestDTO.getDateOfBirthTo() >> DATE_OF_BIRTH_TO
		1 * staffQueryBuilder.between(Staff_.dateOfBirth, DATE_OF_BIRTH_FROM, DATE_OF_BIRTH_TO)
		1 * staffRequestDTO.getDateOfDeathFrom() >> DATE_OF_DEATH_FROM
		1 * staffRequestDTO.getDateOfDeathTo() >> DATE_OF_DEATH_TO
		1 * staffQueryBuilder.between(Staff_.dateOfDeath, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO)

		then: 'enum criteria is set'
		1 * staffRequestDTO.getGender() >> GENDER
		1 * staffQueryBuilder.equal(Staff_.gender, GENDER)

		then: 'boolean criteria are set'
		1 * staffRequestDTO.getArtDepartment() >> ART_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.artDepartment, ART_DEPARTMENT)
		1 * staffRequestDTO.getArtDirector() >> ART_DIRECTOR
		1 * staffQueryBuilder.equal(Staff_.artDirector, ART_DIRECTOR)
		1 * staffRequestDTO.getProductionDesigner() >> PRODUCTION_DESIGNER
		1 * staffQueryBuilder.equal(Staff_.productionDesigner, PRODUCTION_DESIGNER)
		1 * staffRequestDTO.getCameraAndElectricalDepartment() >> CAMERA_AND_ELECTRICAL_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.cameraAndElectricalDepartment, CAMERA_AND_ELECTRICAL_DEPARTMENT)
		1 * staffRequestDTO.getCinematographer() >> CINEMATOGRAPHER
		1 * staffQueryBuilder.equal(Staff_.cinematographer, CINEMATOGRAPHER)
		1 * staffRequestDTO.getCastingDepartment() >> CASTING_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.castingDepartment, CASTING_DEPARTMENT)
		1 * staffRequestDTO.getCostumeDepartment() >> COSTUME_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.costumeDepartment, COSTUME_DEPARTMENT)
		1 * staffRequestDTO.getCostumeDesigner() >> COSTUME_DESIGNER
		1 * staffQueryBuilder.equal(Staff_.costumeDesigner, COSTUME_DESIGNER)
		1 * staffRequestDTO.getDirector() >> DIRECTOR
		1 * staffQueryBuilder.equal(Staff_.director, DIRECTOR)
		1 * staffRequestDTO.getAssistantAndSecondUnitDirector() >> ASSISTANT_AND_SECOND_UNIT_DIRECTOR
		1 * staffQueryBuilder.equal(Staff_.assistantAndSecondUnitDirector, ASSISTANT_AND_SECOND_UNIT_DIRECTOR)
		1 * staffRequestDTO.getExhibitAndAttractionStaff() >> EXHIBIT_AND_ATTRACTION_STAFF
		1 * staffQueryBuilder.equal(Staff_.exhibitAndAttractionStaff, EXHIBIT_AND_ATTRACTION_STAFF)
		1 * staffRequestDTO.getFilmEditor() >> FILM_EDITOR
		1 * staffQueryBuilder.equal(Staff_.filmEditor, FILM_EDITOR)
		1 * staffRequestDTO.getLinguist() >> LINGUIST
		1 * staffQueryBuilder.equal(Staff_.linguist, LINGUIST)
		1 * staffRequestDTO.getLocationStaff() >> LOCATION_STAFF
		1 * staffQueryBuilder.equal(Staff_.locationStaff, LOCATION_STAFF)
		1 * staffRequestDTO.getMakeupStaff() >> MAKEUP_STAFF
		1 * staffQueryBuilder.equal(Staff_.makeupStaff, MAKEUP_STAFF)
		1 * staffRequestDTO.getMusicDepartment() >> MUSIC_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.musicDepartment, MUSIC_DEPARTMENT)
		1 * staffRequestDTO.getComposer() >> COMPOSER
		1 * staffQueryBuilder.equal(Staff_.composer, COMPOSER)
		1 * staffRequestDTO.getPersonalAssistant() >> PERSONAL_ASSISTANT
		1 * staffQueryBuilder.equal(Staff_.personalAssistant, PERSONAL_ASSISTANT)
		1 * staffRequestDTO.getProducer() >> PRODUCER
		1 * staffQueryBuilder.equal(Staff_.producer, PRODUCER)
		1 * staffRequestDTO.getProductionAssociate() >> PRODUCTION_ASSOCIATE
		1 * staffQueryBuilder.equal(Staff_.productionAssociate, PRODUCTION_ASSOCIATE)
		1 * staffRequestDTO.getProductionStaff() >> PRODUCTION_STAFF
		1 * staffQueryBuilder.equal(Staff_.productionStaff, PRODUCTION_STAFF)
		1 * staffRequestDTO.getPublicationStaff() >> PUBLICATION_STAFF
		1 * staffQueryBuilder.equal(Staff_.publicationStaff, PUBLICATION_STAFF)
		1 * staffRequestDTO.getScienceConsultant() >> SCIENCE_CONSULTANT
		1 * staffQueryBuilder.equal(Staff_.scienceConsultant, SCIENCE_CONSULTANT)
		1 * staffRequestDTO.getSoundDepartment() >> SOUND_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.soundDepartment, SOUND_DEPARTMENT)
		1 * staffRequestDTO.getSpecialAndVisualEffectsStaff() >> SPECIAL_AND_VISUAL_EFFECTS_STAFF
		1 * staffQueryBuilder.equal(Staff_.specialAndVisualEffectsStaff, SPECIAL_AND_VISUAL_EFFECTS_STAFF)
		1 * staffRequestDTO.getAuthor() >> AUTHOR
		1 * staffQueryBuilder.equal(Staff_.author, AUTHOR)
		1 * staffRequestDTO.getAudioAuthor() >> AUDIO_AUTHOR
		1 * staffQueryBuilder.equal(Staff_.audioAuthor, AUDIO_AUTHOR)
		1 * staffRequestDTO.getCalendarArtist() >> CALENDAR_ARTIST
		1 * staffQueryBuilder.equal(Staff_.calendarArtist, CALENDAR_ARTIST)
		1 * staffRequestDTO.getComicArtist() >> COMIC_ARTIST
		1 * staffQueryBuilder.equal(Staff_.comicArtist, COMIC_ARTIST)
		1 * staffRequestDTO.getComicAuthor() >> COMIC_AUTHOR
		1 * staffQueryBuilder.equal(Staff_.comicAuthor, COMIC_AUTHOR)
		1 * staffRequestDTO.getComicColorArtist() >> COMIC_COLOR_ARTIST
		1 * staffQueryBuilder.equal(Staff_.comicColorArtist, COMIC_COLOR_ARTIST)
		1 * staffRequestDTO.getComicInteriorArtist() >> COMIC_INTERIOR_ARTIST
		1 * staffQueryBuilder.equal(Staff_.comicInteriorArtist, COMIC_INTERIOR_ARTIST)
		1 * staffRequestDTO.getComicInkArtist() >> COMIC_INK_ARTIST
		1 * staffQueryBuilder.equal(Staff_.comicInkArtist, COMIC_INK_ARTIST)
		1 * staffRequestDTO.getComicPencilArtist() >> COMIC_PENCIL_ARTIST
		1 * staffQueryBuilder.equal(Staff_.comicPencilArtist, COMIC_PENCIL_ARTIST)
		1 * staffRequestDTO.getComicLetterArtist() >> COMIC_LETTER_ARTIST
		1 * staffQueryBuilder.equal(Staff_.comicLetterArtist, COMIC_LETTER_ARTIST)
		1 * staffRequestDTO.getComicStripArtist() >> COMIC_STRIP_ARTIST
		1 * staffQueryBuilder.equal(Staff_.comicStripArtist, COMIC_STRIP_ARTIST)
		1 * staffRequestDTO.getGameArtist() >> GAME_ARTIST
		1 * staffQueryBuilder.equal(Staff_.gameArtist, GAME_ARTIST)
		1 * staffRequestDTO.getGameAuthor() >> GAME_AUTHOR
		1 * staffQueryBuilder.equal(Staff_.gameAuthor, GAME_AUTHOR)
		1 * staffRequestDTO.getNovelArtist() >> NOVEL_ARTIST
		1 * staffQueryBuilder.equal(Staff_.novelArtist, NOVEL_ARTIST)
		1 * staffRequestDTO.getNovelAuthor() >> NOVEL_AUTHOR
		1 * staffQueryBuilder.equal(Staff_.novelAuthor, NOVEL_AUTHOR)
		1 * staffRequestDTO.getReferenceArtist() >> REFERENCE_ARTIST
		1 * staffQueryBuilder.equal(Staff_.referenceArtist, REFERENCE_ARTIST)
		1 * staffRequestDTO.getReferenceAuthor() >> REFERENCE_AUTHOR
		1 * staffQueryBuilder.equal(Staff_.referenceAuthor, REFERENCE_AUTHOR)
		1 * staffRequestDTO.getPublicationArtist() >> PUBLICATION_ARTIST
		1 * staffQueryBuilder.equal(Staff_.publicationArtist, PUBLICATION_ARTIST)
		1 * staffRequestDTO.getPublicationDesigner() >> PUBLICATION_DESIGNER
		1 * staffQueryBuilder.equal(Staff_.publicationDesigner, PUBLICATION_DESIGNER)
		1 * staffRequestDTO.getPublicationEditor() >> PUBLICATION_EDITOR
		1 * staffQueryBuilder.equal(Staff_.publicationEditor, PUBLICATION_EDITOR)
		1 * staffRequestDTO.getPublicityArtist() >> PUBLICITY_ARTIST
		1 * staffQueryBuilder.equal(Staff_.publicityArtist, PUBLICITY_ARTIST)
		1 * staffRequestDTO.getCbsDigitalStaff() >> CBS_DIGITAL_STAFF
		1 * staffQueryBuilder.equal(Staff_.cbsDigitalStaff, CBS_DIGITAL_STAFF)
		1 * staffRequestDTO.getIlmProductionStaff() >> ILM_PRODUCTION_STAFF
		1 * staffQueryBuilder.equal(Staff_.ilmProductionStaff, ILM_PRODUCTION_STAFF)
		1 * staffRequestDTO.getSpecialFeaturesStaff() >> SPECIAL_FEATURES_STAFF
		1 * staffQueryBuilder.equal(Staff_.specialFeaturesStaff, SPECIAL_FEATURES_STAFF)
		1 * staffRequestDTO.getStoryEditor() >> STORY_EDITOR
		1 * staffQueryBuilder.equal(Staff_.storyEditor, STORY_EDITOR)
		1 * staffRequestDTO.getStudioExecutive() >> STUDIO_EXECUTIVE
		1 * staffQueryBuilder.equal(Staff_.studioExecutive, STUDIO_EXECUTIVE)
		1 * staffRequestDTO.getStuntDepartment() >> STUNT_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.stuntDepartment, STUNT_DEPARTMENT)
		1 * staffRequestDTO.getTransportationDepartment() >> TRANSPORTATION_DEPARTMENT
		1 * staffQueryBuilder.equal(Staff_.transportationDepartment, TRANSPORTATION_DEPARTMENT)
		1 * staffRequestDTO.getVideoGameProductionStaff() >> VIDEO_GAME_PRODUCTION_STAFF
		1 * staffQueryBuilder.equal(Staff_.videoGameProductionStaff, VIDEO_GAME_PRODUCTION_STAFF)
		1 * staffRequestDTO.getWriter() >> WRITER
		1 * staffQueryBuilder.equal(Staff_.writer, WRITER)

		then: 'sort is set'
		1 * staffRequestDTO.getSort() >> SORT
		1 * staffQueryBuilder.setSort(SORT)

		then: 'page is searched for and returned'
		1 * staffQueryBuilder.findPage() >> page
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
