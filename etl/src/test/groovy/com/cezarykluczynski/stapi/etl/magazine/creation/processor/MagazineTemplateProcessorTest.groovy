package com.cezarykluczynski.stapi.etl.magazine.creation.processor

import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplate
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractMagazineTest
import com.google.common.collect.Sets

class MagazineTemplateProcessorTest extends AbstractMagazineTest {

	private UidGenerator uidGeneratorMock

	private MagazineTemplateProcessor magazineTemplateProcessor

	private final Page page = Mock()

	void setup() {
		uidGeneratorMock = Mock()
		magazineTemplateProcessor = new MagazineTemplateProcessor(uidGeneratorMock)
	}

	void "converts MagazineTemplate to Magazine"() {
		given:
		MagazineSeries magazineSeries1 = Mock()
		MagazineSeries magazineSeries2 = Mock()
		Staff editor1 = Mock()
		Staff editor2 = Mock()
		Company publisher1 = Mock()
		Company publisher2 = Mock()

		MagazineTemplate magazineTemplate = new MagazineTemplate(
				page: page,
				title: TITLE,
				publishedYear: PUBLISHED_YEAR,
				publishedMonth: PUBLISHED_MONTH,
				publishedDay: PUBLISHED_DAY,
				coverYear: COVER_YEAR,
				coverMonth: COVER_MONTH,
				coverDay: COVER_DAY,
				numberOfPages: NUMBER_OF_PAGES,
				issueNumber: ISSUE_NUMBER,
				magazineSeries: Sets.newHashSet(magazineSeries1, magazineSeries2),
				editors: Sets.newHashSet(editor1, editor2),
				publishers: Sets.newHashSet(publisher1, publisher2))

		when:
		Magazine magazine = magazineTemplateProcessor.process(magazineTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, Magazine) >> UID
		0 * _
		magazine.uid == UID
		magazine.page == page
		magazine.title == TITLE
		magazine.publishedYear == PUBLISHED_YEAR
		magazine.publishedMonth == PUBLISHED_MONTH
		magazine.publishedDay == PUBLISHED_DAY
		magazine.coverYear == COVER_YEAR
		magazine.coverMonth == COVER_MONTH
		magazine.coverDay == COVER_DAY
		magazine.numberOfPages == NUMBER_OF_PAGES
		magazine.issueNumber == ISSUE_NUMBER
		magazine.magazineSeries.contains magazineSeries1
		magazine.magazineSeries.contains magazineSeries2
		magazine.editors.contains editor1
		magazine.editors.contains editor2
		magazine.publishers.contains publisher1
		magazine.publishers.contains publisher2
	}

}
