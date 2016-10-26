package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;

import java.util.List;

public interface PageApi {

	Page getPage(String title);

	List<Page> getPages(List<String> titles);

}
