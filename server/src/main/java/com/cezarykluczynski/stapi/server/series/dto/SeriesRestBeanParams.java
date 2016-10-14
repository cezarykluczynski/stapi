package com.cezarykluczynski.stapi.server.series.dto;

import javax.ws.rs.QueryParam;

public class SeriesRestBeanParams {

	@QueryParam("title")
	private String title;

	public String getTitle() {
		return title;
	}

}
