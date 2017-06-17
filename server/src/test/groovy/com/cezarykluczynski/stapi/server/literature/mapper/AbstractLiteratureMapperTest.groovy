package com.cezarykluczynski.stapi.server.literature.mapper

import com.cezarykluczynski.stapi.model.literature.entity.Literature
import com.cezarykluczynski.stapi.util.AbstractLiteratureTest

abstract class AbstractLiteratureMapperTest extends AbstractLiteratureTest {

	protected static Literature createLiterature() {
		new Literature(
				uid: UID,
				title: TITLE,
				earthlyOrigin: EARTHLY_ORIGIN,
				shakespeareanWork: SHAKESPEAREAN_WORK,
				report: REPORT,
				scientificLiterature: SCIENTIFIC_LITERATURE,
				technicalManual: TECHNICAL_MANUAL,
				religiousLiterature: RELIGIOUS_LITERATURE)
	}

}
