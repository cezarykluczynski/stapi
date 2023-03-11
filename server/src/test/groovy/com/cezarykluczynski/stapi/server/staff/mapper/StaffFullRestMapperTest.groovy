package com.cezarykluczynski.stapi.server.staff.mapper

import com.cezarykluczynski.stapi.client.rest.model.StaffFull
import com.cezarykluczynski.stapi.client.rest.model.StaffV2Full
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import org.mapstruct.factory.Mappers

class StaffFullRestMapperTest extends AbstractStaffMapperTest {

	private StaffFullRestMapper staffFullRestMapper

	void setup() {
		staffFullRestMapper = Mappers.getMapper(StaffFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Staff staff = createStaff()

		when:
		StaffFull staffFull = staffFullRestMapper.mapFull(staff)

		then:
		staffFull.name == NAME
		staffFull.uid == UID
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
		staffFull.assistantOrSecondUnitDirector == ASSISTANT_AND_SECOND_UNIT_DIRECTOR
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
		staffFull.writtenEpisodes.size() == staff.writtenEpisodes.size()
		staffFull.teleplayAuthoredEpisodes.size() == staff.teleplayAuthoredEpisodes.size()
		staffFull.storyAuthoredEpisodes.size() == staff.storyAuthoredEpisodes.size()
		staffFull.directedEpisodes.size() == staff.directedEpisodes.size()
		staffFull.episodes.size() == staff.episodes.size()
		staffFull.writtenMovies.size() == staff.writtenMovies.size()
		staffFull.screenplayAuthoredMovies.size() == staff.screenplayAuthoredMovies.size()
		staffFull.storyAuthoredMovies.size() == staff.storyAuthoredMovies.size()
		staffFull.directedMovies.size() == staff.directedMovies.size()
		staffFull.producedMovies.size() == staff.producedMovies.size()
		staffFull.movies.size() == staff.movies.size()
	}

	void "maps DB entity to full REST V2 entity"() {
		given:
		Staff staff = createStaff()

		when:
		StaffV2Full staffV2Full = staffFullRestMapper.mapV2Full(staff)

		then:
		staffV2Full.name == NAME
		staffV2Full.uid == UID
		staffV2Full.birthName == BIRTH_NAME
		staffV2Full.gender == GENDER_ENUM_REST
		staffV2Full.dateOfBirth == DATE_OF_BIRTH_FROM_DB
		staffV2Full.dateOfDeath == DATE_OF_DEATH_FROM_DB
		staffV2Full.placeOfBirth == PLACE_OF_BIRTH
		staffV2Full.placeOfDeath == PLACE_OF_DEATH
		staffV2Full.artDepartment == ART_DEPARTMENT
		staffV2Full.artDirector == ART_DIRECTOR
		staffV2Full.productionDesigner == PRODUCTION_DESIGNER
		staffV2Full.cameraAndElectricalDepartment == CAMERA_AND_ELECTRICAL_DEPARTMENT
		staffV2Full.cinematographer == CINEMATOGRAPHER
		staffV2Full.castingDepartment == CASTING_DEPARTMENT
		staffV2Full.costumeDepartment == COSTUME_DEPARTMENT
		staffV2Full.costumeDesigner == COSTUME_DESIGNER
		staffV2Full.director == DIRECTOR
		staffV2Full.assistantOrSecondUnitDirector == ASSISTANT_AND_SECOND_UNIT_DIRECTOR
		staffV2Full.exhibitAndAttractionStaff == EXHIBIT_AND_ATTRACTION_STAFF
		staffV2Full.filmEditor == FILM_EDITOR
		staffV2Full.filmationProductionStaff == FILMATION_PRODUCTION_STAFF
		staffV2Full.linguist == LINGUIST
		staffV2Full.locationStaff == LOCATION_STAFF
		staffV2Full.makeupStaff == MAKEUP_STAFF
		staffV2Full.merchandiseStaff == MERCHANDISE_STAFF
		staffV2Full.musicDepartment == MUSIC_DEPARTMENT
		staffV2Full.composer == COMPOSER
		staffV2Full.personalAssistant == PERSONAL_ASSISTANT
		staffV2Full.producer == PRODUCER
		staffV2Full.productionAssociate == PRODUCTION_ASSOCIATE
		staffV2Full.productionStaff == PRODUCTION_STAFF
		staffV2Full.publicationStaff == PUBLICATION_STAFF
		staffV2Full.scienceConsultant == SCIENCE_CONSULTANT
		staffV2Full.soundDepartment == SOUND_DEPARTMENT
		staffV2Full.specialAndVisualEffectsStaff == SPECIAL_AND_VISUAL_EFFECTS_STAFF
		staffV2Full.author == AUTHOR
		staffV2Full.audioAuthor == AUDIO_AUTHOR
		staffV2Full.calendarArtist == CALENDAR_ARTIST
		staffV2Full.comicArtist == COMIC_ARTIST
		staffV2Full.comicAuthor == COMIC_AUTHOR
		staffV2Full.comicColorArtist == COMIC_COLOR_ARTIST
		staffV2Full.comicCoverArtist == COMIC_COVER_ARTIST
		staffV2Full.comicInteriorArtist == COMIC_INTERIOR_ARTIST
		staffV2Full.comicInkArtist == COMIC_INK_ARTIST
		staffV2Full.comicPencilArtist == COMIC_PENCIL_ARTIST
		staffV2Full.comicLetterArtist == COMIC_LETTER_ARTIST
		staffV2Full.comicStripArtist == COMIC_STRIP_ARTIST
		staffV2Full.gameArtist == GAME_ARTIST
		staffV2Full.gameAuthor == GAME_AUTHOR
		staffV2Full.novelArtist == NOVEL_ARTIST
		staffV2Full.novelAuthor == NOVEL_AUTHOR
		staffV2Full.referenceArtist == REFERENCE_ARTIST
		staffV2Full.referenceAuthor == REFERENCE_AUTHOR
		staffV2Full.publicationArtist == PUBLICATION_ARTIST
		staffV2Full.publicationDesigner == PUBLICATION_DESIGNER
		staffV2Full.publicationEditor == PUBLICATION_EDITOR
		staffV2Full.publicityArtist == PUBLICITY_ARTIST
		staffV2Full.cbsDigitalStaff == CBS_DIGITAL_STAFF
		staffV2Full.ilmProductionStaff == ILM_PRODUCTION_STAFF
		staffV2Full.specialFeaturesStaff == SPECIAL_FEATURES_STAFF
		staffV2Full.storyEditor == STORY_EDITOR
		staffV2Full.studioExecutive == STUDIO_EXECUTIVE
		staffV2Full.stuntDepartment == STUNT_DEPARTMENT
		staffV2Full.transportationDepartment == TRANSPORTATION_DEPARTMENT
		staffV2Full.videoGameProductionStaff == VIDEO_GAME_PRODUCTION_STAFF
		staffV2Full.writer == WRITER
		staffV2Full.writtenEpisodes.size() == staff.writtenEpisodes.size()
		staffV2Full.teleplayAuthoredEpisodes.size() == staff.teleplayAuthoredEpisodes.size()
		staffV2Full.storyAuthoredEpisodes.size() == staff.storyAuthoredEpisodes.size()
		staffV2Full.directedEpisodes.size() == staff.directedEpisodes.size()
		staffV2Full.episodes.size() == staff.episodes.size()
		staffV2Full.writtenMovies.size() == staff.writtenMovies.size()
		staffV2Full.screenplayAuthoredMovies.size() == staff.screenplayAuthoredMovies.size()
		staffV2Full.storyAuthoredMovies.size() == staff.storyAuthoredMovies.size()
		staffV2Full.directedMovies.size() == staff.directedMovies.size()
		staffV2Full.producedMovies.size() == staff.producedMovies.size()
		staffV2Full.movies.size() == staff.movies.size()
	}

}
