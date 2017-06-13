package com.cezarykluczynski.stapi.etl.template.magazine.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextStaffProcessor
import com.cezarykluczynski.stapi.etl.common.processor.company.WikitextToCompaniesProcessor
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplate
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplateParameter
import com.cezarykluczynski.stapi.etl.template.publishable.processor.PublishableTemplatePublishedDatesEnrichingProcessor
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class MagazineTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String PUBLISHER = 'PUBLISHER'
	private static final String RELEASED = 'PUBLISHED'
	private static final String PAGES_STRING = '32'
	private static final Integer PAGES_INTEGER = 32
	private static final String EDITOR = 'EDITOR'
	private static final String ISSUE = 'ISSUE'

	private NumberOfPartsProcessor numberOfPartsProcessorMock

	private WikitextToCompaniesProcessor wikitextToCompaniesProcessorMock

	private WikitextStaffProcessor wikitextStaffProcessorMock

	private PublishableTemplatePublishedDatesEnrichingProcessor publishableTemplatePublishedDatesEnrichingProcessorMock

	private MagazineTemplatePartsEnrichingProcessor magazineTemplatePartsEnrichingProcessor

	void setup() {
		numberOfPartsProcessorMock = Mock()
		wikitextToCompaniesProcessorMock = Mock()
		wikitextStaffProcessorMock = Mock()
		publishableTemplatePublishedDatesEnrichingProcessorMock = Mock()
		magazineTemplatePartsEnrichingProcessor = new MagazineTemplatePartsEnrichingProcessor(numberOfPartsProcessorMock,
				wikitextToCompaniesProcessorMock, wikitextStaffProcessorMock, publishableTemplatePublishedDatesEnrichingProcessorMock)
	}

	void "sets issue number"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineTemplateParameter.ISSUE, value: ISSUE)
		MagazineTemplate magazineTemplate = new MagazineTemplate()

		when:
		magazineTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineTemplate))

		then:
		0 * _
		magazineTemplate.issueNumber == ISSUE
	}

	void "sets publishers from WikitextToCompaniesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineTemplateParameter.PUBLISHER, value: PUBLISHER)
		MagazineTemplate magazineTemplate = new MagazineTemplate()
		Company company1 = Mock()
		Company company2 = Mock()

		when:
		magazineTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineTemplate))

		then:
		1 * wikitextToCompaniesProcessorMock.process(PUBLISHER) >> Sets.newHashSet(company1, company2)
		0 * _
		magazineTemplate.publishers.size() == 2
		magazineTemplate.publishers.contains company1
		magazineTemplate.publishers.contains company2
	}

	void "sets editors from WikitextStaffProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineTemplateParameter.EDITOR, value: EDITOR)
		MagazineTemplate magazineTemplate = new MagazineTemplate()
		Staff editor1 = Mock()
		Staff editor2 = Mock()

		when:
		magazineTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineTemplate))

		then:
		1 * wikitextStaffProcessorMock.process(EDITOR) >> Sets.newHashSet(editor1, editor2)
		0 * _
		magazineTemplate.editors.size() == 2
		magazineTemplate.editors.contains editor1
		magazineTemplate.editors.contains editor2
	}

	void "passes MagazineTemplate to PublishableTemplatePublishedDatesEnrichingProcessor, when released part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineTemplateParameter.RELEASED, value: RELEASED)
		MagazineTemplate magazineTemplate = new MagazineTemplate()

		when:
		magazineTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineTemplate))

		then:
		1 * publishableTemplatePublishedDatesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, ComicSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "passes MagazineTemplate to PublishableTemplatePublishedDatesEnrichingProcessor, when cover date part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineTemplateParameter.COVER_DATE, value: RELEASED)
		MagazineTemplate magazineTemplate = new MagazineTemplate()

		when:
		magazineTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineTemplate))

		then:
		1 * publishableTemplatePublishedDatesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, ComicSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "sets number of pages from NumberOfPartsProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineTemplateParameter.PAGES, value: PAGES_STRING)
		MagazineTemplate magazineTemplate = new MagazineTemplate()

		when:
		magazineTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineTemplate))

		then:
		1 * numberOfPartsProcessorMock.process(PAGES_STRING) >> PAGES_INTEGER
		0 * _
		magazineTemplate.numberOfPages == PAGES_INTEGER
	}

}
