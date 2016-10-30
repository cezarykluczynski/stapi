package com.cezarykluczynski.stapi.model.performer.repository;

import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PerformerRepositoryImpl implements PerformerRepositoryCustom {

	private JpaContext jpaContext;

	@Autowired
	public PerformerRepositoryImpl(JpaContext jpaContext) {
		this.jpaContext = jpaContext;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Performer> findMatching(PerformerRequestDTO performerRequestDTO, Pageable pageable) {
		// TODO
		return null;
	}

}
