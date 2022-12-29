package com.cezarykluczynski.stapi.server.performer.dto;

import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

import java.time.LocalDate;

public class PerformerV2RestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("birthName")
	private String birthName;

	@FormParam("gender")
	private Gender gender;

	@FormParam("dateOfBirthFrom")
	private LocalDate dateOfBirthFrom;

	@FormParam("dateOfBirthTo")
	private LocalDate dateOfBirthTo;

	@FormParam("dateOfDeathFrom")
	private LocalDate dateOfDeathFrom;

	@FormParam("dateOfDeathTo")
	private LocalDate dateOfDeathTo;

	@FormParam("placeOfBirth")
	private String placeOfBirth;

	@FormParam("placeOfDeath")
	private String placeOfDeath;

	@FormParam("animalPerformer")
	private Boolean animalPerformer;

	@FormParam("audiobookPerformer")
	private Boolean audiobookPerformer;

	@FormParam("cutPerformer")
	private Boolean cutPerformer;

	@FormParam("disPerformer")
	private Boolean disPerformer;

	@FormParam("ds9Performer")
	private Boolean ds9Performer;

	@FormParam("entPerformer")
	private Boolean entPerformer;

	@FormParam("filmPerformer")
	private Boolean filmPerformer;

	@FormParam("ldPerformer")
	private Boolean ldPerformer;

	@FormParam("picPerformer")
	private Boolean picPerformer;

	@FormParam("proPerformer")
	private Boolean proPerformer;

	@FormParam("puppeteer")
	private Boolean puppeteer;

	@FormParam("snwPerformer")
	private Boolean snwPerformer;

	@FormParam("standInPerformer")
	private Boolean standInPerformer;

	@FormParam("stPerformer")
	private Boolean stPerformer;

	@FormParam("stuntPerformer")
	private Boolean stuntPerformer;

	@FormParam("tasPerformer")
	private Boolean tasPerformer;

	@FormParam("tngPerformer")
	private Boolean tngPerformer;

	@FormParam("tosPerformer")
	private Boolean tosPerformer;

	@FormParam("videoGamePerformer")
	private Boolean videoGamePerformer;

	@FormParam("voicePerformer")
	private Boolean voicePerformer;

	@FormParam("voyPerformer")
	private Boolean voyPerformer;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public String getBirthName() {
		return birthName;
	}

	public Gender getGender() {
		return gender;
	}

	public LocalDate getDateOfBirthFrom() {
		return dateOfBirthFrom;
	}

	public LocalDate getDateOfBirthTo() {
		return dateOfBirthTo;
	}

	public LocalDate getDateOfDeathFrom() {
		return dateOfDeathFrom;
	}

	public LocalDate getDateOfDeathTo() {
		return dateOfDeathTo;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public String getPlaceOfDeath() {
		return placeOfDeath;
	}

	public Boolean getAnimalPerformer() {
		return animalPerformer;
	}

	public Boolean getAudiobookPerformer() {
		return audiobookPerformer;
	}

	public Boolean getCutPerformer() {
		return cutPerformer;
	}

	public Boolean getDisPerformer() {
		return disPerformer;
	}

	public Boolean getDs9Performer() {
		return ds9Performer;
	}

	public Boolean getEntPerformer() {
		return entPerformer;
	}

	public Boolean getLdPerformer() {
		return ldPerformer;
	}

	public Boolean getPicPerformer() {
		return picPerformer;
	}

	public Boolean getProPerformer() {
		return proPerformer;
	}

	public Boolean getPuppeteer() {
		return puppeteer;
	}

	public Boolean getSnwPerformer() {
		return snwPerformer;
	}

	public Boolean getFilmPerformer() {
		return filmPerformer;
	}

	public Boolean getStandInPerformer() {
		return standInPerformer;
	}

	public Boolean getStPerformer() {
		return stPerformer;
	}

	public Boolean getStuntPerformer() {
		return stuntPerformer;
	}

	public Boolean getTasPerformer() {
		return tasPerformer;
	}

	public Boolean getTngPerformer() {
		return tngPerformer;
	}

	public Boolean getTosPerformer() {
		return tosPerformer;
	}

	public Boolean getVideoGamePerformer() {
		return videoGamePerformer;
	}

	public Boolean getVoicePerformer() {
		return voicePerformer;
	}

	public Boolean getVoyPerformer() {
		return voyPerformer;
	}

	public static PerformerV2RestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		PerformerV2RestBeanParams performerV2RestBeanParams = new PerformerV2RestBeanParams();
		performerV2RestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		performerV2RestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		performerV2RestBeanParams.setSort(pageSortBeanParams.getSort());
		return performerV2RestBeanParams;
	}

}
