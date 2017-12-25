package com.cezarykluczynski.stapi.etl.staff.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerCategoriesActorTemplateEnrichingProcessor
import com.cezarykluczynski.stapi.etl.performer.creation.service.ActorPageFilter
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffCategoriesActorTemplateEnrichingProcessor
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffReader
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateListPageProcessor
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplatePageProcessor
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateSinglePageProcessor
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateTemplateProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PageToLifeRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PageToGenderProcessor
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import org.springframework.context.ApplicationContext

class StaffCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_STAFF = 'TITLE_STAFF'

	private ApplicationContext applicationContextMock

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private StaffCreationConfiguration staffCreationConfiguration

	void setup() {
		applicationContextMock = Mock()
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		staffCreationConfiguration = new StaffCreationConfiguration(
				applicationContext: applicationContextMock,
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "StaffReader is created with all pages when step is not completed"() {
		when:
		StaffReader staffReader = staffCreationConfiguration.staffReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(staffReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_STAFF) >> false
		1 * categoryApiMock.getPages(CategoryTitles.STAFF, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_STAFF)
		0 * _
		categoryHeaderTitleList.contains TITLE_STAFF
	}

	void "StaffReader is created with no pages when step is completed"() {
		when:
		StaffReader staffReader = staffCreationConfiguration.staffReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(staffReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_STAFF) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

	void "ActorTemplateSinglePageProcessor is created"() {
		given:
		PageToGenderProcessor pageToGenderProcessorMock = Mock()
		PageToLifeRangeProcessor pageToLifeRangeProcessorMock = Mock()
		ActorTemplateTemplateProcessor actorTemplateTemplateProcessorMock = Mock()
		PerformerCategoriesActorTemplateEnrichingProcessor performerCategoriesActorTemplateEnrichingProcessorMock = Mock()
		PageBindingService pageBindingServiceMock = Mock()
		TemplateFinder templateFinderMock = Mock()

		when:
		ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessor = staffCreationConfiguration
				.actorTemplateSinglePageProcessor()

		then:
		1 * applicationContextMock.getBean(PageToGenderProcessor) >> pageToGenderProcessorMock
		1 * applicationContextMock.getBean(PageToLifeRangeProcessor) >> pageToLifeRangeProcessorMock
		1 * applicationContextMock.getBean(ActorTemplateTemplateProcessor) >> actorTemplateTemplateProcessorMock
		1 * applicationContextMock.getBean(StaffCategoriesActorTemplateEnrichingProcessor) >>
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

	void "ActorTemplatePageProcessor is created"() {
		given:
		ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessorMock = Mock()
		ActorTemplateListPageProcessor actorTemplateListPageProcessorMock = Mock()
		ActorPageFilter actorPageFilterMock = Mock()

		when:
		ActorTemplatePageProcessor actorTemplatePageProcessor = staffCreationConfiguration.actorTemplatePageProcessor()

		then:
		1 * applicationContextMock.getBean(ActorPageFilter) >> actorPageFilterMock
		1 * applicationContextMock.getBean(StaffCreationConfiguration.STAFF_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR,
				ActorTemplateSinglePageProcessor) >> actorTemplateSinglePageProcessorMock
		1 * applicationContextMock.getBean(ActorTemplateListPageProcessor) >> actorTemplateListPageProcessorMock
		0 * _
		actorTemplatePageProcessor.actorPageFilter == actorPageFilterMock
		actorTemplatePageProcessor.actorTemplateSinglePageProcessor == actorTemplateSinglePageProcessorMock
		actorTemplatePageProcessor.actorTemplateListPageProcessor == actorTemplateListPageProcessorMock
	}

}
