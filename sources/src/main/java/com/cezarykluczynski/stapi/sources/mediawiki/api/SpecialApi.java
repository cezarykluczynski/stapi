package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;

import java.util.List;

public interface SpecialApi {

	List<PageHeader> getPagesTranscludingTemplate(String templateName, MediaWikiSource mediaWikiSource);

}
