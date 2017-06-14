package com.cezarykluczynski.stapi.etl.common.mapper;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class MediaWikiSourceMapper {

	private static final String NO_MAPPINGS = "No mapping for %s";

	public MediaWikiSource fromEntityToSources(com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource entity) {
		if (entity == null) {
			return null;
		}

		switch (entity) {
			case MEMORY_ALPHA_EN:
				return MediaWikiSource.MEMORY_ALPHA_EN;
			case MEMORY_BETA_EN:
				return MediaWikiSource.MEMORY_BETA_EN;
			default:
				throw new StapiRuntimeException(String.format(NO_MAPPINGS, entity));
		}
	}

	public com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource fromSourcesToEntity(MediaWikiSource source) {
		if (source == null) {
			return null;
		}

		switch (source) {
			case MEMORY_ALPHA_EN:
				return com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource.MEMORY_ALPHA_EN;
			case MEMORY_BETA_EN:
				return com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource.MEMORY_BETA_EN;
			default:
				throw new StapiRuntimeException(String.format(NO_MAPPINGS, source));
		}
	}

}
