package com.cezarykluczynski.stapi.server.soundtrack.mapper

import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractSoundtrackTest

abstract class AbstractSoundtrackMapperTest extends AbstractSoundtrackTest {

	protected Soundtrack createSoundtrack() {
		new Soundtrack(
				uid: UID,
				title: TITLE,
				releaseDate: RELEASE_DATE,
				length: LENGTH,
				labels: createSetOfRandomNumberOfMocks(Company),
				composers: createSetOfRandomNumberOfMocks(Staff),
				contributors: createSetOfRandomNumberOfMocks(Staff),
				orchestrators: createSetOfRandomNumberOfMocks(Staff),
				references: createSetOfRandomNumberOfMocks(Reference))
	}

}
