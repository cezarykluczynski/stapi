package com.cezarykluczynski.stapi.etl.character.link.processor;

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationCacheKey;
import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap;
import com.cezarykluczynski.stapi.etl.character.common.service.CharactersRelationsCache;
import com.cezarykluczynski.stapi.etl.template.fictional.dto.FictionalTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.hologram.dto.HologramTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Sets;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CharacterLinkCacheStoringProcessor implements ItemProcessor<Page, CharacterRelationsMap> {

	private static final Set<String> SIDEBAR_INDIVIDUAL_RELATIONS_KEYS = Sets.newHashSet(IndividualTemplateParameter.FATHER,
			IndividualTemplateParameter.MOTHER, IndividualTemplateParameter.OWNER, IndividualTemplateParameter.SIBLING,
			IndividualTemplateParameter.RELATIVE, IndividualTemplateParameter.SPOUSE, IndividualTemplateParameter.CHILDREN);

	private static final Set<String> SIDEBAR_HOLOGRAM_RELATIONS_KEYS = Sets.newHashSet(HologramTemplateParameter.CREATOR,
			HologramTemplateParameter.SPOUSE, HologramTemplateParameter.CHILDREN, HologramTemplateParameter.RELATIVE);

	private static final Set<String> SIDEBAR_FICTIONAL_RELATIONS_KEYS = Sets.newHashSet(FictionalTemplateParameter.CREATOR,
			FictionalTemplateParameter.CHARACTER, FictionalTemplateParameter.SPOUSE, FictionalTemplateParameter.CHILDREN,
			FictionalTemplateParameter.RELATIVE);

	private final TemplateFinder templateFinder;

	private final CharactersRelationsCache charactersRelationsCache;

	public CharacterLinkCacheStoringProcessor(TemplateFinder templateFinder, CharactersRelationsCache charactersRelationsCache) {
		this.templateFinder = templateFinder;
		this.charactersRelationsCache = charactersRelationsCache;
	}

	@Override
	public CharacterRelationsMap process(Page item) throws Exception {
		CharacterRelationsMap characterRelationsMap = new CharacterRelationsMap();

		Optional<Template> sidebarIndividualTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_INDIVIDUAL);
		sidebarIndividualTemplateOptional.ifPresent(sidebarIndividualTemplate -> sidebarIndividualTemplate
				.getParts().stream()
				.filter(part -> SIDEBAR_INDIVIDUAL_RELATIONS_KEYS.contains(part.getKey()))
				.forEach(part -> addToCache(part, TemplateTitle.SIDEBAR_INDIVIDUAL, characterRelationsMap)));

		Optional<Template> sidebarHologramTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_HOLOGRAM);
		sidebarHologramTemplateOptional.ifPresent(sidebarHologramTemplate -> sidebarHologramTemplate
				.getParts().stream()
				.filter(part -> SIDEBAR_HOLOGRAM_RELATIONS_KEYS.contains(part.getKey()))
				.forEach(part -> addToCache(part, TemplateTitle.SIDEBAR_HOLOGRAM, characterRelationsMap)));

		Optional<Template> sidebarFictionalTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_FICTIONAL);
		sidebarFictionalTemplateOptional.ifPresent(sidebarFictionalTemplate -> sidebarFictionalTemplate
				.getParts().stream()
				.filter(part -> SIDEBAR_FICTIONAL_RELATIONS_KEYS.contains(part.getKey()))
				.forEach(part -> addToCache(part, TemplateTitle.SIDEBAR_FICTIONAL, characterRelationsMap)));

		charactersRelationsCache.put(item.getPageId(), characterRelationsMap);
		return characterRelationsMap;
	}

	private void addToCache(Template.Part part, String sidebarTemplateTitle, CharacterRelationsMap characterRelationsMap) {
		characterRelationsMap.put(CharacterRelationCacheKey.of(sidebarTemplateTitle, part.getKey()), part);
	}

}
