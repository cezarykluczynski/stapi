package com.cezarykluczynski.stapi.server.series.dto;

import javax.ws.rs.FormParam;

public class SeriesRestBeanParams {

	@FormParam(value = "title")
	private String title;

	public String getTitle() {
		return title;
	}

}
