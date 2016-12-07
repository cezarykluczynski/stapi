package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.common.dto.Gender
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryDTO
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.cezarykluczynski.stapi.util.constant.PageName
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.google.common.collect.Lists
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

class IndividualTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String WIKITEXT = 'WIKITEXT'
	private static final String VALUE = 'VALUE'
	private static final Integer HEIGHT = 183
	private static final Integer WEIGHT = 77
	private static final Integer YEAR = 1970
	private static final Integer MONTH = 10
	private static final Integer DAY = 7
	private static final Long PAGE_ID = 11L
	private static final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN
	private static final String PLACE = 'PLACE'
	private static final Gender GENDER = Gender.F
	private static final MaritalStatus MARITAL_STATUS = MaritalStatus.MARRIED
	private static final BloodType BLOOD_TYPE = BloodType.B_NEGATIVE

	private PartToGenderProcessor partToGenderProcessorMock

	private IndividualLifeBoundaryProcessor individualLifeBoundaryProcessorMock

	private IndividualActorLinkingProcessor individualActorLinkingProcessorMock

	private IndividualDateOfDeathEnrichingProcessor individualDateOfDeathEnrichingProcessorMock

	private IndividualHeightProcessor individualHeightProcessorMock

	private IndividualWeightProcessor individualWeightProcessorMock

	private IndividualBloodTypeProcessor individualBloodTypeProcessorMock

	private IndividualMaritalStatusProcessor individualMaritalStatusProcessorMock

	private WikitextApi wikitextApiMock

	private PageBindingService pageBindingServiceMock

	private TemplateFinder templateFinderMock

	private IndividualTemplatePageProcessor individualTemplatePageProcessor

	def setup() {
		partToGenderProcessorMock = Mock(PartToGenderProcessor)
		individualLifeBoundaryProcessorMock = Mock(IndividualLifeBoundaryProcessor)
		individualActorLinkingProcessorMock = Mock(IndividualActorLinkingProcessor)
		individualDateOfDeathEnrichingProcessorMock = Mock(IndividualDateOfDeathEnrichingProcessor)
		individualHeightProcessorMock = Mock(IndividualHeightProcessor)
		individualWeightProcessorMock = Mock(IndividualWeightProcessor)
		individualBloodTypeProcessorMock = Mock(IndividualBloodTypeProcessor)
		individualMaritalStatusProcessorMock = Mock(IndividualMaritalStatusProcessor)
		wikitextApiMock = Mock(WikitextApi)
		pageBindingServiceMock = Mock(PageBindingService)
		templateFinderMock = Mock(TemplateFinder)
		individualTemplatePageProcessor = new IndividualTemplatePageProcessor(partToGenderProcessorMock,
				individualLifeBoundaryProcessorMock,individualActorLinkingProcessorMock,
				individualDateOfDeathEnrichingProcessorMock, individualHeightProcessorMock,
				individualWeightProcessorMock, individualBloodTypeProcessorMock, individualMaritalStatusProcessorMock,
				wikitextApiMock, pageBindingServiceMock, templateFinderMock)

	}

	def "returns null when page name starts with 'Unnamed '"() {
		given:
		Page page = new Page(
				title: "Unnamed humanoids",
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "returns null when page name starts with 'List of '"() {
		given:
		Page page = new Page(
				title: "List of some people",
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "returns null when page name is 'Memory alpha personnel'"() {
		given:
		Page page = new Page(
				title: PageName.MEMORY_ALPHA_PERSONNEL,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "returns null when category list contains Production lists"() {
		given:
		Page page = new Page(
				title: TITLE,
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.PRODUCTION_LISTS)
				),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "returns null when category list contains Lists"() {
		given:
		Page page = new Page(
				title: TITLE,
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.LISTS)
				),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "returns null when page is sorted on top of any category"() {
		given:
		Page page = new Page(
				title: TITLE,
				wikitext: WIKITEXT,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(
				new PageLink(
						title: "Category:Some page"
				),
				new PageLink(
						title: "category:Some other page",
						description: StringUtils.EMPTY
				),
				new PageLink(
						title: "category:Yet another page",
						description: "Page, yet another"
				)
		)
		individualTemplate == null
	}

	def "missing template results IndividualTemplate with only the name and page"() {
		given:
		Page page = new Page(
				title: TITLE,
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())
		PageEntity pageEntity = new PageEntity()

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		1 * wikitextApiMock.getPageLinksFromWikitext(null) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> pageEntity
		0 * _
		individualTemplate.name == TITLE
		individualTemplate.page == pageEntity
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 4
	}

	def "sets name from page title, and cuts brackets when they are present"() {
		given:
		Page page = new Page(
				title: TITLE + " (civilian)",
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())
		wikitextApiMock.getPageLinksFromWikitext(*_) >> Lists.newArrayList()

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> new PageEntity()
		individualTemplate.name == TITLE
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 4
	}

	def "sets gender from PartToGenderProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplatePageProcessor.GENDER)
		Page page = createPageWithTemplatePart(templatePart)

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.of(page.templates[0])
		1 * partToGenderProcessorMock.process(templatePart) >> GENDER
		1 * wikitextApiMock.getPageLinksFromWikitext(null) >> Lists.newArrayList()
		1 * individualDateOfDeathEnrichingProcessorMock.enrich(_)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> new PageEntity()
		0 * _
		individualTemplate.gender == GENDER
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 5
	}

	def "when actor key is found, part is passed to IndividualActorLinkingProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplatePageProcessor.ACTOR)
		Page page = createPageWithTemplatePart(templatePart)
		IndividualTemplate individualTemplateInActorLinkingProcessor = null

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.of(page.templates[0])
		1 * wikitextApiMock.getPageLinksFromWikitext(null) >> Lists.newArrayList()
		1 * individualDateOfDeathEnrichingProcessorMock.enrich(_)
		1 * individualActorLinkingProcessorMock.enrich(_ as EnrichablePair<Template.Part, IndividualTemplate>) >> {
				EnrichablePair<Template.Part, IndividualTemplate> enrichablePair ->
			assert enrichablePair.input == templatePart
			individualTemplateInActorLinkingProcessor = enrichablePair.output
		}
		1 * pageBindingServiceMock.fromPageToPageEntity(page)
		0 * _
		individualTemplateInActorLinkingProcessor == individualTemplate
	}

	def "sets height from IndividualHeightProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePageProcessor.HEIGHT,
				value: VALUE)
		Page page = createPageWithTemplatePart(templatePart)

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.of(page.templates[0])
		1 * individualHeightProcessorMock.process(VALUE) >> HEIGHT
		1 * wikitextApiMock.getPageLinksFromWikitext(null) >> Lists.newArrayList()
		1 * individualDateOfDeathEnrichingProcessorMock.enrich(_)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> new PageEntity()
		0 * _
		individualTemplate.height == HEIGHT
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 5
	}

	def "sets weight from IndividualWeightProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePageProcessor.WEIGHT,
				value: VALUE)
		Page page = createPageWithTemplatePart(templatePart)

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.of(page.templates[0])
		1 * individualWeightProcessorMock.process(VALUE) >> WEIGHT
		1 * wikitextApiMock.getPageLinksFromWikitext(null) >> Lists.newArrayList()
		1 * individualDateOfDeathEnrichingProcessorMock.enrich(_)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> new PageEntity()
		0 * _
		individualTemplate.weight == WEIGHT
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 5
	}

	def "sets serial number when it is not empty"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePageProcessor.SERIAL_NUMBER,
				value: VALUE)
		Page page = createPageWithTemplatePart(templatePart)

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.of(page.templates[0])
		1 * wikitextApiMock.getPageLinksFromWikitext(null) >> Lists.newArrayList()
		1 * individualDateOfDeathEnrichingProcessorMock.enrich(_)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> new PageEntity()
		0 * _
		individualTemplate.serialNumber == VALUE
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 5
	}

	def "sets productOfRedirect flag to true"() {
		given:
		Page page = new Page(
				title: TITLE,
				redirectPath: Lists.newArrayList(Mock(PageHeader))
		)
		wikitextApiMock.getPageLinksFromWikitext(_) >> Lists.newArrayList()

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		individualTemplate.productOfRedirect
	}

	def "sets productOfRedirect flag to false"() {
		given:
		Page page = new Page(
				title: TITLE
		)
		wikitextApiMock.getPageLinksFromWikitext(_) >> Lists.newArrayList()

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		!individualTemplate.productOfRedirect
	}

	def "does not set serial number when it is not empty"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePageProcessor.SERIAL_NUMBER,
				value: "")
		Page page = createPageWithTemplatePart(templatePart)
		PageEntity pageEntity = new PageEntity()

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.of(page.templates[0])
		1 * wikitextApiMock.getPageLinksFromWikitext(null) >> Lists.newArrayList()
		1 * individualDateOfDeathEnrichingProcessorMock.enrich(_)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> pageEntity
		0 * _
		individualTemplate.serialNumber == null
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 4
	}

	def "sets birth values from IndividualLifeBoundaryProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePageProcessor.BORN,
				value: VALUE)
		Page page = createPageWithTemplatePart(templatePart)
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = new IndividualLifeBoundaryDTO(
				year: YEAR,
				month: MONTH,
				day: DAY,
				place: PLACE
		)

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.of(page.templates[0])
		1 * individualLifeBoundaryProcessorMock.process(VALUE) >> individualLifeBoundaryDTO
		1 * wikitextApiMock.getPageLinksFromWikitext(null) >> Lists.newArrayList()
		1 * individualDateOfDeathEnrichingProcessorMock.enrich(_)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> new PageEntity()
		0 * _
		individualTemplate.yearOfBirth == YEAR
		individualTemplate.monthOfBirth == MONTH
		individualTemplate.dayOfBirth == DAY
		individualTemplate.placeOfBirth == PLACE
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 8
	}

	def "sets death values from IndividualLifeBoundaryProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePageProcessor.DIED,
				value: VALUE)
		Page page = createPageWithTemplatePart(templatePart)
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = new IndividualLifeBoundaryDTO(
				year: YEAR,
				month: MONTH,
				day: DAY,
				place: PLACE
		)

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.of(page.templates[0])
		1 * individualLifeBoundaryProcessorMock.process(VALUE) >> individualLifeBoundaryDTO
		1 * wikitextApiMock.getPageLinksFromWikitext(null) >> Lists.newArrayList()
		1 * individualDateOfDeathEnrichingProcessorMock.enrich(_)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> new PageEntity()
		0 * _
		individualTemplate.yearOfDeath == YEAR
		individualTemplate.monthOfDeath == MONTH
		individualTemplate.dayOfDeath == DAY
		individualTemplate.placeOfDeath == PLACE
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 8
	}

	def "sets marital status from IndividualMaritalStatusProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePageProcessor.MARITAL_STATUS,
				value: VALUE)
		Page page = createPageWithTemplatePart(templatePart)

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.of(page.templates[0])
		1 * individualMaritalStatusProcessorMock.process(VALUE) >> MARITAL_STATUS
		1 * wikitextApiMock.getPageLinksFromWikitext(null) >> Lists.newArrayList()
		1 * individualDateOfDeathEnrichingProcessorMock.enrich(_)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> new PageEntity()
		0 * _
		individualTemplate.maritalStatus == MARITAL_STATUS
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 5
	}

	def "sets blood type from IndividualBloodTypeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePageProcessor.BLOOD_TYPE,
				value: VALUE)
		Page page = createPageWithTemplatePart(templatePart)

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.of(page.templates[0])
		1 * individualBloodTypeProcessorMock.process(VALUE) >> BLOOD_TYPE
		1 * wikitextApiMock.getPageLinksFromWikitext(null) >> Lists.newArrayList()
		1 * individualDateOfDeathEnrichingProcessorMock.enrich(_)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> new PageEntity()
		0 * _
		individualTemplate.bloodType == BLOOD_TYPE
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 5
	}

	private static Page createPageWithTemplatePart(Template.Part templatePart) {
		return new Page(
				title: TITLE,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList(
						new Template(
								title: TemplateName.SIDEBAR_INDIVIDUAL,
								parts: Lists.newArrayList(templatePart)
						)
				)
		)
	}

}
