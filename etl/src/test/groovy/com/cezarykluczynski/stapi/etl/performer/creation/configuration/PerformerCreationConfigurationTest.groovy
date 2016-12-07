package com.cezarykluczynski.stapi.etl.performer.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.common.service.JobCompletenessDecider
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerCategoriesActorTemplateEnrichingProcessor
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerReader
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateListPageProcessor
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplatePageProcessor
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateSinglePageProcessor
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateTemplateProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PageToLifeRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PageToGenderProcessor
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import org.springframework.context.ApplicationContext

class PerformerCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_PERFORMERS = 'TITLE_PERFORMERS'
	private static final String TITLE_ANIMAL_PERFORMERS = "TITLE_ANIMAL_PERFORMERS"
	private static final String TITLE_DIS_PERFORMERS = "TITLE_DIS_PERFORMERS"
	private static final String TITLE_DS9_PERFORMERS = "TITLE_DS9_PERFORMERS"
	private static final String TITLE_ENT_PERFORMERS = "TITLE_ENT_PERFORMERS"
	private static final String TITLE_FILM_PERFORMERS = "TITLE_FILM_PERFORMERS"
	private static final String TITLE_STAND_INS = "TITLE_STAND_INS"
	private static final String TITLE_STUNT_PERFORMERS = "TITLE_STUNT_PERFORMERS"
	private static final String TITLE_TAS_PERFORMERS = "TITLE_TAS_PERFORMERS"
	private static final String TITLE_TNG_PERFORMERS = "TITLE_TNG_PERFORMERS"
	private static final String TITLE_TOS_PERFORMERS = "TITLE_TOS_PERFORMERS"
	private static final String TITLE_VIDEO_GAME_PERFORMERS = "TITLE_VIDEO_GAME_PERFORMERS"
	private static final String TITLE_VOICE_PERFORMERS = "TITLE_VOICE_PERFORMERS"
	private static final String TITLE_VOY_PERFORMERS = "TITLE_VOY_PERFORMERS"

	private ApplicationContext applicationContextMock

	private CategoryApi categoryApiMock

	private JobCompletenessDecider jobCompletenessDeciderMock

	private PerformerCreationConfiguration performerCreationConfiguration

	def setup() {
		applicationContextMock = Mock(ApplicationContext)
		categoryApiMock = Mock(CategoryApi)
		jobCompletenessDeciderMock = Mock(JobCompletenessDecider)
		performerCreationConfiguration = new PerformerCreationConfiguration(
				applicationContext: applicationContextMock,
				categoryApi: categoryApiMock,
				jobCompletenessDecider: jobCompletenessDeciderMock)
	}

	def "PerformerReader is created is created with all pages when step is not completed"() {
		when:
		PerformerReader performerReader = performerCreationConfiguration.performerReader()
		List<String> categoryHeaderTitleList = readerToList(performerReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobCompletenessDecider.STEP_002_CREATE_PERFORMERS) >> false
		1 * categoryApiMock.getPages(CategoryName.PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.ANIMAL_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ANIMAL_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.DIS_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_DIS_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.DS9_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_DS9_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.ENT_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ENT_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.FILM_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_FILM_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.STAND_INS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_STAND_INS)
		1 * categoryApiMock.getPages(CategoryName.STUNT_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_STUNT_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.TAS_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_TAS_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.TNG_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_TNG_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.TOS_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_TOS_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.TOS_REMASTERED_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_TOS_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.VIDEO_GAME_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_VIDEO_GAME_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.VOICE_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_VOICE_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.VOY_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_VOY_PERFORMERS)
		0 * _
		categoryHeaderTitleList.contains TITLE_PERFORMERS
		categoryHeaderTitleList.contains TITLE_ANIMAL_PERFORMERS
		categoryHeaderTitleList.contains TITLE_DIS_PERFORMERS
		categoryHeaderTitleList.contains TITLE_DS9_PERFORMERS
		categoryHeaderTitleList.contains TITLE_ENT_PERFORMERS
		categoryHeaderTitleList.contains TITLE_FILM_PERFORMERS
		categoryHeaderTitleList.contains TITLE_STAND_INS
		categoryHeaderTitleList.contains TITLE_STUNT_PERFORMERS
		categoryHeaderTitleList.contains TITLE_TAS_PERFORMERS
		categoryHeaderTitleList.contains TITLE_TNG_PERFORMERS
		categoryHeaderTitleList.contains TITLE_TOS_PERFORMERS
		categoryHeaderTitleList.contains TITLE_VIDEO_GAME_PERFORMERS
		categoryHeaderTitleList.contains TITLE_VOICE_PERFORMERS
		categoryHeaderTitleList.contains TITLE_VOY_PERFORMERS
	}

	def "PerformerReader is created with no pages when step is completed"() {
		when:
		PerformerReader performerReader = performerCreationConfiguration.performerReader()
		List<String> categoryHeaderTitleList = readerToList(performerReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobCompletenessDecider.STEP_002_CREATE_PERFORMERS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

	def "ActorTemplateSinglePageProcessor is created"() {
		given:
		PageToGenderProcessor pageToGenderProcessorMock = Mock(PageToGenderProcessor)
		PageToLifeRangeProcessor pageToLifeRangeProcessorMock = Mock(PageToLifeRangeProcessor)
		ActorTemplateTemplateProcessor actorTemplateTemplateProcessorMock = Mock(ActorTemplateTemplateProcessor)
		PerformerCategoriesActorTemplateEnrichingProcessor performerCategoriesActorTemplateEnrichingProcessorMock =
				Mock(PerformerCategoriesActorTemplateEnrichingProcessor)
		PageBindingService pageBindingServiceMock = Mock(PageBindingService)
		TemplateFinder templateFinderMock = Mock(TemplateFinder)

		when:
		ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessor = performerCreationConfiguration
				.actorTemplateSinglePageProcessor()

		then:
		1 * applicationContextMock.getBean(PageToGenderProcessor) >> pageToGenderProcessorMock
		1 * applicationContextMock.getBean(PageToLifeRangeProcessor) >> pageToLifeRangeProcessorMock
		1 * applicationContextMock.getBean(ActorTemplateTemplateProcessor) >> actorTemplateTemplateProcessorMock
		1 * applicationContextMock.getBean(PerformerCategoriesActorTemplateEnrichingProcessor) >>
				performerCategoriesActorTemplateEnrichingProcessorMock
		1 * applicationContextMock.getBean(PageBindingService) >> pageBindingServiceMock
		1 * applicationContextMock.getBean(TemplateFinder) >> templateFinderMock
		0 * _
		actorTemplateSinglePageProcessor.pageToGenderProcessor == pageToGenderProcessorMock
		actorTemplateSinglePageProcessor.pageToLifeRangeProcessor == pageToLifeRangeProcessorMock
		actorTemplateSinglePageProcessor.actorTemplateTemplateProcessor == actorTemplateTemplateProcessorMock
		actorTemplateSinglePageProcessor.categoriesActorTemplateEnrichingProcessor == performerCategoriesActorTemplateEnrichingProcessorMock
		actorTemplateSinglePageProcessor.pageBindingService == pageBindingServiceMock
		actorTemplateSinglePageProcessor.templateFinder == templateFinderMock

	}

	def "ActorTemplatePageProcessor is created"() {
		given:
		ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessorMock = Mock(ActorTemplateSinglePageProcessor)
		ActorTemplateListPageProcessor actorTemplateListPageProcessorMock = Mock(ActorTemplateListPageProcessor)

		when:
		ActorTemplatePageProcessor actorTemplatePageProcessor = performerCreationConfiguration
				.actorTemplatePageProcessor()

		then:
		1 * applicationContextMock.getBean(PerformerCreationConfiguration.PERFORMER_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR,
				ActorTemplateSinglePageProcessor) >> actorTemplateSinglePageProcessorMock
		1 * applicationContextMock.getBean(ActorTemplateListPageProcessor) >> actorTemplateListPageProcessorMock
		0 * _
		actorTemplatePageProcessor.actorTemplateSinglePageProcessor == actorTemplateSinglePageProcessorMock
		actorTemplatePageProcessor.actorTemplateListPageProcessor == actorTemplateListPageProcessorMock
	}

}
