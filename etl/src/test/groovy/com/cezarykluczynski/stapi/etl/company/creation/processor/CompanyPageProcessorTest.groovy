package com.cezarykluczynski.stapi.etl.company.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.company.creation.provider.CompanyNameFixedValueProvider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as EtlPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.cezarykluczynski.stapi.util.constant.PageName
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class CompanyPageProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String NAME_FROM_FIXED_VALUE_HOLDER = 'NAME_FROM_FIXED_VALUE_HOLDER'
	private static final String GUID = 'GUID'

	private PageBindingService pageBindingServiceMock

	private GuidGenerator guidGeneratorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private CompanyNameFixedValueProvider companyNameFixedValueProviderMock

	private CompanyPageProcessor companyPageProcessor

	void setup() {
		pageBindingServiceMock = Mock(PageBindingService)
		guidGeneratorMock = Mock(GuidGenerator)
		categoryTitlesExtractingProcessorMock = Mock(CategoryTitlesExtractingProcessor)
		companyNameFixedValueProviderMock = Mock(CompanyNameFixedValueProvider)
		companyPageProcessor = new CompanyPageProcessor(pageBindingServiceMock, guidGeneratorMock, categoryTitlesExtractingProcessorMock,
				companyNameFixedValueProviderMock)
	}

	@SuppressWarnings('LineLength')
	@Unroll('set #flagName flag when #categoryHeaderList is passed')
	void "set flagName when categoryHeaderList is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}
		companyNameFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()

		expect:
		Company company = companyPageProcessor.process(page)
		flag == company[flagName]
		trueBooleans == ReflectionTestUtils.getNumberOfTrueBooleanFields(company)

		where:
		page                                                                                    | flagName                          | flag  | trueBooleans
		new EtlPage(categories: Lists.newArrayList())                                           | 'broadcaster'                     | false | 0
		new EtlPage(categories: createList(CategoryName.BROADCASTERS))                          | 'broadcaster'                     | true  | 1
		new EtlPage(categories: createList(CategoryName.COLLECTIBLE_COMPANIES))                 | 'collectibleCompany'              | true  | 1
		new EtlPage(categories: createList(CategoryName.CONGLOMERATES))                         | 'conglomerate'                    | true  | 1
		new EtlPage(categories: createList(CategoryName.DIGITAL_VISUAL_EFFECTS_COMPANIES))      | 'digitalVisualEffectsCompany'     | true  | 1
		new EtlPage(categories: createList(CategoryName.DISTRIBUTORS))                          | 'distributor'                     | true  | 1
		new EtlPage(categories: createList(CategoryName.GAME_COMPANIES))                        | 'gameCompany'                     | true  | 1
		new EtlPage(categories: createList(CategoryName.FILM_EQUIPMENT_COMPANIES))              | 'filmEquipmentCompany'            | true  | 1
		new EtlPage(categories: createList(CategoryName.MAKE_UP_EFFECTS_STUDIOS))               | 'makeUpEffectsStudio'             | true  | 1
		new EtlPage(categories: createList(CategoryName.MATTE_PAINTING_COMPANIES))              | 'mattePaintingCompany'            | true  | 1
		new EtlPage(categories: createList(CategoryName.MODEL_AND_MINIATURE_EFFECTS_COMPANIES)) | 'modelAndMiniatureEffectsCompany' | true  | 1
		new EtlPage(categories: createList(CategoryName.POST_PRODUCTION_COMPANIES))             | 'postProductionCompany'           | true  | 1
		new EtlPage(categories: createList(CategoryName.PRODUCTION_COMPANIES))                  | 'productionCompany'               | true  | 1
		new EtlPage(categories: createList(CategoryName.PROP_COMPANIES))                        | 'propCompany'                     | true  | 1
		new EtlPage(categories: createList(CategoryName.RECORD_LABELS))                         | 'recordLabel'                     | true  | 1
		new EtlPage(categories: createList(CategoryName.SPECIAL_EFFECTS_COMPANIES))             | 'specialEffectsCompany'           | true  | 1
		new EtlPage(categories: createList(CategoryName.TV_AND_FILM_PRODUCTION_COMPANIES))      | 'tvAndFilmProductionCompany'      | true  | 1
		new EtlPage(categories: createList(CategoryName.VIDEO_GAME_COMPANIES))                  | 'videoGameCompany'                | true  | 1
	}

	void "returns null when page is a result of redirec"() {
		given:
		EtlPage etlPage = new EtlPage(redirectPath: Lists.newArrayList(Mock(PageHeader)))

		when:
		Company company = companyPageProcessor.process(etlPage)

		then:
		company == null
	}

	void "returns null when it is starship miniatures list"() {
		given:
		EtlPage etlPage = new EtlPage(title: PageName.STAR_TREK_STARSHIP_MINIATURES)

		when:
		Company company = companyPageProcessor.process(etlPage)

		then:
		company == null
	}

	void "converts Page to Company"() {
		given:
		EtlPage etlPage = new EtlPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Company company = companyPageProcessor.process(etlPage)

		then:
		1 * companyNameFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(etlPage) >> modelPage
		1 * guidGeneratorMock.generateFromPage(modelPage, Company) >> GUID
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		0 * _
		company.name == NAME
		company.guid == GUID
		company.page == modelPage
	}

	void "user name from fixed value holder, when it is present"() {
		given:
		EtlPage etlPage = new EtlPage(title: NAME)

		when:
		Company company = companyPageProcessor.process(etlPage)

		then:
		1 * companyNameFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.found(NAME_FROM_FIXED_VALUE_HOLDER)
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		company.name == NAME_FROM_FIXED_VALUE_HOLDER
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
