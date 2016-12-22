package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

abstract class AbstractEpisodeTemplateProcessorTest extends Specification {

	protected static Template.Part createTemplatePart(String key, String value) {
		return new Template.Part(
				key: key,
				value: value
		)
	}

}
