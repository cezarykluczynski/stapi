package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IndividualTemplateDateOfDeathEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<CharacterTemplate> {

	private static final String KIA = "kia";
	private static final List<String> DEAD_SYNONYMS = Lists.newArrayList(
			"deceased",
			"suicide",
			"dead",
			"died",
			"killed",
			"assassinate",
			"murder",
			"executed",
			"destroyed",
			"missing in action",
			"missing-in-action",
			"existence erased",
			"disassembled",
			"ceased to exist",
			"missing"
	);
	private static final List<String> NOT_DEAD_SYNONYMS = Lists.newArrayList(
			"arrest",
			"active",
			"alive",
			"in custody",
			"punished",
			"detained",
			"unknown",
			"disappeared",
			"insane",
			"incarcerated",
			"assimilate",
			"retired",
			"expelled",
			"comatose",
			"evolved",
			"imprisoned",
			"relieved",
			"resigned",
			"rehabilitation",
			"trapped",
			"assigned to",
			"discredited",
			"mutated",
			"institutionalized",
			"pending trial",
			"assumed the identity",
			"captured",
			"merged",
			"separated from the collective"
	);

	private final WikitextApi wikitextApi;

	private final IndividualDateStatusValueToYearProcessor individualDateStatusValueToYearProcessor;

	public IndividualTemplateDateOfDeathEnrichingProcessor(WikitextApi wikitextApi,
			IndividualDateStatusValueToYearProcessor individualDateStatusValueToYearProcessor) {
		this.wikitextApi = wikitextApi;
		this.individualDateStatusValueToYearProcessor = individualDateStatusValueToYearProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, CharacterTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		CharacterTemplate characterTemplate = enrichablePair.getOutput();

		Template.Part status = null;
		Template.Part dateStatus = null;

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();

			switch (key) {
				case IndividualTemplateParameter.STATUS:
					status = part;
					break;
				case IndividualTemplateParameter.DATE_STATUS:
					dateStatus = part;
					break;
				default:
					break;
			}
		}

		if (status != null && dateStatus != null) {
			doEnrich(characterTemplate, status, dateStatus);
		}
	}

	private void doEnrich(CharacterTemplate characterTemplate, Template.Part status, Template.Part dateStatus) throws Exception {
		String statusValue = StringUtils.lowerCase(StringUtils.trim(status.getValue()));

		if (StringUtils.isBlank(statusValue)) {
			return;
		}

		List<PageLink> pageLink = wikitextApi.getPageLinksFromWikitext(status.getValue());

		boolean isDeceased = false;

		if (KIA.equals(statusValue) || !pageLink.isEmpty() && KIA.equals(pageLink.get(0).getDescription())) {
			isDeceased = true;
		}

		isDeceased = isDeceased || DEAD_SYNONYMS.stream().anyMatch(statusValue::contains);
		boolean isAlive = NOT_DEAD_SYNONYMS.stream().anyMatch(statusValue::contains);

		if (isAlive && isDeceased) {
			log.warn("Conflicting findings on whether individual {} is dead or alive, found both in status: {}",
					characterTemplate.getName(), statusValue);
		} else if (!isAlive && !isDeceased) {
			log.warn("Could not determine whether individual {} is dead or alive, found status: {}",
					characterTemplate.getName(), statusValue);
		} else if (isDeceased) {
			characterTemplate.setDeceased(true);
			characterTemplate.setYearOfDeath(individualDateStatusValueToYearProcessor.process(dateStatus.getValue()));
		}
	}

}
