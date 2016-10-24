package com.cezarykluczynski.stapi.server.common.dto;

import javax.ws.rs.QueryParam;

public class PageAwareBeanParams {

	@QueryParam(value = "pageNumber")
	private Integer pageNumber;

	@QueryParam(value = "pageSize")
	private Integer pageSize;


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
}
