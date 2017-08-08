package com.cezarykluczynski.stapi.server.video_game.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;
import java.time.LocalDate;

public class VideoGameRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("title")
	private String title;

	@FormParam("releaseDateFrom")
	private LocalDate releaseDateFrom;

	@FormParam("releaseDateTo")
	private LocalDate releaseDateTo;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public LocalDate getReleaseDateFrom() {
		return releaseDateFrom;
	}

	public LocalDate getReleaseDateTo() {
		return releaseDateTo;
	}

	public static VideoGameRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		VideoGameRestBeanParams videoGameRestBeanParams = new VideoGameRestBeanParams();
		videoGameRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		videoGameRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		videoGameRestBeanParams.setSort(pageSortBeanParams.getSort());
		return videoGameRestBeanParams;
	}

}
