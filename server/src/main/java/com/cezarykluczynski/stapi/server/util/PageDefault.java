package com.cezarykluczynski.stapi.server.util;

import org.springframework.data.domain.PageRequest;

public class PageDefault {

	private static final int PAGE_NUMBER = 0;
	private static final int PAGE_SIZE = 50;

	public static final PageRequest PAGE_REQUEST = new PageRequest(PAGE_NUMBER, PAGE_SIZE);

}
