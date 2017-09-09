package com.cezarykluczynski.stapi.etl.template.character.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

class CharacterTemplateFlagsEnrichingProcessorTest extends Specification {

	private TemplateFinder templateFinderMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private CharacterTemplateFlagsEnrichingProcessor characterTemplateFlagsEnrichingProcessor

	void setup() {
		templateFinderMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		characterTemplateFlagsEnrichingProcessor = new CharacterTemplateFlagsEnrichingProcessor(templateFinderMock,
				categoryTitlesExtractingProcessorMock)
	}

	void "adds mirror flag when title contains 'mirror'"() {
		given:
		Page page = new Page(title: 'Jonathan Archer (mirror)')
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		characterTemplateFlagsEnrichingProcessor.enrich(EnrichablePair.of(page, characterTemplate))

		then:
		1 * templateFinderMock.findTemplate(page, _) >> Optional.empty()
		1 * categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> Lists.newArrayList()
		0 * _
		characterTemplate.mirror
		!characterTemplate.alternateReality
		!characterTemplate.hologram
		!characterTemplate.fictionalCharacter
	}

	void "adds mirror flag when mirror template is present"() {
		given:
		Page page = new Page(title: 'Jonathan Archer')
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		characterTemplateFlagsEnrichingProcessor.enrich(EnrichablePair.of(page, characterTemplate))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> Lists.newArrayList()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.MIRROR) >> Optional.of(new Template())
		1 * templateFinderMock.findTemplate(page, TemplateTitle.ALT_REALITY) >> Optional.empty()
		0 * _
		characterTemplate.mirror
		!characterTemplate.alternateReality
		!characterTemplate.hologram
		!characterTemplate.fictionalCharacter
	}

	void "adds mirror flag when category contains mirror inhabitants"() {
		given:
		Page page = new Page(
				title: 'Jonathan Archer',
				categories: Lists.newArrayList(new CategoryHeader(title: CategoryTitle.MIRROR_UNIVERSE_INHABITANTS)))
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		characterTemplateFlagsEnrichingProcessor.enrich(EnrichablePair.of(page, characterTemplate))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}
		2 * templateFinderMock.findTemplate(page, _) >> Optional.empty()
		0 * _
		characterTemplate.mirror
		!characterTemplate.alternateReality
		!characterTemplate.hologram
		!characterTemplate.fictionalCharacter
	}

	void "adds alternate reality flag when title contains 'alternate reality'"() {
		given:
		Page page = new Page(title: 'Carol Marcus (alternate reality)')
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		characterTemplateFlagsEnrichingProcessor.enrich(EnrichablePair.of(page, characterTemplate))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> Lists.newArrayList()
		1 * templateFinderMock.findTemplate(page, _) >> Optional.empty()
		0 * _
		!characterTemplate.mirror
		characterTemplate.alternateReality
		!characterTemplate.hologram
		!characterTemplate.fictionalCharacter
	}

	void "adds alternate reality flag when alternate reality template is present"() {
		given:
		Page page = new Page(title: 'Jonathan Archer')
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		characterTemplateFlagsEnrichingProcessor.enrich(EnrichablePair.of(page, characterTemplate))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> Lists.newArrayList()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.MIRROR) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.ALT_REALITY) >> Optional.of(new Template())
		0 * _
		!characterTemplate.mirror
		characterTemplate.alternateReality
		!characterTemplate.hologram
		!characterTemplate.fictionalCharacter
	}

	void "adds alternate reality flag when category name contains 'alternate reality'"() {
		given:
		Page page = new Page(
				title: 'Jonathan Archer',
				categories: Lists.newArrayList(new CategoryHeader(title: 'USS Enterprise (NCC-1701) personnel (alternate reality)')))
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		characterTemplateFlagsEnrichingProcessor.enrich(EnrichablePair.of(page, characterTemplate))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}
		2 * templateFinderMock.findTemplate(page, _) >> Optional.empty()
		0 * _
		!characterTemplate.mirror
		characterTemplate.alternateReality
		!characterTemplate.hologram
		!characterTemplate.fictionalCharacter
	}

	void "adds hologram flag when category from fictional characters categories is present"() {
		given:
		Page page = new Page(
				title: StringUtils.EMPTY,
				categories: Lists.newArrayList(new CategoryHeader(
						title: RandomUtil.randomItem(CharacterTemplateFlagsEnrichingProcessor.HOLOGRAMS_CATEGORIES))))
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		characterTemplateFlagsEnrichingProcessor.enrich(EnrichablePair.of(page, characterTemplate))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}
		2 * templateFinderMock.findTemplate(page, _) >> Optional.empty()
		0 * _
		!characterTemplate.mirror
		!characterTemplate.alternateReality
		characterTemplate.hologram
		!characterTemplate.fictionalCharacter
	}

	void "adds fictional character flag when category from fictional characters categories is present"() {
		given:
		Page page = new Page(
				title: StringUtils.EMPTY,
				categories: Lists.newArrayList(new CategoryHeader(
						title: RandomUtil.randomItem(CharacterTemplateFlagsEnrichingProcessor.FICTIONAL_CHARACTERS_CATEGORIES))))
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		characterTemplateFlagsEnrichingProcessor.enrich(EnrichablePair.of(page, characterTemplate))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}
		2 * templateFinderMock.findTemplate(page, _) >> Optional.empty()
		0 * _
		!characterTemplate.mirror
		!characterTemplate.alternateReality
		!characterTemplate.hologram
		characterTemplate.fictionalCharacter
	}

}
