package com.cezarykluczynski.stapi.etl.organization.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.organization.creation.service.OrganizationPageFilter
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class OrganizationPageProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String NAME_CORRECTED = 'NAME_CORRECTED'
	private static final String UID = 'UID'

	private OrganizationPageFilter organizationPageFilterMock

	private PageBindingService pageBindingServiceMock

	private UidGenerator uidGeneratorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private OrganizationNameFixedValueProvider organizationNameFixedValueProviderMock

	private OrganizationPageProcessor organizationPageProcessor

	void setup() {
		organizationPageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		uidGeneratorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		organizationNameFixedValueProviderMock = Mock()
		organizationPageProcessor = new OrganizationPageProcessor(organizationPageFilterMock, pageBindingServiceMock, uidGeneratorMock,
				categoryTitlesExtractingProcessorMock, organizationNameFixedValueProviderMock)
	}

	void "should return null when page should be filtered out"() {
		given:
		SourcesPage page = Mock()

		when:
		Organization organization = organizationPageProcessor.process(page)

		then:
		1 * organizationPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		organization == null
	}

	void "sets name from OrganizationNameFixedValueProvider when it is found"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)

		when:
		Organization organization = organizationPageProcessor.process(page)

		then:
		1 * organizationPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * organizationNameFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.found(NAME_CORRECTED)
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		organization.name == NAME_CORRECTED
	}

	void "keeps original name when OrganizationNameFixedValueProvider found nothing"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)

		when:
		Organization organization = organizationPageProcessor.process(page)

		then:
		1 * organizationNameFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.empty()
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		organization.name == NAME
	}

	void "page is bound"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Organization organization = organizationPageProcessor.process(page)

		then:
		1 * organizationNameFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.empty()
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		organization.page == modelPage
	}

	void "UID is generated"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Organization organization = organizationPageProcessor.process(page)

		then:
		1 * organizationNameFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.empty()
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Organization) >> UID
		organization.uid == UID
	}

	@SuppressWarnings('LineLength')
	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "sets flagName when page is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}
		organizationNameFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()

		expect:
		Organization organization = organizationPageProcessor.process(page)
		organization[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(organization) == trueBooleans

		where:
		page                                                                                         | flagName                        | flag  | trueBooleans
		new SourcesPage(categories: Lists.newArrayList())                                            | 'government'                    | false | 0
		new SourcesPage(categories: createList(CategoryTitle.GOVERNMENTS))                           | 'government'                    | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.INTERGOVERNMENTAL_ORGANIZATIONS))       | 'intergovernmentalOrganization' | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.EARTH_INTERGOVERNMENTAL_ORGANIZATIONS)) | 'intergovernmentalOrganization' | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.RESEARCH_ORGANIZATIONS))                | 'researchOrganization'          | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.SPORTS_ORGANIZATIONS))                  | 'sportOrganization'             | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.MEDICAL_ORGANIZATIONS))                 | 'medicalOrganization'           | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.MILITARY_UNITS))                        | 'militaryUnit'                  | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.MILITARY_ORGANIZATIONS))                | 'militaryOrganization'          | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.EARTH_MILITARY_ORGANIZATIONS))          | 'militaryOrganization'          | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.AGENCIES))                              | 'governmentAgency'              | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.BAJORAN_AGENCIES))                      | 'governmentAgency'              | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.CARDASSIAN_AGENCIES))                   | 'governmentAgency'              | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.EARTH_AGENCIES))                        | 'governmentAgency'              | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.FEDERATION_AGENCIES))                   | 'governmentAgency'              | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.FERENGI_AGENCIES))                      | 'governmentAgency'              | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.KLINGON_AGENCIES))                      | 'governmentAgency'              | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.LAW_ENFORCEMENT_AGENCIES))              | 'governmentAgency'              | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.LAW_ENFORCEMENT_AGENCIES))              | 'lawEnforcementAgency'          | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.PRISONS_AND_PENAL_COLONIES))            | 'prisonOrPenalColony'           | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.ROMULAN_AGENCIES))                      | 'governmentAgency'              | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.VULCAN_AGENCIES))                       | 'governmentAgency'              | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.MIRROR_UNIVERSE))                       | 'mirror'                        | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.ALTERNATE_REALITY))                     | 'alternateReality'              | true  | 1
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
