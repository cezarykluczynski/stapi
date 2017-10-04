package com.cezarykluczynski.stapi.etl.template.magazine_series.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYear
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor
import com.cezarykluczynski.stapi.etl.template.magazine_series.dto.MagazineSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.magazine_series.dto.MagazineSeriesTemplateParameter
import com.cezarykluczynski.stapi.etl.template.publishable_series.dto.PublishableSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.publishable_series.processor.PublishableSeriesTemplatePartsEnrichingProcessor
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class MagazineSeriesTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String ISSUES_STRING = '12'
	private static final String EDITOR = 'EDITOR'
	private static final Integer ISSUES_INTEGER = 12
	private static final Integer ISSUES_INTEGER_2 = 22
	private static final String TITLE = 'TITLE'
	private static final Integer MONTH_FROM = 6
	private static final Integer YEAR_FROM = 1979
	private static final Integer MONTH_TO = 11
	private static final Integer YEAR_TO = 1981

	private PublishableSeriesTemplatePartsEnrichingProcessor publishableSeriesTemplatePartsEnrichingProcessorMock

	private NumberOfPartsProcessor numberOfPartsProcessorMock

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private MagazineSeriesPublicationDatesFixedValueProvider magazineSeriesPublicationDatesFixedValueProviderMock

	private MagazineSeriesNumberOfIssuesFixedValueProvider magazineSeriesNumberOfIssuesFixedValueProviderMock

	private MagazineSeriesTemplatePartsEnrichingProcessor magazineSeriesTemplatePartsEnrichingProcessor

	void setup() {
		publishableSeriesTemplatePartsEnrichingProcessorMock = Mock()
		numberOfPartsProcessorMock = Mock()
		wikitextToEntitiesProcessorMock = Mock()
		magazineSeriesPublicationDatesFixedValueProviderMock = Mock()
		magazineSeriesNumberOfIssuesFixedValueProviderMock = Mock()
		magazineSeriesTemplatePartsEnrichingProcessor = new MagazineSeriesTemplatePartsEnrichingProcessor(
				publishableSeriesTemplatePartsEnrichingProcessorMock, numberOfPartsProcessorMock, wikitextToEntitiesProcessorMock,
				magazineSeriesPublicationDatesFixedValueProviderMock, magazineSeriesNumberOfIssuesFixedValueProviderMock)
	}

	void "passes enrichable pair to PublishableSeriesTemplatePartsEnrichingProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineSeriesTemplateParameter.PUBLISHER)
		List<Template.Part> templatePartList = Lists.newArrayList(templatePart)
		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate()

		when:
		magazineSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(templatePartList, magazineSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<List<Template.Part>, PublishableSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == templatePartList
				assert enrichablePair.output == magazineSeriesTemplate
		}
		1 * magazineSeriesPublicationDatesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * magazineSeriesNumberOfIssuesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		0 * _
	}

	void "parses number of issues"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineSeriesTemplateParameter.ISSUES, value: ISSUES_STRING)
		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate()

		when:
		magazineSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_)
		1 * numberOfPartsProcessorMock.process(ISSUES_STRING) >> ISSUES_INTEGER
		1 * magazineSeriesPublicationDatesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * magazineSeriesNumberOfIssuesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		0 * _
		magazineSeriesTemplate.numberOfIssues == ISSUES_INTEGER
	}

	void "does not set number of issues, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineSeriesTemplateParameter.ISSUES, value: ISSUES_STRING)
		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate(numberOfIssues: ISSUES_INTEGER_2)

		when:
		magazineSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_)
		1 * magazineSeriesPublicationDatesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * magazineSeriesNumberOfIssuesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		0 * _
		magazineSeriesTemplate.numberOfIssues == ISSUES_INTEGER_2
	}

	void "adds editors from WikitextStaffProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineSeriesTemplateParameter.EDITOR, value: EDITOR)
		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate()
		Staff editor1 = Mock()
		Staff editor2 = Mock()

		when:
		magazineSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_)
		1 * wikitextToEntitiesProcessorMock.findStaff(EDITOR) >> Lists.newArrayList(editor1, editor2)
		1 * magazineSeriesPublicationDatesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * magazineSeriesNumberOfIssuesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		0 * _
		magazineSeriesTemplate.editors.size() == 2
		magazineSeriesTemplate.editors.contains editor1
		magazineSeriesTemplate.editors.contains editor2
	}

	void "adds publication dates from MagazineSeriesPublicationDatesFixedValueProvider"() {
		given:
		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate(title: TITLE)
		MonthYearRange monthYearRange = MonthYearRange.of(MonthYear.of(MONTH_FROM, YEAR_FROM), MonthYear.of(MONTH_TO, YEAR_TO))

		when:
		magazineSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(), magazineSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_)
		1 * magazineSeriesPublicationDatesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(monthYearRange)
		1 * magazineSeriesNumberOfIssuesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		0 * _
		magazineSeriesTemplate.publishedMonthFrom == MONTH_FROM
		magazineSeriesTemplate.publishedYearFrom == YEAR_FROM
		magazineSeriesTemplate.publishedMonthTo == MONTH_TO
		magazineSeriesTemplate.publishedYearTo == YEAR_TO
	}

	void "adds number of issues from MagazineSeriesNumberOfIssuesFixedValueProvider"() {
		given:
		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate(title: TITLE)

		when:
		magazineSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(), magazineSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_)
		1 * magazineSeriesPublicationDatesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		1 * magazineSeriesNumberOfIssuesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(ISSUES_INTEGER)
		0 * _
		magazineSeriesTemplate.numberOfIssues == ISSUES_INTEGER
	}

}
