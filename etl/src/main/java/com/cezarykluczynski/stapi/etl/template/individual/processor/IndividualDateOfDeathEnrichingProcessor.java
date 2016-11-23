package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class IndividualDateOfDeathEnrichingProcessor implements
		ItemEnrichingProcessor<EnrichablePair<Template, IndividualTemplate>> {

	private static final String STATUS = "status";
	private static final String DATE_STATUS = "datestatus";
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
			"assimilate",
			"merged",
			"separated from the collective"
	);

	private WikitextApi wikitextApi;

	private IndividualDateStatusValueToYearProcessor individualDateStatusValueToYearProcessor;

	@Inject
	public IndividualDateOfDeathEnrichingProcessor(WikitextApi wikitextApi,
			IndividualDateStatusValueToYearProcessor individualDateStatusValueToYearProcessor) {
		this.wikitextApi = wikitextApi;
		this.individualDateStatusValueToYearProcessor = individualDateStatusValueToYearProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, IndividualTemplate> enrichablePair) {
		Template template = enrichablePair.getInput();
		IndividualTemplate individualTemplate = enrichablePair.getOutput();

		Template.Part status = null;
		Template.Part dateStatus = null;

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();

			switch (key) {
				case STATUS:
					status = part;
					break;
				case DATE_STATUS:
					dateStatus = part;
					break;
			}
		}

		if (status != null && dateStatus != null) {
			try {
				doEnrich(individualTemplate, status, dateStatus);
			} catch (Exception e) {
			}
		}
	}

	private void doEnrich(IndividualTemplate individualTemplate, Template.Part status, Template.Part dateStatus)
			throws Exception {
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
			log.error("Conflicting findings on whether individual {} is dead or alive, found both in status: {}",
					individualTemplate.getName(), statusValue);
		} else if (!isAlive && !isDeceased) {
			log.warn("Could not determine whether individual {} is dead or alive, found status: {}",
					individualTemplate.getName(), statusValue);
		} else if (isDeceased) {
			individualTemplate.setDeceased(true);
			individualTemplate.setYearOfDeath(individualDateStatusValueToYearProcessor.process(dateStatus.getValue()));
		}
	}

}