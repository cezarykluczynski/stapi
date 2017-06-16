package com.cezarykluczynski.stapi.etl.magazine_series.creation.processor

import com.cezarykluczynski.stapi.etl.template.magazine_series.dto.MagazineSeriesTemplate
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractMagazineSeriesTest
import com.google.common.collect.Sets

class MagazineSeriesTemplateProcessorTest extends AbstractMagazineSeriesTest {

	private final Page page = Mock()

	private UidGenerator uidGeneratorMock

	private MagazineSeriesTemplateProcessor magazineSeriesTemplateProcessor

	void setup() {
		uidGeneratorMock = Mock()
		magazineSeriesTemplateProcessor = new MagazineSeriesTemplateProcessor(uidGeneratorMock)
	}

	void "converts MagazineSeriesTemplate to MagazineSeries"() {
		given:
		Company publisher1 = Mock()
		Company publisher2 = Mock()
		Staff editor1 = Mock()
		Staff editor2 = Mock()
		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate(
				page: page,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedMonthFrom: PUBLISHED_MONTH_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				publishedMonthTo: PUBLISHED_MONTH_TO,
				numberOfIssues: NUMBER_OF_ISSUES,
				publishers: Sets.newHashSet(publisher1, publisher2),
				editors: Sets.newHashSet(editor1, editor2))

		when:
		MagazineSeries magazineSeries = magazineSeriesTemplateProcessor.process(magazineSeriesTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, MagazineSeries) >> UID
		0 * _
		magazineSeries.uid == UID
		magazineSeries.page == page
		magazineSeries.title == TITLE
		magazineSeries.publishedYearFrom == PUBLISHED_YEAR_FROM
		magazineSeries.publishedMonthFrom == PUBLISHED_MONTH_FROM
		magazineSeries.publishedYearTo == PUBLISHED_YEAR_TO
		magazineSeries.publishedMonthTo == PUBLISHED_MONTH_TO
		magazineSeries.numberOfIssues == NUMBER_OF_ISSUES
		magazineSeries.publishers.size() == 2
		magazineSeries.publishers.contains publisher1
		magazineSeries.publishers.contains publisher2
		magazineSeries.editors.size() == 2
		magazineSeries.editors.contains editor1
		magazineSeries.editors.contains editor2
	}

}
