package com.cezarykluczynski.stapi.client.api.dto;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPageSortBaseCriteria {

	private Integer pageNumber;

	private Integer pageSize;

	private final List<RestSortClause> sort = new ArrayList<>();

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<RestSortClause> getSort() {
		return sort;
	}

}
