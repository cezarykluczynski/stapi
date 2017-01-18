package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.google.common.collect.Lists
import spock.lang.Specification

class IndividualMirrorAlternateUniverseEnrichingProcessorTest extends Specification {

	private TemplateFinder templateFinderMock

	private IndividualMirrorAlternateUniverseEnrichingProcessor individualMirrorAlternateUniverseEnrichingProcessor

	def setup() {
		templateFinderMock = Mock(TemplateFinder)
		individualMirrorAlternateUniverseEnrichingProcessor = new IndividualMirrorAlternateUniverseEnrichingProcessor(templateFinderMock)
	}

	def "adds mirror flag when title contains mirror"() {
		given:
		Page page = new Page(title: 'Jonathan Archer (mirror)')
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualMirrorAlternateUniverseEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		2 * templateFinderMock.findTemplate(page, _) >> Optional.empty()
		0 * _
		individualTemplate.mirror
		!individualTemplate.alternateReality
	}

	def "adds mirror flag when mirror template is present"() {
		given:
		Page page = new Page(title: 'Jonathan Archer')
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualMirrorAlternateUniverseEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.MIRROR) >> Optional.of(new Template())
		1 * templateFinderMock.findTemplate(page, TemplateName.ALT_REALITY) >> Optional.empty()
		0 * _
		individualTemplate.mirror
		!individualTemplate.alternateReality
	}

	def "adds mirror flag when category contains mirror inhabitants"() {
		given:
		Page page = new Page(
				title: 'Jonathan Archer',
				categories: Lists.newArrayList(new CategoryHeader(title: CategoryName.MIRROR_UNIVERSE_INHABITANTS)))
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualMirrorAlternateUniverseEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		2 * templateFinderMock.findTemplate(page, _) >> Optional.empty()
		0 * _
		individualTemplate.mirror
		!individualTemplate.alternateReality
	}

	def "adds alternate reality flag when title contains mirror"() {
		given:
		Page page = new Page(title: 'Carol Marcus (alternate reality)')
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualMirrorAlternateUniverseEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		2 * templateFinderMock.findTemplate(page, _) >> Optional.empty()
		0 * _
		!individualTemplate.mirror
		individualTemplate.alternateReality
	}

	def "adds alternate reality flag when alternate reality template is present"() {
		given:
		Page page = new Page(title: 'Jonathan Archer')
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualMirrorAlternateUniverseEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.MIRROR) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateName.ALT_REALITY) >> Optional.of(new Template())
		0 * _
		!individualTemplate.mirror
		individualTemplate.alternateReality
	}

	def "adds alternate reality flag when category name contains alternate reality"() {
		given:
		Page page = new Page(
				title: 'Jonathan Archer',
				categories: Lists.newArrayList(new CategoryHeader(title: 'USS Enterprise (NCC-1701) personnel (alternate reality)')))
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualMirrorAlternateUniverseEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		2 * templateFinderMock.findTemplate(page, _) >> Optional.empty()
		0 * _
		!individualTemplate.mirror
		individualTemplate.alternateReality
	}

}
