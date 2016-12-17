package com.cezarykluczynski.stapi.server.util;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import org.springframework.data.domain.PageRequest;

public class PageDefault {

	public static final int PAGE_NUMBER = 0;
	public static final int PAGE_SIZE = 50;
	public static final int SINGLE_ITEM_PAGE_SIZE = 1;
	public static final int PAGE_SIZE_MIN = 1;
	public static final int PAGE_SIZE_MAX = 100;

	public static final PageRequest PAGE_REQUEST = new PageRequest(PAGE_NUMBER, PAGE_SIZE);
	public static final PageSortBeanParams SINGLE_PAGE_BEAN_PARAMS = new PageSortBeanParams();

	static {
		SINGLE_PAGE_BEAN_PARAMS.setPageNumber(PAGE_NUMBER);
		SINGLE_PAGE_BEAN_PARAMS.setPageSize(SINGLE_ITEM_PAGE_SIZE);
	}

}
