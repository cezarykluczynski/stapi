package com.cezarykluczynski.stapi.wiki.api;

import com.cezarykluczynski.stapi.wiki.dto.Page;

public interface PageApi {

	Page getPage(String title);

}
