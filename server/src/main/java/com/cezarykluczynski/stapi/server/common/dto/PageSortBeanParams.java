package com.cezarykluczynski.stapi.server.common.dto;

import javax.ws.rs.QueryParam;

public class PageSortBeanParams {

	@QueryParam("pageNumber")
	private Integer pageNumber;

	@QueryParam("pageSize")
	private Integer pageSize;

	@QueryParam("sort")
	private String sort;

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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}
