package com.cezarykluczynski.stapi.model.common.repository;

import org.springframework.data.domain.Page;

public abstract class AbstractRepositoryImpl<T> {

	protected abstract void clearProxies(Page<T> page, boolean doClearProxies);

}
