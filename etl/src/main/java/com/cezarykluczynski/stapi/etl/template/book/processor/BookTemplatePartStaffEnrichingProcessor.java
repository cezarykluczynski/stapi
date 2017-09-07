package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextStaffProcessor;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplateParameter;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

@Service
public class BookTemplatePartStaffEnrichingProcessor implements ItemWithTemplatePartEnrichingProcessor<BookTemplate> {

	private final WikitextStaffProcessor wikitextStaffProcessor;

	@Inject
	public BookTemplatePartStaffEnrichingProcessor(WikitextStaffProcessor wikitextStaffProcessor) {
		this.wikitextStaffProcessor = wikitextStaffProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, BookTemplate> enrichablePair) throws Exception {
		Template.Part templatePart = enrichablePair.getInput();
		BookTemplate bookTemplate = enrichablePair.getOutput();
		Set<Staff> staffSet = wikitextStaffProcessor.process(templatePart.getValue());
		String templatePartKey = templatePart.getKey();

		if (BookTemplateParameter.AUTHOR.equals(templatePartKey)) {
			bookTemplate.getAuthors().addAll(staffSet);
		} else if (BookTemplateParameter.ARTIST.equals(templatePartKey)) {
			bookTemplate.getArtists().addAll(staffSet);
		} else if (BookTemplateParameter.EDITOR.equals(templatePartKey)) {
			bookTemplate.getEditors().addAll(staffSet);
		} else if (BookTemplateParameter.AUDIOBOOK_READ_BY.equals(templatePartKey)) {
			bookTemplate.getAudiobookNarrators().addAll(staffSet);
		}
	}

}
