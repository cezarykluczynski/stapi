package com.cezarykluczynski.stapi.model.staff.repository

import com.cezarykluczynski.stapi.model.common.entity.Gender
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.model.staff.query.StaffQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractRealWorldPersonTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class StaffRepositoryImplTest extends AbstractRealWorldPersonTest {

	private static final Gender GENDER = Gender.F

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

		then: 'string criteria are set'
		1 * staffRequestDTO.getName() >> NAME
		1 * staffQueryBuilder.like("name", NAME)
		1 * staffRequestDTO.getBirthName() >> BIRTH_NAME
		1 * staffQueryBuilder.like("birthName", BIRTH_NAME)
		1 * staffRequestDTO.getPlaceOfBirth() >> PLACE_OF_BIRTH
		1 * staffQueryBuilder.like("placeOfBirth", PLACE_OF_BIRTH)
		1 * staffRequestDTO.getPlaceOfDeath() >> PLACE_OF_DEATH
		1 * staffQueryBuilder.like("placeOfDeath", PLACE_OF_DEATH)

		then: 'date criteria are set'
		1 * staffRequestDTO.getDateOfBirthFrom() >> DATE_OF_BIRTH_FROM
		1 * staffRequestDTO.getDateOfBirthTo() >> DATE_OF_BIRTH_TO
		1 * staffQueryBuilder.between("dateOfBirth", DATE_OF_BIRTH_FROM, DATE_OF_BIRTH_TO)
		1 * staffRequestDTO.getDateOfDeathFrom() >> DATE_OF_DEATH_FROM
		1 * staffRequestDTO.getDateOfDeathTo() >> DATE_OF_DEATH_TO
		1 * staffQueryBuilder.between("dateOfDeath", DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO)

		then: 'enum criteria is set'
		1 * staffRequestDTO.getGender() >> GENDER
		1 * staffQueryBuilder.equal("gender", GENDER)

		then: 'boolean criteria are set'
		1 * staffRequestDTO.getArtDepartment() >> ART_DEPARTMENT
		1 * staffQueryBuilder.equal("artDepartment", ART_DEPARTMENT)
		1 * staffRequestDTO.getArtDirector() >> ART_DIRECTOR
		1 * staffQueryBuilder.equal("artDirector", ART_DIRECTOR)
		1 * staffRequestDTO.getProductionDesigner() >> PRODUCTION_DESIGNER
		1 * staffQueryBuilder.equal("productionDesigner", PRODUCTION_DESIGNER)
		1 * staffRequestDTO.getCameraAndElectricalDepartment() >> CAMERA_AND_ELECTRICAL_DEPARTMENT
		1 * staffQueryBuilder.equal("cameraAndElectricalDepartment", CAMERA_AND_ELECTRICAL_DEPARTMENT)
		1 * staffRequestDTO.getCinematographer() >> CINEMATOGRAPHER
		1 * staffQueryBuilder.equal("cinematographer", CINEMATOGRAPHER)
		1 * staffRequestDTO.getCastingDepartment() >> CASTING_DEPARTMENT
		1 * staffQueryBuilder.equal("castingDepartment", CASTING_DEPARTMENT)
		1 * staffRequestDTO.getCostumeDepartment() >> COSTUME_DEPARTMENT
		1 * staffQueryBuilder.equal("costumeDepartment", COSTUME_DEPARTMENT)
		1 * staffRequestDTO.getCostumeDesigner() >> COSTUME_DESIGNER
		1 * staffQueryBuilder.equal("costumeDesigner", COSTUME_DESIGNER)
		1 * staffRequestDTO.getDirector() >> DIRECTOR
		1 * staffQueryBuilder.equal("director", DIRECTOR)
		1 * staffRequestDTO.getAssistantAndSecondUnitDirector() >> ASSISTANT_AND_SECOND_UNIT_DIRECTOR
		1 * staffQueryBuilder.equal("assistantAndSecondUnitDirector", ASSISTANT_AND_SECOND_UNIT_DIRECTOR)
		1 * staffRequestDTO.getExhibitAndAttractionStaff() >> EXHIBIT_AND_ATTRACTION_STAFF
		1 * staffQueryBuilder.equal("exhibitAndAttractionStaff", EXHIBIT_AND_ATTRACTION_STAFF)
		1 * staffRequestDTO.getFilmEditor() >> FILM_EDITOR
		1 * staffQueryBuilder.equal("filmEditor", FILM_EDITOR)
		1 * staffRequestDTO.getLinguist() >> LINGUIST
		1 * staffQueryBuilder.equal("linguist", LINGUIST)
		1 * staffRequestDTO.getLocationStaff() >> LOCATION_STAFF
		1 * staffQueryBuilder.equal("locationStaff", LOCATION_STAFF)
		1 * staffRequestDTO.getMakeupStaff() >> MAKEUP_STAFF
		1 * staffQueryBuilder.equal("makeupStaff", MAKEUP_STAFF)
		1 * staffRequestDTO.getMusicDepartment() >> MUSIC_DEPARTMENT
		1 * staffQueryBuilder.equal("musicDepartment", MUSIC_DEPARTMENT)
		1 * staffRequestDTO.getComposer() >> COMPOSER
		1 * staffQueryBuilder.equal("composer", COMPOSER)
		1 * staffRequestDTO.getPersonalAssistant() >> PERSONAL_ASSISTANT
		1 * staffQueryBuilder.equal("personalAssistant", PERSONAL_ASSISTANT)
		1 * staffRequestDTO.getProducer() >> PRODUCER
		1 * staffQueryBuilder.equal("producer", PRODUCER)
		1 * staffRequestDTO.getProductionAssociate() >> PRODUCTION_ASSOCIATE
		1 * staffQueryBuilder.equal("productionAssociate", PRODUCTION_ASSOCIATE)
		1 * staffRequestDTO.getProductionStaff() >> PRODUCTION_STAFF
		1 * staffQueryBuilder.equal("productionStaff", PRODUCTION_STAFF)
		1 * staffRequestDTO.getPublicationStaff() >> PUBLICATION_STAFF
		1 * staffQueryBuilder.equal("publicationStaff", PUBLICATION_STAFF)
		1 * staffRequestDTO.getScienceConsultant() >> SCIENCE_CONSULTANT
		1 * staffQueryBuilder.equal("scienceConsultant", SCIENCE_CONSULTANT)
		1 * staffRequestDTO.getSoundDepartment() >> SOUND_DEPARTMENT
		1 * staffQueryBuilder.equal("soundDepartment", SOUND_DEPARTMENT)
		1 * staffRequestDTO.getSpecialAndVisualEffectsStaff() >> SPECIAL_AND_VISUAL_EFFECTS_STAFF
		1 * staffQueryBuilder.equal("specialAndVisualEffectsStaff", SPECIAL_AND_VISUAL_EFFECTS_STAFF)
		1 * staffRequestDTO.getAuthor() >> AUTHOR
		1 * staffQueryBuilder.equal("author", AUTHOR)
		1 * staffRequestDTO.getAudioAuthor() >> AUDIO_AUTHOR
		1 * staffQueryBuilder.equal("audioAuthor", AUDIO_AUTHOR)
		1 * staffRequestDTO.getCalendarArtist() >> CALENDAR_ARTIST
		1 * staffQueryBuilder.equal("calendarArtist", CALENDAR_ARTIST)
		1 * staffRequestDTO.getComicArtist() >> COMIC_ARTIST
		1 * staffQueryBuilder.equal("comicArtist", COMIC_ARTIST)
		1 * staffRequestDTO.getComicAuthor() >> COMIC_AUTHOR
		1 * staffQueryBuilder.equal("comicAuthor", COMIC_AUTHOR)
		1 * staffRequestDTO.getComicColorArtist() >> COMIC_COLOR_ARTIST
		1 * staffQueryBuilder.equal("comicColorArtist", COMIC_COLOR_ARTIST)
		1 * staffRequestDTO.getComicInteriorArtist() >> COMIC_INTERIOR_ARTIST
		1 * staffQueryBuilder.equal("comicInteriorArtist", COMIC_INTERIOR_ARTIST)
		1 * staffRequestDTO.getComicInkArtist() >> COMIC_INK_ARTIST
		1 * staffQueryBuilder.equal("comicInkArtist", COMIC_INK_ARTIST)
		1 * staffRequestDTO.getComicPencilArtist() >> COMIC_PENCIL_ARTIST
		1 * staffQueryBuilder.equal("comicPencilArtist", COMIC_PENCIL_ARTIST)
		1 * staffRequestDTO.getComicLetterArtist() >> COMIC_LETTER_ARTIST
		1 * staffQueryBuilder.equal("comicLetterArtist", COMIC_LETTER_ARTIST)
		1 * staffRequestDTO.getComicStripArtist() >> COMIC_STRIP_ARTIST
		1 * staffQueryBuilder.equal("comicStripArtist", COMIC_STRIP_ARTIST)
		1 * staffRequestDTO.getGameArtist() >> GAME_ARTIST
		1 * staffQueryBuilder.equal("gameArtist", GAME_ARTIST)
		1 * staffRequestDTO.getGameAuthor() >> GAME_AUTHOR
		1 * staffQueryBuilder.equal("gameAuthor", GAME_AUTHOR)
		1 * staffRequestDTO.getNovelArtist() >> NOVEL_ARTIST
		1 * staffQueryBuilder.equal("novelArtist", NOVEL_ARTIST)
		1 * staffRequestDTO.getNovelAuthor() >> NOVEL_AUTHOR
		1 * staffQueryBuilder.equal("novelAuthor", NOVEL_AUTHOR)
		1 * staffRequestDTO.getReferenceArtist() >> REFERENCE_ARTIST
		1 * staffQueryBuilder.equal("referenceArtist", REFERENCE_ARTIST)
		1 * staffRequestDTO.getReferenceAuthor() >> REFERENCE_AUTHOR
		1 * staffQueryBuilder.equal("referenceAuthor", REFERENCE_AUTHOR)
		1 * staffRequestDTO.getPublicationArtist() >> PUBLICATION_ARTIST
		1 * staffQueryBuilder.equal("publicationArtist", PUBLICATION_ARTIST)
		1 * staffRequestDTO.getPublicationDesigner() >> PUBLICATION_DESIGNER
		1 * staffQueryBuilder.equal("publicationDesigner", PUBLICATION_DESIGNER)
		1 * staffRequestDTO.getPublicationEditor() >> PUBLICATION_EDITOR
		1 * staffQueryBuilder.equal("publicationEditor", PUBLICATION_EDITOR)
		1 * staffRequestDTO.getPublicityArtist() >> PUBLICITY_ARTIST
		1 * staffQueryBuilder.equal("publicityArtist", PUBLICITY_ARTIST)
		1 * staffRequestDTO.getCbsDigitalStaff() >> CBS_DIGITAL_STAFF
		1 * staffQueryBuilder.equal("cbsDigitalStaff", CBS_DIGITAL_STAFF)
		1 * staffRequestDTO.getIlmProductionStaff() >> ILM_PRODUCTION_STAFF
		1 * staffQueryBuilder.equal("ilmProductionStaff", ILM_PRODUCTION_STAFF)
		1 * staffRequestDTO.getSpecialFeaturesStaff() >> SPECIAL_FEATURES_STAFF
		1 * staffQueryBuilder.equal("specialFeaturesStaff", SPECIAL_FEATURES_STAFF)
		1 * staffRequestDTO.getStoryEditor() >> STORY_EDITOR
		1 * staffQueryBuilder.equal("storyEditor", STORY_EDITOR)
		1 * staffRequestDTO.getStudioExecutive() >> STUDIO_EXECUTIVE
		1 * staffQueryBuilder.equal("studioExecutive", STUDIO_EXECUTIVE)
		1 * staffRequestDTO.getStuntDepartment() >> STUNT_DEPARTMENT
		1 * staffQueryBuilder.equal("stuntDepartment", STUNT_DEPARTMENT)
		1 * staffRequestDTO.getTransportationDepartment() >> TRANSPORTATION_DEPARTMENT
		1 * staffQueryBuilder.equal("transportationDepartment", TRANSPORTATION_DEPARTMENT)
		1 * staffRequestDTO.getVideoGameProductionStaff() >> VIDEO_GAME_PRODUCTION_STAFF
		1 * staffQueryBuilder.equal("videoGameProductionStaff", VIDEO_GAME_PRODUCTION_STAFF)
		1 * staffRequestDTO.getWriter() >> WRITER
		1 * staffQueryBuilder.equal("writer", WRITER)

		then: 'page is searched for and returned'
		1 * staffQueryBuilder.findPage() >> page
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
