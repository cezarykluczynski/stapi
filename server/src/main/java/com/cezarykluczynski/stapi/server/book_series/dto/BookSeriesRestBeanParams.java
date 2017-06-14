package com.cezarykluczynski.stapi.server.book_series.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class BookSeriesRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("title")
	private String title;

	@FormParam("publishedYearFrom")
	private Integer publishedYearFrom;

	@FormParam("publishedYearTo")
	private Integer publishedYearTo;

	@FormParam("numberOfBooksFrom")
	private Integer numberOfBooksFrom;

	@FormParam("numberOfBooksTo")
	private Integer numberOfBooksTo;

	@FormParam("yearFrom")
	private Integer yearFrom;

	@FormParam("yearTo")
	private Integer yearTo;

	@FormParam("miniseries")
	private Boolean miniseries;

	@SuppressWarnings("MemberName")
	@FormParam("eBookSeries")
	private Boolean eBookSeries;

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

	public Integer getNumberOfBooksFrom() {
		return numberOfBooksFrom;
	}

	public Integer getNumberOfBooksTo() {
		return numberOfBooksTo;
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

	public Boolean getEBookSeries() {
		return eBookSeries;
	}

	public static BookSeriesRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		BookSeriesRestBeanParams bookSeriesRestBeanParams = new BookSeriesRestBeanParams();
		bookSeriesRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		bookSeriesRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		bookSeriesRestBeanParams.setSort(pageSortBeanParams.getSort());
		return bookSeriesRestBeanParams;
	}

}
