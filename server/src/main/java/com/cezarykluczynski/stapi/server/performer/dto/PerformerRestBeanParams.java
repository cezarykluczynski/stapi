package com.cezarykluczynski.stapi.server.performer.dto;

import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;
import java.time.LocalDate;

public class PerformerRestBeanParams extends PageSortBeanParams {

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

	@FormParam("disPerformer")
	private Boolean disPerformer;

	@FormParam("ds9Performer")
	private Boolean ds9Performer;

	@FormParam("entPerformer")
	private Boolean entPerformer;

	@FormParam("filmPerformer")
	private Boolean filmPerformer;

	@FormParam("standInPerformer")
	private Boolean standInPerformer;

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

	public Boolean getDisPerformer() {
		return disPerformer;
	}

	public Boolean getDs9Performer() {
		return ds9Performer;
	}

	public Boolean getEntPerformer() {
		return entPerformer;
	}

	public Boolean getFilmPerformer() {
		return filmPerformer;
	}

	public Boolean getStandInPerformer() {
		return standInPerformer;
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

	public static PerformerRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		PerformerRestBeanParams performerRestBeanParams = new PerformerRestBeanParams();
		performerRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		performerRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		performerRestBeanParams.setSort(pageSortBeanParams.getSort());
		return performerRestBeanParams;
	}

}
