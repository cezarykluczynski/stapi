package com.cezarykluczynski.stapi.etl.medical_condition.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.medical_condition.creation.service.MedicalConditionPageFilter
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class MedicalConditionPageProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String NAME_WITH_BRACKETS = 'NAME (with brackets)'
	private static final String UID = 'UID'

	private MedicalConditionPageFilter medicalConditionPageFilterMock

	private PageBindingService pageBindingServiceMock

	private UidGenerator uidGeneratorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private MedicalConditionPageProcessor medicalConditionPageProcessor

	void setup() {
		medicalConditionPageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		uidGeneratorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		medicalConditionPageProcessor = new MedicalConditionPageProcessor(medicalConditionPageFilterMock, pageBindingServiceMock, uidGeneratorMock,
				categoryTitlesExtractingProcessorMock)
	}

	void "returns null when page should be filtered out"() {
		given:
		SourcesPage page = Mock()

		when:
		MedicalCondition medicalCondition = medicalConditionPageProcessor.process(page)

		then:
		1 * medicalConditionPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		medicalCondition == null
	}

	void "parses page"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME_WITH_BRACKETS)
		ModelPage modelPage = new ModelPage()

		when:
		MedicalCondition medicalCondition = medicalConditionPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, MedicalCondition) >> UID
		medicalCondition.name == NAME
		medicalCondition.uid == UID
	}

	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "sets flagName when page is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}

		expect:
		MedicalCondition medicalCondition = medicalConditionPageProcessor.process(page)
		medicalCondition[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(medicalCondition) == trueBooleans

		where:
		page                                                                            | flagName                 | flag  | trueBooleans
		new SourcesPage(categories: Lists.newArrayList())                               | 'psychologicalCondition' | false | 0
		new SourcesPage(categories: createList(CategoryTitle.PSYCHOLOGICAL_CONDITIONS)) | 'psychologicalCondition' | true  | 1
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
