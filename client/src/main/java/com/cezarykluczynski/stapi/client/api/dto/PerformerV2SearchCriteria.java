package com.cezarykluczynski.stapi.client.api.dto;

import com.cezarykluczynski.stapi.client.rest.model.Gender;

import java.time.LocalDate;

public class PerformerV2SearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private String birthName;

	private Gender gender;

	private LocalDate dateOfBirthFrom;

	private LocalDate dateOfBirthTo;

	private LocalDate dateOfDeathFrom;

	private LocalDate dateOfDeathTo;

	private String placeOfBirth;

	private String placeOfDeath;

	private Boolean animalPerformer;

	private Boolean audiobookPerformer;

	private Boolean cutPerformer;

	private Boolean disPerformer;

	private Boolean ds9Performer;

	private Boolean entPerformer;

	private Boolean filmPerformer;

	private Boolean ldPerformer;

	private Boolean picPerformer;

	private Boolean proPerformer;

	private Boolean puppeteer;

	private Boolean snwPerformer;

	private Boolean standInPerformer;

	private Boolean stPerformer;

	private Boolean stuntPerformer;

	private Boolean tasPerformer;

	private Boolean tngPerformer;

	private Boolean tosPerformer;

	private Boolean videoGamePerformer;

	private Boolean voicePerformer;

	private Boolean voyPerformer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthName() {
		return birthName;
	}

	public void setBirthName(String birthName) {
		this.birthName = birthName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirthFrom() {
		return dateOfBirthFrom;
	}

	public void setDateOfBirthFrom(LocalDate dateOfBirthFrom) {
		this.dateOfBirthFrom = dateOfBirthFrom;
	}

	public LocalDate getDateOfBirthTo() {
		return dateOfBirthTo;
	}

	public void setDateOfBirthTo(LocalDate dateOfBirthTo) {
		this.dateOfBirthTo = dateOfBirthTo;
	}

	public LocalDate getDateOfDeathFrom() {
		return dateOfDeathFrom;
	}

	public void setDateOfDeathFrom(LocalDate dateOfDeathFrom) {
		this.dateOfDeathFrom = dateOfDeathFrom;
	}

	public LocalDate getDateOfDeathTo() {
		return dateOfDeathTo;
	}

	public void setDateOfDeathTo(LocalDate dateOfDeathTo) {
		this.dateOfDeathTo = dateOfDeathTo;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getPlaceOfDeath() {
		return placeOfDeath;
	}

	public void setPlaceOfDeath(String placeOfDeath) {
		this.placeOfDeath = placeOfDeath;
	}

	public Boolean getAnimalPerformer() {
		return animalPerformer;
	}

	public void setAnimalPerformer(Boolean animalPerformer) {
		this.animalPerformer = animalPerformer;
	}

	public Boolean getAudiobookPerformer() {
		return audiobookPerformer;
	}

	public void setAudiobookPerformer(Boolean audiobookPerformer) {
		this.audiobookPerformer = audiobookPerformer;
	}

	public Boolean getCutPerformer() {
		return cutPerformer;
	}

	public void setCutPerformer(Boolean cutPerformer) {
		this.cutPerformer = cutPerformer;
	}

	public Boolean getDisPerformer() {
		return disPerformer;
	}

	public void setDisPerformer(Boolean disPerformer) {
		this.disPerformer = disPerformer;
	}

	public Boolean getDs9Performer() {
		return ds9Performer;
	}

	public void setDs9Performer(Boolean ds9Performer) {
		this.ds9Performer = ds9Performer;
	}

	public Boolean getEntPerformer() {
		return entPerformer;
	}

	public void setEntPerformer(Boolean entPerformer) {
		this.entPerformer = entPerformer;
	}

	public Boolean getFilmPerformer() {
		return filmPerformer;
	}

	public void setFilmPerformer(Boolean filmPerformer) {
		this.filmPerformer = filmPerformer;
	}

	public Boolean getLdPerformer() {
		return ldPerformer;
	}

	public void setLdPerformer(Boolean ldPerformer) {
		this.ldPerformer = ldPerformer;
	}

	public Boolean getPicPerformer() {
		return picPerformer;
	}

	public void setPicPerformer(Boolean picPerformer) {
		this.picPerformer = picPerformer;
	}

	public Boolean getProPerformer() {
		return proPerformer;
	}

	public void setProPerformer(Boolean proPerformer) {
		this.proPerformer = proPerformer;
	}

	public Boolean getPuppeteer() {
		return puppeteer;
	}

	public void setPuppeteer(Boolean puppeteer) {
		this.puppeteer = puppeteer;
	}

	public Boolean getSnwPerformer() {
		return snwPerformer;
	}

	public void setSnwPerformer(Boolean snwPerformer) {
		this.snwPerformer = snwPerformer;
	}

	public Boolean getStandInPerformer() {
		return standInPerformer;
	}

	public void setStandInPerformer(Boolean standInPerformer) {
		this.standInPerformer = standInPerformer;
	}

	public Boolean getStPerformer() {
		return stPerformer;
	}

	public void setStPerformer(Boolean stPerformer) {
		this.stPerformer = stPerformer;
	}

	public Boolean getStuntPerformer() {
		return stuntPerformer;
	}

	public void setStuntPerformer(Boolean stuntPerformer) {
		this.stuntPerformer = stuntPerformer;
	}

	public Boolean getTasPerformer() {
		return tasPerformer;
	}

	public void setTasPerformer(Boolean tasPerformer) {
		this.tasPerformer = tasPerformer;
	}

	public Boolean getTngPerformer() {
		return tngPerformer;
	}

	public void setTngPerformer(Boolean tngPerformer) {
		this.tngPerformer = tngPerformer;
	}

	public Boolean getTosPerformer() {
		return tosPerformer;
	}

	public void setTosPerformer(Boolean tosPerformer) {
		this.tosPerformer = tosPerformer;
	}

	public Boolean getVideoGamePerformer() {
		return videoGamePerformer;
	}

	public void setVideoGamePerformer(Boolean videoGamePerformer) {
		this.videoGamePerformer = videoGamePerformer;
	}

	public Boolean getVoicePerformer() {
		return voicePerformer;
	}

	public void setVoicePerformer(Boolean voicePerformer) {
		this.voicePerformer = voicePerformer;
	}

	public Boolean getVoyPerformer() {
		return voyPerformer;
	}

	public void setVoyPerformer(Boolean voyPerformer) {
		this.voyPerformer = voyPerformer;
	}

}
