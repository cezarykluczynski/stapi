package com.cezarykluczynski.stapi.etl.template.military_conflict.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MilitaryConflictTemplatePartOfEnrichingProcessor implements ItemWithTemplatePartEnrichingProcessor<MilitaryConflictTemplate> {

	private static final String DOMINION_WAR = "Dominion War";
	private static final List<String> KLINGON_WAR = Lists.newArrayList("Klingon", "War");

	private final WikitextApi wikitextApi;

	public MilitaryConflictTemplatePartOfEnrichingProcessor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, MilitaryConflictTemplate> enrichablePair) throws Exception {
		List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(enrichablePair.getInput().getValue());
		MilitaryConflictTemplate militaryConflictTemplate = enrichablePair.getOutput();

		if (StringUtil.containsIgnoreCase(pageTitleList, DOMINION_WAR)) {
			militaryConflictTemplate.setDominionWarBattle(true);
		}

		if (pageTitleList.stream().anyMatch(pageTitle -> StringUtil.containsAllIgnoreCase(pageTitle, KLINGON_WAR))) {
			militaryConflictTemplate.setKlingonWar(true);
		}
	}

}
