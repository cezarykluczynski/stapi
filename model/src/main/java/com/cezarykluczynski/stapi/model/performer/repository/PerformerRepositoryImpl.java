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
	public Page<Performer> findMatching(PerformerRequestDTO performerRequestDTO, Pageable pageable) {
		QueryBuilder<Performer> performerQueryBuilder = performerQueryBuiler.createQueryBuilder(pageable);

		performerQueryBuilder.like("name", performerRequestDTO.getName());
		performerQueryBuilder.like("birthName", performerRequestDTO.getBirthName());
		performerQueryBuilder.like("placeOfBirth", performerRequestDTO.getPlaceOfBirth());
		performerQueryBuilder.like("placeOfDeath", performerRequestDTO.getPlaceOfDeath());
		performerQueryBuilder.between("dateOfBirth", performerRequestDTO.getDateOfBirthFrom(),
				performerRequestDTO.getDateOfBirthTo());
		performerQueryBuilder.between("dateOfDeath", performerRequestDTO.getDateOfDeathFrom(),
				performerRequestDTO.getDateOfDeathTo());
		performerQueryBuilder.equal("gender", performerRequestDTO.getGender());
		performerQueryBuilder.equal("animalPerformer", performerRequestDTO.getAnimalPerformer());
		performerQueryBuilder.equal("disPerformer", performerRequestDTO.getDisPerformer());
		performerQueryBuilder.equal("ds9Performer", performerRequestDTO.getDs9Performer());
		performerQueryBuilder.equal("entPerformer", performerRequestDTO.getEntPerformer());
		performerQueryBuilder.equal("filmPerformer", performerRequestDTO.getFilmPerformer());
		performerQueryBuilder.equal("standInPerformer", performerRequestDTO.getStandInPerformer());
		performerQueryBuilder.equal("stuntPerformer", performerRequestDTO.getStuntPerformer());
		performerQueryBuilder.equal("tasPerformer", performerRequestDTO.getTasPerformer());
		performerQueryBuilder.equal("tngPerformer", performerRequestDTO.getTngPerformer());
		performerQueryBuilder.equal("tosPerformer", performerRequestDTO.getTosPerformer());
		performerQueryBuilder.equal("videoGamePerformer", performerRequestDTO.getVideoGamePerformer());
		performerQueryBuilder.equal("voicePerformer", performerRequestDTO.getVoicePerformer());
		performerQueryBuilder.equal("voyPerformer", performerRequestDTO.getVoyPerformer());

		return performerQueryBuilder.search();
	}

}
