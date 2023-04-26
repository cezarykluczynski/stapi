package com.cezarykluczynski.stapi.model.spacecraft_class.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass_;
import com.cezarykluczynski.stapi.model.spacecraft_class.query.SpacecraftClassQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class SpacecraftClassRepositoryImpl implements SpacecraftClassRepositoryCustom {

	private final SpacecraftClassQueryBuilderFactory spacecraftClassQueryBuilderFactory;

	public SpacecraftClassRepositoryImpl(SpacecraftClassQueryBuilderFactory spacecraftClassQueryBuilderFactory) {
		this.spacecraftClassQueryBuilderFactory = spacecraftClassQueryBuilderFactory;
	}

	@Override
	public Page<SpacecraftClass> findMatching(SpacecraftClassRequestDTO criteria, Pageable pageable) {
		QueryBuilder<SpacecraftClass> spacecraftClassQueryBuilder = spacecraftClassQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		spacecraftClassQueryBuilder.equal(SpacecraftClass_.uid, uid);
		spacecraftClassQueryBuilder.like(SpacecraftClass_.name, criteria.getName());
		spacecraftClassQueryBuilder.equal(SpacecraftClass_.warpCapable, criteria.getWarpCapable());
		spacecraftClassQueryBuilder.equal(SpacecraftClass_.mirror, criteria.getMirror());
		spacecraftClassQueryBuilder.equal(SpacecraftClass_.alternateReality, criteria.getAlternateReality());
		spacecraftClassQueryBuilder.setSort(criteria.getSort());
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.species);
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.owners, doFetch);
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.operators, doFetch);
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.affiliations, doFetch);
		spacecraftClassQueryBuilder.divideQueries();
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.spacecraftTypes, doFetch);
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.armaments, doFetch);
		spacecraftClassQueryBuilder.divideQueries();
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.defenses, doFetch);
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.spacecrafts, doFetch);

		return spacecraftClassQueryBuilder.findPage();
	}

}
