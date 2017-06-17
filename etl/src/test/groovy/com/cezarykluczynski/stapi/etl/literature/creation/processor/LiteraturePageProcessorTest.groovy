package com.cezarykluczynski.stapi.etl.literature.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.literature.creation.service.LiteraturePageFilter
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.literature.entity.Literature
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class LiteraturePageProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String NAME_TO_CLEAN = 'NAME (poem)'
	private static final String NAME_THEORETICAL = 'Gravimetric Power Limits (Theoretical)'
	private static final String NAME_OMEGA_IV = 'Bible (Omega IV)'
	private static final String UID = 'UID'

	private LiteraturePageFilter literaturePageFilterMock

	private PageBindingService pageBindingServiceMock

	private UidGenerator uidGeneratorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock
	private TemplateFinder templateFinderMock

	private LiteraturePageProcessor literaturePageProcessor

	void setup() {
		literaturePageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		uidGeneratorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		templateFinderMock = Mock()
		literaturePageProcessor = new LiteraturePageProcessor(literaturePageFilterMock, pageBindingServiceMock, uidGeneratorMock,
				categoryTitlesExtractingProcessorMock, templateFinderMock)
	}

	void "should return null when page should be filtered out"() {
		given:
		SourcesPage page = Mock()

		when:
		Literature literature = literaturePageProcessor.process(page)

		then:
		1 * literaturePageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		literature == null
	}

	void "keeps original title when it's ending with (Theoretical)"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME_THEORETICAL)

		when:
		Literature literature = literaturePageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.RELIGIOUS_TEXTS) >> Optional.empty()
		literature.title == NAME_THEORETICAL
	}

	void "keeps original title when it's ending with (Omega IV)"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME_OMEGA_IV)

		when:
		Literature literature = literaturePageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.RELIGIOUS_TEXTS) >> Optional.empty()
		literature.title == NAME_OMEGA_IV
	}

	void "cleans title"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME_TO_CLEAN)

		when:
		Literature literature = literaturePageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.RELIGIOUS_TEXTS) >> Optional.empty()
		literature.title == NAME
	}

	void "page is bound"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Literature literature = literaturePageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.RELIGIOUS_TEXTS) >> Optional.empty()
		literature.page == modelPage
	}

	void "UID is generated"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Literature literature = literaturePageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Literature) >> UID
		1 * templateFinderMock.findTemplate(page, TemplateTitle.RELIGIOUS_TEXTS) >> Optional.empty()
		literature.uid == UID
	}

	@SuppressWarnings('LineLength')
	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "sets flagName when page is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}
		templateFinderMock.findTemplate(page, TemplateTitle.RELIGIOUS_TEXTS) >> Optional.empty()

		expect:
		Literature literature = literaturePageProcessor.process(page)
		literature[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(literature) == trueBooleans

		where:
		page                                                                                   | flagName               | flag  | trueBooleans
		new SourcesPage(categories: Lists.newArrayList())                                      | 'earthlyOrigin'        | false | 0
		new SourcesPage(categories: createList(CategoryTitle.EARTH_LITERATURE))                | 'earthlyOrigin'        | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.SHAKESPEAREAN_WORKS))             | 'earthlyOrigin'        | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.SHAKESPEAREAN_WORKS))             | 'shakespeareanWork'    | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.REPORTS))                         | 'report'               | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.SCIENTIFIC_LITERATURE))           | 'scientificLiterature' | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.SCIENTIFIC_LITERATURE_RETCONNED)) | 'scientificLiterature' | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.TECHNICAL_MANUALS))               | 'technicalManual'      | true  | 1
	}

	void "sets religiousLiterature flag when ReligiousTexts template is found"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		Template religiousTextsTemplate = Mock()

		when:
		Literature literature = literaturePageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.RELIGIOUS_TEXTS) >> Optional.of(religiousTextsTemplate)
		literature.religiousLiterature
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
