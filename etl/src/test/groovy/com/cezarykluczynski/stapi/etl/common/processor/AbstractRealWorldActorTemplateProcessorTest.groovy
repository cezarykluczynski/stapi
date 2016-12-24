package com.cezarykluczynski.stapi.etl.common.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.util.AbstractRealWorldPersonTest

abstract class AbstractRealWorldActorTemplateProcessorTest extends AbstractRealWorldPersonTest {

	protected static final Gender GENDER = Gender.F
	protected static final String GUID = 'GUID'
	protected static final Page PAGE = new Page(id: 1L)

}
