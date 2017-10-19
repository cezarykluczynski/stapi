package com.cezarykluczynski.stapi.etl.technology.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.technology.creation.service.TechnologyPageFilter
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class TechnologyPageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_WITH_BRACKETS = 'TITLE (with brackets)'
	private static final String NAME = 'NAME'
	private static final String UID = 'UID'

	private TechnologyPageFilter technologyPageFilterMock

	private PageBindingService pageBindingServiceMock

	private UidGenerator uidGeneratorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private TechnologyPageProcessor technologyPageProcessor

	void setup() {
		technologyPageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		uidGeneratorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		technologyPageProcessor = new TechnologyPageProcessor(technologyPageFilterMock, pageBindingServiceMock, uidGeneratorMock,
				categoryTitlesExtractingProcessorMock)
	}

	void "should return null when page should be filtered out"() {
		given:
		SourcesPage page = Mock()

		when:
		Technology technology = technologyPageProcessor.process(page)

		then:
		1 * technologyPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		technology == null
	}

	void "sets name from page title, and cuts brackets when they are present"() {
		given:
		SourcesPage page = new SourcesPage(title: TITLE_WITH_BRACKETS)

		when:
		Technology technology = technologyPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		technology.name == TITLE
	}

	void "page is bound"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Technology technology = technologyPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		technology.page == modelPage
	}

	void "UID is generated"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Technology technology = technologyPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Technology) >> UID
		technology.uid == UID
	}

	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "sets flagName when page is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}

		expect:
		Technology technology = technologyPageProcessor.process(page)
		technology[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(technology) == trueBooleans

		where:
		page                                                                                       | flagName                   | flag  | trueBooleans
		new SourcesPage(categories: Lists.newArrayList())                                          | 'borgTechnology'           | false | 0
		new SourcesPage(categories: createList(CategoryTitle.BORG_TECHNOLOGY))                     | 'borgTechnology'           | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.BORG_COMPONENTS))                     | 'borgTechnology'           | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.BORG_COMPONENTS))                     | 'borgComponent'            | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.COMMUNICATIONS_TECHNOLOGY))           | 'communicationsTechnology' | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.COMMUNICATIONS_TECHNOLOGY_RETCONNED)) | 'communicationsTechnology' | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.COMPUTER_TECHNOLOGY))                 | 'computerTechnology'       | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.COMPUTER_PROGRAMMING))                | 'computerTechnology'       | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.COMPUTER_PROGRAMMING))                | 'computerProgramming'      | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.SUBROUTINES))                         | 'computerTechnology'       | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.SUBROUTINES))                         | 'computerProgramming'      | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.SUBROUTINES))                         | 'subroutine'               | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.DATABASES))                           | 'computerTechnology'       | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.DATABASES))                           | 'database'                 | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.ENERGY_TECHNOLOGY))                   | 'energyTechnology'         | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.ENERGY_TECHNOLOGY_RETCONNED))         | 'energyTechnology'         | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.FICTIONAL_TECHNOLOGY))                | 'fictionalTechnology'      | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.HOLOGRAPHIC_TECHNOLOGY))              | 'holographicTechnology'    | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.IDENTIFICATION_TECHNOLOGY))           | 'identificationTechnology' | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.LIFE_SUPPORT_TECHNOLOGY))             | 'lifeSupportTechnology'    | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.SENSOR_TECHNOLOGY))                   | 'sensorTechnology'         | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.SENSOR_TECHNOLOGY_RETCONNED))         | 'sensorTechnology'         | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.SHIELD_TECHNOLOGY))                   | 'shieldTechnology'         | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.SHIELD_TECHNOLOGY_RETCONNED))         | 'shieldTechnology'         | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.TOOLS))                               | 'tool'                     | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.CULINARY_TOOLS))                      | 'tool'                     | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.CULINARY_TOOLS))                      | 'culinaryTool'             | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.ENGINEERING_TOOLS))                   | 'tool'                     | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.ENGINEERING_TOOLS))                   | 'engineeringTool'          | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.HOUSEHOLD_TOOLS))                     | 'tool'                     | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.HOUSEHOLD_TOOLS))                     | 'householdTool'            | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.MEDICAL_EQUIPMENT))                   | 'tool'                     | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.MEDICAL_EQUIPMENT))                   | 'medicalEquipment'         | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.TRANSPORTER_TECHNOLOGY))              | 'transporterTechnology'    | true  | 1

	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
