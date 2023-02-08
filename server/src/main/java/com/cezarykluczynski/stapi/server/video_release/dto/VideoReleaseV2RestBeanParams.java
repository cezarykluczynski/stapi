package com.cezarykluczynski.stapi.server.video_release.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import jakarta.ws.rs.FormParam;

public class VideoReleaseV2RestBeanParams extends PageSortBeanParams {

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

	@FormParam("documentary")
	private Boolean documentary;

	@FormParam("specialFeatures")
	private Boolean specialFeatures;

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

	public Boolean getDocumentary() {
		return documentary;
	}

	public Boolean getSpecialFeatures() {
		return specialFeatures;
	}

	public static VideoReleaseV2RestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		VideoReleaseV2RestBeanParams videoReleaseV2RestBeanParams = new VideoReleaseV2RestBeanParams();
		videoReleaseV2RestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		videoReleaseV2RestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		videoReleaseV2RestBeanParams.setSort(pageSortBeanParams.getSort());
		return videoReleaseV2RestBeanParams;
	}

}
