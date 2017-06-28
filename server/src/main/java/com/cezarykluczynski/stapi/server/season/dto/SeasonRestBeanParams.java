package com.cezarykluczynski.stapi.server.season.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class SeasonRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("title")
	private String title;

	@FormParam("seasonNumberFrom")
	private Integer seasonNumberFrom;

	@FormParam("seasonNumberTo")
	private Integer seasonNumberTo;

	@FormParam("numberOfEpisodesFrom")
	private Integer numberOfEpisodesFrom;

	@FormParam("numberOfEpisodesTo")
	private Integer numberOfEpisodesTo;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public Integer getSeasonNumberFrom() {
		return seasonNumberFrom;
	}

	public Integer getSeasonNumberTo() {
		return seasonNumberTo;
	}

	public Integer getNumberOfEpisodesFrom() {
		return numberOfEpisodesFrom;
	}

	public Integer getNumberOfEpisodesTo() {
		return numberOfEpisodesTo;
	}

	public static SeasonRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		SeasonRestBeanParams seasonRestBeanParams = new SeasonRestBeanParams();
		seasonRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		seasonRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		seasonRestBeanParams.setSort(pageSortBeanParams.getSort());
		return seasonRestBeanParams;
	}

}
