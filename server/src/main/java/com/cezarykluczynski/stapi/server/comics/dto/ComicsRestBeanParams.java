package com.cezarykluczynski.stapi.server.comics.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class ComicsRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("title")
	private String title;

	@FormParam("publishedYearFrom")
	private Integer publishedYearFrom;

	@FormParam("publishedYearTo")
	private Integer publishedYearTo;

	@FormParam("numberOfPagesFrom")
	private Integer numberOfPagesFrom;

	@FormParam("numberOfPagesTo")
	private Integer numberOfPagesTo;

	@FormParam("stardateFrom")
	private Float stardateFrom;

	@FormParam("stardateTo")
	private Float stardateTo;

	@FormParam("yearFrom")
	private Integer yearFrom;

	@FormParam("yearTo")
	private Integer yearTo;

	@FormParam("photonovel")
	private Boolean photonovel;

	@FormParam("adaptation")
	private Boolean adaptation;

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

	public Integer getNumberOfPagesFrom() {
		return numberOfPagesFrom;
	}

	public Integer getNumberOfPagesTo() {
		return numberOfPagesTo;
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

	public Boolean getPhotonovel() {
		return photonovel;
	}

	public Boolean getAdaptation() {
		return adaptation;
	}

	public static ComicsRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		ComicsRestBeanParams comicsRestBeanParams = new ComicsRestBeanParams();
		comicsRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		comicsRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		comicsRestBeanParams.setSort(pageSortBeanParams.getSort());
		return comicsRestBeanParams;
	}

}
