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

		return performerQueryBuilder.search();
	}

}
