package com.cezarykluczynski.stapi.etl.template.comics.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextStaffProcessor;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplateParameter;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

@Service
public class ComicsTemplatePartStaffEnrichingProcessor implements ItemWithTemplatePartEnrichingProcessor<ComicsTemplate> {

	private final WikitextStaffProcessor wikitextStaffProcessor;

	@Inject
	public ComicsTemplatePartStaffEnrichingProcessor(WikitextStaffProcessor wikitextStaffProcessor) {
		this.wikitextStaffProcessor = wikitextStaffProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, ComicsTemplate> enrichablePair) throws Exception {
		Template.Part templatePart = enrichablePair.getInput();
		ComicsTemplate comicsTemplate = enrichablePair.getOutput();
		Set<Staff> staffSet = wikitextStaffProcessor.process(templatePart.getValue());
		String templatePartKey = templatePart.getKey();

		if (ComicsTemplateParameter.WRITER.equals(templatePartKey)) {
			comicsTemplate.getWriters().addAll(staffSet);
		} else if (ComicsTemplateParameter.ARTIST.equals(templatePartKey)) {
			comicsTemplate.getArtists().addAll(staffSet);
		} else if (ComicsTemplateParameter.EDITOR.equals(templatePartKey)) {
			comicsTemplate.getEditors().addAll(staffSet);
		}
	}

}
