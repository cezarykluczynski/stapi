package com.cezarykluczynski.stapi.etl.character.link.processor;

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationCacheKey;
import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap;
import com.cezarykluczynski.stapi.etl.character.common.service.CharactersRelationsCache;
import com.cezarykluczynski.stapi.etl.character.link.relation.service.CharacterRelationsTemplateParametersDetector;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CharacterLinkCacheStoringProcessor implements ItemProcessor<Page, CharacterRelationsMap> {

	private final TemplateFinder templateFinder;

	private final CharacterRelationsTemplateParametersDetector characterRelationsTemplateParametersDetector;

	private final CharactersRelationsCache charactersRelationsCache;

	public CharacterLinkCacheStoringProcessor(TemplateFinder templateFinder,
			CharacterRelationsTemplateParametersDetector characterRelationsTemplateParametersDetector,
			CharactersRelationsCache charactersRelationsCache) {
		this.templateFinder = templateFinder;
		this.characterRelationsTemplateParametersDetector = characterRelationsTemplateParametersDetector;
		this.charactersRelationsCache = charactersRelationsCache;
	}

	@Override
	public CharacterRelationsMap process(Page item) throws Exception {
		CharacterRelationsMap characterRelationsMap = new CharacterRelationsMap();

		Optional<Template> sidebarIndividualTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_INDIVIDUAL);
		sidebarIndividualTemplateOptional.ifPresent(sidebarIndividualTemplate -> sidebarIndividualTemplate
				.getParts().stream()
				.filter(part -> characterRelationsTemplateParametersDetector.isSidebarIndividualPartKey(part.getKey()))
				.forEach(part -> addToCache(part, TemplateTitle.SIDEBAR_INDIVIDUAL, characterRelationsMap)));

		Optional<Template> sidebarHologramTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_HOLOGRAM);
		sidebarHologramTemplateOptional.ifPresent(sidebarHologramTemplate -> sidebarHologramTemplate
				.getParts().stream()
				.filter(part -> characterRelationsTemplateParametersDetector.isSidebarHologramPartKey(part.getKey()))
				.forEach(part -> addToCache(part, TemplateTitle.SIDEBAR_HOLOGRAM, characterRelationsMap)));

		Optional<Template> sidebarFictionalTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_FICTIONAL);
		sidebarFictionalTemplateOptional.ifPresent(sidebarFictionalTemplate -> sidebarFictionalTemplate
				.getParts().stream()
				.filter(part -> characterRelationsTemplateParametersDetector.isSidebarFictionalPartKey(part.getKey()))
				.forEach(part -> addToCache(part, TemplateTitle.SIDEBAR_FICTIONAL, characterRelationsMap)));

		charactersRelationsCache.put(item.getPageId(), characterRelationsMap);
		return characterRelationsMap;
	}

	private void addToCache(Template.Part part, String sidebarTemplateTitle, CharacterRelationsMap characterRelationsMap) {
		characterRelationsMap.put(CharacterRelationCacheKey.of(sidebarTemplateTitle, part.getKey()), part);
	}

}
