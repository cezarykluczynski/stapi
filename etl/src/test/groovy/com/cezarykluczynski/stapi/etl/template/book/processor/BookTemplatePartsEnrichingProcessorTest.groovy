package com.cezarykluczynski.stapi.etl.template.book.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.company.WikitextToCompaniesProcessor
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplateParameter
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class BookTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String AUTHOR = 'AUTHOR'
	private static final String ARTIST = 'ARTIST'
	private static final String EDITOR = 'EDITOR'
	private static final String AUDIOBOOK_NARRATOR = 'AUDIOBOOK_NARRATOR'
	private static final String PUBLISHER = 'PUBLISHER'
	private static final String AUDIOBOOK_PUBLISHER = 'AUDIOBOOK_PUBLISHER'
	private static final String PUBLISHED = 'PUBLISHED'
	private static final String AUDIOBOOK_PUBLISHED = 'AUDIOBOOK_PUBLISHED'

	private BookTemplatePartStaffEnrichingProcessor bookTemplatePartStaffEnrichingProcessorMock

	private WikitextToCompaniesProcessor wikitextToCompaniesProcessorMock

	private BookTemplatePublishedDatesEnrichingProcessor bookTemplatePublishedDatesEnrichingProcessorMock

	private BookTemplatePartsEnrichingProcessor bookTemplatePartsEnrichingProcessor

	void setup() {
		bookTemplatePartStaffEnrichingProcessorMock = Mock()
		wikitextToCompaniesProcessorMock = Mock()
		bookTemplatePublishedDatesEnrichingProcessorMock = Mock()
		bookTemplatePartsEnrichingProcessor = new BookTemplatePartsEnrichingProcessor(bookTemplatePartStaffEnrichingProcessorMock,
				wikitextToCompaniesProcessorMock, bookTemplatePublishedDatesEnrichingProcessorMock)
	}

	void "passes BookTemplate to BookTemplatePartStaffEnrichingProcessor when author part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUTHOR, value: AUTHOR)
		BookTemplate bookTemplate = new BookTemplate()
		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * bookTemplatePartStaffEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, BookTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "passes BookTemplate to BookTemplatePartStaffEnrichingProcessor when artist part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.ARTIST, value: ARTIST)
		BookTemplate bookTemplate = new BookTemplate()
		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * bookTemplatePartStaffEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, BookTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "passes BookTemplate to BookTemplatePartStaffEnrichingProcessor when editor part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.EDITOR, value: EDITOR)
		BookTemplate bookTemplate = new BookTemplate()
		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * bookTemplatePartStaffEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, BookTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "passes BookTemplate to BookTemplatePartStaffEnrichingProcessor when audiobook read by part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUDIOBOOK_READ_BY, value: AUDIOBOOK_NARRATOR)
		BookTemplate bookTemplate = new BookTemplate()
		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * bookTemplatePartStaffEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, BookTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "sets publishers from WikitextToCompaniesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.PUBLISHER, value: PUBLISHER)
		BookTemplate bookTemplate = new BookTemplate()
		Company company1 = Mock()
		Company company2 = Mock()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * wikitextToCompaniesProcessorMock.process(PUBLISHER) >> Sets.newHashSet(company1, company2)
		0 * _
		bookTemplate.publishers.size() == 2
		bookTemplate.publishers.contains company1
		bookTemplate.publishers.contains company2
	}

	void "sets audiobook publishers from WikitextToCompaniesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUDIOBOOK_PUBLISHER, value: AUDIOBOOK_PUBLISHER)
		BookTemplate bookTemplate = new BookTemplate()
		Company company1 = Mock()
		Company company2 = Mock()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * wikitextToCompaniesProcessorMock.process(AUDIOBOOK_PUBLISHER) >> Sets.newHashSet(company1, company2)
		0 * _
		bookTemplate.audiobookPublishers.size() == 2
		bookTemplate.audiobookPublishers.contains company1
		bookTemplate.audiobookPublishers.contains company2
	}

	void "passes BookTemplate to BookTemplatePublishedDatesEnrichingProcessor, when published part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.PUBLISHED, value: PUBLISHED)
		BookTemplate bookTemplate = new BookTemplate()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * bookTemplatePublishedDatesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, ComicSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "passes BookTemplate to BookTemplatePublishedDatesEnrichingProcessor, when audiobook published part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUDIOBOOK_PUBLISHED, value: AUDIOBOOK_PUBLISHED)
		BookTemplate bookTemplate = new BookTemplate()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * bookTemplatePublishedDatesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, ComicSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

}
