package com.cezarykluczynski.stapi.model.spacecraft.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.spacecraft.dto.SpacecraftRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft_;
import com.cezarykluczynski.stapi.model.spacecraft.query.SpacecraftQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class SpacecraftRepositoryImpl implements SpacecraftRepositoryCustom {

	private final SpacecraftQueryBuilderFactory spacecraftQueryBuilderFactory;

	public SpacecraftRepositoryImpl(SpacecraftQueryBuilderFactory spacecraftQueryBuilderFactory) {
		this.spacecraftQueryBuilderFactory = spacecraftQueryBuilderFactory;
	}

	@Override
	public Page<Spacecraft> findMatching(SpacecraftRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Spacecraft> spacecraftQueryBuilder = spacecraftQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		spacecraftQueryBuilder.equal(Spacecraft_.uid, uid);
		spacecraftQueryBuilder.like(Spacecraft_.name, criteria.getName());
		spacecraftQueryBuilder.setSort(criteria.getSort());
		spacecraftQueryBuilder.fetch(Spacecraft_.spacecraftClass);
		spacecraftQueryBuilder.fetch(Spacecraft_.spacecraftClass, SpacecraftClass_.species, doFetch);
		spacecraftQueryBuilder.fetch(Spacecraft_.spacecraftClass, SpacecraftClass_.owner, doFetch);
		spacecraftQueryBuilder.fetch(Spacecraft_.spacecraftClass, SpacecraftClass_.operator, doFetch);
		spacecraftQueryBuilder.fetch(Spacecraft_.spacecraftClass, SpacecraftClass_.affiliation, doFetch);
		spacecraftQueryBuilder.fetch(Spacecraft_.owner);
		spacecraftQueryBuilder.fetch(Spacecraft_.operator);
		spacecraftQueryBuilder.fetch(Spacecraft_.spacecraftTypes, doFetch);

		return spacecraftQueryBuilder.findPage();
	}

}
