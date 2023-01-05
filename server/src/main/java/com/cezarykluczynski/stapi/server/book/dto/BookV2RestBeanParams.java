package com.cezarykluczynski.stapi.server.book.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import jakarta.ws.rs.FormParam;

public class BookV2RestBeanParams extends PageSortBeanParams {

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

	@FormParam("novel")
	private Boolean novel;

	@FormParam("referenceBook")
	private Boolean referenceBook;

	@FormParam("biographyBook")
	private Boolean biographyBook;

	@FormParam("rolePlayingBook")
	private Boolean rolePlayingBook;

	@SuppressWarnings("MemberName")
	@FormParam("eBook")
	private Boolean eBook;

	@FormParam("anthology")
	private Boolean anthology;

	@FormParam("novelization")
	private Boolean novelization;

	@FormParam("unauthorizedPublication")
	private Boolean unauthorizedPublication;

	@FormParam("audiobook")
	private Boolean audiobook;

	@FormParam("audiobookAbridged")
	private Boolean audiobookAbridged;

	@FormParam("audiobookPublishedYearFrom")
	private Integer audiobookPublishedYearFrom;

	@FormParam("audiobookPublishedYearTo")
	private Integer audiobookPublishedYearTo;

	@FormParam("audiobookRunTimeFrom")
	private Integer audiobookRunTimeFrom;

	@FormParam("audiobookRunTimeTo")
	private Integer audiobookRunTimeTo;

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

	public Boolean getNovel() {
		return novel;
	}

	public Boolean getReferenceBook() {
		return referenceBook;
	}

	public Boolean getBiographyBook() {
		return biographyBook;
	}

	public Boolean getRolePlayingBook() {
		return rolePlayingBook;
	}

	public Boolean getEBook() {
		return eBook;
	}

	public Boolean getAnthology() {
		return anthology;
	}

	public Boolean getNovelization() {
		return novelization;
	}

	public Boolean getUnauthorizedPublication() {
		return unauthorizedPublication;
	}

	public Boolean getAudiobook() {
		return audiobook;
	}

	public Boolean getAudiobookAbridged() {
		return audiobookAbridged;
	}

	public Integer getAudiobookPublishedYearFrom() {
		return audiobookPublishedYearFrom;
	}

	public Integer getAudiobookPublishedYearTo() {
		return audiobookPublishedYearTo;
	}

	public Integer getAudiobookRunTimeFrom() {
		return audiobookRunTimeFrom;
	}

	public Integer getAudiobookRunTimeTo() {
		return audiobookRunTimeTo;
	}

	public static BookV2RestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		BookV2RestBeanParams bookV2RestBeanParams = new BookV2RestBeanParams();
		bookV2RestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		bookV2RestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		bookV2RestBeanParams.setSort(pageSortBeanParams.getSort());
		return bookV2RestBeanParams;
	}

}

