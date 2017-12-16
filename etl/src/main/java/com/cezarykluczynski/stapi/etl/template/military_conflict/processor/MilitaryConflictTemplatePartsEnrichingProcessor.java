package com.cezarykluczynski.stapi.etl.template.military_conflict.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartListEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate;
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilitaryConflictTemplatePartsEnrichingProcessor implements ItemWithTemplatePartListEnrichingProcessor<MilitaryConflictTemplate> {

	private final MilitaryConflictTemplatePartOfEnrichingProcessor militaryConflictTemplatePartOfEnrichingProcessor;

	private final WikitextToYearRangeProcessor wikitextToYearRangeProcessor;

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	public MilitaryConflictTemplatePartsEnrichingProcessor(
			MilitaryConflictTemplatePartOfEnrichingProcessor militaryConflictTemplatePartOfEnrichingProcessor,
			WikitextToYearRangeProcessor wikitextToYearRangeProcessor,
			WikitextToEntitiesProcessor wikitextToEntitiesProcessor) {
		this.militaryConflictTemplatePartOfEnrichingProcessor = militaryConflictTemplatePartOfEnrichingProcessor;
		this.wikitextToYearRangeProcessor = wikitextToYearRangeProcessor;
		this.wikitextToEntitiesProcessor = wikitextToEntitiesProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, MilitaryConflictTemplate> enrichablePair) throws Exception {
		MilitaryConflictTemplate militaryConflictTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case MilitaryConflictTemplateParameter.PART_OF:
					militaryConflictTemplatePartOfEnrichingProcessor.enrich(EnrichablePair.of(part, militaryConflictTemplate));
					break;
				case MilitaryConflictTemplateParameter.DATE:
					YearRange yearRange = wikitextToYearRangeProcessor.process(value);
					if (yearRange != null) {
						militaryConflictTemplate.setYearFrom(yearRange.getYearFrom());
						militaryConflictTemplate.setYearTo(yearRange.getYearTo());
					}
					break;
				case MilitaryConflictTemplateParameter.LOCATION:
					militaryConflictTemplate.getLocations().addAll(wikitextToEntitiesProcessor.findLocations(value));
					break;
				case MilitaryConflictTemplateParameter.COMBATANT_1:
					militaryConflictTemplate.getFirstSideBelligerents().addAll(wikitextToEntitiesProcessor.findOrganizations(value));
					break;
				case MilitaryConflictTemplateParameter.COMMANDER_1:
					militaryConflictTemplate.getFirstSideCommanders().addAll(wikitextToEntitiesProcessor.findCharacters(value));
					break;
				case MilitaryConflictTemplateParameter.COMBATANT_2:
					militaryConflictTemplate.getSecondSideBelligerents().addAll(wikitextToEntitiesProcessor.findOrganizations(value));
					break;
				case MilitaryConflictTemplateParameter.COMMANDER_2:
					militaryConflictTemplate.getSecondSideCommanders().addAll(wikitextToEntitiesProcessor.findCharacters(value));
					break;
				default:
					break;
			}
		}
	}

}
