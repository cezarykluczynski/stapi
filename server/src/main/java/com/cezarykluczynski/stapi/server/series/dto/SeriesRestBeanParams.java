package com.cezarykluczynski.stapi.server.series.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageAwareBeanParams;

import javax.ws.rs.FormParam;

public class SeriesRestBeanParams extends PageAwareBeanParams {

	@FormParam(value = "title")
	private String title;

	public String getTitle() {
		return title;
	}

	public static SeriesRestBeanParams fromPageAwareBeanParams(PageAwareBeanParams pageAwareBeanParams) {
		if (pageAwareBeanParams == null) {
			return null;
		}

		SeriesRestBeanParams seriesRestBeanParams = new SeriesRestBeanParams();
		seriesRestBeanParams.setPageNumber(pageAwareBeanParams.getPageNumber());
		seriesRestBeanParams.setPageSize(pageAwareBeanParams.getPageSize());
		return seriesRestBeanParams;
	}

}
