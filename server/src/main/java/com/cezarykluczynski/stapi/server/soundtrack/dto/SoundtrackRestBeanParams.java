package com.cezarykluczynski.stapi.server.soundtrack.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;
import java.time.LocalDate;

public class SoundtrackRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("title")
	private String title;

	@FormParam("releaseDateFrom")
	private LocalDate releaseDateFrom;

	@FormParam("releaseDateTo")
	private LocalDate releaseDateTo;

	@FormParam("lengthFrom")
	private Integer lengthFrom;

	@FormParam("lengthTo")
	private Integer lengthTo;

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

	public Integer getLengthFrom() {
		return lengthFrom;
	}

	public Integer getLengthTo() {
		return lengthTo;
	}

	public static SoundtrackRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		SoundtrackRestBeanParams soundtrackRestBeanParams = new SoundtrackRestBeanParams();
		soundtrackRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		soundtrackRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		soundtrackRestBeanParams.setSort(pageSortBeanParams.getSort());
		return soundtrackRestBeanParams;
	}

}
