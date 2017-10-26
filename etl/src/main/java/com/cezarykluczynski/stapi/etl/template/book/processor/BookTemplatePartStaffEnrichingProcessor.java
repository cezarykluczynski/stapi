package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplateParameter;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookTemplatePartStaffEnrichingProcessor implements ItemWithTemplatePartEnrichingProcessor<BookTemplate> {

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	public BookTemplatePartStaffEnrichingProcessor(WikitextToEntitiesProcessor wikitextToEntitiesProcessor) {
		this.wikitextToEntitiesProcessor = wikitextToEntitiesProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, BookTemplate> enrichablePair) throws Exception {
		Template.Part templatePart = enrichablePair.getInput();
		BookTemplate bookTemplate = enrichablePair.getOutput();
		List<Staff> staffList = wikitextToEntitiesProcessor.findStaff(templatePart.getValue());
		String templatePartKey = templatePart.getKey();

		if (BookTemplateParameter.AUTHOR.equals(templatePartKey)) {
			bookTemplate.getAuthors().addAll(staffList);
		} else if (BookTemplateParameter.ARTIST.equals(templatePartKey)) {
			bookTemplate.getArtists().addAll(staffList);
		} else if (BookTemplateParameter.EDITOR.equals(templatePartKey)) {
			bookTemplate.getEditors().addAll(staffList);
		} else if (BookTemplateParameter.AUDIOBOOK_READ_BY.equals(templatePartKey)) {
			bookTemplate.getAudiobookNarrators().addAll(staffList);
		}
	}

}
