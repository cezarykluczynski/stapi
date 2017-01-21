package com.cezarykluczynski.stapi.etl.common.processor

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

abstract class AbstractTemplateProcessorTest extends Specification {

	protected static Template.Part createTemplatePart(String key, String value) {
		new Template.Part(
				key: key,
				value: value
		)
	}

}
