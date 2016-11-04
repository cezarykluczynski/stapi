package com.cezarykluczynski.stapi.model.page.service;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;

import java.util.List;

public interface PreSavePageAwareFilter {

	List<PageAware> process(List<PageAware> pageAwareList, Class<? extends PageAware> baseClass);

}
