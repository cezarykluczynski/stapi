package com.cezarykluczynski.stapi.etl.template.comics.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextStaffProcessor
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplateParameter
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Sets
import spock.lang.Specification

class ComicsTemplatePartStaffEnrichingProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextStaffProcessor wikitextStaffProcessorMock

	private ComicsTemplatePartStaffEnrichingProcessor comicsTemplatePartStaffEnrichingProcessor

	void setup() {
		wikitextStaffProcessorMock = Mock()
		comicsTemplatePartStaffEnrichingProcessor = new ComicsTemplatePartStaffEnrichingProcessor(wikitextStaffProcessorMock)
	}

	void "gets writers from wikitext"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicsTemplateParameter.WRITER, value: WIKITEXT)
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		Staff staff1 = Mock(Staff)
		Staff staff2 = Mock(Staff)

		when:
		comicsTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicsTemplate))

		then:
		1 * wikitextStaffProcessorMock.process(WIKITEXT) >> Sets.newHashSet(staff1, staff2)
		0 * _
		comicsTemplate.writers.size() == 2
		comicsTemplate.writers.contains staff1
		comicsTemplate.writers.contains staff2
		comicsTemplate.artists.empty
		comicsTemplate.editors.empty
		comicsTemplate.staff.empty
	}

	void "gets artists from wikitext"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicsTemplateParameter.ARTIST, value: WIKITEXT)
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		Staff staff1 = Mock(Staff)
		Staff staff2 = Mock(Staff)

		when:
		comicsTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicsTemplate))

		then:
		1 * wikitextStaffProcessorMock.process(WIKITEXT) >> Sets.newHashSet(staff1, staff2)
		0 * _
		comicsTemplate.artists.size() == 2
		comicsTemplate.artists.contains staff1
		comicsTemplate.artists.contains staff2
		comicsTemplate.writers.empty
		comicsTemplate.editors.empty
		comicsTemplate.staff.empty
	}

	void "gets editors from wikitext"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicsTemplateParameter.EDITOR, value: WIKITEXT)
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		Staff staff1 = Mock(Staff)
		Staff staff2 = Mock(Staff)

		when:
		comicsTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicsTemplate))

		then:
		1 * wikitextStaffProcessorMock.process(WIKITEXT) >> Sets.newHashSet(staff1, staff2)
		0 * _
		comicsTemplate.editors.size() == 2
		comicsTemplate.editors.contains staff1
		comicsTemplate.editors.contains staff2
		comicsTemplate.writers.empty
		comicsTemplate.artists.empty
		comicsTemplate.staff.empty
	}

	void "tolerates parts with unknown key"() {
		given:
		Template.Part templatePart = new Template.Part(key: '', value: WIKITEXT)
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		Staff staff1 = Mock(Staff)

		when:
		comicsTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicsTemplate))

		then:
		1 * wikitextStaffProcessorMock.process(WIKITEXT) >> Sets.newHashSet(staff1)
		0 * _
		comicsTemplate.editors.empty
		comicsTemplate.writers.empty
		comicsTemplate.artists.empty
		comicsTemplate.staff.empty
		notThrown(Exception)
	}

}
