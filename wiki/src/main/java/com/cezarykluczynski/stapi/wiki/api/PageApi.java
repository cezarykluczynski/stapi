package com.cezarykluczynski.stapi.wiki.api;

import com.cezarykluczynski.stapi.wiki.dto.Page;

import java.util.List;

public interface PageApi {

	Page getPage(String title);

	List<Page> getPages(List<String> titles);

}
