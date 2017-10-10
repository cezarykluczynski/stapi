package com.cezarykluczynski.stapi.etl.template.magazine.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.magazine.creation.processor.MagazineTemplateNumberOfPagesFixedValueProvider
import com.cezarykluczynski.stapi.etl.magazine.creation.processor.MagazineTemplatePublicationDatesFixedValueProvider
import com.cezarykluczynski.stapi.etl.template.book.dto.ReferenceBookTemplateParameter
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.PublicationDates
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplate
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplateParameter
import com.cezarykluczynski.stapi.etl.template.publishable.processor.PublishableTemplatePublishedDatesEnrichingProcessor
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class MagazineTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String PUBLICATION = 'PUBLICATION'
	private static final String PUBLISHER = 'PUBLISHER'
	private static final String RELEASED = 'PUBLISHED'
	private static final String PAGES_STRING = '32'
	private static final Integer PAGES_INTEGER = 32
	private static final String EDITOR = 'EDITOR'
	private static final String ISSUE = 'ISSUE'
	private static final String TITLE = 'TITLE'
	protected static final Integer PUBLISHED_YEAR = 1990
	protected static final Integer PUBLISHED_MONTH = 12
	protected static final Integer PUBLISHED_DAY = 31
	protected static final Integer COVER_YEAR = 1991
	protected static final Integer COVER_MONTH = 1
	protected static final Integer COVER_DAY = 2

	private NumberOfPartsProcessor numberOfPartsProcessorMock

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private PublishableTemplatePublishedDatesEnrichingProcessor publishableTemplatePublishedDatesEnrichingProcessorMock

	private MagazineTemplatePublicationDatesFixedValueProvider magazineTemplatePublicationDatesFixedValueProviderMock

	private MagazineTemplateNumberOfPagesFixedValueProvider magazineTemplateNumberOfPagesFixedValueProviderMock

	private MagazineTemplatePartsEnrichingProcessor magazineTemplatePartsEnrichingProcessor

	void setup() {
		numberOfPartsProcessorMock = Mock()
		wikitextToEntitiesProcessorMock = Mock()
		publishableTemplatePublishedDatesEnrichingProcessorMock = Mock()
		magazineTemplatePublicationDatesFixedValueProviderMock = Mock()
		magazineTemplateNumberOfPagesFixedValueProviderMock = Mock()
		magazineTemplatePartsEnrichingProcessor = new MagazineTemplatePartsEnrichingProcessor(numberOfPartsProcessorMock,
				wikitextToEntitiesProcessorMock, publishableTemplatePublishedDatesEnrichingProcessorMock,
				magazineTemplatePublicationDatesFixedValueProviderMock, magazineTemplateNumberOfPagesFixedValueProviderMock)
	}

	void "sets magazine series from WikitextToEntitiesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineTemplateParameter.PUBLICATION, value: PUBLICATION)
		MagazineTemplate magazineTemplate = new MagazineTemplate()
		MagazineSeries magazineSeries1 = Mock()
		MagazineSeries magazineSeries2 = Mock()

		when:
		magazineTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findMagazineSeries(PUBLICATION) >> Lists.newArrayList(magazineSeries1, magazineSeries2)
		1 * magazineTemplatePublicationDatesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * magazineTemplateNumberOfPagesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		0 * _
		magazineTemplate.magazineSeries.size() == 2
		magazineTemplate.magazineSeries.contains magazineSeries1
		magazineTemplate.magazineSeries.contains magazineSeries2
	}

	void "sets issue number"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineTemplateParameter.ISSUE, value: ISSUE)
		MagazineTemplate magazineTemplate = new MagazineTemplate()

		when:
		magazineTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineTemplate))

		then:
		1 * magazineTemplatePublicationDatesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * magazineTemplateNumberOfPagesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		0 * _
		magazineTemplate.issueNumber == ISSUE
	}

	void "sets publishers from WikitextToEntitiesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineTemplateParameter.PUBLISHER, value: PUBLISHER)
		MagazineTemplate magazineTemplate = new MagazineTemplate()
		Company company1 = Mock()
		Company company2 = Mock()

		when:
		magazineTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findCompanies(PUBLISHER) >> Lists.newArrayList(company1, company2)
		1 * magazineTemplatePublicationDatesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * magazineTemplateNumberOfPagesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
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
		1 * wikitextToEntitiesProcessorMock.findStaff(EDITOR) >> Lists.newArrayList(editor1, editor2)
		1 * magazineTemplatePublicationDatesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * magazineTemplateNumberOfPagesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
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
			EnrichablePair<Template.Part, MagazineTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		1 * magazineTemplatePublicationDatesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * magazineTemplateNumberOfPagesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		0 * _
	}

	void "passes MagazineTemplate to PublishableTemplatePublishedDatesEnrichingProcessor, when published part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: ReferenceBookTemplateParameter.PUBLISHED, value: RELEASED)
		MagazineTemplate magazineTemplate = new MagazineTemplate()

		when:
		magazineTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineTemplate))

		then:
		1 * publishableTemplatePublishedDatesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, MagazineTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		1 * magazineTemplatePublicationDatesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * magazineTemplateNumberOfPagesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
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
			EnrichablePair<Template.Part, MagazineTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		1 * magazineTemplatePublicationDatesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * magazineTemplateNumberOfPagesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
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
		1 * magazineTemplatePublicationDatesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * magazineTemplateNumberOfPagesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		0 * _
		magazineTemplate.numberOfPages == PAGES_INTEGER
	}

	void "adds publication dates from MagazineSeriesPublicationDatesFixedValueProvider"() {
		given:
		MagazineTemplate magazineTemplate = new MagazineTemplate(title: TITLE)
		PublicationDates publicationDates = PublicationDates.of(DayMonthYear.of(PUBLISHED_DAY, PUBLISHED_MONTH, PUBLISHED_YEAR),
				DayMonthYear.of(COVER_DAY, COVER_MONTH, COVER_YEAR))

		when:
		magazineTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(), magazineTemplate))

		then:
		1 * magazineTemplatePublicationDatesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(publicationDates)
		1 * magazineTemplateNumberOfPagesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		0 * _
		magazineTemplate.publishedYear == PUBLISHED_YEAR
		magazineTemplate.publishedMonth == PUBLISHED_MONTH
		magazineTemplate.publishedDay == PUBLISHED_DAY
		magazineTemplate.coverDay == COVER_DAY
		magazineTemplate.coverMonth == COVER_MONTH
		magazineTemplate.coverYear == COVER_YEAR
	}

	void "adds number of issues from MagazineSeriesNumberOfIssuesFixedValueProvider"() {
		given:
		MagazineTemplate magazineTemplate = new MagazineTemplate(title: TITLE)

		when:
		magazineTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(), magazineTemplate))

		then:
		1 * magazineTemplatePublicationDatesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		1 * magazineTemplateNumberOfPagesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(PAGES_INTEGER)
		0 * _
		magazineTemplate.numberOfPages == PAGES_INTEGER
	}

}
