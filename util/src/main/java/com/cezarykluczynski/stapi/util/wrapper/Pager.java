package com.cezarykluczynski.stapi.util.wrapper;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Pager {

	private int totalPages;

	private int totalElements;

	private int pageNumber;

	private int pageSize;

	private int numberOfElementsInPage;

	private boolean isFirst;

	private boolean isLast;

	private boolean hasNext;

	private boolean hasPrevious;

}
