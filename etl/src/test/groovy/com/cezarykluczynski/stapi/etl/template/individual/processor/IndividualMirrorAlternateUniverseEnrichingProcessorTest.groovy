package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class IndividualMirrorAlternateUniverseEnrichingProcessorTest extends Specification {

	private TemplateFinder templateFinderMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private IndividualMirrorAlternateUniverseEnrichingProcessor individualMirrorAlternateUniverseEnrichingProcessor

	void setup() {
		templateFinderMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		individualMirrorAlternateUniverseEnrichingProcessor = new IndividualMirrorAlternateUniverseEnrichingProcessor(templateFinderMock,
				categoryTitlesExtractingProcessorMock)
	}

	void "adds mirror flag when title contains mirror"() {
		given:
		Page page = new Page(title: 'Jonathan Archer (mirror)')
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualMirrorAlternateUniverseEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		2 * templateFinderMock.findTemplate(page, _) >> Optional.empty()
		1 * categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> Lists.newArrayList()
		0 * _
		individualTemplate.mirror
		!individualTemplate.alternateReality
	}

	void "adds mirror flag when mirror template is present"() {
		given:
		Page page = new Page(title: 'Jonathan Archer')
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualMirrorAlternateUniverseEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> Lists.newArrayList()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.MIRROR) >> Optional.of(new Template())
		1 * templateFinderMock.findTemplate(page, TemplateTitle.ALT_REALITY) >> Optional.empty()
		0 * _
		individualTemplate.mirror
		!individualTemplate.alternateReality
	}

	void "adds mirror flag when category contains mirror inhabitants"() {
		given:
		Page page = new Page(
				title: 'Jonathan Archer',
				categories: Lists.newArrayList(new CategoryHeader(title: CategoryTitle.MIRROR_UNIVERSE_INHABITANTS)))
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualMirrorAlternateUniverseEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}
		2 * templateFinderMock.findTemplate(page, _) >> Optional.empty()
		0 * _
		individualTemplate.mirror
		!individualTemplate.alternateReality
	}

	void "adds alternate reality flag when title contains mirror"() {
		given:
		Page page = new Page(title: 'Carol Marcus (alternate reality)')
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualMirrorAlternateUniverseEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> Lists.newArrayList()
		2 * templateFinderMock.findTemplate(page, _) >> Optional.empty()
		0 * _
		!individualTemplate.mirror
		individualTemplate.alternateReality
	}

	void "adds alternate reality flag when alternate reality template is present"() {
		given:
		Page page = new Page(title: 'Jonathan Archer')
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualMirrorAlternateUniverseEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> Lists.newArrayList()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.MIRROR) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.ALT_REALITY) >> Optional.of(new Template())
		0 * _
		!individualTemplate.mirror
		individualTemplate.alternateReality
	}

	void "adds alternate reality flag when category name contains alternate reality"() {
		given:
		Page page = new Page(
				title: 'Jonathan Archer',
				categories: Lists.newArrayList(new CategoryHeader(title: 'USS Enterprise (NCC-1701) personnel (alternate reality)')))
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualMirrorAlternateUniverseEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}
		2 * templateFinderMock.findTemplate(page, _) >> Optional.empty()
		0 * _
		!individualTemplate.mirror
		individualTemplate.alternateReality
	}

}
