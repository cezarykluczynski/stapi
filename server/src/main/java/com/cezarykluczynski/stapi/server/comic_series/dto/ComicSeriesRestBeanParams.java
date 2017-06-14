package com.cezarykluczynski.stapi.server.comic_series.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class ComicSeriesRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("title")
	private String title;

	@FormParam("publishedYearFrom")
	private Integer publishedYearFrom;

	@FormParam("publishedYearTo")
	private Integer publishedYearTo;

	@FormParam("numberOfIssuesFrom")
	private Integer numberOfIssuesFrom;

	@FormParam("numberOfIssuesTo")
	private Integer numberOfIssuesTo;

	@FormParam("stardateFrom")
	private Float stardateFrom;

	@FormParam("stardateTo")
	private Float stardateTo;

	@FormParam("yearFrom")
	private Integer yearFrom;

	@FormParam("yearTo")
	private Integer yearTo;

	@FormParam("miniseries")
	private Boolean miniseries;

	@FormParam("photonovelSeries")
	private Boolean photonovelSeries;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public Integer getPublishedYearFrom() {
		return publishedYearFrom;
	}

	public Integer getPublishedYearTo() {
		return publishedYearTo;
	}

	public Integer getNumberOfIssuesFrom() {
		return numberOfIssuesFrom;
	}

	public Integer getNumberOfIssuesTo() {
		return numberOfIssuesTo;
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

	public Boolean getMiniseries() {
		return miniseries;
	}

	public Boolean getPhotonovelSeries() {
		return photonovelSeries;
	}

	public static ComicSeriesRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		ComicSeriesRestBeanParams comicSeriesRestBeanParams = new ComicSeriesRestBeanParams();
		comicSeriesRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		comicSeriesRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		comicSeriesRestBeanParams.setSort(pageSortBeanParams.getSort());
		return comicSeriesRestBeanParams;
	}

}
