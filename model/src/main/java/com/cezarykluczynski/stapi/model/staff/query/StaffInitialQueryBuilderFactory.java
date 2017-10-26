package com.cezarykluczynski.stapi.model.staff.query;

import com.cezarykluczynski.stapi.model.common.query.InitialQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.staff.entity.Staff_;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StaffInitialQueryBuilderFactory implements InitialQueryBuilderFactory<StaffRequestDTO, Staff> {

	private final StaffQueryBuilderFactory staffQueryBuilderFactory;

	public StaffInitialQueryBuilderFactory(StaffQueryBuilderFactory staffQueryBuilderFactory) {
		this.staffQueryBuilderFactory = staffQueryBuilderFactory;
	}

	@Override
	public QueryBuilder<Staff> createInitialQueryBuilder(StaffRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Staff> staffQueryBuilder = staffQueryBuilderFactory.createQueryBuilder(pageable);

		staffQueryBuilder.equal(Staff_.uid, criteria.getUid());
		staffQueryBuilder.like(Staff_.name, criteria.getName());
		staffQueryBuilder.like(Staff_.birthName, criteria.getBirthName());
		staffQueryBuilder.like(Staff_.placeOfBirth, criteria.getPlaceOfBirth());
		staffQueryBuilder.like(Staff_.placeOfDeath, criteria.getPlaceOfDeath());
		staffQueryBuilder.between(Staff_.dateOfBirth, criteria.getDateOfBirthFrom(), criteria.getDateOfBirthTo());
		staffQueryBuilder.between(Staff_.dateOfDeath, criteria.getDateOfDeathFrom(), criteria.getDateOfDeathTo());
		staffQueryBuilder.equal(Staff_.gender, criteria.getGender());
		staffQueryBuilder.equal(Staff_.artDepartment, criteria.getArtDepartment());
		staffQueryBuilder.equal(Staff_.artDirector, criteria.getArtDirector());
		staffQueryBuilder.equal(Staff_.productionDesigner, criteria.getProductionDesigner());
		staffQueryBuilder.equal(Staff_.cameraAndElectricalDepartment, criteria.getCameraAndElectricalDepartment());
		staffQueryBuilder.equal(Staff_.cinematographer, criteria.getCinematographer());
		staffQueryBuilder.equal(Staff_.castingDepartment, criteria.getCastingDepartment());
		staffQueryBuilder.equal(Staff_.costumeDepartment, criteria.getCostumeDepartment());
		staffQueryBuilder.equal(Staff_.costumeDesigner, criteria.getCostumeDesigner());
		staffQueryBuilder.equal(Staff_.director, criteria.getDirector());
		staffQueryBuilder.equal(Staff_.assistantOrSecondUnitDirector, criteria.getAssistantOrSecondUnitDirector());
		staffQueryBuilder.equal(Staff_.exhibitAndAttractionStaff, criteria.getExhibitAndAttractionStaff());
		staffQueryBuilder.equal(Staff_.filmEditor, criteria.getFilmEditor());
		staffQueryBuilder.equal(Staff_.linguist, criteria.getLinguist());
		staffQueryBuilder.equal(Staff_.locationStaff, criteria.getLocationStaff());
		staffQueryBuilder.equal(Staff_.makeupStaff, criteria.getMakeupStaff());
		staffQueryBuilder.equal(Staff_.musicDepartment, criteria.getMusicDepartment());
		staffQueryBuilder.equal(Staff_.composer, criteria.getComposer());
		staffQueryBuilder.equal(Staff_.personalAssistant, criteria.getPersonalAssistant());
		staffQueryBuilder.equal(Staff_.producer, criteria.getProducer());
		staffQueryBuilder.equal(Staff_.productionAssociate, criteria.getProductionAssociate());
		staffQueryBuilder.equal(Staff_.productionStaff, criteria.getProductionStaff());
		staffQueryBuilder.equal(Staff_.publicationStaff, criteria.getPublicationStaff());
		staffQueryBuilder.equal(Staff_.scienceConsultant, criteria.getScienceConsultant());
		staffQueryBuilder.equal(Staff_.soundDepartment, criteria.getSoundDepartment());
		staffQueryBuilder.equal(Staff_.specialAndVisualEffectsStaff, criteria.getSpecialAndVisualEffectsStaff());
		staffQueryBuilder.equal(Staff_.author, criteria.getAuthor());
		staffQueryBuilder.equal(Staff_.audioAuthor, criteria.getAudioAuthor());
		staffQueryBuilder.equal(Staff_.calendarArtist, criteria.getCalendarArtist());
		staffQueryBuilder.equal(Staff_.comicArtist, criteria.getComicArtist());
		staffQueryBuilder.equal(Staff_.comicAuthor, criteria.getComicAuthor());
		staffQueryBuilder.equal(Staff_.comicColorArtist, criteria.getComicColorArtist());
		staffQueryBuilder.equal(Staff_.comicInteriorArtist, criteria.getComicInteriorArtist());
		staffQueryBuilder.equal(Staff_.comicInkArtist, criteria.getComicInkArtist());
		staffQueryBuilder.equal(Staff_.comicPencilArtist, criteria.getComicPencilArtist());
		staffQueryBuilder.equal(Staff_.comicLetterArtist, criteria.getComicLetterArtist());
		staffQueryBuilder.equal(Staff_.comicStripArtist, criteria.getComicStripArtist());
		staffQueryBuilder.equal(Staff_.gameArtist, criteria.getGameArtist());
		staffQueryBuilder.equal(Staff_.gameAuthor, criteria.getGameAuthor());
		staffQueryBuilder.equal(Staff_.novelArtist, criteria.getNovelArtist());
		staffQueryBuilder.equal(Staff_.novelAuthor, criteria.getNovelAuthor());
		staffQueryBuilder.equal(Staff_.referenceArtist, criteria.getReferenceArtist());
		staffQueryBuilder.equal(Staff_.referenceAuthor, criteria.getReferenceAuthor());
		staffQueryBuilder.equal(Staff_.publicationArtist, criteria.getPublicationArtist());
		staffQueryBuilder.equal(Staff_.publicationDesigner, criteria.getPublicationDesigner());
		staffQueryBuilder.equal(Staff_.publicationEditor, criteria.getPublicationEditor());
		staffQueryBuilder.equal(Staff_.publicityArtist, criteria.getPublicityArtist());
		staffQueryBuilder.equal(Staff_.cbsDigitalStaff, criteria.getCbsDigitalStaff());
		staffQueryBuilder.equal(Staff_.ilmProductionStaff, criteria.getIlmProductionStaff());
		staffQueryBuilder.equal(Staff_.specialFeaturesStaff, criteria.getSpecialFeaturesStaff());
		staffQueryBuilder.equal(Staff_.storyEditor, criteria.getStoryEditor());
		staffQueryBuilder.equal(Staff_.studioExecutive, criteria.getStudioExecutive());
		staffQueryBuilder.equal(Staff_.stuntDepartment, criteria.getStuntDepartment());
		staffQueryBuilder.equal(Staff_.transportationDepartment, criteria.getTransportationDepartment());
		staffQueryBuilder.equal(Staff_.videoGameProductionStaff, criteria.getVideoGameProductionStaff());
		staffQueryBuilder.equal(Staff_.writer, criteria.getWriter());
		staffQueryBuilder.setSort(criteria.getSort());

		return staffQueryBuilder;
	}
}
