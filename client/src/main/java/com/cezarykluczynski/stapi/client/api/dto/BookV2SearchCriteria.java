package com.cezarykluczynski.stapi.client.api.dto;

public class BookV2SearchCriteria extends AbstractPageSortBaseCriteria {

	private String title;

	private Integer publishedYearFrom;

	private Integer publishedYearTo;

	private Integer numberOfPagesFrom;

	private Integer numberOfPagesTo;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean novel;

	private Boolean referenceBook;

	private Boolean biographyBook;

	private Boolean rolePlayingBook;

	@SuppressWarnings("MemberName")
	private Boolean eBook;

	private Boolean anthology;

	private Boolean novelization;

	private Boolean unauthorizedPublication;

	private Boolean audiobook;

	private Boolean audiobookAbridged;

	private Integer audiobookPublishedYearFrom;

	private Integer audiobookPublishedYearTo;

	private Integer audiobookRunTimeFrom;

	private Integer audiobookRunTimeTo;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPublishedYearFrom() {
		return publishedYearFrom;
	}

	public void setPublishedYearFrom(Integer publishedYearFrom) {
		this.publishedYearFrom = publishedYearFrom;
	}

	public Integer getPublishedYearTo() {
		return publishedYearTo;
	}

	public void setPublishedYearTo(Integer publishedYearTo) {
		this.publishedYearTo = publishedYearTo;
	}

	public Integer getNumberOfPagesFrom() {
		return numberOfPagesFrom;
	}

	public void setNumberOfPagesFrom(Integer numberOfPagesFrom) {
		this.numberOfPagesFrom = numberOfPagesFrom;
	}

	public Integer getNumberOfPagesTo() {
		return numberOfPagesTo;
	}

	public void setNumberOfPagesTo(Integer numberOfPagesTo) {
		this.numberOfPagesTo = numberOfPagesTo;
	}

	public Float getStardateFrom() {
		return stardateFrom;
	}

	public void setStardateFrom(Float stardateFrom) {
		this.stardateFrom = stardateFrom;
	}

	public Float getStardateTo() {
		return stardateTo;
	}

	public void setStardateTo(Float stardateTo) {
		this.stardateTo = stardateTo;
	}

	public Integer getYearFrom() {
		return yearFrom;
	}

	public void setYearFrom(Integer yearFrom) {
		this.yearFrom = yearFrom;
	}

	public Integer getYearTo() {
		return yearTo;
	}

	public void setYearTo(Integer yearTo) {
		this.yearTo = yearTo;
	}

	public Boolean getNovel() {
		return novel;
	}

	public void setNovel(Boolean novel) {
		this.novel = novel;
	}

	public Boolean getReferenceBook() {
		return referenceBook;
	}

	public void setReferenceBook(Boolean referenceBook) {
		this.referenceBook = referenceBook;
	}

	public Boolean getBiographyBook() {
		return biographyBook;
	}

	public void setBiographyBook(Boolean biographyBook) {
		this.biographyBook = biographyBook;
	}

	public Boolean getRolePlayingBook() {
		return rolePlayingBook;
	}

	public void setRolePlayingBook(Boolean rolePlayingBook) {
		this.rolePlayingBook = rolePlayingBook;
	}

	public Boolean getEBook() {
		return eBook;
	}

	public void setEBook(@SuppressWarnings({"ParameterName", "HiddenField"}) Boolean eBook) {
		this.eBook = eBook;
	}

	public Boolean getAnthology() {
		return anthology;
	}

	public void setAnthology(Boolean anthology) {
		this.anthology = anthology;
	}

	public Boolean getNovelization() {
		return novelization;
	}

	public void setNovelization(Boolean novelization) {
		this.novelization = novelization;
	}

	public Boolean getUnauthorizedPublication() {
		return unauthorizedPublication;
	}

	public void setUnauthorizedPublication(Boolean unauthorizedPublication) {
		this.unauthorizedPublication = unauthorizedPublication;
	}

	public Boolean getAudiobook() {
		return audiobook;
	}

	public void setAudiobook(Boolean audiobook) {
		this.audiobook = audiobook;
	}

	public Boolean getAudiobookAbridged() {
		return audiobookAbridged;
	}

	public void setAudiobookAbridged(Boolean audiobookAbridged) {
		this.audiobookAbridged = audiobookAbridged;
	}

	public Integer getAudiobookPublishedYearFrom() {
		return audiobookPublishedYearFrom;
	}

	public void setAudiobookPublishedYearFrom(Integer audiobookPublishedYearFrom) {
		this.audiobookPublishedYearFrom = audiobookPublishedYearFrom;
	}

	public Integer getAudiobookPublishedYearTo() {
		return audiobookPublishedYearTo;
	}

	public void setAudiobookPublishedYearTo(Integer audiobookPublishedYearTo) {
		this.audiobookPublishedYearTo = audiobookPublishedYearTo;
	}

	public Integer getAudiobookRunTimeFrom() {
		return audiobookRunTimeFrom;
	}

	public void setAudiobookRunTimeFrom(Integer audiobookRunTimeFrom) {
		this.audiobookRunTimeFrom = audiobookRunTimeFrom;
	}

	public Integer getAudiobookRunTimeTo() {
		return audiobookRunTimeTo;
	}

	public void setAudiobookRunTimeTo(Integer audiobookRunTimeTo) {
		this.audiobookRunTimeTo = audiobookRunTimeTo;
	}

}
