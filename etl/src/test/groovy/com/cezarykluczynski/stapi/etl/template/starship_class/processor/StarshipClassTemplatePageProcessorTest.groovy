package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate
import com.cezarykluczynski.stapi.etl.spacecraft_class.creation.service.SpacecraftClassPageFilter
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import spock.lang.Specification

class StarshipClassTemplatePageProcessorTest extends Specification {

	private static final String TITLE_WITH_BRACKETS = 'TITLE (something in brackets)'
	private static final String TITLE = 'TITLE'

	private SpacecraftClassPageFilter starshipClassFilterMock

	private TemplateFinder templateFinderMock

	private PageBindingService pageBindingServiceMock

	private StarshipClassTemplateCategoriesEnrichingProcessor starshipClassTemplateCategoriesEnrichingProcessorMock

	private StarshipClassTemplateCompositeEnrichingProcessor starshipClassTemplateCompositeEnrichingProcessorMock

	private StarshipClassTemplatePageProcessor starshipClassTemplatePageProcessor

	void setup() {
		starshipClassFilterMock = Mock()
		templateFinderMock = Mock()
		pageBindingServiceMock = Mock()
		starshipClassTemplateCategoriesEnrichingProcessorMock = Mock()
		starshipClassTemplateCompositeEnrichingProcessorMock = Mock()
		starshipClassTemplatePageProcessor = new StarshipClassTemplatePageProcessor(starshipClassFilterMock, templateFinderMock,
				pageBindingServiceMock, starshipClassTemplateCategoriesEnrichingProcessorMock, starshipClassTemplateCompositeEnrichingProcessorMock)
	}

	void "when StarshipClassFilter returns true, null is returned"() {
		given:
		Page page = Mock()

		when:
		StarshipClassTemplate starshipClassTemplate = starshipClassTemplatePageProcessor.process(page)

		then:
		1 * starshipClassFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		starshipClassTemplate == null
	}

	void "when sidebar starship class template is not found, basic StarshipClassTemplate is returned"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock()
		Page page = new Page(
				title: TITLE_WITH_BRACKETS,
				categories: categoryHeaderList)
		ModelPage modelPage = Mock()

		when:
		StarshipClassTemplate starshipClassTemplate = starshipClassTemplatePageProcessor.process(page)

		then:
		1 * starshipClassFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_STARSHIP_CLASS) >> Optional.empty()
		1 * starshipClassTemplateCategoriesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template, StarshipClassTemplate> enrichablePair ->
				assert enrichablePair.input == categoryHeaderList
				assert enrichablePair.output != null
		}
		0 * _
		starshipClassTemplate.name == TITLE
		starshipClassTemplate.page == modelPage
	}

	void "when sidebar starship class template is found, it is passed along with StarshipClassTemplate to enriching processor"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock()
		Page page = new Page(
				title: TITLE_WITH_BRACKETS,
				categories: categoryHeaderList)
		ModelPage modelPage = Mock()
		Template template = Mock()

		when:
		StarshipClassTemplate starshipClassTemplate = starshipClassTemplatePageProcessor.process(page)

		then:
		1 * starshipClassFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_STARSHIP_CLASS) >> Optional.of(template)
		1 * starshipClassTemplateCategoriesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Template, StarshipClassTemplate> enrichablePair ->
			assert enrichablePair.input == categoryHeaderList
			assert enrichablePair.output != null
		}
		1 * starshipClassTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Template, StarshipClassTemplate> enrichablePair ->
			assert enrichablePair.input == template
			assert enrichablePair.output != null
		}
		0 * _
		starshipClassTemplate.name == TITLE
		starshipClassTemplate.page == modelPage
	}

}
