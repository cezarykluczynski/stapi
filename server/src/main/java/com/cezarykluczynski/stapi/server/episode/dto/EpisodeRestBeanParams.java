package com.cezarykluczynski.stapi.server.episode.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;
import java.time.LocalDate;

public class EpisodeRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("title")
	private String title;

	@FormParam("seasonNumberFrom")
	private Integer seasonNumberFrom;

	@FormParam("seasonNumberTo")
	private Integer seasonNumberTo;

	@FormParam("episodeNumberFrom")
	private Integer episodeNumberFrom;

	@FormParam("episodeNumberTo")
	private Integer episodeNumberTo;

	@FormParam("productionSerialNumber")
	private String productionSerialNumber;

	@FormParam("featureLength")
	private Boolean featureLength;

	@FormParam("stardateFrom")
	private Float stardateFrom;

	@FormParam("stardateTo")
	private Float stardateTo;

	@FormParam("yearFrom")
	private Integer yearFrom;

	@FormParam("yearTo")
	private Integer yearTo;

	@FormParam("usAirDateFrom")
	private LocalDate usAirDateFrom;

	@FormParam("usAirDateTo")
	private LocalDate usAirDateTo;

	@FormParam("finalScriptDateFrom")
	private LocalDate finalScriptDateFrom;

	@FormParam("finalScriptDateTo")
	private LocalDate finalScriptDateTo;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public Integer getSeasonNumberFrom() {
		return seasonNumberFrom;
	}

	public Integer getSeasonNumberTo() {
		return seasonNumberTo;
	}

	public Integer getEpisodeNumberFrom() {
		return episodeNumberFrom;
	}

	public Integer getEpisodeNumberTo() {
		return episodeNumberTo;
	}

	public String getProductionSerialNumber() {
		return productionSerialNumber;
	}

	public Boolean getFeatureLength() {
		return featureLength;
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

	public LocalDate getUsAirDateFrom() {
		return usAirDateFrom;
	}

	public LocalDate getUsAirDateTo() {
		return usAirDateTo;
	}

	public LocalDate getFinalScriptDateFrom() {
		return finalScriptDateFrom;
	}

	public LocalDate getFinalScriptDateTo() {
		return finalScriptDateTo;
	}

	public static EpisodeRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		EpisodeRestBeanParams episodeRestBeanParams = new EpisodeRestBeanParams();
		episodeRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		episodeRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		episodeRestBeanParams.setSort(pageSortBeanParams.getSort());
		return episodeRestBeanParams;
	}

}
