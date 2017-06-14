package com.cezarykluczynski.stapi.etl.common.mapper

import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource as ModelMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import spock.lang.Specification

class MediaWikiSourceMapperTest extends Specification {

	private MediaWikiSourceMapper mediaWikiSourceMapper

	void setup() {
		mediaWikiSourceMapper = new MediaWikiSourceMapper()
	}

	void "maps entity enum to sources enum"() {
		expect:
		mediaWikiSourceMapper.fromEntityToSources(null) == null
		mediaWikiSourceMapper.fromEntityToSources(ModelMediaWikiSource.MEMORY_ALPHA_EN) == SourcesMediaWikiSource.MEMORY_ALPHA_EN
		mediaWikiSourceMapper.fromEntityToSources(ModelMediaWikiSource.MEMORY_BETA_EN) == SourcesMediaWikiSource.MEMORY_BETA_EN
	}

	void "maps source enum to entity enum"() {
		expect:
		mediaWikiSourceMapper.fromSourcesToEntity(null) == null
		mediaWikiSourceMapper.fromSourcesToEntity(SourcesMediaWikiSource.MEMORY_ALPHA_EN) == ModelMediaWikiSource.MEMORY_ALPHA_EN
		mediaWikiSourceMapper.fromSourcesToEntity(SourcesMediaWikiSource.MEMORY_BETA_EN) == ModelMediaWikiSource.MEMORY_BETA_EN
	}

	void "should not map source technical helper, because it's not a source of data"() {
		when:
		mediaWikiSourceMapper.fromSourcesToEntity(SourcesMediaWikiSource.TECHNICAL_HELPER)

		then:
		thrown(StapiRuntimeException)
	}

}
