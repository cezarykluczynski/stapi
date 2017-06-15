package com.cezarykluczynski.stapi.server.magazine.mapper

import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractMagazineTest

abstract class AbstractMagazineMapperTest extends AbstractMagazineTest {

	protected Magazine createMagazine() {
		new Magazine(
				uid: UID,
				title: TITLE,
				publishedYear: PUBLISHED_YEAR,
				publishedMonth: PUBLISHED_MONTH,
				publishedDay: PUBLISHED_DAY,
				coverYear: COVER_YEAR,
				coverMonth: COVER_MONTH,
				coverDay: COVER_DAY,
				numberOfPages: NUMBER_OF_PAGES,
				issueNumber: ISSUE_NUMBER,
				editors: createSetOfRandomNumberOfMocks(Staff),
				publishers: createSetOfRandomNumberOfMocks(Company),
				magazineSeries: createSetOfRandomNumberOfMocks(MagazineSeries))
	}

}
