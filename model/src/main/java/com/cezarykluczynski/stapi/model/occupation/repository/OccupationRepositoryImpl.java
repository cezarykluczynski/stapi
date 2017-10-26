package com.cezarykluczynski.stapi.model.occupation.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation_;
import com.cezarykluczynski.stapi.model.occupation.query.OccupationQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class OccupationRepositoryImpl implements OccupationRepositoryCustom {

	private final OccupationQueryBuilderFactory occupationQueryBuilderFactory;

	public OccupationRepositoryImpl(OccupationQueryBuilderFactory occupationQueryBuilderFactory) {
		this.occupationQueryBuilderFactory = occupationQueryBuilderFactory;
	}

	@Override
	public Page<Occupation> findMatching(OccupationRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Occupation> occupationQueryBuilder = occupationQueryBuilderFactory.createQueryBuilder(pageable);

		occupationQueryBuilder.equal(Occupation_.uid, criteria.getUid());
		occupationQueryBuilder.like(Occupation_.name, criteria.getName());
		occupationQueryBuilder.equal(Occupation_.legalOccupation, criteria.getLegalOccupation());
		occupationQueryBuilder.equal(Occupation_.medicalOccupation, criteria.getMedicalOccupation());
		occupationQueryBuilder.equal(Occupation_.scientificOccupation, criteria.getScientificOccupation());
		occupationQueryBuilder.setSort(criteria.getSort());

		return occupationQueryBuilder.findPage();
	}

}
