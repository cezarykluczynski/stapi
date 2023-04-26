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
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		occupationQueryBuilder.equal(Occupation_.uid, uid);
		occupationQueryBuilder.like(Occupation_.name, criteria.getName());
		occupationQueryBuilder.equal(Occupation_.artsOccupation, criteria.getArtsOccupation());
		occupationQueryBuilder.equal(Occupation_.communicationOccupation, criteria.getCommunicationOccupation());
		occupationQueryBuilder.equal(Occupation_.economicOccupation, criteria.getEconomicOccupation());
		occupationQueryBuilder.equal(Occupation_.educationOccupation, criteria.getEducationOccupation());
		occupationQueryBuilder.equal(Occupation_.entertainmentOccupation, criteria.getEntertainmentOccupation());
		occupationQueryBuilder.equal(Occupation_.illegalOccupation, criteria.getIllegalOccupation());
		occupationQueryBuilder.equal(Occupation_.legalOccupation, criteria.getLegalOccupation());
		occupationQueryBuilder.equal(Occupation_.medicalOccupation, criteria.getMedicalOccupation());
		occupationQueryBuilder.equal(Occupation_.scientificOccupation, criteria.getScientificOccupation());
		occupationQueryBuilder.equal(Occupation_.sportsOccupation, criteria.getSportsOccupation());
		occupationQueryBuilder.equal(Occupation_.victualOccupation, criteria.getVictualOccupation());
		occupationQueryBuilder.setSort(criteria.getSort());
		occupationQueryBuilder.fetch(Occupation_.characters, doFetch);

		return occupationQueryBuilder.findPage();
	}

}
