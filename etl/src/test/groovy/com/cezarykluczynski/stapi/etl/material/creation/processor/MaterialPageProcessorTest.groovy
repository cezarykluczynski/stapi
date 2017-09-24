package com.cezarykluczynski.stapi.etl.material.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.material.creation.service.MaterialPageFilter
import com.cezarykluczynski.stapi.etl.material.creation.service.MaterialsAndSubstancesDetectorService
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.material.entity.Material
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class MaterialPageProcessorTest extends Specification {

	private static final String NAME_WITH_BRACKETS = 'NAME (with brackets)'
	private static final String NAME = 'NAME'
	private static final String UID = 'UID'
	private static final String HUTZELITE_27 = 'Hutzelite_27'

	private MaterialPageFilter materialPageFilterMock

	private PageBindingService pageBindingServiceMock

	private UidGenerator uidGeneratorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private MaterialsAndSubstancesDetectorService materialsAndSubstancesDetectorServiceMock

	private TemplateFinder templateFinderMock

	private MaterialPageProcessor materialPageProcessor

	void setup() {
		materialPageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		uidGeneratorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		materialsAndSubstancesDetectorServiceMock = Mock()
		templateFinderMock = Mock()
		materialPageProcessor = new MaterialPageProcessor(materialPageFilterMock, pageBindingServiceMock, uidGeneratorMock,
				categoryTitlesExtractingProcessorMock, materialsAndSubstancesDetectorServiceMock, templateFinderMock)
	}

	void "should return null when page should be filtered out"() {
		given:
		SourcesPage page = Mock()

		when:
		Material material = materialPageProcessor.process(page)

		then:
		1 * materialPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		material == null
	}

	void "material is generated without categories and flags from MaterialsAndSubstancesDetectorService"() {
		given:
		CategoryHeader categoryHeader = Mock()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(categoryHeader)
		SourcesPage page = new SourcesPage(
				title: NAME_WITH_BRACKETS,
				categories: categoryHeaderList)
		ModelPage modelPage = new ModelPage()

		when:
		Material material = materialPageProcessor.process(page)

		then:
		1 * materialPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Material) >> UID
		1 * materialsAndSubstancesDetectorServiceMock.isExplosive(NAME_WITH_BRACKETS) >> false
		1 * materialsAndSubstancesDetectorServiceMock.isAlloyOrComposite(NAME_WITH_BRACKETS) >> false
		1 * materialsAndSubstancesDetectorServiceMock.isFuel(NAME_WITH_BRACKETS) >> false
		1 * materialsAndSubstancesDetectorServiceMock.isPreciousMaterial(NAME_WITH_BRACKETS) >> false
		1 * templateFinderMock.hasTemplate(page, TemplateTitle.MINERALS) >> false
		0 * _
		material.name == NAME
		material.uid == UID
		material.page == modelPage
		!material.biochemicalCompound
		!material.drug
		!material.poisonousSubstance
		!material.explosive
		!material.gemstone
		!material.alloyOrComposite
		!material.fuel
		!material.mineral
		!material.preciousMaterial
	}

	void "material is generated without categories and with flags from RanksTemplateService and TemplateFinder"() {
		given:
		CategoryHeader categoryHeader = Mock()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(categoryHeader)
		SourcesPage page = new SourcesPage(
				title: NAME_WITH_BRACKETS,
				categories: categoryHeaderList)
		ModelPage modelPage = new ModelPage()

		when:
		Material material = materialPageProcessor.process(page)

		then:
		1 * materialPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Material) >> UID
		1 * materialsAndSubstancesDetectorServiceMock.isExplosive(NAME_WITH_BRACKETS) >> true
		1 * materialsAndSubstancesDetectorServiceMock.isAlloyOrComposite(NAME_WITH_BRACKETS) >> true
		1 * materialsAndSubstancesDetectorServiceMock.isFuel(NAME_WITH_BRACKETS) >> true
		1 * materialsAndSubstancesDetectorServiceMock.isPreciousMaterial(NAME_WITH_BRACKETS) >> true
		1 * templateFinderMock.hasTemplate(page, TemplateTitle.MINERALS) >> true
		0 * _
		material.name == NAME
		material.uid == UID
		material.page == modelPage
		!material.biochemicalCompound
		!material.drug
		!material.poisonousSubstance
		material.explosive
		material.gemstone
		material.alloyOrComposite
		material.fuel
		material.mineral
		material.preciousMaterial
	}

	void "when explosives category is found, but page is not among listed explosives, and no other related categories are found"() {
		given:
		CategoryHeader categoryHeader = Mock()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(categoryHeader)
		SourcesPage page = new SourcesPage(
				title: NAME,
				categories: categoryHeaderList)
		ModelPage modelPage = new ModelPage()

		when:
		Material material = materialPageProcessor.process(page)

		then:
		1 * materialPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.EXPLOSIVES)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Material) >> UID
		1 * materialsAndSubstancesDetectorServiceMock.isExplosive(NAME) >> false
		0 * _
		material == null
	}

	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "sets flagName when page is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}
		materialsAndSubstancesDetectorServiceMock.isExplosive(HUTZELITE_27) >> true

		expect:
		Material material = materialPageProcessor.process(page)
		material[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(material) == trueBooleans

		where:
		page                                                                                   | flagName              | flag  | trueBooleans
		new SourcesPage(categories: Lists.newArrayList())                                      | 'biochemicalCompound' | false | 0
		new SourcesPage(categories: createList(CategoryTitle.BIOCHEMICAL_COMPOUNDS))           | 'biochemicalCompound' | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.BIOCHEMICAL_COMPOUNDS))           | 'chemicalCompound'    | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.DRUGS))                           | 'drug'                | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.DRUGS))                           | 'chemicalCompound'    | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.POISONOUS_SUBSTANCES))            | 'poisonousSubstance'  | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.POISONOUS_SUBSTANCES))            | 'chemicalCompound'    | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.EXPLOSIVES), title: HUTZELITE_27) | 'explosive'           | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.GEMSTONES))                       | 'gemstone'            | true  | 1
	}

	private static List<CategoryHeader> createList(String material) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(material))
	}

}
