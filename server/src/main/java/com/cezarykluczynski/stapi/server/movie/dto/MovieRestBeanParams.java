package com.cezarykluczynski.stapi.server.movie.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;
import java.time.LocalDate;

public class MovieRestBeanParams extends PageSortBeanParams {

	@FormParam("guid")
	private String guid;

	@FormParam("title")
	private String title;

	@FormParam("stardateFrom")
	private Float stardateFrom;

	@FormParam("stardateTo")
	private Float stardateTo;

	@FormParam("yearFrom")
	private Integer yearFrom;

	@FormParam("yearTo")
	private Integer yearTo;

	@FormParam("usReleaseDateFrom")
	private LocalDate usReleaseDateFrom;

	@FormParam("usReleaseDateTo")
	private LocalDate usReleaseDateTo;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTitle() {
		return title;
	}

	public Float getStardateFrom() {
		return stardateFrom;
	}

	public Float getStardateTo() {
		return stardateTo;
	}

	public Integer getYearFrom() {
		return yearFrom;
	}

	public Integer getYearTo() {
		return yearTo;
	}

	public LocalDate getUsReleaseDateFrom() {
		return usReleaseDateFrom;
	}

	public LocalDate getUsReleaseDateTo() {
		return usReleaseDateTo;
	}

	public static MovieRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		MovieRestBeanParams movieRestBeanParams = new MovieRestBeanParams();
		movieRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		movieRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		movieRestBeanParams.setSort(pageSortBeanParams.getSort());
		return movieRestBeanParams;
	}

}
