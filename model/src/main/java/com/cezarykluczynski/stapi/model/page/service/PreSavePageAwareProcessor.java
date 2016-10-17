package com.cezarykluczynski.stapi.model.page.service;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;

import java.util.List;

public interface PreSavePageAwareProcessor {

	List<PageAware> process(List<PageAware> pageAwareList);

}
