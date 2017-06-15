package com.cezarykluczynski.stapi.server.magazine.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class MagazineRestBeanParams extends PageSortBeanParams {

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

	public static MagazineRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		MagazineRestBeanParams magazineRestBeanParams = new MagazineRestBeanParams();
		magazineRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		magazineRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		magazineRestBeanParams.setSort(pageSortBeanParams.getSort());
		return magazineRestBeanParams;
	}

}
