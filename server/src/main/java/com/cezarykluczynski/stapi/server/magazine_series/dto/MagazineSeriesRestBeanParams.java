package com.cezarykluczynski.stapi.server.magazine_series.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class MagazineSeriesRestBeanParams extends PageSortBeanParams {

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

	public static MagazineSeriesRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		MagazineSeriesRestBeanParams magazineSeriesRestBeanParams = new MagazineSeriesRestBeanParams();
		magazineSeriesRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		magazineSeriesRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		magazineSeriesRestBeanParams.setSort(pageSortBeanParams.getSort());
		return magazineSeriesRestBeanParams;
	}

}
