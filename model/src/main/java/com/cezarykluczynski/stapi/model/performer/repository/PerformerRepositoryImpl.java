package com.cezarykluczynski.stapi.model.performer.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.query.PerformerQueryBuiler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Repository
public class PerformerRepositoryImpl implements PerformerRepositoryCustom {

	private PerformerQueryBuiler performerQueryBuiler;

	@Inject
	public PerformerRepositoryImpl(PerformerQueryBuiler performerQueryBuiler) {
		this.performerQueryBuiler = performerQueryBuiler;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Performer> findMatching(PerformerRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Performer> performerQueryBuilder = performerQueryBuiler.createQueryBuilder(pageable);

		performerQueryBuilder.like("name", criteria.getName());
		performerQueryBuilder.like("birthName", criteria.getBirthName());
		performerQueryBuilder.like("placeOfBirth", criteria.getPlaceOfBirth());
		performerQueryBuilder.like("placeOfDeath", criteria.getPlaceOfDeath());
		performerQueryBuilder.between("dateOfBirth", criteria.getDateOfBirthFrom(),
				criteria.getDateOfBirthTo());
		performerQueryBuilder.between("dateOfDeath", criteria.getDateOfDeathFrom(),
				criteria.getDateOfDeathTo());
		performerQueryBuilder.equal("gender", criteria.getGender());
		performerQueryBuilder.equal("animalPerformer", criteria.getAnimalPerformer());
		performerQueryBuilder.equal("disPerformer", criteria.getDisPerformer());
		performerQueryBuilder.equal("ds9Performer", criteria.getDs9Performer());
		performerQueryBuilder.equal("entPerformer", criteria.getEntPerformer());
		performerQueryBuilder.equal("filmPerformer", criteria.getFilmPerformer());
		performerQueryBuilder.equal("standInPerformer", criteria.getStandInPerformer());
		performerQueryBuilder.equal("stuntPerformer", criteria.getStuntPerformer());
		performerQueryBuilder.equal("tasPerformer", criteria.getTasPerformer());
		performerQueryBuilder.equal("tngPerformer", criteria.getTngPerformer());
		performerQueryBuilder.equal("tosPerformer", criteria.getTosPerformer());
		performerQueryBuilder.equal("videoGamePerformer", criteria.getVideoGamePerformer());
		performerQueryBuilder.equal("voicePerformer", criteria.getVoicePerformer());
		performerQueryBuilder.equal("voyPerformer", criteria.getVoyPerformer());

		return performerQueryBuilder.findPage();
	}

}
