package com.cezarykluczynski.stapi.etl.template.comics.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplateParameter;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComicsTemplatePartStaffEnrichingProcessor implements ItemWithTemplatePartEnrichingProcessor<ComicsTemplate> {

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	public ComicsTemplatePartStaffEnrichingProcessor(WikitextToEntitiesProcessor wikitextToEntitiesProcessor) {
		this.wikitextToEntitiesProcessor = wikitextToEntitiesProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, ComicsTemplate> enrichablePair) throws Exception {
		Template.Part templatePart = enrichablePair.getInput();
		ComicsTemplate comicsTemplate = enrichablePair.getOutput();
		List<Staff> staffList = wikitextToEntitiesProcessor.findStaff(templatePart.getValue());
		String templatePartKey = templatePart.getKey();

		if (ComicsTemplateParameter.WRITER.equals(templatePartKey)) {
			comicsTemplate.getWriters().addAll(staffList);
		} else if (ComicsTemplateParameter.ARTIST.equals(templatePartKey)) {
			comicsTemplate.getArtists().addAll(staffList);
		} else if (ComicsTemplateParameter.EDITOR.equals(templatePartKey)) {
			comicsTemplate.getEditors().addAll(staffList);
		}
	}

}
