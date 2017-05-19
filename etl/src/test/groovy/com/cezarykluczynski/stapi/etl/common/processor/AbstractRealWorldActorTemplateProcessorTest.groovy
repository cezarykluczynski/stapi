package com.cezarykluczynski.stapi.etl.common.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender as EtlGender
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender as ModelGender
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.util.AbstractRealWorldPersonTest

abstract class AbstractRealWorldActorTemplateProcessorTest extends AbstractRealWorldPersonTest {

	protected static final ModelGender MODEL_GENDER = ModelGender.F
	protected static final EtlGender ETL_GENDER = EtlGender.F
	protected static final String UID = 'UID'
	protected static final Page PAGE = new Page(id: 1L)

}
