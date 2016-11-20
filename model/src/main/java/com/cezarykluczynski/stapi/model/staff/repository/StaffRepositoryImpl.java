package com.cezarykluczynski.stapi.model.staff.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.staff.query.StaffQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Repository
public class StaffRepositoryImpl implements StaffRepositoryCustom {

	private StaffQueryBuilderFactory staffQueryBuilderFactory;

	@Inject
	public StaffRepositoryImpl(StaffQueryBuilderFactory staffQueryBuilderFactory) {
		this.staffQueryBuilderFactory = staffQueryBuilderFactory;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Staff> findMatching(StaffRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Staff> staffQueryBuilder = staffQueryBuilderFactory.createQueryBuilder(pageable);

		staffQueryBuilder.equal("guid", criteria.getGuid());
		staffQueryBuilder.like("name", criteria.getName());
		staffQueryBuilder.like("birthName", criteria.getBirthName());
		staffQueryBuilder.like("placeOfBirth", criteria.getPlaceOfBirth());
		staffQueryBuilder.like("placeOfDeath", criteria.getPlaceOfDeath());
		staffQueryBuilder.between("dateOfBirth", criteria.getDateOfBirthFrom(),
				criteria.getDateOfBirthTo());
		staffQueryBuilder.between("dateOfDeath", criteria.getDateOfDeathFrom(),
				criteria.getDateOfDeathTo());
		staffQueryBuilder.equal("gender", criteria.getGender());
		staffQueryBuilder.equal("artDepartment", criteria.getArtDepartment());
		staffQueryBuilder.equal("artDirector", criteria.getArtDirector());
		staffQueryBuilder.equal("productionDesigner", criteria.getProductionDesigner());
		staffQueryBuilder.equal("cameraAndElectricalDepartment", criteria.getCameraAndElectricalDepartment());
		staffQueryBuilder.equal("cinematographer", criteria.getCinematographer());
		staffQueryBuilder.equal("castingDepartment", criteria.getCastingDepartment());
		staffQueryBuilder.equal("costumeDepartment", criteria.getCostumeDepartment());
		staffQueryBuilder.equal("costumeDesigner", criteria.getCostumeDesigner());
		staffQueryBuilder.equal("director", criteria.getDirector());
		staffQueryBuilder.equal("assistantAndSecondUnitDirector", criteria.getAssistantAndSecondUnitDirector());
		staffQueryBuilder.equal("exhibitAndAttractionStaff", criteria.getExhibitAndAttractionStaff());
		staffQueryBuilder.equal("filmEditor", criteria.getFilmEditor());
		staffQueryBuilder.equal("linguist", criteria.getLinguist());
		staffQueryBuilder.equal("locationStaff", criteria.getLocationStaff());
		staffQueryBuilder.equal("makeupStaff", criteria.getMakeupStaff());
		staffQueryBuilder.equal("musicDepartment", criteria.getMusicDepartment());
		staffQueryBuilder.equal("composer", criteria.getComposer());
		staffQueryBuilder.equal("personalAssistant", criteria.getPersonalAssistant());
		staffQueryBuilder.equal("producer", criteria.getProducer());
		staffQueryBuilder.equal("productionAssociate", criteria.getProductionAssociate());
		staffQueryBuilder.equal("productionStaff", criteria.getProductionStaff());
		staffQueryBuilder.equal("publicationStaff", criteria.getPublicationStaff());
		staffQueryBuilder.equal("scienceConsultant", criteria.getScienceConsultant());
		staffQueryBuilder.equal("soundDepartment", criteria.getSoundDepartment());
		staffQueryBuilder.equal("specialAndVisualEffectsStaff", criteria.getSpecialAndVisualEffectsStaff());
		staffQueryBuilder.equal("author", criteria.getAuthor());
		staffQueryBuilder.equal("audioAuthor", criteria.getAudioAuthor());
		staffQueryBuilder.equal("calendarArtist", criteria.getCalendarArtist());
		staffQueryBuilder.equal("comicArtist", criteria.getComicArtist());
		staffQueryBuilder.equal("comicAuthor", criteria.getComicAuthor());
		staffQueryBuilder.equal("comicColorArtist", criteria.getComicColorArtist());
		staffQueryBuilder.equal("comicInteriorArtist", criteria.getComicInteriorArtist());
		staffQueryBuilder.equal("comicInkArtist", criteria.getComicInkArtist());
		staffQueryBuilder.equal("comicPencilArtist", criteria.getComicPencilArtist());
		staffQueryBuilder.equal("comicLetterArtist", criteria.getComicLetterArtist());
		staffQueryBuilder.equal("comicStripArtist", criteria.getComicStripArtist());
		staffQueryBuilder.equal("gameArtist", criteria.getGameArtist());
		staffQueryBuilder.equal("gameAuthor", criteria.getGameAuthor());
		staffQueryBuilder.equal("novelArtist", criteria.getNovelArtist());
		staffQueryBuilder.equal("novelAuthor", criteria.getNovelAuthor());
		staffQueryBuilder.equal("referenceArtist", criteria.getReferenceArtist());
		staffQueryBuilder.equal("referenceAuthor", criteria.getReferenceAuthor());
		staffQueryBuilder.equal("publicationArtist", criteria.getPublicationArtist());
		staffQueryBuilder.equal("publicationDesigner", criteria.getPublicationDesigner());
		staffQueryBuilder.equal("publicationEditor", criteria.getPublicationEditor());
		staffQueryBuilder.equal("publicityArtist", criteria.getPublicityArtist());
		staffQueryBuilder.equal("cbsDigitalStaff", criteria.getCbsDigitalStaff());
		staffQueryBuilder.equal("ilmProductionStaff", criteria.getIlmProductionStaff());
		staffQueryBuilder.equal("specialFeaturesStaff", criteria.getSpecialFeaturesStaff());
		staffQueryBuilder.equal("storyEditor", criteria.getStoryEditor());
		staffQueryBuilder.equal("studioExecutive", criteria.getStudioExecutive());
		staffQueryBuilder.equal("stuntDepartment", criteria.getStuntDepartment());
		staffQueryBuilder.equal("transportationDepartment", criteria.getTransportationDepartment());
		staffQueryBuilder.equal("videoGameProductionStaff", criteria.getVideoGameProductionStaff());
		staffQueryBuilder.equal("writer", criteria.getWriter());
		staffQueryBuilder.setOrder(criteria.getOrder());

		return staffQueryBuilder.findPage();
	}

}
