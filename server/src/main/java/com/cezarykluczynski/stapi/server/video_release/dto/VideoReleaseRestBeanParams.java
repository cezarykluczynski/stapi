package com.cezarykluczynski.stapi.server.video_release.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class VideoReleaseRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("title")
	private String title;

	@FormParam("yearFrom")
	private Integer yearFrom;

	@FormParam("yearTo")
	private Integer yearTo;

	@FormParam("runTimeFrom")
	private Integer runTimeFrom;

	@FormParam("runTimeTo")
	private Integer runTimeTo;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public Integer getYearFrom() {
		return yearFrom;
	}

	public Integer getYearTo() {
		return yearTo;
	}

	public Integer getRunTimeFrom() {
		return runTimeFrom;
	}

	public Integer getRunTimeTo() {
		return runTimeTo;
	}

	public static VideoReleaseRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		VideoReleaseRestBeanParams videoReleaseRestBeanParams = new VideoReleaseRestBeanParams();
		videoReleaseRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		videoReleaseRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		videoReleaseRestBeanParams.setSort(pageSortBeanParams.getSort());
		return videoReleaseRestBeanParams;
	}

}
