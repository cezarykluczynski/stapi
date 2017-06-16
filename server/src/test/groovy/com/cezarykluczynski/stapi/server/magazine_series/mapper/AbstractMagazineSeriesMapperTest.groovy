package com.cezarykluczynski.stapi.server.magazine_series.mapper

import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractMagazineSeriesTest

abstract class AbstractMagazineSeriesMapperTest extends AbstractMagazineSeriesTest {

	protected MagazineSeries createMagazineSeries() {
		new MagazineSeries(
				uid: UID,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedMonthFrom: PUBLISHED_MONTH_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				publishedMonthTo: PUBLISHED_MONTH_TO,
				numberOfIssues: NUMBER_OF_ISSUES,
				publishers: createSetOfRandomNumberOfMocks(Company),
				editors: createSetOfRandomNumberOfMocks(Staff),
				magazines: createSetOfRandomNumberOfMocks(Magazine))
	}

}
