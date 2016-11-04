package com.cezarykluczynski.stapi.etl.staff.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerCategoriesActorTemplateEnrichingProcessor
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffCategoriesActorTemplateEnrichingProcessor
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffReader
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateListPageProcessor
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplatePageProcessor
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateSinglePageProcessor
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateTemplateProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PageToLifeRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PageToGenderProcessor
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import org.springframework.context.ApplicationContext

class StaffCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_ART_DEPARTMENT = "TITLE_ART_DEPARTMENT"
	private static final String TITLE_ART_DIRECTORS = "TITLE_ART_DIRECTORS"
	private static final String TITLE_PRODUCTION_DESIGNERS = "TITLE_PRODUCTION_DESIGNERS"
	private static final String TITLE_CAMERA_AND_ELECTRICAL_DEPARTMENT = "TITLE_CAMERA_AND_ELECTRICAL_DEPARTMENT"
	private static final String TITLE_CINEMATOGRAPHERS = "TITLE_CINEMATOGRAPHERS"
	private static final String TITLE_CASTING_DEPARTMENT = "TITLE_CASTING_DEPARTMENT"
	private static final String TITLE_COSTUME_DEPARTMENT = "TITLE_COSTUME_DEPARTMENT"
	private static final String TITLE_COSTUME_DESIGNERS = "TITLE_COSTUME_DESIGNERS"
	private static final String TITLE_DIRECTORS = "TITLE_DIRECTORS"
	private static final String TITLE_ASSISTANT_AND_SECOND_UNIT_DIRECTORS = "TITLE_ASSISTANT_AND_SECOND_UNIT_DIRECTORS"
	private static final String TITLE_EXHIBIT_AND_ATTRACTION_STAFF = "TITLE_EXHIBIT_AND_ATTRACTION_STAFF"
	private static final String TITLE_FILM_EDITORS = "TITLE_FILM_EDITORS"
	private static final String TITLE_LINGUISTS = "TITLE_LINGUISTS"
	private static final String TITLE_LOCATION_STAFF = "TITLE_LOCATION_STAFF"
	private static final String TITLE_MAKEUP_STAFF = "TITLE_MAKEUP_STAFF"
	private static final String TITLE_MUSIC_DEPARTMENT = "TITLE_MUSIC_DEPARTMENT"
	private static final String TITLE_COMPOSERS = "TITLE_COMPOSERS"
	private static final String TITLE_PERSONAL_ASSISTANTS = "TITLE_PERSONAL_ASSISTANTS"
	private static final String TITLE_PRODUCERS = "TITLE_PRODUCERS"
	private static final String TITLE_PRODUCTION_ASSOCIATES = "TITLE_PRODUCTION_ASSOCIATES"
	private static final String TITLE_PRODUCTION_STAFF = "TITLE_PRODUCTION_STAFF"
	private static final String TITLE_PUBLICATION_STAFF = "TITLE_PUBLICATION_STAFF"
	private static final String TITLE_SCIENCE_CONSULTANTS = "TITLE_SCIENCE_CONSULTANTS"
	private static final String TITLE_SOUND_DEPARTMENT = "TITLE_SOUND_DEPARTMENT"
	private static final String TITLE_SPECIAL_AND_VISUAL_EFFECTS_STAFF = "TITLE_SPECIAL_AND_VISUAL_EFFECTS_STAFF"
	private static final String TITLE_STAR_TREK_AUTHORS = "TITLE_STAR_TREK_AUTHORS"
	private static final String TITLE_STAR_TREK_AUDIO_AUTHORS = "TITLE_STAR_TREK_AUDIO_AUTHORS"
	private static final String TITLE_STAR_TREK_CALENDAR_ARTISTS = "TITLE_STAR_TREK_CALENDAR_ARTISTS"
	private static final String TITLE_STAR_TREK_COMIC_ARTISTS = "TITLE_STAR_TREK_COMIC_ARTISTS"
	private static final String TITLE_STAR_TREK_COMIC_AUTHORS = "TITLE_STAR_TREK_COMIC_AUTHORS"
	private static final String TITLE_STAR_TREK_COMIC_COLOR_ARTISTS = "TITLE_STAR_TREK_COMIC_COLOR_ARTISTS"
	private static final String TITLE_STAR_TREK_COMIC_INTERIOR_ARTISTS = "TITLE_STAR_TREK_COMIC_INTERIOR_ARTISTS"
	private static final String TITLE_STAR_TREK_COMIC_INK_ARTISTS = "TITLE_STAR_TREK_COMIC_INK_ARTISTS"
	private static final String TITLE_STAR_TREK_COMIC_PENCIL_ARTISTS = "TITLE_STAR_TREK_COMIC_PENCIL_ARTISTS"
	private static final String TITLE_STAR_TREK_COMIC_LETTER_ARTISTS = "TITLE_STAR_TREK_COMIC_LETTER_ARTISTS"
	private static final String TITLE_STAR_TREK_COMIC_STRIP_ARTISTS = "TITLE_STAR_TREK_COMIC_STRIP_ARTISTS"
	private static final String TITLE_STAR_TREK_GAME_ARTISTS = "TITLE_STAR_TREK_GAME_ARTISTS"
	private static final String TITLE_STAR_TREK_GAME_AUTHORS = "TITLE_STAR_TREK_GAME_AUTHORS"
	private static final String TITLE_STAR_TREK_NOVEL_ARTISTS = "TITLE_STAR_TREK_NOVEL_ARTISTS"
	private static final String TITLE_STAR_TREK_NOVEL_AUTHORS = "TITLE_STAR_TREK_NOVEL_AUTHORS"
	private static final String TITLE_STAR_TREK_REFERENCE_ARTISTS = "TITLE_STAR_TREK_REFERENCE_ARTISTS"
	private static final String TITLE_STAR_TREK_REFERENCE_AUTHORS = "TITLE_STAR_TREK_REFERENCE_AUTHORS"
	private static final String TITLE_STAR_TREK_PUBLICATION_ARTISTS = "TITLE_STAR_TREK_PUBLICATION_ARTISTS"
	private static final String TITLE_STAR_TREK_PUBLICATION_DESIGNERS = "TITLE_STAR_TREK_PUBLICATION_DESIGNERS"
	private static final String TITLE_STAR_TREK_PUBLICATION_EDITORS = "TITLE_STAR_TREK_PUBLICATION_EDITORS"
	private static final String TITLE_STAR_TREK_PUBLICITY_ARTISTS = "TITLE_STAR_TREK_PUBLICITY_ARTISTS"
	private static final String TITLE_CBS_DIGITAL_STAFF = "TITLE_CBS_DIGITAL_STAFF"
	private static final String TITLE_ILM_PRODUCTION_STAFF = "TITLE_ILM_PRODUCTION_STAFF"
	private static final String TITLE_SPECIAL_FEATURES_STAFF = "TITLE_SPECIAL_FEATURES_STAFF"
	private static final String TITLE_STORY_EDITORS = "TITLE_STORY_EDITORS"
	private static final String TITLE_STUDIO_EXECUTIVES = "TITLE_STUDIO_EXECUTIVES"
	private static final String TITLE_STUNT_DEPARTMENT = "TITLE_STUNT_DEPARTMENT"
	private static final String TITLE_TRANSPORTATION_DEPARTMENT = "TITLE_TRANSPORTATION_DEPARTMENT"
	private static final String TITLE_VIDEO_GAME_PRODUCTION_STAFF = "TITLE_VIDEO_GAME_PRODUCTION_STAFF"
	private static final String TITLE_WRITERS = "TITLE_WRITERS"

	private ApplicationContext applicationContextMock

	private CategoryApi categoryApiMock

	private StaffCreationConfiguration staffCreationConfiguration

	def setup() {
		applicationContextMock = Mock(ApplicationContext)
		categoryApiMock = Mock(CategoryApi)
		staffCreationConfiguration = new StaffCreationConfiguration(
				applicationContext: applicationContextMock,
				categoryApi: categoryApiMock)
	}

	def "StaffReader is created"() {
		when:
		StaffReader staffReader = staffCreationConfiguration.staffReader()
		List<String> categoryHeaderTitleList = readerToList(staffReader)

		then:
		1 * categoryApiMock.getPages(CategoryName.ART_DEPARTMENT) >> createListWithPageHeaderTitle(TITLE_ART_DEPARTMENT)
		1 * categoryApiMock.getPages(CategoryName.ART_DIRECTORS) >> createListWithPageHeaderTitle(TITLE_ART_DIRECTORS)
		1 * categoryApiMock.getPages(CategoryName.PRODUCTION_DESIGNERS) >> createListWithPageHeaderTitle(TITLE_PRODUCTION_DESIGNERS)
		1 * categoryApiMock.getPages(CategoryName.CAMERA_AND_ELECTRICAL_DEPARTMENT) >> createListWithPageHeaderTitle(TITLE_CAMERA_AND_ELECTRICAL_DEPARTMENT)
		1 * categoryApiMock.getPages(CategoryName.CINEMATOGRAPHERS) >> createListWithPageHeaderTitle(TITLE_CINEMATOGRAPHERS)
		1 * categoryApiMock.getPages(CategoryName.CASTING_DEPARTMENT) >> createListWithPageHeaderTitle(TITLE_CASTING_DEPARTMENT)
		1 * categoryApiMock.getPages(CategoryName.COSTUME_DEPARTMENT) >> createListWithPageHeaderTitle(TITLE_COSTUME_DEPARTMENT)
		1 * categoryApiMock.getPages(CategoryName.COSTUME_DESIGNERS) >> createListWithPageHeaderTitle(TITLE_COSTUME_DESIGNERS)
		1 * categoryApiMock.getPages(CategoryName.DIRECTORS) >> createListWithPageHeaderTitle(TITLE_DIRECTORS)
		1 * categoryApiMock.getPages(CategoryName.ASSISTANT_AND_SECOND_UNIT_DIRECTORS) >> createListWithPageHeaderTitle(TITLE_ASSISTANT_AND_SECOND_UNIT_DIRECTORS)
		1 * categoryApiMock.getPages(CategoryName.EXHIBIT_AND_ATTRACTION_STAFF) >> createListWithPageHeaderTitle(TITLE_EXHIBIT_AND_ATTRACTION_STAFF)
		1 * categoryApiMock.getPages(CategoryName.FILM_EDITORS) >> createListWithPageHeaderTitle(TITLE_FILM_EDITORS)
		1 * categoryApiMock.getPages(CategoryName.LINGUISTS) >> createListWithPageHeaderTitle(TITLE_LINGUISTS)
		1 * categoryApiMock.getPages(CategoryName.LOCATION_STAFF) >> createListWithPageHeaderTitle(TITLE_LOCATION_STAFF)
		1 * categoryApiMock.getPages(CategoryName.MAKEUP_STAFF) >> createListWithPageHeaderTitle(TITLE_MAKEUP_STAFF)
		1 * categoryApiMock.getPages(CategoryName.MUSIC_DEPARTMENT) >> createListWithPageHeaderTitle(TITLE_MUSIC_DEPARTMENT)
		1 * categoryApiMock.getPages(CategoryName.COMPOSERS) >> createListWithPageHeaderTitle(TITLE_COMPOSERS)
		1 * categoryApiMock.getPages(CategoryName.PERSONAL_ASSISTANTS) >> createListWithPageHeaderTitle(TITLE_PERSONAL_ASSISTANTS)
		1 * categoryApiMock.getPages(CategoryName.PRODUCERS) >> createListWithPageHeaderTitle(TITLE_PRODUCERS)
		1 * categoryApiMock.getPages(CategoryName.PRODUCTION_ASSOCIATES) >> createListWithPageHeaderTitle(TITLE_PRODUCTION_ASSOCIATES)
		1 * categoryApiMock.getPages(CategoryName.PRODUCTION_STAFF) >> createListWithPageHeaderTitle(TITLE_PRODUCTION_STAFF)
		1 * categoryApiMock.getPages(CategoryName.PUBLICATION_STAFF) >> createListWithPageHeaderTitle(TITLE_PUBLICATION_STAFF)
		1 * categoryApiMock.getPages(CategoryName.SCIENCE_CONSULTANTS) >> createListWithPageHeaderTitle(TITLE_SCIENCE_CONSULTANTS)
		1 * categoryApiMock.getPages(CategoryName.SOUND_DEPARTMENT) >> createListWithPageHeaderTitle(TITLE_SOUND_DEPARTMENT)
		1 * categoryApiMock.getPages(CategoryName.SPECIAL_AND_VISUAL_EFFECTS_STAFF) >> createListWithPageHeaderTitle(TITLE_SPECIAL_AND_VISUAL_EFFECTS_STAFF)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_AUTHORS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_AUTHORS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_AUDIO_AUTHORS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_AUDIO_AUTHORS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_CALENDAR_ARTISTS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_CALENDAR_ARTISTS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_COMIC_ARTISTS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_COMIC_ARTISTS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_COMIC_AUTHORS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_COMIC_AUTHORS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_COMIC_COLOR_ARTISTS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_COMIC_COLOR_ARTISTS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_COMIC_INTERIOR_ARTISTS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_COMIC_INTERIOR_ARTISTS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_COMIC_INK_ARTISTS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_COMIC_INK_ARTISTS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_COMIC_PENCIL_ARTISTS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_COMIC_PENCIL_ARTISTS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_COMIC_LETTER_ARTISTS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_COMIC_LETTER_ARTISTS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_COMIC_STRIP_ARTISTS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_COMIC_STRIP_ARTISTS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_GAME_ARTISTS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_GAME_ARTISTS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_GAME_AUTHORS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_GAME_AUTHORS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_NOVEL_ARTISTS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_NOVEL_ARTISTS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_NOVEL_AUTHORS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_NOVEL_AUTHORS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_REFERENCE_ARTISTS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_REFERENCE_ARTISTS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_REFERENCE_AUTHORS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_REFERENCE_AUTHORS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_PUBLICATION_ARTISTS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_PUBLICATION_ARTISTS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_PUBLICATION_DESIGNERS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_PUBLICATION_DESIGNERS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_PUBLICATION_EDITORS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_PUBLICATION_EDITORS)
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_PUBLICITY_ARTISTS) >> createListWithPageHeaderTitle(TITLE_STAR_TREK_PUBLICITY_ARTISTS)
		1 * categoryApiMock.getPages(CategoryName.CBS_DIGITAL_STAFF) >> createListWithPageHeaderTitle(TITLE_CBS_DIGITAL_STAFF)
		1 * categoryApiMock.getPages(CategoryName.ILM_PRODUCTION_STAFF) >> createListWithPageHeaderTitle(TITLE_ILM_PRODUCTION_STAFF)
		1 * categoryApiMock.getPages(CategoryName.SPECIAL_FEATURES_STAFF) >> createListWithPageHeaderTitle(TITLE_SPECIAL_FEATURES_STAFF)
		1 * categoryApiMock.getPages(CategoryName.STORY_EDITORS) >> createListWithPageHeaderTitle(TITLE_STORY_EDITORS)
		1 * categoryApiMock.getPages(CategoryName.STUDIO_EXECUTIVES) >> createListWithPageHeaderTitle(TITLE_STUDIO_EXECUTIVES)
		1 * categoryApiMock.getPages(CategoryName.STUNT_DEPARTMENT) >> createListWithPageHeaderTitle(TITLE_STUNT_DEPARTMENT)
		1 * categoryApiMock.getPages(CategoryName.TRANSPORTATION_DEPARTMENT) >> createListWithPageHeaderTitle(TITLE_TRANSPORTATION_DEPARTMENT)
		1 * categoryApiMock.getPages(CategoryName.VIDEO_GAME_PRODUCTION_STAFF) >> createListWithPageHeaderTitle(TITLE_VIDEO_GAME_PRODUCTION_STAFF)
		1 * categoryApiMock.getPages(CategoryName.WRITERS) >> createListWithPageHeaderTitle(TITLE_WRITERS)
		0 * _
		categoryHeaderTitleList.contains TITLE_ART_DEPARTMENT
		categoryHeaderTitleList.contains TITLE_ART_DIRECTORS
		categoryHeaderTitleList.contains TITLE_PRODUCTION_DESIGNERS
		categoryHeaderTitleList.contains TITLE_CAMERA_AND_ELECTRICAL_DEPARTMENT
		categoryHeaderTitleList.contains TITLE_CINEMATOGRAPHERS
		categoryHeaderTitleList.contains TITLE_CASTING_DEPARTMENT
		categoryHeaderTitleList.contains TITLE_COSTUME_DEPARTMENT
		categoryHeaderTitleList.contains TITLE_COSTUME_DESIGNERS
		categoryHeaderTitleList.contains TITLE_DIRECTORS
		categoryHeaderTitleList.contains TITLE_ASSISTANT_AND_SECOND_UNIT_DIRECTORS
		categoryHeaderTitleList.contains TITLE_EXHIBIT_AND_ATTRACTION_STAFF
		categoryHeaderTitleList.contains TITLE_FILM_EDITORS
		categoryHeaderTitleList.contains TITLE_LINGUISTS
		categoryHeaderTitleList.contains TITLE_LOCATION_STAFF
		categoryHeaderTitleList.contains TITLE_MAKEUP_STAFF
		categoryHeaderTitleList.contains TITLE_MUSIC_DEPARTMENT
		categoryHeaderTitleList.contains TITLE_COMPOSERS
		categoryHeaderTitleList.contains TITLE_PERSONAL_ASSISTANTS
		categoryHeaderTitleList.contains TITLE_PRODUCERS
		categoryHeaderTitleList.contains TITLE_PRODUCTION_ASSOCIATES
		categoryHeaderTitleList.contains TITLE_PRODUCTION_STAFF
		categoryHeaderTitleList.contains TITLE_PUBLICATION_STAFF
		categoryHeaderTitleList.contains TITLE_SCIENCE_CONSULTANTS
		categoryHeaderTitleList.contains TITLE_SOUND_DEPARTMENT
		categoryHeaderTitleList.contains TITLE_SPECIAL_AND_VISUAL_EFFECTS_STAFF
		categoryHeaderTitleList.contains TITLE_STAR_TREK_AUTHORS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_AUDIO_AUTHORS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_CALENDAR_ARTISTS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_COMIC_ARTISTS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_COMIC_AUTHORS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_COMIC_COLOR_ARTISTS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_COMIC_INTERIOR_ARTISTS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_COMIC_INK_ARTISTS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_COMIC_PENCIL_ARTISTS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_COMIC_LETTER_ARTISTS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_COMIC_STRIP_ARTISTS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_GAME_ARTISTS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_GAME_AUTHORS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_NOVEL_ARTISTS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_NOVEL_AUTHORS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_REFERENCE_ARTISTS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_REFERENCE_AUTHORS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_PUBLICATION_ARTISTS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_PUBLICATION_DESIGNERS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_PUBLICATION_EDITORS
		categoryHeaderTitleList.contains TITLE_STAR_TREK_PUBLICITY_ARTISTS
		categoryHeaderTitleList.contains TITLE_CBS_DIGITAL_STAFF
		categoryHeaderTitleList.contains TITLE_ILM_PRODUCTION_STAFF
		categoryHeaderTitleList.contains TITLE_SPECIAL_FEATURES_STAFF
		categoryHeaderTitleList.contains TITLE_STORY_EDITORS
		categoryHeaderTitleList.contains TITLE_STUDIO_EXECUTIVES
		categoryHeaderTitleList.contains TITLE_STUNT_DEPARTMENT
		categoryHeaderTitleList.contains TITLE_TRANSPORTATION_DEPARTMENT
		categoryHeaderTitleList.contains TITLE_VIDEO_GAME_PRODUCTION_STAFF
		categoryHeaderTitleList.contains TITLE_WRITERS
	}

	def "ActorTemplateSinglePageProcessor is created"() {
		given:
		PageToGenderProcessor pageToGenderProcessorMock = Mock(PageToGenderProcessor)
		PageToLifeRangeProcessor pageToLifeRangeProcessorMock = Mock(PageToLifeRangeProcessor)
		ActorTemplateTemplateProcessor actorTemplateTemplateProcessorMock = Mock(ActorTemplateTemplateProcessor)
		PerformerCategoriesActorTemplateEnrichingProcessor performerCategoriesActorTemplateEnrichingProcessorMock =
				Mock(PerformerCategoriesActorTemplateEnrichingProcessor)

		when:
		ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessor = staffCreationConfiguration
				.actorTemplateSinglePageProcessor()

		then:
		1 * applicationContextMock.getBean(PageToGenderProcessor) >> pageToGenderProcessorMock
		1 * applicationContextMock.getBean(PageToLifeRangeProcessor) >> pageToLifeRangeProcessorMock
		1 * applicationContextMock.getBean(ActorTemplateTemplateProcessor) >> actorTemplateTemplateProcessorMock
		1 * applicationContextMock.getBean(StaffCategoriesActorTemplateEnrichingProcessor) >>
				performerCategoriesActorTemplateEnrichingProcessorMock
		actorTemplateSinglePageProcessor.pageToGenderProcessor == pageToGenderProcessorMock
		actorTemplateSinglePageProcessor.pageToLifeRangeProcessor == pageToLifeRangeProcessorMock
		actorTemplateSinglePageProcessor.actorTemplateTemplateProcessor == actorTemplateTemplateProcessorMock
		actorTemplateSinglePageProcessor.categoriesActorTemplateEnrichingProcessor == performerCategoriesActorTemplateEnrichingProcessorMock

	}

	def "ActorTemplatePageProcessor is created"() {
		given:
		ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessorMock = Mock(ActorTemplateSinglePageProcessor)
		ActorTemplateListPageProcessor actorTemplateListPageProcessorMock = Mock(ActorTemplateListPageProcessor)

		when:
		ActorTemplatePageProcessor actorTemplatePageProcessor = staffCreationConfiguration
				.actorTemplatePageProcessor()

		then:
		1 * applicationContextMock.getBean(StaffCreationConfiguration.STAFF_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR,
				ActorTemplateSinglePageProcessor) >> actorTemplateSinglePageProcessorMock
		1 * applicationContextMock.getBean(ActorTemplateListPageProcessor) >> actorTemplateListPageProcessorMock
		actorTemplatePageProcessor.actorTemplateSinglePageProcessor == actorTemplateSinglePageProcessorMock
		actorTemplatePageProcessor.actorTemplateListPageProcessor == actorTemplateListPageProcessorMock
	}

}
