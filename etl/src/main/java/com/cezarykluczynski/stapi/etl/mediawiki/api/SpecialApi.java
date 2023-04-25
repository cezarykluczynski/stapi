package com.cezarykluczynski.stapi.etl.mediawiki.api;

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;

import java.util.List;

public interface SpecialApi {

	List<PageHeader> getPagesTranscludingTemplate(String templateName, MediaWikiSource mediaWikiSource);

}
