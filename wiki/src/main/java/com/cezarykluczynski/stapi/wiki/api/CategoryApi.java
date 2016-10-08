package com.cezarykluczynski.stapi.wiki.api;

import com.cezarykluczynski.stapi.wiki.dto.PageHeader;

import java.util.List;

public interface CategoryApi {

	List<PageHeader> getPages(String title);

}
