package com.cezarykluczynski.stapi.etl.character.link.processor

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationCacheKey
import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap
import com.cezarykluczynski.stapi.etl.character.common.service.CharactersRelationsCache
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class CharacterLinkCacheStoringProcessorTest extends Specification {

	private static final Long PAGE_ID = 5L

	private TemplateFinder templateFinderMock

	private CharactersRelationsCache charactersRelationsCacheMock

	private CharacterLinkCacheStoringProcessor characterLinkCacheStoringProcessor

	void setup() {
		templateFinderMock = Mock()
		charactersRelationsCacheMock = Mock()
		characterLinkCacheStoringProcessor = new CharacterLinkCacheStoringProcessor(templateFinderMock, charactersRelationsCacheMock)
	}

	@SuppressWarnings('LineLength')
	void "puts values from all templates into cache"() {
		given:
		Template.Part sidebarIndividualTemplateTemplatePart = new Template.Part(key: RandomUtil.randomItem(CharacterLinkCacheStoringProcessor.SIDEBAR_INDIVIDUAL_RELATIONS_KEYS))
		Template.Part sidebarHologramTemplateTemplatePart = new Template.Part(key: RandomUtil.randomItem(CharacterLinkCacheStoringProcessor.SIDEBAR_HOLOGRAM_RELATIONS_KEYS))
		Template.Part sidebarFictionalTemplateTemplatePart = new Template.Part(key: RandomUtil.randomItem(CharacterLinkCacheStoringProcessor.SIDEBAR_FICTIONAL_RELATIONS_KEYS))
		Template.Part templatePartToSkip = new Template.Part(key: 'skip me')

		Template sidebarIndividualTemplate = new Template(
				title: TemplateTitle.SIDEBAR_INDIVIDUAL,
				parts: Lists.newArrayList(templatePartToSkip, sidebarIndividualTemplateTemplatePart))
		Template sidebarHologramTemplate = new Template(
				title: TemplateTitle.SIDEBAR_HOLOGRAM,
				parts: Lists.newArrayList(sidebarHologramTemplateTemplatePart, templatePartToSkip))
		Template sidebarFictionalTemplate = new Template(
				title: TemplateTitle.SIDEBAR_FICTIONAL,
				parts: Lists.newArrayList(sidebarFictionalTemplateTemplatePart))

		List<Template> templateList = Lists.newArrayList(sidebarIndividualTemplate, sidebarHologramTemplate, sidebarFictionalTemplate)
		Page page = new Page(pageId: PAGE_ID, templates: templateList)

		when:
		CharacterRelationsMap characterRelationsMap = characterLinkCacheStoringProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_INDIVIDUAL) >> Optional.of(sidebarIndividualTemplate)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_HOLOGRAM) >> Optional.of(sidebarHologramTemplate)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_FICTIONAL) >> Optional.of(sidebarFictionalTemplate)
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMapInput ->
			assert pageId == PAGE_ID
			assert characterRelationsMapInput.size() == 3
			assert characterRelationsMapInput[CharacterRelationCacheKey.of(TemplateTitle.SIDEBAR_INDIVIDUAL, sidebarIndividualTemplateTemplatePart.key)] == sidebarIndividualTemplateTemplatePart
			assert characterRelationsMapInput[CharacterRelationCacheKey.of(TemplateTitle.SIDEBAR_HOLOGRAM, sidebarHologramTemplateTemplatePart.key)] == sidebarHologramTemplateTemplatePart
			assert characterRelationsMapInput[CharacterRelationCacheKey.of(TemplateTitle.SIDEBAR_FICTIONAL, sidebarFictionalTemplateTemplatePart.key)] == sidebarFictionalTemplateTemplatePart
		}
		0 * _
		characterRelationsMap.size() == 3
		characterRelationsMap[CharacterRelationCacheKey.of(TemplateTitle.SIDEBAR_INDIVIDUAL, sidebarIndividualTemplateTemplatePart.key)] == sidebarIndividualTemplateTemplatePart
		characterRelationsMap[CharacterRelationCacheKey.of(TemplateTitle.SIDEBAR_HOLOGRAM, sidebarHologramTemplateTemplatePart.key)] == sidebarHologramTemplateTemplatePart
		characterRelationsMap[CharacterRelationCacheKey.of(TemplateTitle.SIDEBAR_FICTIONAL, sidebarFictionalTemplateTemplatePart.key)] == sidebarFictionalTemplateTemplatePart
	}

	void "puts empty map into cache if nothing could be found"() {
		given:
		Page page = new Page(pageId: PAGE_ID)

		when:
		CharacterRelationsMap characterRelationsMap = characterLinkCacheStoringProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_HOLOGRAM) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_FICTIONAL) >> Optional.empty()
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> {
				Long pageId, CharacterRelationsMap characterRelationsMapInput ->
			assert pageId == PAGE_ID
			assert characterRelationsMapInput.isEmpty()
		}
		0 * _
		characterRelationsMap.isEmpty()
	}

}
