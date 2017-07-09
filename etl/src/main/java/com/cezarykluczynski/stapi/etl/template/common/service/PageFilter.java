package com.cezarykluczynski.stapi.etl.template.common.service;

public interface PageFilter<T> {

	boolean shouldBeFilteredOut(T page);

}
