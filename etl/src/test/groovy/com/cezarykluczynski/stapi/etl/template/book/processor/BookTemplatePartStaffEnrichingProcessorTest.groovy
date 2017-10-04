package com.cezarykluczynski.stapi.etl.template.book.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplateParameter
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class BookTemplatePartStaffEnrichingProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private BookTemplatePartStaffEnrichingProcessor bookTemplatePartStaffEnrichingProcessor

	void setup() {
		wikitextToEntitiesProcessorMock = Mock()
		bookTemplatePartStaffEnrichingProcessor = new BookTemplatePartStaffEnrichingProcessor(wikitextToEntitiesProcessorMock)
	}

	void "gets authors from wikitext"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUTHOR, value: WIKITEXT)
		BookTemplate bookTemplate = new BookTemplate()
		Staff staff1 = Mock()
		Staff staff2 = Mock()

		when:
		bookTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(templatePart, bookTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findStaff(WIKITEXT) >> Lists.newArrayList(staff1, staff2)
		0 * _
		bookTemplate.authors.size() == 2
		bookTemplate.authors.contains staff1
		bookTemplate.authors.contains staff2
		bookTemplate.artists.empty
		bookTemplate.editors.empty
		bookTemplate.audiobookNarrators.empty
	}

	void "gets artists from wikitext"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.ARTIST, value: WIKITEXT)
		BookTemplate bookTemplate = new BookTemplate()
		Staff staff1 = Mock()
		Staff staff2 = Mock()

		when:
		bookTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(templatePart, bookTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findStaff(WIKITEXT) >> Lists.newArrayList(staff1, staff2)
		0 * _
		bookTemplate.artists.size() == 2
		bookTemplate.artists.contains staff1
		bookTemplate.artists.contains staff2
		bookTemplate.authors.empty
		bookTemplate.editors.empty
		bookTemplate.audiobookNarrators.empty
	}

	void "gets editors from wikitext"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.EDITOR, value: WIKITEXT)
		BookTemplate bookTemplate = new BookTemplate()
		Staff staff1 = Mock()
		Staff staff2 = Mock()

		when:
		bookTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(templatePart, bookTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findStaff(WIKITEXT) >> Lists.newArrayList(staff1, staff2)
		0 * _
		bookTemplate.editors.size() == 2
		bookTemplate.editors.contains staff1
		bookTemplate.editors.contains staff2
		bookTemplate.authors.empty
		bookTemplate.artists.empty
		bookTemplate.audiobookNarrators.empty
	}

	void "gets audiobook narrators from wikitext"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUDIOBOOK_READ_BY, value: WIKITEXT)
		BookTemplate bookTemplate = new BookTemplate()
		Staff staff1 = Mock()
		Staff staff2 = Mock()

		when:
		bookTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(templatePart, bookTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findStaff(WIKITEXT) >> Lists.newArrayList(staff1, staff2)
		0 * _
		bookTemplate.audiobookNarrators.size() == 2
		bookTemplate.audiobookNarrators.contains staff1
		bookTemplate.audiobookNarrators.contains staff2
		bookTemplate.authors.empty
		bookTemplate.artists.empty
		bookTemplate.editors.empty
	}

	void "tolerates parts with unknown key"() {
		given:
		Template.Part templatePart = new Template.Part(key: '', value: WIKITEXT)
		BookTemplate bookTemplate = new BookTemplate()
		Staff staff1 = Mock()

		when:
		bookTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(templatePart, bookTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findStaff(WIKITEXT) >> Lists.newArrayList(staff1)
		0 * _
		bookTemplate.authors.empty
		bookTemplate.artists.empty
		bookTemplate.editors.empty
		bookTemplate.audiobookNarrators.empty
		notThrown(Exception)
	}

}
